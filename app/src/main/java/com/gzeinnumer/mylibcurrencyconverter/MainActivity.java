package com.gzeinnumer.mylibcurrencyconverter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyConverter;
import com.gzeinnumer.mylibcurrencyconverter.utils.StringTools;

//import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyConvertera;

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

        sample1();
        sample2();
        sample3();
    }

    private void sample1() {
        editText.addTextChangedListener(new CurrencyConverter(editText, "RP "));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                Log.d(TAG, "onClick: "+ StringTools.trimCommaOfString(str, "RP "));

                textView.setText(StringTools.trimCommaOfString(str, "RP "));
            }
        });
    }

    private void sample2() {
        editText.addTextChangedListener(new CurrencyConverter(editText, "RP ", new CurrencyConverter.StringCallBack() {
            @Override
            public void realString(String value) {
                textView.setText("(Real Value) : "+value + " && (Preview) : "+editText.getText().toString());
            }
        }));
    }

    private void sample3() {
        editText.addTextChangedListener(new CurrencyConverter(editText, new CurrencyConverter.StringCallBack() {
            @Override
            public void realString(String value) {
                textView.setText("(Real Value) : "+value + " && (Preview) : "+editText.getText().toString());
            }
        }));
    }
}