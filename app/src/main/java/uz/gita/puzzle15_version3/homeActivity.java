package uz.gita.puzzle15_version3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class homeActivity extends AppCompatActivity {
    Settings settings;
    static MediaPlayer soundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        settings = Settings.getInstance(this);

         soundButton = MediaPlayer.create(this, R.raw.sound_btn);

        findViewById(R.id.btn_about).setOnClickListener(view -> {
            playSoundBtn();
            aboutActivity.start(this);
        });


        findViewById(R.id.btn_exit).setOnClickListener(view -> {
            playSoundBtn();
            finish();
        });

        findViewById(R.id.btn_newGame).setOnClickListener(view -> {
            playSoundBtn();
            gameActivity.start(this);
        });

//        findViewById(R.id.btn_settings).setOnClickListener(view -> {
//            playSoundBtn();
//            SettingsActivity.start(this);
//        });
    }

    public static void playSoundBtn(){
        if(Settings.getSoundState())
            soundButton.start();
    }

    public static void start(Activity activity){
        Intent intent = new Intent(activity, homeActivity.class);
        activity.startActivity(intent);
    }
}