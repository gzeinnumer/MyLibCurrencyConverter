<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example12.jpg" width="400"/>
</p>

<h1 align="center">
    MyLibCurrencyConverter
</h1>

<p align="center">
    <a><img src="https://img.shields.io/badge/Version-4.0.1-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Kotlin-Suport-green?logo=kotlin&style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <p>Simple way to convert 1000000 to 1,000,000 .</p>
</p>

---
# Content List
* [Download](#download)
* [Feature List](#feature-list)
* [Tech stack and 3rd library](#tech-stack-and-3rd-library)
* [Usage](#usage)
* [Example Code/App](#example-codeapp)
* [Version](#version)
* [Contribution](#contribution)

---
# Download
Add maven `jitpack.io` and `dependencies` in build.gradle (Project) :
```gradle
// build.gradle project
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

// build.gradle app/module
dependencies {
  ...
  implementation 'com.github.gzeinnumer:MyLibCurrencyConverter:version'
  implementation 'com.google.android.material:material:1.2.0'
}
```

---
# Feature List
- [x] **CurrencyConverter Programatically**.
- [x] **CurrencyConverter In View (XML)**.

---
# Tech stack and 3rd library
- TextWatcher ([docs](https://developer.android.com/reference/android/text/TextWatcher))
- Material.io ([docs](https://material.io/develop/android/docs/getting-started))

---
# Usage

**First Step**. Use `MaterialComponents` in your style :
```xml
<style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
    <!-- Customize your theme here. -->
</style>
```

#
### CurrencyConverter Programatically.

**Note** `RP 123,456,789.111` To Remove `Prefix` `RP` and symbol `,` you can use this :
```java
StringTools.trimCommaOfString(str, "RP ");
```

* **Sample 1** -> Simple `TextWacher`
```java
editText.addTextChangedListener(new CurrencyConverter(editText));

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String str = editText.getText().toString();
        Log.d(TAG, "onClick: "+ StringTools.trimCommaOfString(str, "RP "));
        Log.d(TAG, "onClick: "+editText.getText().toString());

        textView.setText(StringTools.trimCommaOfString(str));
    }
});
```

* **Sample 2** -> Simple `TextWacher` With `CallBack`
```java
editText.addTextChangedListener(new CurrencyConverter(editText, new CurrencyConverter.StringCallBack() {
    @Override
    public void realString(String value) {
        Log.d(TAG, "realString: "+value);
        Log.d(TAG, "realString: "+editText.getText().toString());

        textView.setText("(Real Value) : "+value + " && (Preview) : "+editText.getText().toString());
    }
}));
```

* **Sample 3** -> Simple `TextWacher` With `Prefix`
```java
editText.addTextChangedListener(new CurrencyConverter(editText, "RP "));

button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String str = editText.getText().toString();
        Log.d(TAG, "onClick: "+StringTools.trimCommaOfString(str));
        Log.d(TAG, "onClick: "+editText.getText().toString());

        textView.setText(StringTools.trimCommaOfString(str));
    }
});
```

* **Sample 4** -> Simple `TextWacher` With `Prefix` And `CallBack`
```java
editText.addTextChangedListener(new CurrencyConverter(editText, "RP " ,new CurrencyConverter.StringCallBack() {
    @Override
    public void realString(String value) {
        Log.d(TAG, "realString: "+value);
        Log.d(TAG, "realString: "+editText.getText().toString());

        textView.setText("(Real Value) : "+value + " && (Preview) : "+editText.getText().toString());
    }
}));
```

Preview For **Sample 1-4**:
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example1.jpg" width="400"/>|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example2.jpg" width="400"/>|
|---|---|
|**Sample 1 & 2**|**Sample 3 & 4**|

#
### CurrencyConverter In View (XML).
* **Sample 1** -> In `EditText`
```xml
<com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditText
    android:id="@+id/ed"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hint="Dalam Jumlah Rupiah"
    app:prefix="RP " />
```
Custom your view
```xml
app:hint="Dalam Jumlah Rupiah" // for Hint of EditText
app:maxLength="20" // for Max Character in EditText include Prefix and Comma
app:prefix="RP " // for Prefix Example -> RP 1.000.000
app:textSize="12sp" // for TextSize on input
```
Remove `Prefix` and symbol `,` and get `real value`
```java
CurrencyEditTextOutlinedBox ed1 = findViewById(R.id.ed);

String str = StringTools.trimCommaOfString(ed1.getText(), "RP ");
```

Preview :
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example4.jpg" width="400"/>|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example9.jpg" width="400"/>|
|---|---|

* **Sample 2** -> Use Material Design
```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:hint="DaKlam Bentuk Rupiah">

    <com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="numberDecimal"
        app:prefix="RP " />

</com.google.android.material.textfield.TextInputLayout>
```
Remove `Prefix` and symbol `,` and get `real value`
```java
CurrencyEditTextOutlinedBox ed1 = findViewById(R.id.ed);

String str = StringTools.trimCommaOfString(ed1.getText(), "RP ");
```

Preview :
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example5.jpg" width="400"/>|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/preview/example10.jpg" width="400"/>|
|---|---|

---
# Example Code/App

**FullCode [MainActivity](https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/app/src/main/java/com/gzeinnumer/mylibcurrencyconverter/MainActivity.java)  & [XML](https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/master/app/src/main/res/layout/activity_main.xml)**

[Sample Code And App](https://github.com/gzeinnumer/MyLibCurrencyConverterExample)

---
# Version
- **4.0.0**
  - First Release
- **4.0.1**
  - Take Out Useless Method

---
# Contribution
You can sent your constibution to `branch` `open-pull`.

---

```
Copyright 2020 M. Fadli Zein
```
