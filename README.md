# AmazonS3Sample
これは、SpringBootを使ってファイルをAmazon S3へ保存するWeb上で動作するチャットアプリケーションのサンプルです。

## 事前準備

AWSにEC2とMySQLのRDSインスタンスを立ち上げてください。

ビルドには、gradleが必要です。  
[https://gradle.org/](https://gradle.org/)から最新バージョンをEC2にセットアップしてください。

また、Amazon S3のバケットも立ち上げる必要が有ります。
S3には、IAMでユーザーを作成し、S3へ権限を与えてください。

## 環境変数

アプリケーションの起動のためには、いくつか環境変数の設定が必要です。
以下の環境変数をEC2に設定してください。

```
# S3のエンドポイント
export S3SAMPLE_DBHOST=s3sample.xxxxxxxxxxxx.ap-northeast-1.rds.amazonaws.com:3306
# DBユーザー
export S3SAMPLE_DBUSER=user
# DBパスワード
export S3SAMPLE_DBPASSWORD=password
# IAMユーザーのアクセスキー
export S3SAMPLE_ACCESSKEY=ABCDEFGHIJKLMNOPQRSTUVWXYZ123
# シークレットキー
export S3SAMPLE_SECRETKEY=ABCDE1234567890FGHYJKLMN1234OPQRSTUvwxyz
# S3のバケット名
export S3SAMPLE_BUCKET=bucket
```

## 起動方法

このプロジェクトを任意の場所にクローンのち、プロジェクト内で以下のコマンドを実行してください。

```
$ gradle build
$ cd build/libs
$ java -jar *.jar --server.port=8080
```

多分起動します。
起動しなかったら、ごめんなさい。
