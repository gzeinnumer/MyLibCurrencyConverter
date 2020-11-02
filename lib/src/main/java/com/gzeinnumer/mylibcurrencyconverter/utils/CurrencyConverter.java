package com.gzeinnumer.mylibcurrencyconverter.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyConverter implements TextWatcher {
    private static final int MAX_LENGTH = 100;
    private static final int MAX_DECIMAL = 3;
    private final EditText editText;
    private String previousCleanString;
    private String prefix = "";
    private static String sSrefix = "";
    StringCallBack stringCallBack;

    public interface StringCallBack{
        void realString(String value);
    }

    public CurrencyConverter(EditText editText) {
        this.editText = editText;
    }

    public CurrencyConverter(EditText editText, String prefix) {
        this.editText = editText;
        this.prefix = prefix;
        sSrefix = prefix;
    }

    public CurrencyConverter(EditText editText, String prefix, StringCallBack stringCallBack) {
        this.editText = editText;
        this.prefix = prefix;
        sSrefix = prefix;
        this.stringCallBack = stringCallBack;
    }

    public CurrencyConverter(EditText editText, StringCallBack stringCallBack) {
        this.editText = editText;
        this.stringCallBack = stringCallBack;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        if (str.length() < prefix.length()) {
            editText.setText(prefix);
            editText.setSelection(prefix.length());
            return;
        }
        if (str.equals(prefix)) {
            return;
        }
        // cleanString this the string which not contain prefix and ,
        String cleanString = str.replace(prefix, "").replaceAll("[,]", "");
        // for prevent afterTextChanged recursive call
        if (cleanString.equals(previousCleanString) || cleanString.isEmpty()) {
            return;
        }
        previousCleanString = cleanString;

        String formattedString;
        if (cleanString.contains(".")) {
            formattedString = formatDecimal(cleanString);
        } else {
            formattedString = formatInteger(cleanString);
        }
        editText.removeTextChangedListener(this); // Remove listener
        editText.setText(formattedString);
        handleSelection();
        if (stringCallBack!=null)
            stringCallBack.realString(trimCommaOfString(formattedString));
        editText.addTextChangedListener(this); // Add back the listener
    }

    private String formatInteger(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat(prefix + "#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    private String formatDecimal(String str) {
        if (str.equals(".")) {
            return prefix + ".";
        }
        BigDecimal parsed = new BigDecimal(str);
        // example pattern VND #,###.00
        DecimalFormat formatter = new DecimalFormat(prefix + "#,###." + getDecimalPattern(str),
                new DecimalFormatSymbols(Locale.US));
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(parsed);
    }

    /**
     * It will return suitable pattern for format decimal
     * For example: 10.2 -> return 0 | 10.23 -> return 00, | 10.235 -> return 000
     */
    private String getDecimalPattern(String str) {
        int decimalCount = str.length() - str.indexOf(".") - 1;
        StringBuilder decimalPattern = new StringBuilder();
        for (int i = 0; i < decimalCount && i < MAX_DECIMAL; i++) {
            decimalPattern.append("0");
        }
        return decimalPattern.toString();
    }

    private void handleSelection() {
        if (editText.getText().length() <= MAX_LENGTH) {
            editText.setSelection(editText.getText().length());
        } else {
            editText.setSelection(MAX_LENGTH);
        }
    }

    private static String trimCommaOfString(String string) {
        if (string.contains(",") || CurrencyConverter.sSrefix.length()>0) {
            if (CurrencyConverter.sSrefix.length()>0)
                return string.replace(",", "").replace(CurrencyConverter.sSrefix,"");
            else
                return string.replace(",", "");
        } else {
            return string;
        }
    }

    private static String getFormattedString(String text) {
        String res = "";
        try {
            String temp = text.replace(",", "");
            long part1;
            String part2 = "";
            int dotIndex = temp.indexOf(".");
            if (dotIndex >= 0) {
                part1 = Long.parseLong(temp.substring(0, dotIndex));
                if (dotIndex + 1 <= temp.length()) {
                    part2 = temp.substring(dotIndex + 1).trim().replace(".", "").replace(",", "");
                }
            } else
                part1 = Long.parseLong(temp);

            res = getStringWithSeparator(part1);
            if (part2.length() > 0)
                res += "." + part2;
            else if (dotIndex >= 0)
                res += ".";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public static String getStringWithSeparator(long value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        String f = formatter.format(value);
        return f;
    }
}
