package uz.gita.puzzle15_version3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

public class winActivity extends AppCompatActivity {
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        mp = MediaPlayer.create(this, R.raw.sound_winner);

        if(Settings.getSoundState()){
            mp.start();
        }

        TextView stepsFinished = findViewById(R.id.stepsFinished);

        stepsFinished.setText(getIntent().getStringExtra("MessageSteps"));

        Button button = findViewById(R.id.btn_newGame);

        button.setOnClickListener(view -> {
            if(Settings.getSoundState())
                homeActivity.playSoundBtn();
            finish();
            gameActivity.start(this);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        super.onBackPressed();
    }

    public static void start(Activity activity, String steps){
        Intent intent = new Intent(activity, winActivity.class);
        intent.putExtra("MessageSteps", steps);
        activity.startActivity(intent);
    }
}