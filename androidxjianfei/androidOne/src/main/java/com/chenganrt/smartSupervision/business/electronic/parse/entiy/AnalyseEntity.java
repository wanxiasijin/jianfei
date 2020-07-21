package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;


/**
 * Created by Administrator on 2019/5/30.
 */

public class AnalyseEntity extends Head implements Serializable {
    private static final long serialVersionUID = -4202472701980049773L;

    private int unsigned;
    private String unsignedRate;
    private int confirmed;
    private String confirmedRate;
    private int cancelled;
    private String cancelledRate;
    private int signed;
    private String signedRate;
    private int toSign;
    private String toSignRate;
    private int soilExcepted;
    private String soilExceptedRate;
    private int autoConfirmed;
    private String autoConfirmedRate;

    private String percentage;
    private String type;
    private int value;


    public String getUnsignedRate() {
        return unsignedRate;
    }

    public void setUnsignedRate(String unsignedRate) {
        this.unsignedRate = unsignedRate;
    }


    public String getConfirmedRate() {
        return confirmedRate;
    }

    public void setConfirmedRate(String confirmedRate) {
        this.confirmedRate = confirmedRate;
    }


    public String getCancelledRate() {
        return cancelledRate;
    }

    public void setCancelledRate(String cancelledRate) {
        this.cancelledRate = cancelledRate;
    }


    public String getSignedRate() {
        return signedRate;
    }

    public void setSignedRate(String signedRate) {
        this.signedRate = signedRate;
    }

    public String getPercentage() {
        return percentage;
    }

    public int getUnsigned() {
        return unsigned;
    }

    public void setUnsigned(int unsigned) {
        this.unsigned = unsigned;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public int getSigned() {
        return signed;
    }

    public void setSigned(int signed) {
        this.signed = signed;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public AnalyseEntity(String percentage) {
        this.percentage = percentage;
    }

    public int getToSign() {
        return toSign;
    }

    public void setToSign(int toSign) {
        this.toSign = toSign;
    }

    public String getToSignRate() {
        return toSignRate;
    }

    public void setToSignRate(String toSignRate) {
        this.toSignRate = toSignRate;
    }

    public AnalyseEntity(int value, String percentage, String type) {
        this.percentage = percentage;
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSoilExcepted() {
        return soilExcepted;
    }

    public void setSoilExcepted(int soilExcepted) {
        this.soilExcepted = soilExcepted;
    }

    public String getSoilExceptedRate() {
        return soilExceptedRate;
    }

    public void setSoilExceptedRate(String soilExceptedRate) {
        this.soilExceptedRate = soilExceptedRate;
    }

    public int getAutoConfirmed() {
        return autoConfirmed;
    }

    public void setAutoConfirmed(int autoConfirmed) {
        this.autoConfirmed = autoConfirmed;
    }

    public String getAutoConfirmedRate() {
        return autoConfirmedRate;
    }

    public void setAutoConfirmedRate(String autoConfirmedRate) {
        this.autoConfirmedRate = autoConfirmedRate;
    }

    public AnalyseEntity() {
    }

    public AnalyseEntity(Head head) {
        super(head);
    }

}
