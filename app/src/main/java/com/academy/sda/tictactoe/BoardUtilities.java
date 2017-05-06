package com.academy.sda.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BoardUtilities extends Activity {

    private Context context;

    List<Button> buttons = new ArrayList<>();

    public BoardUtilities(Context context) {
        this.context = context;
    }

    public void drawBoard(TableLayout tableLayout){

        for(int i=0; i<8; ++i) {
            TableRow row = new TableRow(context);
            tableLayout.addView(row);

            for (int j = 0; j < 8; ++j) {
                final Button button = new Button(context);
                button.setLayoutParams(new TableRow.LayoutParams(135,135));

                button.setTag(new Coordinates(i,j));
                if(isBlack(button)){
                    //button.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                    button.setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeShortToast(((Coordinates) button.getTag()).toString());
                    }
                });

                row.addView(button);

            }
        }
    }

    public void setPawns(TableLayout tableLayout){
        for(Button b: buttons) {
            if(b.getTag().equals(new Coordinates(1,2))) {
                b.setText("Dupa");
                //DO WHAT YOU WANT
            }
        }
    }


    private  void makeShortToast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    private boolean isBlack(Button button){
        return (((Coordinates) button.getTag()).getRow() +
                ((Coordinates) button.getTag()).getColumn()) % 2 == 0;
    }
}
