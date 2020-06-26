package com.example.a183311027_makgz.AdvertManager;

public class Adverts {

    private int id;
    private String title;
    private String advertsType;
    private String address;
    private String fee;
    private String city;
    private String district;
    private String floor;
    private String numberOfRooms;
    private byte[] image;


    public Adverts(int id, String title,String advertsType, String city,String district,String floor,byte[] image) {
        this.id = id;
        this.title = title;
        this.advertsType = advertsType;
        this.address = address;
        this.fee = fee;
        this.city = city;
        this.district = district;
        this.floor = floor;
        this.numberOfRooms = numberOfRooms;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getFee() {
        return fee;
    }

    public String getFloor() {
        return floor;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getAdvertsType() {
        return advertsType;
    }

    public String getDistrict() {
        return district;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public byte[] getImage() {
        return image;
    }

}
