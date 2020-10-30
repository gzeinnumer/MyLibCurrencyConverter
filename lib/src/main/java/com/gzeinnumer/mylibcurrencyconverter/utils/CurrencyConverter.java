package com.gzeinnumer.mylibcurrencyconverter.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


import java.util.StringTokenizer;


public class CurrencyConverter implements TextWatcher {

    EditText editText;
    StringCallBack stringCallBack;

    public interface StringCallBack{
        void realString(String value);
    }


    public CurrencyConverter(EditText editText) {
        this.editText = editText;
    }

    public CurrencyConverter(EditText editText, StringCallBack stringCallBack) {
        this.editText = editText;
        this.stringCallBack = stringCallBack;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();

            if (value != null && !value.equals("")) {
                if (value.startsWith(".")) {
                    editText.setText("0.");
                }
                if (value.startsWith("0") && !value.startsWith("0.")) {
                    editText.setText("");
                }

                String str = editText.getText().toString().replaceAll(",", "");
                if (!value.equals("")){
                    editText.setText(getDecimalFormattedString(str));
                    stringCallBack.realString(trimCommaOfString(getDecimalFormattedString(str)));
                }
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }
    }

    private static String getDecimalFormattedString(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = ".";
        }
        for (int k = j; ; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3) {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }
    }

    public static String trimCommaOfString(String string) {
        if (string.contains(",")) {
            return string.replace(",", "");
        } else {
            return string;
        }
    }
}