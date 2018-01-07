package com.snake.jake.podcastking;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button btnPlay, btnDecrement, btnIncrement;
    private TextView tvMediaInfo, tvElapsedTime, tvTotalTime;
    private SeekBar seekBar;
    private Handler handler = new Handler();

    private DecimalFormat decimalFormat = new DecimalFormat("00");

    private static final int FORWARD_TIME = 5000;
    private static final int BACKWARD_TIME = 5000;

    private Runnable progressUpdater = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                setElapsedTime();
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mediaPlayer = MediaPlayer.create(this, R.raw.clueless);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnDecrement = (Button) findViewById(R.id.btnBackwardTime);
        btnIncrement = (Button) findViewById(R.id.btnForwardTime);
        tvElapsedTime = (TextView) findViewById(R.id.tvElapsedTime);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);

        btnPlay.setOnClickListener(this::playPause);
        btnDecrement.setOnClickListener(this::decrement);
        btnIncrement.setOnClickListener(this::increment);
        tvMediaInfo = (TextView) findViewById(R.id.tvMediaInfo);
        tvMediaInfo.setText("Clueless.mp3 now playing");


        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        seekBar.setMax(mediaPlayer.getDuration());

        setTotalTime();
    }

    public void playPause(View view) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
                handler.postDelayed(progressUpdater, 100);
            }
        }
    }

    public void increment(View view) {
        int duration = mediaPlayer.getDuration();
        int currentTime = mediaPlayer.getCurrentPosition();

        if ((currentTime + FORWARD_TIME) < duration) {
            mediaPlayer.seekTo(currentTime + FORWARD_TIME);
        }

    }

    public void decrement(View view) {
        int targetTime = mediaPlayer.getCurrentPosition() - BACKWARD_TIME;
        if (targetTime > 0) {
            mediaPlayer.seekTo(targetTime);
        }
    }

    private void setElapsedTime() {
        int elapsed = mediaPlayer.getCurrentPosition();
        tvElapsedTime.setText(String.format
                (
                        "%s:%s",
                        decimalFormat.format(TimeUnit.MILLISECONDS.toMinutes(elapsed)),
                        decimalFormat.format(TimeUnit.MILLISECONDS.toSeconds(elapsed))
                ));

    }

    private void setTotalTime() {
        int total = mediaPlayer.getDuration();
        tvTotalTime.setText(String.format
                (
                        "%s:%s",
                        decimalFormat.format(TimeUnit.MILLISECONDS.toMinutes(total)),
                        decimalFormat.format(TimeUnit.MILLISECONDS.toSeconds(total))
                ));
    }
}
