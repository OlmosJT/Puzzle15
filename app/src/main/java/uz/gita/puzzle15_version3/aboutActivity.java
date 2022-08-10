package uz.gita.puzzle15_version3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class aboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.btn_back).setOnClickListener(view -> {
            homeActivity.playSoundBtn();
            onBackPressed();
        });
    }

    public static void start(Activity activity){
        Intent intent = new Intent(activity, aboutActivity.class);
        activity.startActivity(intent);
    }
}