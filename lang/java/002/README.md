
AWS SDK for Java を使用したオブジェクトの取得
https://docs.aws.amazon.com/ja_jp/AmazonS3/latest/dev/RetrievingObjectUsingJava.html

## SDKを落とす

https://sdk-for-java.amazonwebservices.com/latest/aws-java-sdk.zip から SDKを落とす

aws-java-sdk-1.11.381.zipを解凍

## ライブラリ追加

- libの中のjarファイルをインテリジェーで読み込む。

Project StructureからLibraiesを選択。
aws-java-sdk-1.11.381/lib/aws-java-sdk-1.11.381.jar

- third-partyの中のlibの中のjarを全部読み込む。

これでimport com.amazonaws.services.s3.model.S3Object;などのインポートが通るようになるはず。


あとはファイル作って動かせばよし。

## S3のファイル取得



```java:Main.java
package jp.sample;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("start!!!!!!");

        final GetObject getobject = new GetObject();
        getobject.main();
        System.out.println("end!!!!!!");


    }


}
```


実行前にリージェン、バケット名、ファイル名を変数にもたせておくこと。


```java:GetObject.java
package jp.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;

public class GetObject {

    public static void main() throws IOException {

        System.out.println("main->");

        //ここを編集すること！！
        String clientRegion = "*** Client region ***";
        String bucketName = "*** Bucket name ***";
        String key = "*** Object key ***";

        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        try {
            System.out.println("try->");
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Get an object and print its contents.
            System.out.println("Downloading an object");
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            System.out.println("Content: ");
            displayTextInputStream(fullObject.getObjectContent());

            // Get a range of bytes from an object and print the bytes.
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(bucketName, key)
                    .withRange(0,9);
            objectPortion = s3Client.getObject(rangeObjectRequest);
            System.out.println("Printing bytes retrieved.");
            displayTextInputStream(objectPortion.getObjectContent());

            // Get an entire object, overriding the specified response headers, and print the object's content.
            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
                    .withCacheControl("No-cache")
                    .withContentDisposition("attachment; filename=example.txt");
            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(bucketName, key)
                    .withResponseHeaders(headerOverrides);
            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
            displayTextInputStream(headerOverrideObject.getObjectContent());
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if(fullObject != null) {
                fullObject.close();
            }
            if(objectPortion != null) {
                objectPortion.close();
            }
            if(headerOverrideObject != null) {
                headerOverrideObject.close();
            }
        }
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }
}
```


# 参考

外部ライブラリを追加する
http://cortyuming.hateblo.jp/entry/20131007/p1
AWS SDK for Java のセットアップ
https://docs.aws.amazon.com/ja_jp/sdk-for-java/v1/developer-guide/setup-install.html

この辺も数少ない情報源
https://aws.amazon.com/jp/blogs/developer/category/programing-language/java/page/2/

サンプル
aws-sdk-java
https://github.com/aws/aws-sdk-java
s3サンプル
https://github.com/aws/aws-sdk-java/tree/master/src/samples/AmazonS3
javaサンプル
https://github.com/aws-samples/aws-java-sample
