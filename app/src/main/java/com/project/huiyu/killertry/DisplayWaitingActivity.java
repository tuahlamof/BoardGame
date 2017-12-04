package com.project.huiyu.killertry;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class DisplayWaitingActivity extends AppCompatActivity {

    private static Button startButton;
    private static TextView hostStartGame;
    private static Button enterButton;
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                startButton.setVisibility(View.VISIBLE);
            } else if (msg.what == 2) {
                hostStartGame.setVisibility(View.VISIBLE);
                enterButton.setVisibility(View.VISIBLE);
            }
        }
    };
    public static Handler getmHandler() {
        return mHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_waiting);
        startButton = findViewById(R.id.stop_waiting_and_start);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startTheGame();
                enterTheGame();
            }
        });
        startButton.setVisibility(View.INVISIBLE);
        hostStartGame = findViewById(R.id.host_start_the_game);
        hostStartGame.setVisibility(View.INVISIBLE);
        enterButton = findViewById(R.id.enter_the_game);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTheGame();
            }
        });
        enterButton.setVisibility(View.INVISIBLE);

    }
    private void startTheGame() {
        JSONObject startGameJson = new JSONObject();
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", UserInfo.getUserInstance().getId());
            temp.put("isGameStart", true);
            startGameJson.put("type", "startGame");
            startGameJson.put("data", temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EchoWebSocketListener.getWsInstance().send(startGameJson.toString());
    }
    private void enterTheGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
