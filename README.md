### flutter_mix
flutter混合开发

想要工程跑起来，需要做如下的配置：
##1.配置flutter_sdk
按照flutter官网配置即可
https://flutterchina.club/setup-macos/

##2.工程目录下的my_flutter，这个因为是我初始化创建的，所以指向的flutter_sdk目录都是我本地的，需要更改。否则会编译报错，本人就遇到了这样的坑
详见：https://blog.csdn.net/rzleilei/article/details/101438296

#更改文件：my_flutter/.android/local.properties里面的flutter.sdk变量

