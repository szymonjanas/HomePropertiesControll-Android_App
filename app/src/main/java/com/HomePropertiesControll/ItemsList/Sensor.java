package com.HomePropertiesControll.ItemsList;

import java.util.UUID;

public class Sensor {
    private String uuid;
    private String name;
    private String type;
    private String location;
    private int level;


    public Sensor(String uuid, String name, String type, String location, int level) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.location = location;
        this.level = level;
    }

    public String getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public int getLevel() {
        return level;
    }
}
