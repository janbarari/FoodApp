-keepclasseswithmembers class * extends io.github.janbarari.genericrecyclerview.viewholder.GenericViewHolder { *; }
-keepclasseswithmembers class io.github.janbarari.genericrecyclerview.* { *; }

#Some of androidx drawables ignored by proguard, this rule avoid proguard to ignore them
-keep class androidx.appcompat.graphics.** { *; }

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}