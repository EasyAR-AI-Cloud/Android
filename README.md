### EasyAR AI Cloud Client SDK for Android

#### 运行Sample
注册和获取 key、secret

* 手势识别： HandGestureFactory.init("xxxx","xxxx");

* 人体姿势： BodyPoseFactory.init("xxxx","xxxx");

#### 创建项目
1， sdk 识别过程需要使用网络，请确保网络稳定通常，推荐在 wifi 网络环境下使用。 AndroidManifest.xml 文件中添加网络权限

···
<uses-permission android:name="android.permission.INTERNET"/>
···

2， 将获取到的 aicloud-release.jar 文件复制粘贴到 android studio 的左侧 app/libs 目录下， 在 aicloud-release.jar 右键选择 Add As Library

3， 参考Sample，注册和获取 key、secret

* 手势识别： HandGestureFactory.init("xxxx","xxxx");

* 人体姿势： BodyPoseFactory.init("xxxx","xxxx");
