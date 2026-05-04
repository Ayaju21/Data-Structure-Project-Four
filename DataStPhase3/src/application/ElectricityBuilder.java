package application;


import java.time.LocalDate;

public class ElectricityBuilder {

    private LocalDate date;
    private float israeliLines;
    private float gazaPowerPlant;
    private float egyptianLines;
    private float totalDailySupplyAvailable;
    private float overallDemand;
    private float powerCutsHoursDay;
    private float temperature;

    public ElectricityBuilder withEgyptianLines(float egyptianLines) {
        this.egyptianLines = egyptianLines;
        return this;
    }

    public ElectricityBuilder withGazaPowerPlant(float gazaPowerPlant) {
        this.gazaPowerPlant = gazaPowerPlant;
        return this;
    }

    public ElectricityBuilder withOverallDemand(float overallDemand) {
        this.overallDemand = overallDemand;
        return this;
    }

    public ElectricityBuilder withTotalDailySupplyAvailable(float totalDailySupplyAvailable) {
        this.totalDailySupplyAvailable = totalDailySupplyAvailable;
        return this;
    }

    public ElectricityBuilder withPowerCutsHoursDay(float powerCutsHoursDay) {
        this.powerCutsHoursDay = powerCutsHoursDay;
        return this;
    }

    public ElectricityBuilder withTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public ElectricityBuilder withIsraeliLines(float israeliLines) {
        this.israeliLines = israeliLines;
        return this;
    }

    public ElectricityBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ElectricityBuilder withDate(String date) {
        return this.withDate(LocalDate.parse(date));
    }

    public Electricity build() {
        return new Electricity(date, israeliLines, gazaPowerPlant, egyptianLines, totalDailySupplyAvailable,
                overallDemand, powerCutsHoursDay, temperature);
    }


}
