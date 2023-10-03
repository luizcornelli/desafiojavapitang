package com.desafiojavapitang.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CarRequest {

    private Integer year;
    private String licensePlate;
    private String model;
    private String color;

    public CarRequest(){
    }

    public CarRequest(Integer year, String licensePlate, String model, String color) {
        this.year = year;
        this.licensePlate = licensePlate;
        this.model = model;
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
