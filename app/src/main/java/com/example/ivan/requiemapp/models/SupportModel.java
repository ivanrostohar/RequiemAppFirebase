package com.example.ivan.requiemapp.models;

/**
 * Created by Ivan on 3.1.2017..
 */

public class SupportModel {
    private boolean drive;
    private String description;

    public SupportModel() {
    }

    public SupportModel(boolean drive, String description) {
        this.drive = drive;
        this.description = description;
    }

    public boolean isDrive() {
        return drive;
    }

    public void setDrive(boolean drive) {
        this.drive = drive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
