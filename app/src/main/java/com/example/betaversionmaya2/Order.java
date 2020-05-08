package com.example.betaversionmaya2;

import java.util.ArrayList;

public class Order {
    private String uidP, uidB, datetime, dur, remark;
    private ArrayList<Offer> offerlist = new ArrayList<>();
    boolean active;

    public Order (){}
    public Order (String uidP, String uidB, String datetime, String dur,String remark, ArrayList<Offer> offerlist, boolean active) {
        this.uidP=uidP;
        this.uidB=uidB;
        this.datetime=datetime;
        this.dur=dur;
        this.remark=remark;
        this.offerlist=offerlist;
        this.active=active;
    }

    public String getUidP() {
        return uidP;
    }

    public void setUidP(String uidP) {
        this.uidP=uidP;
    }

    public String getUidB() {
        return uidB;
    }

    public void setUidB(String uidB) {
        this.uidB=uidB;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String dur) {
        this.remark=remark;
    }

    public ArrayList<Offer> getOfferlist(){return offerlist;}

    public void setOfferlist (ArrayList<Offer> offerlist){this.offerlist=offerlist;}

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active=active;
    }

}
