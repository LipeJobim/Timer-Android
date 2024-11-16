package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView countdownTimer;
    private EditText inputTime;
    private Button setTimeButton, startButton;
    private CountDownTimer timer;

    private long timeInMillis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        countdownTimer = findViewById(R.id.countdown_timer);
        inputTime = findViewById(R.id.input_time);
        setTimeButton = findViewById(R.id.set_time);
        startButton = findViewById(R.id.start);


        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputTime.getText().toString();
                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, insira um tempo válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                long inputSeconds = Long.parseLong(input);
                timeInMillis = inputSeconds * 1000;
                updateCountdownText();
            }
        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeInMillis == 0) {
                    Toast.makeText(MainActivity.this, "Defina um tempo antes de começar", Toast.LENGTH_SHORT).show();
                    return;
                }
                startTimer();
            }
        });
    }


    private void startTimer() {
        timer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                countdownTimer.setText("00:00:00");
                Toast.makeText(MainActivity.this, "O tempo acabou!", Toast.LENGTH_SHORT).show();


                MediaPlayer mysong = MediaPlayer.create(MainActivity.this, R.raw.som);
                mysong.start();
            }
        }.start();
    }


    private void updateCountdownText() {
        long hours = (timeInMillis / 1000) / 3600;
        long minutes = ((timeInMillis / 1000) % 3600) / 60;
        long seconds = (timeInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        countdownTimer.setText(timeLeftFormatted);
    }
}
