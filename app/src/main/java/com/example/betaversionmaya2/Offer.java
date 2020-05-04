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

    public String getUidP() {
        return uidP;
    }

    public void setUidP(String uidP) {
        this.uidP=uidP;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime=datetime;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur=dur;
    }

}
