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

#---------------------------------基本指令区 start----------------------------------

# 忽略警告
-ignorewarnings
# 代码混淆压缩比，在0~7之间
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
# 预校验
-dontpreverify
# 记录日志
-verbose

# google推荐算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 避免混淆Annotation、内部类、泛型、匿名类
-keepattributes Exceptions,InnerClasses,Signature,Deprecated, SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

# 重命名抛出异常时的文件名称
-renamesourcefileattribute SourceFile

# 运行抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 不忽略指定类库的public类成员（变量和方法），默认情况下，ProGuard会忽略他们
-dontskipnonpubliclibraryclassmembers

-printmapping proguardMapping.txt

# 解决AGPBI警告
-keepattributes Exceptions

#---------------------------------基本指令区 end----------------------------------

#---------------------------------系统组件 start----------------------------------

#继承自activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

# 所有View的子类及其子类的get、set方法都不进行混淆
-keep public class * extends android.view.View {
 *** get*();
 void set*(***);
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
-keepclassmembers class * extends android.app.Activity {
 public void *(android.view.View);
}

# 对于带有回调函数onXXEvent的，不能被混淆
-keepclassmembers class * {
 void *(*Event);
}

# 枚举类不能被混淆
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

# natvie 方法不混淆
-keepclasseswithmembernames class * {
 native <methods>;
}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

#不混淆Serializable接口的子类中指定的某些成员变量和方法
-keepclassmembers class * implements java.io.Serializable {
 static final long serialVersionUID;
 private static final java.io.ObjectStreamField[] serialPersistentFields;
 !static !transient <fields>;
 private void writeObject(java.io.ObjectOutputStream);
 private void readObject(java.io.ObjectInputStream);
 java.lang.Object writeReplace();
 java.lang.Object readResolve();
}

-keepclassmembers class * {
 public <init> (org.json.JSONObject);
}

# 不混淆R类里及其所有内部static类中的所有static变量字段，$是用来分割内嵌类与其母体的标志
-keep public class **.R$*{
 public static final int *;
}

#（可选）避免Log打印输出
-assumenosideeffects class android.util.Log {
 public static *** v(...);
 public static *** d(...);
 public static *** i(...);
 public static *** w(...);
}

# webview
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
 public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
 public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
 public boolean *(android.webkit.WebView, java.lang.String);
}

#保留Keep注解的类名和方法
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
 @android.support.annotation.Keep *;
}

#---------------------------------系统组件 end----------------------------------

#------------------------------依赖库 start------------------------------

# androidx
-dontnote androidx.**
-dontwarn androidx.**

# arouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
#-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

# AVLoadingIndicatorView
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

# BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
 *;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers class **$** extends com.chad.library.adapter.base.BaseViewHolder {
 <init>(...);
}

-dontwarn jp.co.cyberagent.android.gpuimage.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
 **[] $VALUES;
 public *;
}
-keep class com.bumptech.glide.load.** {*;}

# fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

# gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

# Gson specific classes
-keep class sun.** { *; }
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# ijkplayer
-keep class tv.danmaku.ijk.media.player.**{*;}

# javax
-dontwarn javax.annotation.**
-dontwarn javax.inject.**

# mupdf
-keep class com.artifex.mupdf.** {*;}

# okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}

# okhttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# ormlite-android
-dontwarn com.j256.ormlite.**
-keep class com.j256.ormlite.** { *;}

# protobuf
-keep class com.google.protobuf.** {
 public protected *;
}

# recyclerview-animators/glide-transformations
-keep class jp.wasabeef.** {*;}
-dontwarn jp.wasabeef.*

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# AndroidX
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

-dontnote androidx.**
-dontwarn androidx.**
#-keep class androidx.** { *; }
#-keep interface androidx.** { *; }

# ViewBinding
-keepclasseswithmembers class * implements androidx.viewbinding.ViewBinding {
    public static *** inflate(...);
}

# ViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}