package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.FType;
import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;


/**
 * Created by Administrator on 2019/12/25.
 */

public class BuildEntity extends Head implements Serializable, FType {
    private static final long serialVersionUID = 6716793494370100404L;

    private String SiteName;
    private String SiteFenceName;
    private String SiteAddress;
    private String BuildingUnit;
    private String ConstructUnit;
    private String MonitorUnit;
    private String SiteId;
    private String SiteFenceId;

    public BuildEntity() {
    }

    public BuildEntity(Head head) {
        super(head);
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getSiteAddress() {
        return SiteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        SiteAddress = siteAddress;
    }

    public String getBuildingUnit() {
        return BuildingUnit;
    }

    public void setBuildingUnit(String buildingUnit) {
        BuildingUnit = buildingUnit;
    }

    public String getConstructUnit() {
        return ConstructUnit;
    }

    public void setConstructUnit(String constructUnit) {
        ConstructUnit = constructUnit;
    }

    public String getMonitorUnit() {
        return MonitorUnit;
    }

    public void setMonitorUnit(String monitorUnit) {
        MonitorUnit = monitorUnit;
    }

    public String getSiteId() {
        return SiteId;
    }

    public void setSiteId(String siteId) {
        SiteId = siteId;
    }

    public String getSiteFenceId() {
        return SiteFenceId;
    }

    public void setSiteFenceId(String siteFenceId) {
        SiteFenceId = siteFenceId;
    }

    public String getSiteFenceName() {
        return SiteFenceName;
    }

    public void setSiteFenceName(String siteFenceName) {
        SiteFenceName = siteFenceName;
    }
}
