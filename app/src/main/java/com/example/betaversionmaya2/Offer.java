package com.example.betaversionmaya2;

public class Offer {
    private String uidB, remark;
    private int price;

    public Offer (){}
    public Offer (String uidB, int price, String remark) {
        this.uidB=uidB;
        this.price=price;
        this.remark=remark;
    }

    public String getUidB() {
        return uidB;
    }

    public void setUidB(String uidB) {
        this.uidB=uidB;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price=price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark=remark;
    }

}
