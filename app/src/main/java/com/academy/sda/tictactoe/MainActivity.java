package com.academy.sda.tictactoe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String sign = "X";

    private boolean gameOver = false;

    private BoardUtilities boardUtilities = new BoardUtilities(this);

    private void makeBoard(Context context) {
        /*TableLayout layout = (TableLayout) findViewById(R.id.theBoard);
        layout.addView(new TableRow(this));*/

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));// assuming the parent view is a LinearLayout

        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);// TableLayout is the parent view


        TextView textView = new TextView(context);
        textView.setLayoutParams(rowParams);// TableRow is the parent view
        textView.setText("Dupa");

        tableRow.addView(textView);

        //setContentView(layout);
    }


    private static final int MY_BUTTON = 9000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.board);

        boardUtilities.drawBoard(tableLayout);

        boardUtilities.setPawns(tableLayout);


    }

    public void onButtonClick(View view) {
        if (gameOver) {
            makeShortToast("Esta finito!");
            return;
        }

        Button button = (Button) view;
        if (button.getText().equals("")) {
            button.setText(sign);
            switchSign();
            checkBoard();
        } else {
            makeShortToast("Hey, that's already clicked");
        }

    }

    private void switchSign() {
        if (sign.equals("O")) {
            sign = "X";
        } else {
            sign = "O";
        }
    }

    private void checkBoard() {

    }

    private void checkButtonsForWinner(List<Button> buttons) {
        if ((buttons.get(0).getText().equals("") || buttons.get(1).getText().equals("") || buttons.get(2).getText().equals(""))) {
            return;
        }
        if (buttons.get(0).getText().equals(buttons.get(1).getText()) && buttons.get(1).getText().equals(buttons.get(2).getText())) {
            makeShortToast(buttons.get(0).getText().toString() + " has won!");
            gameOver = true;
        }
    }


    private  void makeShortToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

}
