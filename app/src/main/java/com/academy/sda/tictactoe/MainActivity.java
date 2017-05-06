package com.academy.sda.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.academy.sda.tictactoe.logic.Coordinates;
import com.academy.sda.tictactoe.logic.Game;
import com.academy.sda.tictactoe.view.BoardUtilities;

public class MainActivity extends AppCompatActivity {

    private BoardUtilities boardUtilities;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.board);

        Game game = new Game();
        this.boardUtilities = new BoardUtilities(this, tableLayout);
        boardUtilities.drawBoard(game);

    }

    public void onClick(View view) {
        Button button = (Button) view;
        Coordinates coordinates =
                new Coordinates(((Coordinates) button.getTag()).getRow(),
                        ((Coordinates) button.getTag()).getColumn());


    }


    private void makeShortToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

}
