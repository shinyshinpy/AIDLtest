# AIDLテスト

Androidアプリにおいて、AIDL通信の特性を知るためのテストアプリ

## テスト内容

AIDL通信はクライアント側に対し提供するjarを変更せずとも動作を変えれるのかどうか

## 仕様

testServiceはactivityを起動させると自動起動する。
今回テストしたい対象の実装はここに入っている

testActivityを起動し、start service -> Call API の順に押下するとAIDL通信でAPIコールができる

## jarの作り方

testServiceをビルドすると `testService\app\build\intermediates\javac\debug\classes\com\shin\testservice` にクラスファイルが生成される
このうち、ITestInterface（対象のAIDL）のみ残し、comからコピーする

コピーした位置でコマンドを開き、以下コマンドを実行

```sh
# com/ が見える位置
jar -cvf testInterface.jar ./com/shin/testservice/*.class
```

これでjarの中身が以下のようになっていればOK

![](/images/image2024-08-09-02-50-26.png)

## 起動方法

### testService

```sh
adb install -r -g testService.apk
adb shell am start -n com.shin.testservice/.MainActivity -a com.shin.testservice.SERVICE_ACTION
```

### testActivity

※ AndroidStudio上でインストール・起動をさせてたのでapkで起動しない可能性あり...

```sh
adb install -r -g testActivity.apk
# 端末上で起動
```
