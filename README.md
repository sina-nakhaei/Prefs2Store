# Prefs2Store - Migrate SharedPreferences to DataStore
![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Platform](https://img.shields.io/badge/platform-Android-brightgreen)
![Kotlin](https://img.shields.io/badge/language-Kotlin-purple)
![JitPack](https://img.shields.io/badge/dependency-JitPack-yellow)

📌 Features

✅ Migrate data from SharedPreferences to DataStore seamlessly

✅ Supports multiple data types (String, Int, Boolean, Float, Long, Set<String>)

✅ Includes error handling and progress tracking

## Installation 📦

To use this library in your project, follow these steps:

### Step 1: Add the JitPack repository

In your root `settings.gradle` file, add the JitPack repository:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the dependency

In your `build.gradle` (Module level), add the dependency:

```gradle
dependencies {
     implementation 'com.github.sina-nakhaei:Prefs2Store:$version'
}
```

## Usage
   ``````kotlin
val sharedPrefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
val dataStore: DataStore<Preferences> = context.createDataStore(name = "my_datastore")

val prefs2Store = Prefs2Store(sharedPrefs, dataStore)

lifecycleScope.launch {
    prefs2Store.migrate(
        excludeKeys = setOf("skip_this_key"),
        clearOnSuccess = true,
        onError = { key, e -> Log.e("Migration", "Failed to migrate $key: $e") },
    )
}
``````

## 🤝 Contributing
Found a bug? Have an idea? Open an issue or submit a pull request.
