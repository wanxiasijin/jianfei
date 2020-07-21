package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2019/12/25.
 */

public class VehicleEntity extends Head implements Serializable {
    private static final long serialVersionUID = -1879465692943822365L;

    private String vehicleNo;

    private List<VehicleEntity> vehicleEntities;

    public VehicleEntity() {
    }

    public VehicleEntity(Head head) {
        super(head);
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public List<VehicleEntity> getVehicleEntities() {
        return vehicleEntities;
    }

    public void setVehicleEntities(List<VehicleEntity> vehicleEntities) {
        this.vehicleEntities = vehicleEntities;
    }
}
