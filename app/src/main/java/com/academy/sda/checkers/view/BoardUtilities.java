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

import com.academy.sda.checkers.model.Move;
import com.academy.sda.checkers.model.Board;
import com.academy.sda.checkers.model.Field;
import com.academy.sda.checkers.logic.Game;
import com.academy.sda.checkers.model.Pawn;
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

   public void updateBoard(final Game game) {
        Board board = game.getBoard();

        for (int i = 0; i < board.size(); ++i) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < board.size(); ++j) {
                Button button = (Button) row.getChildAt(j);

                Pawn thisPawn = board.getPawn(new Field(i, j));
                if (thisPawn.getPlayer() == Player.PLAYER_A) {
                    button.setText(Player.PLAYER_A.getText());
                } else if (thisPawn.getPlayer() == Player.PLAYER_B) {
                    button.setText(Player.PLAYER_B.getText());
                } else {
                    button.setText(Player.PLAYER_NONE.getText());
                }
                if (thisPawn.isQueen()) {
                    button.setText(button.getText() + "Q");
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

                field = new Field(i, j);
                button.setTag(field);

                if (field.isBlack()) {
                    button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                }

                Pawn thisPawn = board.getPawn(new Field(i, j));
                if (thisPawn.getPlayer() == Player.PLAYER_A) {
                    button.setText(Player.PLAYER_A.getText());
                } else if (thisPawn.getPlayer() == Player.PLAYER_B) {
                    button.setText(Player.PLAYER_B.getText());
                } else {
                    button.setText(Player.PLAYER_NONE.getText());
                }
                if (thisPawn.isQueen()) {
                    button.setText(button.getText() + "Q");
                }
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //makeShortToast(button.getTag().toString());
                        Field field = (Field) button.getTag();
                        if (firstMove) {
                            firstField = field;
                            firstMove = false;
                        } else {
                            Move move = new Move(firstField,field);
                            Move.MoveType moveType = game.attemptMove(move);
                            logDebug(moveType.toString());
                            if (moveType == Move.MoveType.MOVE_FINAL || moveType == Move.MoveType.CAPTURE_FINAL) {
                                updateBoard(game);
                            } else if (moveType == Move.MoveType.CAPTURE_NOT_FINAL) {
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


    private void logDebug(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }

}
