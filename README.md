# AmazonS3Sample
これは、SpringBootを使ってファイルをAmazon S3へ保存するWeb上で動作するチャットアプリケーションのサンプルです。

## 事前準備

AWSにEC2とMySQLのRDSインスタンスを立ち上げてください。

また、Amazon S3のバケットも立ち上げる必要が有ります。
S3には、IAMでユーザーを作成し、S3へ権限を与えてください。

※データベース作成時に、DB名は"s3sample_db"の指定をしてください。

## 環境変数

アプリケーションの起動のためには、いくつか実行環境へ合わせた設定が必要です。
AWSのインスタンス作成時、またはダッシュボードで以下の項目を確認し、以下の環境変数をサーバに設定してください。


- DB設定
  - DBは、作成時にデータベース名を"s3sample_db"に設定してください。
  - エンドポイント・ユーザーは、RDSのインスタンス一覧より確認することができます。
  - パスワードは、作成時に確認してください。
- S3のバケット名
  - S3バケット作成時に設定したバケット名を確認してください。（作成後でも、ダッシュボードからバケット名は確認することができます。）
- IAM
  - S3へアプリケーションからアクセスします。その時、権限が必要になるため権限の設定・鍵が必要なります。それは、IAMから設定、取得することができるので、AWSのWebコンソールより設定をしてください。（IAMユーザー作成などでググると沢山情報が出ててくると思います。）

```
# DBのエンドポイント
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
$ ./gradlew build
$ java -jar ./build/libs/*.jar --server.port=8080
```

多分起動します。
起動しなかったら、ごめんなさい。
