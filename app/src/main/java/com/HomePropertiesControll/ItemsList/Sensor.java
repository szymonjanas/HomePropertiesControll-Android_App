package com.HomePropertiesControll.ItemsList;

public class Sensor {
    private String id;
    private String name;
    private String type;
    private String location;
    private boolean state;
    private int level;


    public Sensor(String id, String name, String type, String location, int level, boolean state) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.level = level;
        this.state = state;
    }

    public String getId() {
        return id;
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
