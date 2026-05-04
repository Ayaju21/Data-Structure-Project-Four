package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DataBase {

    // Replace with the actual file path
    public final static String DATA_FILE_PATH = "C:\\Users\\EasyLife\\Downloads\\Electricity.csv";

    public static void loadData() {
        loadData(new File(DATA_FILE_PATH));
    }

    public static void loadData(File file) {
        List<Electricity> electricities = readData(file != null ? file : new File(DATA_FILE_PATH));
        Structure.appendRecords(electricities);
    }

    private static List<Electricity> readData(File file) {
        List<Electricity> electricities = new ArrayList<>();
        try ( Scanner in = new Scanner(file) ) {
            in.nextLine();
            while ( in.hasNext() ) {
                String[] line = in.nextLine().split(",");
                Electricity record = new ElectricityBuilder()
                        .withDate(line[0])
                        .withIsraeliLines(Float.parseFloat(line[1]))
                        .withGazaPowerPlant(Float.parseFloat(line[2]))
                        .withEgyptianLines(Float.parseFloat(line[3]))
                        .withTotalDailySupplyAvailable(Float.parseFloat(line[4]))
                        .withOverallDemand(Float.parseFloat(line[5]))
                        .withPowerCutsHoursDay(Float.parseFloat(line[6]))
                        .withTemperature(Float.parseFloat(line[7]))
                        .build();
                electricities.add(record);
            }

        } catch ( FileNotFoundException e ) {
            System.out.println(e.getMessage());
        }
        return electricities;
    }

    public static void writeData(File file) {
        try ( PrintStream writer = new PrintStream(file) ) {
            writer.println(
                    "Date,Israeli_Lines_MWs,Gaza_Power_Plant_MWs,Egyptian_Lines_MWs,Total_daily_Supply_available_in_MWs,Overall_demand_in_MWs,Power_Cuts_hours_day_400mg,Temp");
            List<YearNode> years = Structure.getData().inOrderList();
            for ( YearNode year : years ) {
                List<MonthNode> months = year.months.inOrderList();
                for ( MonthNode month : months ) {
                    List<DayNode> days = month.days.inOrderList();
                    for ( DayNode day : days ) {
                        String recordLine = day.getRecord().toFileString();
                        writer.println(recordLine);
                    }
                }
            }
            writer.close();
        } catch ( FileNotFoundException e ) {
            System.out.println(e.getMessage());
        }

    }

    public static void writeData() {
        writeData(new File(DATA_FILE_PATH));
    }


}
