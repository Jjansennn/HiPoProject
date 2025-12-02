package com.example.hipoproject;
import java.util.Date;

public class Plant {
    private String name;
    private String status;
    private int progress;
    private int imageRes;
    private Date plantingDate;
    private Date harvestDate;

    public Plant(String name, String status, int progress, int imageRes, Date plantingDate, Date harvestDate) {
        this.name = name;
        this.status = status;
        this.progress = progress;
        this.imageRes = imageRes;
        this.plantingDate = plantingDate;
        this.harvestDate = harvestDate;
    }

    // getters
    public String getName() { return name; }
    public String getStatus() { return status; }
    public int getProgress() { return progress; }
    public int getImageRes() { return imageRes; }
    public Date getPlantingDate() { return plantingDate; }
    public Date getHarvestDate() { return harvestDate; }
}
