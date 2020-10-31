package com.gzeinnumer.mylibcurrencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyConverter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText editText;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

//        sample1();
        sample2();
    }

    private void sample1() {
        editText.addTextChangedListener(new CurrencyConverter(editText));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                Log.d(TAG, "onClick: "+CurrencyConverter.trimCommaOfString(str));

                textView.setText(CurrencyConverter.trimCommaOfString(str));
            }
        });
    }

    private void sample2() {
        editText.addTextChangedListener(new CurrencyConverter(editText, new CurrencyConverter.StringCallBack() {
            @Override
            public void realString(String value) {
                Log.d(TAG, "realString: "+value);
                Log.d(TAG, "realString: "+editText.getText().toString());

                textView.setText("(Real Value) : "+value + " && (Preview) : "+editText.getText().toString());
            }
        }));
    }
}