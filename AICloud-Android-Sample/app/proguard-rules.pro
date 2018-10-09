# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-dontpreverify
-repackageclasses ''
-overloadaggressively
-allowaccessmodification



-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }




# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# Gson specific classes



-keepattributes *Annotation*

-dontwarn org.apache.**




-dontwarn android.support.**

-dontwarn com.ut.mini.**
-dontwarn okio.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**
-dontwarn android.support.v4.**
-dontwarn com.android.volley.**
-dontwarn com.loopj.**
-dontwarn com.prime31.**
-dontwarn com.tencent.**
-dontwarn org.acra.**
-dontwarn org.android.**

-keepattributes *Annotation*

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }


-keep public class * extends android.view.View{*;}
-keep public class * implements java.io.Serializable{*;}

-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}



-keep class org.android.agoo.service.* {*;}

-keep class org.android.spdy.**{*;}





#-dontwarn cn.eastar.dl
#-keep class cn.easyar.dl.HandGestureFactory {
#    public <methods>;
#}
#
#-keep class cn.easyar.dl.BodyPoseFactory {
#    public <methods>;
#}
#
##-keep class cn.easyar.dl.Recognizer {
##    public <methods>;
##}
#
#-keep class cn.easyar.dl.HandGesture {
#    public <methods>;
#    public <fields>;
#}
#-keep class cn.easyar.dl.BodyPose {
#    public <methods>;
#    public <fields>;
#}
#
#-keep class cn.easyar.dl.RecognizeCallback {
#    public <methods>;
#}