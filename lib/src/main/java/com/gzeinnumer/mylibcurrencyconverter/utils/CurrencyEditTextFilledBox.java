package com.gzeinnumer.mylibcurrencyconverter.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gzeinnumer.mylibcurrencyconverter.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class CurrencyEditTextFilledBox extends TextInputLayout {
    private String prefix = "";
    private static String sprefix = "";
    private static int MAX_LENGTH = 30;
    private static final int MAX_DECIMAL = 3;
    Context context;
    AttributeSet attrs;

    public CurrencyEditTextFilledBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        this.attrs = attrs;

        initView();
    }
    CurrencyTextWatcher currencyTextWatcher;

    TextInputLayout textInputLayout;
    TextInputEditText textInputEditText;

    private void initView() {
        inflate(context, R.layout.currency_text_input_edit_text_filled_box, this);

        textInputLayout = findViewById(R.id.TextInputLayout);
        textInputEditText = findViewById(R.id.TextInputEditText);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditTextFilledBox);
        textInputLayout.setHint(attributes.getString(R.styleable.CurrencyEditTextFilledBox_hint));
        if (attributes.getInteger(R.styleable.CurrencyEditTextFilledBox_maxLength,0) != 0){
            MAX_LENGTH = attributes.getInteger(R.styleable.CurrencyEditTextFilledBox_maxLength,0);
        }
        textInputEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(MAX_LENGTH)});
        if (attributes.getDimension(R.styleable.CurrencyEditTextFilledBox_textSize,0) != 0){
            textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, attributes.getDimension(R.styleable.CurrencyEditTextFilledBox_textSize,0));
        }
        this.prefix = attributes.getString(R.styleable.CurrencyEditTextFilledBox_prefix);
        sprefix = this.prefix;

        currencyTextWatcher = new CurrencyTextWatcher(textInputEditText, prefix);
        textInputEditText.addTextChangedListener(currencyTextWatcher);

        attributes.recycle();
    }

    public static String trimCommaOfString(String string) {
        if (string.contains(",")) {
            if (CurrencyEditTextFilledBox.sprefix.length()>0)
                return string.replace(",", "").replace(CurrencyEditTextFilledBox.sprefix,"");
            else
                return string.replace(",", "");
        } else {
            return string;
        }
    }

    public String getText() {
        return toString(Objects.requireNonNull(textInputEditText.getText()).toString());
    }

    private String toString(String toString) {
        return toString;
    }


    private static class CurrencyTextWatcher implements TextWatcher {
        private final EditText editText;
        private String previousCleanString;
        private String prefix;

        CurrencyTextWatcher(EditText editText, String prefix) {
            this.editText = editText;
            this.prefix = prefix;
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
    }
}
