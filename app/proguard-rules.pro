# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

# 指定代码的压缩级别
-optimizationpasses 5

# 包名不混合大小写
-dontusemixedcaseclassnames

# 忽略警告
-ignorewarnings

# 保护注解
-keepattributes *Annotation*

# 如果引用了v4或者v7包
-dontwarn android.support.**

# 保持公有/保护/native不被混淆
-keep class * {
    public *;
    protected *;
    native <methods>;
}

# 保持Parcelable不被混淆
-keep class * implements android.os.Parcelable {
    *;
}

# 保持Serializable不被混淆
-keep class * implements java.io.Serializable {
    *;
}

# 不混淆资源类
-keepclassmembers class **.R$* {
    *;
}

# 保持枚举不被混淆
-keep enum * {
    *;
}
