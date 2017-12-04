package com.project.huiyu.killertry;

import android.content.Intent;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by huiyu on 12/1/17.
 */

public class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    public static int dead1;
    public static int dead2;

    private static WebSocket ws;

    private static String status = "";

    public static WebSocket getWsInstance() {
        if (ws == null) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("ws://10.0.2.2:3002").build();
            EchoWebSocketListener listener = new EchoWebSocketListener();
            ws = client.newWebSocket(request, listener);
        }
        return ws;
    }
    public static String getStatus() {
        return status;
    }
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            JSONObject responseJson = new JSONObject(text);
            String type = responseJson.getString("type");
            if (type.equals("loginSuccess")) {
                JSONObject data = responseJson.getJSONObject("data");
                UserInfo.getUserInstance().setId(data.getInt("id"));
                Log.d("mydebugger","id " + UserInfo.getUserInstance().getId());
            } else if (type.equals("roomInfo")) {
                JSONArray users = responseJson.getJSONObject("data").getJSONArray("users");
                for (int i = 0; i < users.length(); i++) {
                    JSONObject curJson = users.getJSONObject(i);
                    String curName = curJson.getString("name");
                    int curId = curJson.getInt("id");
                    int curSeatId = curJson.getInt("seatId");
                    if (curId == UserInfo.getUserInstance().getId()) {
                        UserInfo.getUserInstance().setSeatId(curSeatId);
                        UserInfo.getUsersArray().add(UserInfo.getUserInstance());
                    } else {
                        UserInfo newUser = new UserInfo();
                        newUser.setSeatId(curSeatId);
                        newUser.setId(curId);
                        newUser.setNickName(curName);
                        UserInfo.getUsersArray().add(newUser);
                    }
                }
                Collections.sort(UserInfo.getUsersArray(), new Comparator<UserInfo>() {
                    @Override
                    public int compare(UserInfo userInfo, UserInfo t1) {
                        return userInfo.getId() - t1.getId();
                    }
                });
                if (UserInfo.getUsersArray().get(0).getId() == UserInfo.getUserInstance().getId()) {
                    UserInfo.getUserInstance().setHost(true);
                }
                for (UserInfo one : UserInfo.getUsersArray()) {
                    Log.d("mydebugger", "id: " + one.getId() + " name: " + one.getNickName() + " seat: " + one.getSeatId());
                }
            } else if (type.equals("roomUserUpdate")) {
                JSONObject newUser = responseJson.getJSONObject("data");
                int newId = newUser.getInt("id");
                String newName = newUser.getString("name");
                int newSeatId = newUser.getInt("seatId");
                Log.d("111111", "one user " + newSeatId);
                if (newId != UserInfo.getUserInstance().getId()) {
                    UserInfo newUserInfo = new UserInfo();
                    newUserInfo.setId(newId);
                    newUserInfo.setNickName(newName);
                    newUserInfo.setSeatId(newSeatId);
                    UserInfo.getUsersArray().add(newUserInfo);
                }
            } else if (type.equals("readyToStart")) {
                if (UserInfo.getUserInstance().getHost()) {
                    Message msg = DisplayWaitingActivity.getmHandler().obtainMessage();
                    msg.what = 1;
                    msg.sendToTarget();
                }
            } else if (type.equals("getRole")) {
                int roleCode = responseJson.getJSONObject("data").getInt("role");
                UserInfo.getUserInstance().setRoleCode(roleCode);
                if (!UserInfo.getUserInstance().getHost()) {
                    Message msg = DisplayWaitingActivity.getmHandler().obtainMessage();
                    msg.what = 2;
                    msg.sendToTarget();
                }
            } else if (type.equals("isDark")) {
                //play music and start game
                //play music and end game
            } else if (type.equals("guardRound")) {
                status = "guardRound";
            } else if (type.equals("werewolfRound")) {
                status = "werewolfRound";
            } else if (type.equals("killedUser") && UserInfo.getUserInstance().getRoleCode() == 4) {
                status = "witchRound";
                JSONObject dataJson = responseJson.getJSONObject("data");
                int deadId = dataJson.getInt("id");
                Message msg = GameFragment.getmHandler().obtainMessage();
                msg.what = deadId;
                msg.sendToTarget();
            } else if (type.equals("witchRound") && UserInfo.getUserInstance().getRoleCode() == 4) {
                status = "witchRound";
                String subRound = responseJson.getJSONObject("data").getString("round");
                if (subRound.equals("witchPoisonRound")) {
                    Message msg = GameFragment.getmHandler().obtainMessage();
                    msg.what = 100000;
                    msg.sendToTarget();
                }
            } else if (type.equals("prophetRound") && UserInfo.getUserInstance().getRoleCode() == 3) {
                status = "prophetRound";
            } else if (type.equals("prophetResult") && UserInfo.getUserInstance().getRoleCode() == 3) {
                JSONObject dataJson = responseJson.getJSONObject("data");
                boolean isWolf = dataJson.getBoolean("isWolf");
                Message msg = GameFragment.getmHandler().obtainMessage();
                if(isWolf) {
                    msg.what = 100001;
                } else {
                    msg.what = 100002;
                }
                msg.sendToTarget();
            } else if (type.equals("dead")) {
                status = "dead";
                JSONObject dataJson = responseJson.getJSONObject("data");
                dead1 = dataJson.getInt("u1");
                dead2 = dataJson.getInt("u2");
//                int u1 = dataJson.getInt("u1");
//                int u2 = dataJson.getInt("u2");
//                Message msg = GameFragment.getmHandler().obtainMessage();
//                if (u1 + u2 == -2) {
//                    msg.what = -100001;
//                } else if (u2 == -1) {
//                    msg.what = -u1;
//                } else {
//                    msg.what = u1 * 1000 + u2;
//                }
                Message msg = GameFragment.getmHandler().obtainMessage();
                msg.what = -1002;
                msg.sendToTarget();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Mydebugger","Receiving : " + text);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        Log.d("Mydebugger", "Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d("Mydebugger","Error : " + t.getMessage());
    }
}
