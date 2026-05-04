package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileOperators {


    public static void loadData() {
        List<Electricity> electricitys = readData(DataBase.DATA_FILE_PATH);
        electricitys.forEach(record -> {

        });
    }

    public static List<Electricity> readData(String path) {
        List<Electricity> electricitys = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(path));
            // skip first line (titles)
            scanner.nextLine();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(","); // split depends on ","
                try {
                    // read rest of data for the record
                    Electricity record = new ElectricityBuilder()
                            .withDate(tokens[0])
                            .withIsraeliLines(Float.parseFloat(tokens[1]))
                            .withGazaPowerPlant(Float.parseFloat(tokens[2]))
                            .withEgyptianLines(Float.parseFloat(tokens[3]))
                            .withTotalDailySupplyAvailable(Float.parseFloat(tokens[4]))
                            .withOverallDemand(Float.parseFloat(tokens[5]))
                            .withPowerCutsHoursDay(Float.parseFloat(tokens[6]))
                            .withTemperature(Float.parseFloat(tokens[7]))
                            .build();
                    // data.addrecord(record);
                    electricitys.add(record);
                } catch (NumberFormatException | DateTimeParseException ex) {
                    // Handle number format parsing errors
                    System.out.println(ex.getMessage());
                }

            }
        } catch (FileNotFoundException ex) {
        }
        return electricitys;
    }

}