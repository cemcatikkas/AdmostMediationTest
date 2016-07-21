-dontobfuscate
-optimizations !class/unboxing/enum
-dontoptimize

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep class android.support.v7.** {
   public protected *;
}

# Various
-keep class * extends java.util.ListResourceBundle { protected Object[][] getContents(); }
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-keepattributes InnerClasses, EnclosingMethod
-keepattributes *Annotation*,EnclosingMethod
-keepclasseswithmembers class * { public <init>(android.content.Context, android.util.AttributeSet, int);  }
-keepclassmembers class fqcn.of.javascript.interface.for.webview { public *;  }
-keepclassmembers class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *; }
-dontwarn android.webkit.**
-keep public class android.support.v4.**
-dontwarn android.support.v4.**

####################################################################################################

-keep class com.kokteyl.ApplicationStart { *; }

# COMSCORE
-keep class com.comscore.** { *; }
-dontwarn com.comscore.**

# GEMIUSSDK
-dontwarn com.gemius.**
-keep class com.gemius.** { *; }
-keepclassmembers class com.gemius.** { *; }

# CRASHLYTICS
-keepattributes *Annotation*
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| ADMOST-AD-SDK

# GOOGLE
-keep class com.android.vending.billing.**
-keep public class com.google.android.gms.ads.** { public *; }
 -keep public class com.google.ads.** { public *; }
-keep class com.google.android.gms.** { *; }
 -dontwarn com.google.android.gms.**
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable { public static final *** NULL; }
-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable { public static final *** CREATOR; }
-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * { @android.support.annotation.Keep <fields>; }
-keepclasseswithmembers class * { @android.support.annotation.Keep <methods>; }
-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * { @com.google.android.gms.common.annotation.KeepName *; }
-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep public @com.google.android.gms.common.util.DynamiteApi class * { public <fields>; public <methods>; }
-dontwarn android.security.NetworkSecurityPolicy
-keep public @com.google.android.gms.common.util.DynamiteApi class * { *; }

# ADMOST
-keep class admost.sdk.** { *; }
-dontwarn admost.sdk.**
# ADMOB
-dontwarn com.google.android.gms.ads.**
-keep public class com.google.android.gms.ads.** {public *;}
-keep public class com.google.ads.** {public *;}
-keep class com.google.android.gms.ads.** {*;}
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
# AMAZON
-dontwarn com.amazon.**
-keep class com.amazon.** {*;}
# CHARTBOOST
-dontwarn org.apache.http.**
-dontwarn com.chartboost.sdk.impl.**
-keep class com.chartboost.sdk.** { *; }
-keepattributes *Annotation*
# INMOBI
-keep class com.inmobi.** {*;}
-dontwarn com.inmobi.**
# FLURRY
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**
# MOPUB
-keepclassmembers class com.mopub.** { public *; }
-keep public class com.mopub.**
-keep public class android.webkit.JavascriptInterface {}
-keep class * extends com.mopub.mobileads.CustomEventBanner {}
-keep class * extends com.mopub.mobileads.CustomEventInterstitial {}
-keep class * extends com.mopub.nativeads.CustomEventNative {}
# FACEBOOK
-dontwarn com.facebook.ads.**
-dontnote com.facebook.ads.**
-keep class com.facebook.** { *; }
-keepattributes Signature
# MOBFOX
-dontwarn com.mobfox.**
-keep class com.mobfox.** {*;}
# LOOPME
-dontwarn com.loopme.**
-keep class com.loopme.** {*;}
# ADFALCON
-dontwarn com.noqoush.**
-keep class com.noqoush.** {*;}
# MEDIABRIX
-dontwarn com.mediabrix.**
-keep class com.mediabrix.** { *; }
-dontwarn com.moat.analytics.**
-keep class com.moat.analytics.** { *; }
# SUPERSONICADS
-dontwarn com.supersonicads.**
-keep class com.supersonicads.** { *; }
-dontwarn com.supersonic.**
-keep class com.supersonic.** { *; }
# VUNGLE
-dontwarn com.vungle.**
-keep class com.vungle.** { public *; }
-keep class javax.inject.*
-keepattributes *Annotation*
-keepattributes Signature
-keep class dagger.*
# PUBNATIVE
-keep class net.pubnative.** { *; }
-dontwarn net.pubnative.**
# APPLOVIN
-dontwarn com.applovin.**
-keep class com.applovin.** { *; }
# FLYMOB
-dontwarn com.flymob.sdk.**
-keep public class com.flymob.sdk.common.** { public *; }
# UNITY
-dontwarn com.unity3d.**
-keep class com.unity3d.ads.android.** { *; }
# VOLLEY
-keep class com.android.volley.** { *; }
-keep interface com.android.volley.** { *; }
-keep class org.apache.commons.logging.**
# MILLENNIAL AND NEXAGE
-dontwarn com.millennialmedia**
-keepclassmembers class com.millennialmedia** {
public *;
}
-keep class com.millennialmedia**
# REVMOB
-dontwarn com.revmob.**
-keep class com.revmob.** { public *; }