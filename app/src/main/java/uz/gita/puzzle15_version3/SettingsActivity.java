package uz.gita.puzzle15_version3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Settings settings;
    SwitchCompat soundSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = Settings.getInstance(this);


        ImageButton btnSound = findViewById(R.id.btnSound);

        if(Settings.getSoundState()){
            btnSound.setBackgroundResource(R.drawable.btn_sound_speaker);
            btnSound.setImageResource(R.drawable.ic_speaker);
        } else {
            btnSound.setBackgroundResource(R.drawable.btn_sound_mute);
            btnSound.setImageResource(R.drawable.ic_mute);
        }


        btnSound.setOnClickListener(view -> {
            homeActivity.playSoundBtn();
            if(Settings.getSoundState()){
                settings.changeSoundState(false);
                btnSound.setBackgroundResource(R.drawable.btn_sound_mute);
                btnSound.setImageResource(R.drawable.ic_mute);
            } else {
                settings.changeSoundState(true);
                btnSound.setBackgroundResource(R.drawable.btn_sound_speaker);
                btnSound.setImageResource(R.drawable.ic_speaker);
            }
        });


        findViewById(R.id.btn_back).setOnClickListener(view -> {
            homeActivity.playSoundBtn();
            onBackPressed();
        });

        findViewById(R.id.btn_clearCache).setOnClickListener(v -> {
            homeActivity.playSoundBtn();
            View view = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.dialog_clear_cache, null);
            AlertDialog dialog = new AlertDialog.Builder(SettingsActivity.this)
                    .setView(view)
                    .setCancelable(false)
                    .create();

            dialog.show();

            view.findViewById(R.id.btnOK).setOnClickListener(view1 -> {
                homeActivity.playSoundBtn();
                settings.clearCache();
                dialog.dismiss();
            });

            view.findViewById(R.id.btnCancel).setOnClickListener(view1 -> {
                homeActivity.playSoundBtn();
                dialog.dismiss();
            });
        });

    }

    public static void start(Activity activity){
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }
}