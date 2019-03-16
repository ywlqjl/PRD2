package com.yanwenli.prd_2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlay extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView = findViewById(R.id.video_view);
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+" Path name ";
//        videoView.setVideoPath();
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/lion_chroma"));


        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);

    }
}
