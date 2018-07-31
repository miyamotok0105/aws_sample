
# 公式

APIリファレンス    
https://docs.aws.amazon.com/cli/latest/reference/rekognition/index.html#cli-aws-rekognition

# cliのサンプル    

```
aws rekognition detect-labels \
--image '{"S3Object":{"Bucket":"bucketname","Name":"object.jpg"}}' \
--region us-east-1 \
--profile adminuser
```


## コレクションの作成

```
aws rekognition create-collection \
    --collection-id "collectionname"
```

https://docs.aws.amazon.com/ja_jp/rekognition/latest/dg/create-collection-procedure.html

## コレクションへの顔の追加

顔画像を登録

```
aws rekognition index-faces \
      --image '{"S3Object":{"Bucket":"bucket-name","Name":"file-name"}}' \
      --collection-id "collection-id" \
      --detection-attributes "ALL" \
      --external-image-id "example-image.jpg" 
```

https://docs.aws.amazon.com/ja_jp/rekognition/latest/dg/add-faces-to-collection-procedure.html


# java hello world

```
javac Sample.java
java Sample
```



```
git clone https://github.com/aws/aws-sdk-java.git

```
