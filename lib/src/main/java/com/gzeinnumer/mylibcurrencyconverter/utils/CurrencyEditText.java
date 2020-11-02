package com.gzeinnumer.mylibcurrencyconverter.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.gzeinnumer.mylibcurrencyconverter.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyEditText extends AppCompatEditText {
    private String prefix = "";
    private static int MAX_LENGTH = 30;
    private static final int MAX_DECIMAL = 3;
    Context context;
    AttributeSet attrs;
    private CurrencyTextWatcher currencyTextWatcher;

    public CurrencyEditText(Context context) {
        this(context, null);
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    private void initView() {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CurrencyEditText);
        this.setHint(attributes.getString(R.styleable.CurrencyEditText_hint));

        this.prefix = attributes.getString(R.styleable.CurrencyEditText_prefix);

        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//        this.setHint(prefix);
        if (attributes.getInteger(R.styleable.CurrencyEditText_maxLength,0) != 0){
            MAX_LENGTH = attributes.getInteger(R.styleable.CurrencyEditText_maxLength,MAX_LENGTH);
        }
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_LENGTH)});

        currencyTextWatcher = new CurrencyTextWatcher(this, prefix);

        attributes.recycle();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



        this.context = context;
        this.attrs = attrs;

        initView();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            this.addTextChangedListener(currencyTextWatcher);
        } else {
            this.removeTextChangedListener(currencyTextWatcher);
        }
        handleCaseCurrencyEmpty(focused);
    }

    /**
     * When currency empty <br/>
     * + When focus EditText, set the default text = prefix (ex: VND) <br/>
     * + When EditText lose focus, set the default text = "", EditText will display hint (ex:VND)
     */
    private void handleCaseCurrencyEmpty(boolean focused) {
        if (focused) {
            if (getText().toString().isEmpty()) {
                setText(prefix);
            }
        } else {
            if (getText().toString().equals(prefix)) {
                setText("");
            }
        }
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
