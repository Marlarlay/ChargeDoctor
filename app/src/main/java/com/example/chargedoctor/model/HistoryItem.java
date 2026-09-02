package com.example.chargedoctor.model;


public class HistoryItem {

    private String date;
    private int health;
    private int current;
    private float temp;
    private String status;
    private String cableName;
    private String type;

    public HistoryItem(
            String date,
            int health,
            int current,
            float temp,
            String status,
            String cableName,
            String type) {

        this.date = date;
        this.health = health;
        this.current = current;
        this.temp = temp;
        this.status = status;
        this.cableName = cableName;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public int getHealth() {
        return health;
    }

    public int getCurrent() {
        return current;
    }

    public float getTemp() {
        return temp;
    }

    public String getStatus() {
        return status;
    }

    public String getCableName() {
        return cableName;
    }
    public String getType() { return type; }
}

