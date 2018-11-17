package com.example.fatih.wirelesscomchat.model;

/**
 * Created by vivek on 2/25/18.
 */

public class Peer {

    private String name;
    private String status;
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Peer{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
