# Regula app icon — file placement

This project targets `minSdk 26`, so adaptive icons (introduced exactly at
API 26) cover every supported device. No legacy per-density PNG mipmap
icons are needed — just the four files below, copied into your app
module's `res/` folder at these exact paths:

```
app/src/main/res/values/ic_launcher_background.xml
app/src/main/res/drawable/ic_launcher_foreground.xml
app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml
app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml
```

If `AndroidManifest.xml` doesn't already reference the icon, add these to
the `<application>` tag:

```xml
android:icon="@mipmap/ic_launcher"
android:roundIcon="@mipmap/ic_launcher_round"
```

## Other files in this folder (not app resources)

- `regula-icon-master.svg` — the source design (dark background, gold
  ring, gold cross), full-bleed 512×512 square, no pre-applied corner
  rounding (Play Store / F-Droid apply their own mask, so don't round it
  yourself).
- `regula-icon-512.png` — flattened raster export of the above. Use this
  for: README header, GitHub social preview image, F-Droid/Play Store
  listing icon upload, or anywhere else you need a plain image rather
  than an Android resource.

## Why no PNG mipmaps

Pre-API 26 devices needed flat PNGs at mdpi/hdpi/xhdpi/xxhdpi/xxxhdpi
because adaptive icons didn't exist yet. Since this app's floor is
API 26, the single vector-based adaptive icon set above is both simpler
and sharper at every size (vector drawables scale perfectly; PNGs don't).
If you ever lower `minSdk` below 26, ask and I'll generate the legacy PNG
set to match.
