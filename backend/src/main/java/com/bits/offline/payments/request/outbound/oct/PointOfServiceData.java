package com.bits.offline.payments.request.outbound.oct;

public class PointOfServiceData {

    private String motoECIIndicator;
    private String panEntryMode;
    private String posConditionCode;

    public String getMotoECIIndicator() {
        return motoECIIndicator;
    }

    public void setMotoECIIndicator(String motoECIIndicator) {
        this.motoECIIndicator = motoECIIndicator;
    }

    public String getPanEntryMode() {
        return panEntryMode;
    }

    public void setPanEntryMode(String panEntryMode) {
        this.panEntryMode = panEntryMode;
    }

    public String getPosConditionCode() {
        return posConditionCode;
    }

    public void setPosConditionCode(String posConditionCode) {
        this.posConditionCode = posConditionCode;
    }

    public static PointOfServiceData createPointOfServiceData() {
        PointOfServiceData pointOfServiceData = new PointOfServiceData();
        pointOfServiceData.setMotoECIIndicator("0");
        pointOfServiceData.setPanEntryMode("90");
        pointOfServiceData.setPosConditionCode("00");
        return pointOfServiceData;
    }
}
