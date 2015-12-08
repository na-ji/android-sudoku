package com.naji_astier.android_sudoku;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class SquareEditText extends EditText {
    public SquareEditText(Context context) {
        super(context);
    }

    public SquareEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int max = Math.max(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(max, max);
    }
}
