package com.example.betaversionmaya2;

public class User {
    private String name, email, phone, uid, address, city, province, zip;
    private Boolean parents;

    public User (){}
    public User (String name, String email, String uid, String phone,String address, String city, String province, String zip,Boolean parents) {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.uid=uid;
        this.address=address;
        this.city=city;
        this.province=province;
        this.zip=zip;
        this.parents=parents;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid=uid;
    }

    public Boolean getParents() {
        return parents;
    }

    public void setParents(Boolean parents) {
        this.parents=parents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address=address;
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city=city; }

    public String getProvince() { return province; }

    public void setProvince(String province) { this.province=province; }

    public String getZip() { return zip; }

    public void setZip(String zip) { this.zip=zip; }

}

