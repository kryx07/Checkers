package com.academy.sda.checkers.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Field;
import com.academy.sda.checkers.logic.Game;
import com.academy.sda.checkers.model.Player;

public class BoardUtilities extends Activity {

    private Context context;
    private TableLayout tableLayout;

    private boolean firstMove = true;
    private Field firstField;

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

                button.setTag(new Field(i, j));
                if (isBlack(button)) {
                    //button.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
                    button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeShortToast(((Field) button.getTag()).toString());
                    }
                });

                row.addView(button);

            }
        }
    }*/

    public void updateBoard(final Game game) {
        Board board = game.getBoard();

        for (int i = 0; i < board.size(); ++i) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < board.size(); ++j) {
                Button button = (Button) row.getChildAt(j);

                Player thisPlayer = board.getPlayer(new Field(i, j));
                if (thisPlayer == Player.PLAYER_A) {
                    button.setText("A");
                } else if (thisPlayer == Player.PLAYER_B) {
                    button.setText("B");
                } else {
                    button.setText("");
                }
            }


        }
    }

    public void drawBoard(final Game game) {
        Board board = game.getBoard();
        Field field;
        for (int i = 0; i < board.size(); ++i) {
            TableRow row = new TableRow(context);
            tableLayout.addView(row);

            for (int j = 0; j < board.size(); ++j) {
                final Button button = new Button(context);
                button.setLayoutParams(new TableRow.LayoutParams(135, 135));

                field=new Field(i,j);
                button.setTag(field);

                if (field.isBlack()) {
                    button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                }
                Player thisPlayer = board.getPlayer(new Field(i, j));
                if (thisPlayer == Player.PLAYER_A) {
                    button.setText("A");
                } else if (thisPlayer == Player.PLAYER_B) {
                    button.setText("B");
                } else {
                    button.setText("");
                }
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        makeShortToast(button.getTag().toString());
                        Field field = (Field) button.getTag();
                        if (firstMove) {
                            firstField = field;
                            firstMove = false;
                        } else {
                            Game.MoveType moveType = game.makeMove(firstField, field);
                            logDebug(moveType.toString());
                            if (moveType == Game.MoveType.MOVE_FINAL) {
                                updateBoard(game);
                            } else if (moveType == Game.MoveType.MOVE_NOT_FINAL) {
                                updateBoard(game);
                                makeShortToast("There is another capture possible");
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
        return (((Field) button.getTag()).getRow() +
                ((Field) button.getTag()).getColumn()) % 2 == 0;
    }

    private void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }

}
