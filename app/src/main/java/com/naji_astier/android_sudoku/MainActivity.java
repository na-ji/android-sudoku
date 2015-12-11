package com.naji_astier.android_sudoku;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SudokuGrid sudokuGrid = Sudoku.getExampleGrid();

        final SudokuAdapter<Element> arrayAdapter = new SudokuAdapter<>(this, R.layout.item_sudoku, sudokuGrid);

        final SquareGridView gridView = (SquareGridView) findViewById(R.id.gridView1);
        gridView.setAdapter(arrayAdapter);


        final Handler handler = new Handler();
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        final SudokuGrid sudokuGrid = Sudoku.generateValidGrid();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                arrayAdapter.setSudokuGrid(sudokuGrid);
                                gridView.setAdapter(arrayAdapter);
                            }
                        });
                    }
                };
                thread.start();
            }
        });
    }
}
