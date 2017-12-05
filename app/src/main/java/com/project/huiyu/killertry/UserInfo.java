package com.project.huiyu.killertry;

import java.util.ArrayList;

public class UserInfo {
    private String nickName = "me";
    private int id;
    private int roomNum;
    private int roleCode;
    private int seatId;
    private boolean isHost = false;
    private boolean hasRescue = false;

    private static UserInfo userInstance;
    private static ArrayList<UserInfo> usersArray;

    public void setHasRescue(boolean hasRescue) {
        this.hasRescue = hasRescue;
    }
    public boolean getHasRescue() {
        return this.hasRescue;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return this.seatId;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }
    public int getRoomNum() {
        return this.roomNum;
    }
    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }
    public int getRoleCode() {
        //    平民      1
        //    狼人      2
        //    预言家    3
        //    女巫      4
        //    守卫      5
        return this.roleCode;
    }
    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }
    public boolean getHost() {
        return this.isHost;
    }
    public static UserInfo getUserInstance() {
        if (userInstance == null) {
            userInstance = new UserInfo();

        }
        return userInstance;
    }
    public static ArrayList<UserInfo> getUsersArray() {
        if (usersArray == null) {
            usersArray = new ArrayList<>();
            //usersArray.add(getUserInstance());
        }
        return usersArray;
    }
}
