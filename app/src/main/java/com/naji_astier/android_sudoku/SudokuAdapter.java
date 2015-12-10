package com.naji_astier.android_sudoku;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SudokuAdapter<Element> extends ArrayAdapter<Element> {
    private SudokuGrid sudokuGrid;
    private List<Integer> conflictPosition;

    public SudokuAdapter(Context context, int resource, List<Element> objects) {
        super(context, resource, objects);
        this.conflictPosition = new ArrayList<>();
    }

    public SudokuAdapter(Context context, int resource, SudokuGrid grid) {
        this(context, resource, (List<Element>) grid.toList());
        this.sudokuGrid = grid;
    }

    public SudokuGrid getSudokuGrid() {
        return sudokuGrid;
    }

    public void setSudokuGrid(SudokuGrid sudokuGrid) {
        this.sudokuGrid = sudokuGrid;
        this.clear();
        this.addAll((List<Element>) this.sudokuGrid.toList());
        conflictPosition.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
        }

        final SquareEditText set = (SquareEditText) convertView;

        set.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                byte row = SudokuGrid.getRow(position);
                byte cell = SudokuGrid.getCell(position);
                conflictPosition.remove(Integer.valueOf(position));
                Collection<Sudoku.Coord> coordArrayList = Sudoku.checkValue(sudokuGrid, row, cell);
                for (Sudoku.Coord coord : coordArrayList) {
                    Integer pos = SudokuGrid.getPosition(coord.row, coord.cell);
                    conflictPosition.remove(pos);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                byte row = SudokuGrid.getRow(position);
                byte cell = SudokuGrid.getCell(position);
                com.naji_astier.android_sudoku.Element element = com.naji_astier.android_sudoku.Element.from(charSequence.toString());

                Log.d("AndroidSudoku", String.format("Position %d -> (%d, %d) = %s", position, row, cell, charSequence.toString()));

                sudokuGrid.set(row, cell, element);
                Collection<Sudoku.Coord> coordArrayList = Sudoku.checkValue(sudokuGrid, row, cell);

                if (coordArrayList.size() > 0) {
                    conflictPosition.add(position);
                    Log.d("AndroidSudoku", String.format("Conflict size : %d", coordArrayList.size()));
                    for (Sudoku.Coord coord : coordArrayList) {
                        int pos = SudokuGrid.getPosition(coord.row, coord.cell);
                        conflictPosition.add(pos);
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if (conflictPosition.contains(position)) {
            set.setBackgroundColor(getContext().getResources().getColor(R.color.red));
        } else {
            set.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        return set;
    }
}
