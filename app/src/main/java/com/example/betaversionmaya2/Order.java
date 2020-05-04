package com.example.betaversionmaya2;

import java.util.ArrayList;

public class Order {
    private String uidP, datetime, dur;
    private ArrayList<Offer> offerlist;

    public Order (){}
    public Order (String uidP, String datetime, String dur, ArrayList<Offer> offerlist) {
        this.uidP=uidP;
        this.datetime=datetime;
        this.dur=dur;
        this.offerlist=offerlist;
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
