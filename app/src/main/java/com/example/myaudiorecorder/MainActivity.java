package com.example.myaudiorecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static int MICROPHONE_CODE = 200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Raj PR6");

        imageView = findViewById(R.id.image);

        if (ismicrophonepresent()){
            getMicrophonePermission();
        }
    }

    public void btnRecordPressed(View v){

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getRecordingFilepath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();

            imageView.animate().rotationBy(360).start();

            Toast.makeText(this, "Recording An Audio...", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void btnStopPressed(View v){

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        imageView.animate().rotationBy(360).start();

        Toast.makeText(this, "Recording Is Stopped...", Toast.LENGTH_SHORT).show();
    }
    public void btnPlayPressed(View v){

        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(getRecordingFilepath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            imageView.animate().rotationBy(360).start();
            Toast.makeText(this, "Recording Is Playing...", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean ismicrophonepresent(){
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else {
            return false;
        }
    }

    private void getMicrophonePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_CODE);
        }
    }

    private String getRecordingFilepath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory,"testRecordingFile"+".mp3");
        return file.getPath();
    }
}