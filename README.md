


# AWS Cliのインストール

https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/installing.html

```
aws --version
pip install awscli --upgrade --user
```

# AWS Cliの設定

https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/cli-chap-getting-started.html


```
aws s3 ls
```

# 画像をs3にアップロード

```
aws s3 cp ./mesoko.png s3://test-bucket/
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




