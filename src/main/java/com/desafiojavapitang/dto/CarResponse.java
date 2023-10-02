package com.desafiojavapitang.dto;

public class CarResponse {

    private Long id;
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;

    public CarResponse(){
    }

    public CarResponse(Long id, Integer year, String licensePlate, String model, String color) {
        this.id = id;
        this.year = year;
        this.licensePlate = licensePlate;
        this.model = model;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
