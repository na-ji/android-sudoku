package com.naji_astier.android_sudoku;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
