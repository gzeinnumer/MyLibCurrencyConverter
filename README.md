<p align="center">
  <img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/dev-1/preview/example7.jpg" width="400"/>
</p>

<h1 align="center">
    MyLibCurrencyConverter
</h1>

<p align="center">
    <a><img src="https://img.shields.io/badge/Version-3.0.0-brightgreen.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/ID-gzeinnumer-blue.svg?style=flat"></a>
    <a><img src="https://img.shields.io/badge/Java-Suport-green?logo=java&style=flat"></a>
    <a><img src="https://img.shields.io/badge/Koltin-Suport-green?logo=kotlin&style=flat"></a>
    <a href="https://github.com/gzeinnumer"><img src="https://img.shields.io/github/followers/gzeinnumer?label=follow&style=social"></a>
    <br>
    <p>Simple way to convert 1000000 to 1,000,000 .</p>
</p>

---
## Download
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
}
```

## Feature List
- [x] **CurrencyConverter Programatically**.
- [x] **CurrencyConverter In View (XML)**.

## Tech stack and 3rd library
- TextWatcher ([docs](https://developer.android.com/reference/android/text/TextWatcher))
- Material.io ([docs](https://material.io/develop/android/docs/getting-started))

---
## Use

### CurrencyConverter Programatically.

**Note** `RP 123,456,789.111` To Remove `Prefix` `RP` and symbol `,` you can use this
```java
String str = editText.getText().toString();
textView.setText(StringTools.trimCommaOfString(str));
```
```kotlin
val str = editText.text;
textView.text = StringTools.trimCommaOfString(str)
```

Here is the `xml` view that i use for Sample 1-4
```xml
<EditText
    android:id="@+id/editText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:inputType="numberDecimal" />
```

* Sample 1 -> Simple TextWacher
> **Java**
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
> **Kotlin**
```kotlin
editText.addTextChangedListener(CurrencyConverter(editText))

button.setOnClickListener {
    val str: String = editText.text.toString()
    Log.d(TAG, "onClick: " + StringTools.trimCommaOfString(str))
    Log.d(TAG, "onClick: "+editText.text.toString())

    textView.text = StringTools.trimCommaOfString(str)
}
```

#
* Sample 2 -> Simple TextWacher With CallBack
> **Java**
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
> **Kotlin**
```kotlin
editText.addTextChangedListener(
    CurrencyConverter(editText,
        StringCallBack { value ->
            Log.d(TAG, "realString: $value")
            Log.d(TAG, "realString: "+editText.text)

            textView.text = StringTools.trimCommaOfString(str)
        }
    )
)
```

#
* Sample 3 -> Simple TextWacher With Prefix
> **Java**
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
> **Kotlin**
```kotlin
editText.addTextChangedListener(CurrencyConverter(editText, "RP "))

button.setOnClickListener {
    val str: String = editText.text.toString()
    Log.d(TAG, "onClick: " + StringTools.trimCommaOfString(str))
    Log.d(TAG, "onClick: "+editText.text.toString())

    textView.text = StringTools.trimCommaOfString(str)
}
```

#
* Sample 4 -> Simple TextWacher With Prefix And CallBack
> **Java**
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
> **Kotlin**
```kotlin
editText.addTextChangedListener(
    CurrencyConverter(editText,
        "RP ",
        StringCallBack { value ->
            Log.d(TAG, "realString: $value")
            Log.d(TAG, "realString: "+editText.text)

            textView.text = StringTools.trimCommaOfString(str)
        }
    )
)
```

#
### CurrencyConverter In View (XML).
* Sample 1 -> In EditText
```xml
<com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditText
    android:id="@+id/ed"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hint="Dalam Jumlah Rupiah"
    app:prefix="RP " />
```
Remove Prefix and symbol " , "
```java
CurrencyEditTextOutlinedBox ed1 = findViewById(R.id.ed);

String str = StringTools.trimCommaOfString(ed1.getText(), "RP ");
```

Preview :
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/dev-1/preview/example4.jpg" width="400"/>|
|---|

#
* Sample 2 -> Use Material Design
```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:hint="Dalam Bentuk Rupiah">

    <com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:prefix="RP " />

</com.google.android.material.textfield.TextInputLayout>
```
Remove Prefix and symbol " , "
```java
CurrencyEditTextOutlinedBox ed1 = findViewById(R.id.ed);

String str = StringTools.trimCommaOfString(ed1.getText(), "RP ");
```

Preview :
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/dev-1/preview/example5.jpg" width="400"/>|
|---|

#
* Sample 3 -> Use Material Design With Simple Code
```xml
//OutlinedBox
<com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditTextOutlinedBox
    android:id="@+id/ed_1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hint="Dalam Jumlah Rupiah"
    app:prefix="RP " />

//FilledBox
<com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditTextFilledBox
    android:id="@+id/ed_2"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hint="Dalam Jumlah Rupiah"
    app:prefix="RP " />
```
```java
//OutlinedBox
CurrencyEditTextOutlinedBox ed1 = findViewById(R.id.ed_1);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        textView.setText(ed1.getText());
        textView.setText(StringTools.trimCommaOfString(ed1.getText(), "RP "));
    }
});

//FilledBox
CurrencyEditTextFilledBox ed1 = findViewById(R.id.ed_2);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        textView.setText(ed1.getText());
        textView.setText(StringTools.trimCommaOfString(ed1.getText(), "RP "));
    }
});
```

Preview :
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/dev-1/preview/example6.jpg" width="400"/>|
|---|


Preview :
|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/dev-1/preview/example1.jpg" width="400"/>|<img src="https://github.com/gzeinnumer/MyLibCurrencyConverter/blob/dev-1/preview/example2.jpg" width="400"/>|
|---|---|

---

```
Copyright 2020 M. Fadli Zein
```
