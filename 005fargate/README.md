

# Docker作成


```
docker build -t flask-fargate:latest .
docker run -d -p 5000:80 flask-fargate
```


-pはポートフォワーディング（ポートの紐付け）。
左の5000がホスト側。右の80がコンテナ側。

http://localhost:5000/



```
ap-northeast-1
アジアパシフィック (東京)

us-east-1
米国東部（バージニア北部）
```


# ECR作成


flask-fargateのecrを作成。    
プッシュコマンドの表示にpushの例が書いてるある。    


```
$(aws ecr get-login --no-include-email --region ap-northeast-1)

docker tag flask-fargate:latest hoge.dkr.ecr.ap-northeast-1.amazonaws.com/flask-fargate:latest

docker push hoge.dkr.ecr.ap-northeast-1.amazonaws.com/flask-fargate:latest

```


# ECSクラスタ作成

EKSではないよ。ECSのクラスタを作成してね。    
flask-fargateの名前でクラスタ作成    

IAMロール作成。    

https://hacknote.jp/archives/51192/

クラスタ作成を押す。    

# タスク定義作成


コンテナ追加ボタン    

flask    

ECRのurlを入れる    
5000ポートを入れる    

タスク作成を押す。    

http://サーバーIP/
    


# 参考にした。

Fagate    
https://qiita.com/oyngtmhr/items/45a0d3158e6dccb0882d

Docker    

https://github.com/miyamotok0105/docker_sample/tree/master/01docker/v17.12/get-started/part2


