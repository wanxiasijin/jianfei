package com.chenganrt.smartSupervision.model.response;

import java.io.Serializable;

public class UserTotalBean implements Serializable {

    public int waste_supply;
    public int waste_demand;
    public int multipurpose_use_supply;
    public int multipurpose_use_demand;


    public int getWaste_supply() {
        return waste_supply;
    }

    public void setWaste_supply(int waste_supply) {
        this.waste_supply = waste_supply;
    }

    public int getWaste_demand() {
        return waste_demand;
    }

    public void setWaste_demand(int waste_demand) {
        this.waste_demand = waste_demand;
    }

    public int getMultipurpose_use_supply() {
        return multipurpose_use_supply;
    }

    public void setMultipurpose_use_supply(int multipurpose_use_supply) {
        this.multipurpose_use_supply = multipurpose_use_supply;
    }

    public int getMultipurpose_use_demand() {
        return multipurpose_use_demand;
    }

    public void setMultipurpose_use_demand(int multipurpose_use_demand) {
        this.multipurpose_use_demand = multipurpose_use_demand;
    }
}
