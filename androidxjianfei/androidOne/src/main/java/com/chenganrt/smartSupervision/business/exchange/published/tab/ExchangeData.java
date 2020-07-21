package com.chenganrt.smartSupervision.business.exchange.published.tab;

public class ExchangeData {

    private int exchange_type;
    private String project;
    private String waste_type;
    private String area;
    private String amount;
    private String image;
    private String start_time;

    public ExchangeData(int exchange_type, String project, String waste_type, String area, String amount, String image, String start_time) {
        this.exchange_type = exchange_type;
        this.project = project;
        this.waste_type = waste_type;
        this.area = area;
        this.amount = amount;
        this.image = image;
        this.start_time = start_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getExchange_type() {
        return exchange_type;
    }

    public void setExchange_type(int exchange_type) {
        this.exchange_type = exchange_type;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getWaste_type() {
        return waste_type;
    }

    public void setWaste_type(String waste_type) {
        this.waste_type = waste_type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

}
