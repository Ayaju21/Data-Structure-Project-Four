package application;

import java.time.LocalDate;

//This class for save the data of electricity
public class Electricity implements Comparable<Electricity> {

    //// Attributes of electricity
    private LocalDate date;
    private float israeliLines;
    private float gazaPowerPlant;
    private float egyptianLines;
    private float totalDailySupplyAvailable;
    private float overallDemand;
    private float powerCutsHoursDay;
    private float temperature;

    public Electricity() { // non arguments constructor
        super();
    }

    //// Constructor to make objects of electricity
    public Electricity(LocalDate dates, float israeli_Lines_MWs, float gaza_Power_Plant_MWs, float egyptian_Lines_MWs,
            float total_daily_Supply_available_in_MWs, float overall_demand_in_MWs, float power_Cuts_hours_day_400mg,
            float temp) {
        this.date = dates;
        this.israeliLines = israeli_Lines_MWs;
        this.gazaPowerPlant = gaza_Power_Plant_MWs;
        this.egyptianLines = egyptian_Lines_MWs;
        this.totalDailySupplyAvailable = total_daily_Supply_available_in_MWs;
        this.overallDemand = overall_demand_in_MWs;
        this.powerCutsHoursDay = power_Cuts_hours_day_400mg;
        this.temperature = temp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getEgyptianLines() {
        return egyptianLines;
    }

    public void setEgyptianLines(float egyptianLines) {
        this.egyptianLines = egyptianLines;
    }

    public float getGazaPowerPlant() {
        return gazaPowerPlant;
    }

    public void setGazaPowerPlant(float gazaPowerPlant) {
        this.gazaPowerPlant = gazaPowerPlant;
    }

    public float getIsraeliLines() {
        return israeliLines;
    }

    public void setIsraeliLines(float israeliLines) {
        this.israeliLines = israeliLines;
    }

    public float getOverallDemand() {
        return overallDemand;
    }

    public void setOverallDemand(float overallDemand) {
        this.overallDemand = overallDemand;
    }

    public float getPowerCutsHoursDay() {
        return powerCutsHoursDay;
    }

    public void setPowerCutsHoursDay(float powerCutsHoursDay) {
        this.powerCutsHoursDay = powerCutsHoursDay;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temp) {
        this.temperature = temp;
    }

    public float getTotalDailySupplyAvailable() {
        return totalDailySupplyAvailable;
    }

    public void setTotalDailySupplyAvailable(float totalDailySupplyAvailable) {
        this.totalDailySupplyAvailable = totalDailySupplyAvailable;
    }

    @Override
    public int compareTo(Electricity day) {
        return this.getDate().compareTo(day.getDate());
    }

    public String toFileString() {
        return date
                + "," + israeliLines
                + "," + gazaPowerPlant
                + "," + egyptianLines
                + "," + totalDailySupplyAvailable
                + "," + overallDemand
                + "," + powerCutsHoursDay
                + "," + temperature;
    }

    @Override
    public String toString() {
        return "{" + "date : " + date
                + ", israeli_lines : " + israeliLines
                + ", gaza_power_plant : " + gazaPowerPlant
                + ", egyptian_lines : " + egyptianLines
                + ", total_daily_supply available : " + totalDailySupplyAvailable
                + ", overall_demand : " + overallDemand
                + ", power_cuts_hours_day : " + powerCutsHoursDay
                + ", temp : " + temperature + '}';
    }

}