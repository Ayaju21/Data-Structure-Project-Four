package application;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class StructurePage {

	    private static double screenWidth = 1000;
	    private static double screenHeight = 700;

	    public static Parent initStructureScreen(Stage stage) {
	        AnchorPane anchorPane = new AnchorPane();
	        anchorPane.setPrefSize(screenWidth , screenHeight);

	        VBox vBox = new VBox();
	        vBox.setAlignment(javafx.geometry.Pos.CENTER);
	        vBox.setLayoutX(25.0);
	        vBox.setLayoutY(55.0);
	        vBox.setSpacing(60.0);

	        HBox hBox = new HBox();
	        hBox.setAlignment(javafx.geometry.Pos.CENTER);
	        hBox.setSpacing(20.0);

	        Button button = new Button("Search");
	        button.setDefaultButton(true);
	        button.setMnemonicParsing(false);
	        button.setCursor(javafx.scene.Cursor.HAND);

	        DatePicker datePicker = new DatePicker();
	        datePicker.setPrefHeight(23.0);
	        datePicker.setPrefWidth(250.0);

	        hBox.getChildren().addAll(button , datePicker);

	        TextArea textArea = new TextArea();
	        textArea.setPrefHeight(500.0);
	        textArea.setPrefWidth(950.0);
	        textArea.setFont(Font.font("Consolas"));

	        vBox.getChildren().addAll(hBox , textArea);

	        anchorPane.getChildren().add(vBox);

	        button.setOnAction(e -> {
	            try {
	                String date = datePicker.getValue().toString();
	                String data = getData(date , true);
	                textArea.setText(data);
	            } catch ( Exception ex ) {
	                textArea.setText("Date is NOT correct");
	            }
	        });

	        return anchorPane;
	    }

	    private static String getData(String date , boolean pretify) {
	        date = date.replaceAll(" " , "").replaceAll("-" , "/") + "/ / /";
	        int year = Integer.parseInt(date.split("/")[0].trim());
	        int month = Integer.parseInt(date.split("/")[1].trim());
	        int day = Integer.parseInt(date.split("/")[2].trim());
	        StringBuilder output = new StringBuilder();
	        try {
	            output.append(Structure.getYearsTree(pretify))
	                    .append("===== ===== ===== ===== =====\n")
	                    .append(Structure.getMonthsTree(year , pretify))
	                    .append("===== ===== ===== ===== =====\n")
	                    .append(Structure.getDaysTree(year , month , pretify))
	                    .append("===== ===== ===== ===== =====\n===== ===== ===== ===== =====\n")
	                    
	                    
	                    .append(Structure.getYearsRootHeight()).append("\n")
	                    .append(Structure.getMonthsRootHeight(year)).append("\n")
	                    .append(Structure.getDaysRootHeight(year , month))
	                    .append("\n===== ===== ===== ===== =====\n")
	                    
	                    
	                    .append("Height of year (").append(year).append(") = ")
	                    .append(Structure.getYearHeight(year)).append("\n")
	                    .append("Height of month (").append(month).append(") = ")
	                    .append(Structure.getMonthHeight(year , month)).append("\n")
	                    .append("Height of day (").append(day).append(") = ")
	                    .append(Structure.getDayHeight(year , month , day));
	        } catch ( Exception ex ) {
	            return ex.getMessage();
	        }
	        return output.toString();
	    }

}