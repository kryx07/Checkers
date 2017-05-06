package com.academy.sda.tictactoe.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.academy.sda.tictactoe.logic.Coordinates;
import com.academy.sda.tictactoe.logic.Game;

public class BoardUtilities extends Activity {

    private Context context;
    private TableLayout tableLayout;

    private boolean firstMove = true;
    private Coordinates firstCoordinates;

    public BoardUtilities(Context context, TableLayout tableLayout) {
        this.context = context;
        this.tableLayout = tableLayout;
    }

    /*public void drawBoard(TableLayout tableLayout) {

        for (int i = 0; i < 8; ++i) {
            TableRow row = new TableRow(context);
            tableLayout.addView(row);

            for (int j = 0; j < 8; ++j) {
                final Button button = new Button(context);
                button.setLayoutParams(new TableRow.LayoutParams(135, 135));

                button.setTag(new Coordinates(i, j));
                if (isBlack(button)) {
                    //button.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                    button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
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
    }*/

    public void updateBoard(final Game game) {
        int[][] board = game.getBoard();

        for (int i = 0; i < board.length; ++i) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            for (int j = 0; j < board.length; ++j) {
                Button button = (Button) row.getChildAt(i);
                if (board[i][j] == Game.PLAYER_A) {
                    button.setText("A");
                } else if (board[i][j] == Game.PLAYER_B) {
                    button.setText("B");
                }
            }

        }
    }

    public void drawBoard(final Game game) {
        int[][] board = game.getBoard();

        //tableLayout=new TableLayout(context);

        for (int i = 0; i < board.length; ++i) {
            TableRow row = new TableRow(context);
            tableLayout.addView(row);

            for (int j = 0; j < board.length; ++j) {
                final Button button = new Button(context);
                button.setLayoutParams(new TableRow.LayoutParams(135, 135));

                button.setTag(new Coordinates(i, j));

                if (isBlack(button)) {
                    button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                }
                if (board[i][j] == Game.PLAYER_A) {
                    button.setText("A");
                } else if (board[i][j] == Game.PLAYER_B) {
                    button.setText("B");
                }
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        makeShortToast(button.getTag().toString());
                        Coordinates coordinates = (Coordinates) button.getTag();
                        if (firstMove) {
                            firstCoordinates = coordinates;
                            firstMove = false;
                        } else {
                            Game.MoveType moveType =game.makeMove(firstCoordinates, coordinates);
                            Log.d(this.getClass().getSimpleName(), moveType.toString());
                            if ( moveType== Game.MoveType.MOVE_FINAL) {
                                updateBoard(game);
                            }
                            firstMove = true;
                        }
                    }
                });

                row.addView(button);
            }
        }
    }


    private void makeShortToast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    private boolean isBlack(Button button) {
        return (((Coordinates) button.getTag()).getRow() +
                ((Coordinates) button.getTag()).getColumn()) % 2 == 0;
    }


}
