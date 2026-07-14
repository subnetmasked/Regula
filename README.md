# Regula

Regula is an offline Catholic moral and doctrinal reference app for Android. It organizes teaching by category, classifies entries as dogma, doctrine, discipline, or theological opinion, and links each answer to magisterial citations — all stored locally with no network access.

## Features (v1.0)

- Browse eight categories of moral and doctrinal topics
- Read concise answers with classification badges and explanatory notes
- View citations grouped by source type (CCC, canon law, Aquinas, magisterial documents)
- Search entries by question or answer text
- Fully offline — no analytics, no tracking, no proprietary SDKs

## Tech stack

- Kotlin, Jetpack Compose, Material 3
- Room (SQLite) for local persistence
- Navigation Compose
- Gradle Kotlin DSL, single `app/` module
- minSdk 26, compileSdk / targetSdk 36

## Content editing

All topics, articles, and citations are defined in JSON — there is no in-app editor.

| What | File |
|---|---|
| **Edit this** | [`app/src/main/assets/content/content.json`](app/src/main/assets/content/content.json) |
| **Authoring guide** | [`content/README.md`](content/README.md) |
| **JSON schema** | [`content/schema.json`](content/schema.json) |

**Workflow:**

1. Edit `content.json` (categories = topics, entries = articles, nested `citations`)
2. Bump `"contentVersion"` so installed apps reload the database
3. `./gradlew installDebug` — no uninstall needed

**Allowed values:**

- `classification`: `dogma`, `doctrine`, `discipline`, `theological_opinion`
- `sourceType`: `ccc`, `canon_law`, `aquinas`, `magisterial`, `scripture`

New categories get a default icon until you add a mapping in `CategoryVisuals.kt` (see `content/README.md`).

## Prerequisites

- JDK 17 (required — newer JDK versions such as 25 are not yet supported by the Android Gradle Plugin)
- Android SDK with API 36 platform installed
- `ANDROID_HOME` (or `ANDROID_SDK_ROOT`) pointing to your SDK

## Build and run

```bash
./gradlew assembleDebug
```

Install on a connected device or emulator:

```bash
./gradlew installDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

## FOSS / F-Droid design

Regula uses only FOSS AndroidX dependencies. There is no Firebase, Google Play Services, analytics, or network permission in the manifest. The app is designed for eventual F-Droid distribution.

## License

Copyright (C) 2026 Regula contributors

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

See [LICENSE](LICENSE) for the full GPLv3 text.
