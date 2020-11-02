package com.gzeinnumer.mylibcurrencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyConvertera;
import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyConverter;
import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditTextFilledBox;
import com.gzeinnumer.mylibcurrencyconverter.utils.CurrencyEditTextOutlinedBox;
import com.gzeinnumer.mylibcurrencyconverter.utils.StringTools;

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
//        sample2();
//        sample3();
//        sample4();
//        sample5();
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

    private void sample4() {
        CurrencyEditTextOutlinedBox ed1 = findViewById(R.id.ed_1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                textView.setText(ed1.getText());
                textView.setText(StringTools.trimCommaOfString(ed1.getText(), "RP "));
            }
        });

    }

    private void sample5() {
        CurrencyEditTextFilledBox ed1 = findViewById(R.id.ed_2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText(ed1.getText());
                textView.setText(StringTools.trimCommaOfString(ed1.getText(), "RP "));
            }
        });

    }
}