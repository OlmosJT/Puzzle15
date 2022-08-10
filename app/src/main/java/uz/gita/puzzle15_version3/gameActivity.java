package uz.gita.puzzle15_version3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class gameActivity extends AppCompatActivity {
    private TextView[][] cell = new TextView[4][4];
    private ArrayList<Integer> nums = new ArrayList<>(15);
    private int x = 3, y = 3; // started invisible position
    private int count; // initially 0
    MediaPlayer mp;
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ImageButton btnMusicOn = findViewById(R.id.btn_musicOn);
        ImageButton btnMusicOff = findViewById(R.id.btn_musicOff);
        // sound
        mp = MediaPlayer.create(this, R.raw.sound_click);

        settings = Settings.getInstance(this);

        loadViews();
        loadData();
        loadDataToView();

        TextView prevScore = findViewById(R.id.prevScore);
        TextView bestScore = findViewById(R.id.bestScore);

        prevScore.setText(settings.getPrevRecordInString());
        bestScore.setText(settings.getBestRecordInString());

        if(Settings.getSoundState()){
            btnMusicOff.setVisibility(View.INVISIBLE);
            btnMusicOn.setVisibility(View.VISIBLE);
        } else {
            btnMusicOn.setVisibility(View.INVISIBLE);
            btnMusicOff.setVisibility(View.VISIBLE);
        }


        findViewById(R.id.btn_shuffle).setOnClickListener(view -> {
            homeActivity.playSoundBtn();
            shuffle();
            loadDataToView();
            count = -1;
            changeCount();
        });

        btnMusicOn.setOnClickListener(view -> {
            homeActivity.playSoundBtn();
            btnMusicOn.setVisibility(View.INVISIBLE);
            btnMusicOff.setVisibility(View.VISIBLE);
            settings.changeSoundState(false);
        });

        btnMusicOff.setOnClickListener(view -> {
            btnMusicOff.setVisibility(View.INVISIBLE);
            btnMusicOn.setVisibility(View.VISIBLE);
            settings.changeSoundState(true);
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog dialog = new AlertDialog.Builder(gameActivity.this)
                .setTitle("Warning!")
                .setMessage("Do you want to exit the game? Your current progress will be lost.")
                .setPositiveButton("Yes", (d, which) -> super.onBackPressed())
                .setNegativeButton("No", (d1, which) -> {} )
                .setCancelable(false)
                .create();

        dialog.show();
    }

    public static void start(Activity activity){
        Intent intent = new Intent(activity, gameActivity.class);
        activity.startActivity(intent);
    }

    private void loadData() {
        for (int i = 1; i <= 15; i++) {
            nums.add(i);
        }
        shuffle();
    }

    private void shuffle() {
        Collections.shuffle(nums);
        while (!isSolvable(nums)) {
            Collections.shuffle(nums);
        }
    }

    private void loadDataToView() {
        cell[x][y].setVisibility(View.VISIBLE);
        x = 3;
        y = 3;
        cell[x][y].setVisibility(View.INVISIBLE);

        for (int i = 0; i < 15; i++) {
            cell[i / 4][i % 4].setText(String.valueOf(nums.get(i)));
        }
    }

    private void loadViews() {
        ConstraintLayout board = findViewById(R.id.gameboard);
        for (int i = 0; i < board.getChildCount(); i++) {
            cell[i / 4][i % 4] = (TextView) board.getChildAt(i);

            cell[i / 4][i % 4].setTag(i);
            cell[i / 4][i % 4].setOnClickListener(view -> {
                int amount = (Integer) view.getTag();
                move(amount / 4, amount % 4);
            });
        }
    }

    private void move(int i, int j) {
        if ((Math.abs(x - i) == 1 && Math.abs(y - j) == 0) ||
                (Math.abs(x - i) == 0 && Math.abs(y - j) == 1)) {
            cell[x][y].setText(cell[i][j].getText());
            cell[x][y].setVisibility(View.VISIBLE);
            cell[i][j].setVisibility((View.INVISIBLE));
            if(Settings.getSoundState()) mp.start();
            x = i;
            y = j;
            changeCount();

            if (didWin()) {
                gameOver();
            }
        }
    }

    private void changeCount() {
        TextView text = findViewById(R.id.counter);
        count++;
        text.setText(String.valueOf(count));
    }

    private boolean didWin() {
        for (int i = 0; i < 15; i++) {
            if (!cell[i / 4][i % 4].getText().equals("" + (i + 1)))
                return false;
        }
        return true;
    }

    private void gameOver() {
        settings.saveCount(count);
        finish();
        winActivity.start(gameActivity.this, String.valueOf(count));
    }

    private boolean isSolvable(ArrayList<Integer> nums) {
        int parity = 0;
        int gridWidth = (int) Math.sqrt(nums.size());
        int row = 0; // the current row we are on
        int blankRow = 0; // the row with the blank tile

        for (int i = 0; i < nums.size(); i++) {
            if (i % gridWidth == 0) { // advance to next row
                row++;
            }
            if (nums.get(i) == 0) { // the blank tile
                blankRow = row; // save the row on which encountered
                continue;
            }
            for (int j = i + 1; j < nums.size(); j++) {
                if (nums.get(i) > nums.get(j) && nums.get(j) != 0) {
                    parity++;
                }
            }
        }

        if (gridWidth % 2 == 0) { // even grid
            if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
                return parity % 2 == 0;
            } else { // blank on even row; counting from bottom
                return parity % 2 != 0;
            }
        } else { // odd grid
            return parity % 2 == 0;
        }
    }

}