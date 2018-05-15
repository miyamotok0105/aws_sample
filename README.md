


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

# Object Detect

物体検出    

```
aws --region us-east-1 rekognition detect-labels \
--image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_normal.png"}}' \
--region RegionName
```

こちらの記事が使えてDetect faces, Compare-facesも使える    
https://dev.classmethod.jp/cloud/aws/amazon-rekognition-using-aws-cli/
    

# Detect faces

顔検出    

```
aws --region us-east-1 rekognition detect-faces \
--image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_aug.png"}}' \
--attributes "ALL"
```

# Compare-faces

同じ顔かどうか顔の比較    

```
aws --region us-east-1 rekognition compare-faces \
--source-image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_nov.png"}}' \
--target-image '{"S3Object":{"Bucket":"test-bucket","Name":"mesoko_dec.png"}}'
```

# APIの使用

https://docs.aws.amazon.com/ja_jp/rekognition/latest/dg/get-started-exercise.html    




