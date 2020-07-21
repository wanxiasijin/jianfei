package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2019/7/12.
 */

public class AnalyseDetailEntity extends Head implements Serializable {
    private static final long serialVersionUID = 3734006667056266830L;

    private String count;
    private List<AnalyseContentEntity> contentEntityList;

    public AnalyseDetailEntity() {
    }

    public AnalyseDetailEntity(Head head) {
        super(head);
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<AnalyseContentEntity> getContentEntityList() {
        return contentEntityList;
    }

    public void setContentEntityList(List<AnalyseContentEntity> contentEntityList) {
        this.contentEntityList = contentEntityList;
    }

    public static class AnalyseContentEntity implements Serializable {

        private static final long serialVersionUID = 7559195584713116849L;

        private String date;
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

        public AnalyseContentEntity() {
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

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

        public void setSignedRate(String signedRate) {
            this.signedRate = signedRate;
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
    }

}
