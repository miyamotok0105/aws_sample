


# AWS Cliのインストール

https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/installing.html

```
aws --version
pip install awscli --upgrade --user
```

# AWS Cliの設定

https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/cli-chap-getting-started.html


```
$ cat ~/.aws/config
[default]
output = json
region = ap-northeast-1
[user2]
output = json
region = ap-northeast-1

$ cat ~/.aws/credentials 
[default]
aws_access_key_id = xxxxxxxxxx
aws_secret_access_key = xxxxxxxxxx
[user2]
aws_access_key_id = xxxxxxxxxx
aws_secret_access_key = xxxxxxxxxx
region = ap-northeast-1
```



```
aws s3 ls

aws s3 ls --profile user2
```

# 画像をs3にアップロード

```
aws s3 cp ./mesoko.png s3://test-bucket/
```

# aws kinesis

### kinesis datastreamsのストリームを作成

```
#ストリーム作成
aws kinesis create-stream --stream-name sample1 --shard-count 1 --region ap-northeast-1
#一覧
aws kinesis list-streams --region ap-northeast-1
```

```
python put-records.py
```

### amazon snsでトピックを作成

```py:
import boto3
import datetime
import time
import uuid

kinesis = boto3.client('kinesis', region_name='ap-northeast-1')
stream_name = 'sample1'

partition_key = str(uuid.uuid4())
data = datetime.datetime.utcnow().strftime('%s')

for i in range(15):
    kinesis.put_record(
        StreamName=stream_name,
        Data=data,
        PartitionKey=partition_key,
    )

```


```
aws sns create-topic --name sample1 --region ap-northeast-1
```

### amazon cloudWatchのアラームを設定


arnはsns登録した時のarnを使用


```
aws cloudwatch put-metric-alarm --alarm-name kinesis-mon --metric-name IncomingRecords --namespace AWS/Kinesis --statistic Sum --period 60 --threshold 10 --comparison-operator GreaterThanThreshold --dimensions Name=StreamName,Value=sample --evaluation-periods 1 --alarm-actions arn:aws:sns:hogehogehogehogehogehogehoge:sample1 --region ap-northeast-1
```

set-alarm-stateコマンドでアラーム状態を変更してアラームをテスト


```
aws cloudwatch set-alarm-state --alarm-name kinesis-mon --state-reason 'initializing' --state-value ALARM --region ap-northeast-1
```


### Lambda関数の作成


```
mkdir resharding-function
cp 3-1/resharding-function.py resharding-function
cp 3-1/trustpolicy.json resharding-function
cp 3-1/permission.json resharding-function
cd resharding-function
pip install -t ./ boto3
```

作成したファイルを利用してIAMロールを作成。
同じフォルダ内で実行。


```
aws iam create-role --role-name resharding_function_role --assume-role-policy-document file://trustpolicy.json
```

IAMロールに割り当てる。ポリシー名はbasic-permissionにしておく。


```
aws iam put-role-policy --role-name resharding_function_role --policy-name basic-permission --policy-document file://permission.json
```

resharding-functionフォルダ内でzip作成

```
zip -r9 resharding-function.zip *
```

arnは先ほどのを使用


```
aws lambda create-function --function-name resharding-function --zip-file fileb://resharding-function.zip --role arn:aws:iam::hogehogehogehoge:role/resharding_function_role --handler resharding-function.lambda_handler --runtime python3.6 --region ap-northeast-1
```

権限を

```
aws lambda add-permission --function-name resharding-function --statement-id 1 --action "lambda:InvokeFunction" --principal sns.amazonaws.com --source-arn arn:aws:iam::hogehogehogehoge:role/resharding_function_role --region ap-northeast-1
```

もし間違って登録した場合は削除してaddし直す

```
aws lambda remove-permission --function-name resharding-function --statement-id 1 --region ap-northeast-1
```

一覧をチェック

```
aws sns list-topics --region ap-northeast-1
```


```
aws sns subscribe --topic-arn トピックのarn --protocol lambda --notification-endpoint arn:aws:lambda:ap-northeast-1:hogehoge:function:resharding-function --region ap-northeast-1
```




```
python put-records.py
aws kinesis describe-stream-summary --stream-name sample1 --region ap-northeast-1
```


# aws cliでrekognition

aws cliにはいっぱいオプションがある。    
Available Servicesを参照    
https://docs.aws.amazon.com/cli/latest/reference/index.html    

## Object Detect

物体検出    

```
aws --region us-east-1 rekognition detect-labels \
--image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_normal.png"}}' \
--region RegionName
```

こちらの記事が使えてDetect faces, Compare-facesも使える    
https://dev.classmethod.jp/cloud/aws/amazon-rekognition-using-aws-cli/
    

## Detect faces

顔検出    

```
aws --region us-east-1 rekognition detect-faces \
--image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_aug.png"}}' \
--attributes "ALL"
```

## Compare-faces

同じ顔かどうか顔の比較    

```
aws --region us-east-1 rekognition compare-faces \
--source-image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_nov.png"}}' \
--target-image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_dec.png"}}'
```

# aws cli のVideo系

## start-person-tracking

https://docs.aws.amazon.com/cli/latest/reference/rekognition/start-face-detection.html    
job idが返ってくる    

```
aws rekognition start-person-tracking \
--video '{"S3Object":{"Bucket":"test-bucket","Name":"hoge.mp4"}}' \
--region RegionName
```

## get-person-tracking

start person trackingで出たjob idを入れる。    
IN_PROGRESSになってる間は処理中。    

```
{
    "JobStatus": "IN_PROGRESS",
    "Persons": []
}
```



```
aws rekognition get-person-tracking --job-id "hoge" --region RegionName
```

終わってたらjsonで返ってくる    

```
{
    "JobStatus": "SUCCEEDED",
    "VideoMetadata": {
        "Codec": "h264",
        "DurationMillis": 37111,
        "Format": "QuickTime / MOV",
        "FrameRate": 29.970369338989258,
        "FrameHeight": 720,
        "FrameWidth": 1280
    },
    "NextToken": "hoge",
    "Persons": [
        {
            "Timestamp": 7,
            "Person": {
                "Index": 0,
                "Face": {
                    "BoundingBox": {
...
```



<hr>

## get-face-detection

https://docs.aws.amazon.com/cli/latest/reference/rekognition/get-face-detection.html    


```
aws --region us-east-1 rekognition get-face-detection \
--job-id "" \
--region RegionName
```


# aws公式ドキュメントのソース
    
https://github.com/awsdocs/aws-doc-sdk-examples

# ruby

ブログなどで紹介されてないsdkについては公式ドキュメントとソースを見よう。    
ソースは結構詳しく書いている。    

公式ドキュメント    
https://docs.aws.amazon.com/sdk-for-ruby/v3/api/Aws/Rekognition/Client.htm    

ソース    
https://github.com/aws/aws-sdk-ruby/blob/caa6ad17c46c357d6bbf0d8c0f442789ecd9e5c4/gems/aws-sdk-rekognition/lib/aws-sdk-rekognition/client.rb    

公式サンプル    
https://github.com/shaicoleman/aws-ruby-examples    

# python

https://github.com/garnaat/paws

# java

https://github.com/aws-samples/aws-java-sample


# APIの使用

https://docs.aws.amazon.com/ja_jp/rekognition/latest/dg/get-started-exercise.html    

# aws video

https://aws.amazon.com/jp/search/?searchQuery=video#facet_type=blogs&page=2&sortResults=modification_date%20desc




