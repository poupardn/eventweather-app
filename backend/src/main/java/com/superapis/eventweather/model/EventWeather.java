package com.superapis.eventweather.model;

public class EventWeather {
    private String eventName;
    private String description;
    private String url;
    private String address;
    private String eventStart;
    private String temp;
    private String feelsLike;
    private String summary;
    private String precChance;

    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the eventStart
     */
    public String getEventStart() {
        return eventStart;
    }

    /**
     * @param eventStart the eventStart to set
     */
    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    /**
     * @return the temp
     */
    public String getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }

    /**
     * @return the feelsLike
     */
    public String getFeelsLike() {
        return feelsLike;
    }

    /**
     * @param feelsLike the feelsLike to set
     */
    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the precChance
     */
    public String getPrecChance() {
        return precChance;
    }

    /**
     * @param precChance the rainChance to set
     */
    public void setPrecChance(String precChance) {
        this.precChance = precChance;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

}