package com.breezometer.entities;

/**
 * Created by Nadzer on 16/03/2016.
 */
public class BreezoMeterEntity {
    private String mCountry;
    private String mDescription;
    private String mSport;
    private String mHealth;
    private String mInside;
    private String mColor;
    private int mBreezometerAqi;
    private int mAqi;

    public BreezoMeterEntity() {}

    public String getCountry() {
        return mCountry;
    }

    public String getHealth() {
        return mHealth;
    }

    public int getBreezometerAqi() {
        return mBreezometerAqi;
    }

    public String getSport() {
        return mSport;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getAqi() {
        return mAqi;
    }

    public void setHealth(String mHealth) {
        this.mHealth = mHealth;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public void setBreezometerAqi(int mBreezometerAqi) {
        this.mBreezometerAqi = mBreezometerAqi;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setSport(String mSport) {
        this.mSport = mSport;
    }

    public void setAqi(int mAqi) {
        this.mAqi = mAqi;
    }

    public String getInside() {
        return mInside;
    }

    public void setInside(String mInside) {
        this.mInside = mInside;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }
}
