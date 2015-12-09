package com.naji_astier.android_sudoku;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

public class SudokuAdapter<Element> extends ArrayAdapter<Element> {
    public SudokuAdapter(Context context, int resource, List<Element> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
        }

        final SquareEditText set = (SquareEditText) convertView;

        set.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("AndroidSudoku", String.format("Position %d -> (%d, %d)", position, SudokuGrid.getRow(position), SudokuGrid.getCell(position)));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return set;
    }
}
