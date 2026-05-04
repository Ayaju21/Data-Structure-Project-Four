package application;

import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FormPage {

  private static double screenWidth = 1000;
  private static double screenHeight = 500;

  private static final double TITLE_HEIGHT = screenHeight * 0.25;
  private static final double TITLE_WIDTH = screenWidth;

  private static final double FORM_HEIGHT = screenHeight - TITLE_HEIGHT;
  private static final double FORM_WIDTH = screenWidth;

  public static Parent initFormPage(Stage stage, Electricity record) {
    // title
    Label title = new Label(record == null ? "Add New Data" : "Update Data");
    title.setPrefHeight(TITLE_HEIGHT);
    title.setPrefWidth(TITLE_WIDTH);
    title.setAlignment(Pos.CENTER);
    title.setFont(Font.font("System", FontWeight.BOLD, 30));

    // form
    Pane form = initForm(stage, record);
    Parent root = new AnchorPane(title, form);
    return root;
  }

  private static Pane initForm(Stage stage, Electricity record) {
    DatePicker field1 = new DatePicker();
    TextField field2 = new TextField();
    TextField field3 = new TextField();
    TextField field4 = new TextField();
    TextField field5 = new TextField();
    TextField field6 = new TextField();
    TextField field7 = new TextField();
    TextField field8 = new TextField();
    if (record != null) {
      field1.setDisable(record != null);
      field1.setValue(record.getDate());
      field2.setText("" + record.getIsraeliLines());
      field3.setText("" + record.getGazaPowerPlant());
      field4.setText("" + record.getEgyptianLines());
      field5.setText("" + record.getTotalDailySupplyAvailable());
      field6.setText("" + record.getOverallDemand());
      field7.setText("" + record.getPowerCutsHoursDay());
      field8.setText("" + record.getTemperature());
    }

    Button field9 = new Button(record == null ? "Save" : "Update");
    field9.setStyle("-fx-background-color: #397;");
    field9.setCursor(Cursor.HAND);
    field9.setOnAction(e -> {
      boolean done = record == null
          ? handleSave(field1.getValue(),
              Float.parseFloat(field2.getText()),
              Float.parseFloat(field3.getText()),
              Float.parseFloat(field4.getText()),
              Float.parseFloat(field5.getText()),
              Float.parseFloat(field6.getText()),
              Float.parseFloat(field7.getText()),
              Float.parseFloat(field8.getText()))
          : handleEdit(field1.getValue(),
              Float.parseFloat(field2.getText()),
              Float.parseFloat(field3.getText()),
              Float.parseFloat(field4.getText()),
              Float.parseFloat(field5.getText()),
              Float.parseFloat(field6.getText()),
              Float.parseFloat(field7.getText()),
              Float.parseFloat(field8.getText()));
      if (done) {
        MainPage.refreshTable();
        stage.close();
      }

    });

    Pane form = new Pane();
    form.setPrefHeight(FORM_HEIGHT);
    form.setPrefWidth(FORM_WIDTH);
    form.setLayoutY(TITLE_HEIGHT);

    form.getChildren().add(initField("Date", field1, 100, 45));
    form.getChildren().add(initField("Israeli Lines", field2, 400, 45));
    form.getChildren().add(initField("Gaza Power Plant", field3, 700, 45));
    form.getChildren().add(initField("Egyptian Lines", field4, 100, 135));
    form.getChildren().add(initField("daily Supply available", field5, 400, 135));
    form.getChildren().add(initField("Overall demand", field6, 700, 135));
    form.getChildren().add(initField("Power Cuts hours day", field7, 100, 225));
    form.getChildren().add(initField("Temperature", field8, 400, 225));
    form.getChildren().add(initField("", field9, 700, 225));

    return form;
  }

  private static Group initField(String text, Node field, double layoutX, double layoutY) {
    if (field instanceof TextField) {
      ((TextField) field).setPrefWidth(FORM_WIDTH / 5);
    } else if (field instanceof Button) {
      ((Button) field).setPrefWidth(FORM_WIDTH / 5);
    }
    if (field instanceof DatePicker) {
      ((DatePicker) field).setPrefWidth(FORM_WIDTH / 5);
    }
    Label label = new Label(text);
    label.setPrefWidth(FORM_WIDTH / 5);
    label.setLayoutY(-22);
    Group g = new Group(label, field);
    g.setLayoutX(layoutX);
    g.setLayoutY(layoutY);
    return g;
  }

  private static boolean handleSave(LocalDate date, float israeli_Lines_MWs, float gaza_Power_Plant_MWs,
      float egyptian_Lines_MWs,
      float total_daily_Supply_available_in_MWs, float overall_demand_in_MWs, float power_Cuts_hours_day_400mg,
      float temp) {
    boolean appended = Structure.appendRecord(
        new Electricity(
            date,
            israeli_Lines_MWs,
            gaza_Power_Plant_MWs,
            egyptian_Lines_MWs,
            total_daily_Supply_available_in_MWs,
            overall_demand_in_MWs,
            power_Cuts_hours_day_400mg,
            temp));
    Alert message = new Alert(AlertType.INFORMATION);
    message.setHeaderText(appended ? "Added successfully" : "The date is wrong or already exists");
    message.setTitle(appended ?  "Successful operation" : "Error!!");
    message.showAndWait();

    return appended;
  }

  private static boolean handleEdit(LocalDate date, float israeli_Lines_MWs, float gaza_Power_Plant_MWs,
      float egyptian_Lines_MWs,
      float total_daily_Supply_available_in_MWs, float overall_demand_in_MWs, float power_Cuts_hours_day_400mg,
      float temp) {
    boolean appended = Structure.editRecord(
        new Electricity(
            date,
            israeli_Lines_MWs,
            gaza_Power_Plant_MWs,
            egyptian_Lines_MWs,
            total_daily_Supply_available_in_MWs,
            overall_demand_in_MWs,
            power_Cuts_hours_day_400mg,
            temp));
    Alert message = new Alert(AlertType.INFORMATION);
    message.setHeaderText(appended ? "Modified successfully" : "The date is wrong or already doesn't exists");
    message.setTitle(appended ? "Successful operation" : "Error!!!");
    message.showAndWait();

    return appended;
  }

}