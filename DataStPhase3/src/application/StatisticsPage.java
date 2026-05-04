package application;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StatisticsPage {

  private static double screenWidth = 1300;
  private static double screenHeight = 900;

  private static final double TITLE_HEIGHT = screenHeight * 0.15;
  private static final double TITLE_WIDTH = screenWidth;

  private static final double FIELDS_HEIGHT = screenHeight * 0.20;
  private static final double FIELDS_WIDTH = screenWidth;

  private static final double BODY_HEIGHT = screenHeight - (TITLE_HEIGHT + FIELDS_HEIGHT);
  private static final double BODY_WIDTH = screenWidth;

  private static final double ROW_HEIGHT = BODY_HEIGHT / 10;
  private static final double ROW_WIDTH = BODY_WIDTH;

  private static TextField yearField;
  private static TextField monthField;
  private static TextField dayField;
  private static VBox calculation;

  public static Parent initStatisticsScreen(Stage stage) {
    // title
    Label title = new Label("Data Statistics");
    title.setPrefHeight(TITLE_HEIGHT);
    title.setPrefWidth(TITLE_WIDTH);
    title.setAlignment(Pos.CENTER);
    title.setFont(Font.font("System", FontWeight.BOLD, 30));

    // body

    HBox fields = new HBox();
    fields.setPrefHeight(FIELDS_HEIGHT);
    fields.setPrefWidth(FIELDS_WIDTH);
    fields.setSpacing(50);
    fields.setAlignment(Pos.CENTER);

    yearField = new TextField();
    monthField = new TextField();
    dayField = new TextField();
    Button btn = new Button("Compute");
    btn.setStyle("-fx-background-color: #589;");
    btn.setCursor(Cursor.HAND);
    btn.setOnAction(e -> calculate());

    fields.getChildren().addAll(
        initField("Year", yearField),
        initField("Month", monthField),
        initField("Day", dayField),
        initField("", btn));

    HBox header = initRow("", "Israeli Lines", "Gaza Power Plant", "Egyptian Lines", "daily Supply available",
        "Overall demand", "Power Cuts hours day", "Temperature");
    calculation = new VBox();
    calculation.setPrefHeight(BODY_HEIGHT - ROW_HEIGHT);
    VBox body = new VBox(fields, new VBox(20, header, calculation));
    body.setSpacing(50);
    body.setLayoutY(TITLE_HEIGHT);
    body.setAlignment(Pos.CENTER);
    body.setPrefHeight(BODY_HEIGHT);
    body.setPrefWidth(BODY_WIDTH);

    return new AnchorPane(title, body);
  }

  private static void calculate() {
    String year = yearField.getText();
    String month = monthField.getText();
    String day = dayField.getText();

    List<Electricity> data = Structure.searchInData(0, -1, year + "-" + month + "-" + day);
    float[] _sum = new float[7];
    float[] _avg = new float[7];
    float[] _max = { Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
        Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY };
    float[] _min = { Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
        Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY };

    for (Electricity record : data) {
      float x = record.getIsraeliLines();
      x = round(x);
      _sum[0] += x;
      _max[0] = Math.max(_max[0], x);
      _min[0] = Math.min(_min[0], x);

      x = record.getGazaPowerPlant();
      x = round(x);
      _sum[1] += x;
      _max[1] = Math.max(_max[1], x);
      _min[1] = Math.min(_min[1], x);

      x = record.getEgyptianLines();
      x = round(x);
      _sum[2] += x;
      _max[2] = Math.max(_max[2], x);
      _min[2] = Math.min(_min[2], x);

      x = record.getTotalDailySupplyAvailable();
      x = round(x);
      _sum[3] += x;
      _max[3] = Math.max(_max[3], x);
      _min[3] = Math.min(_min[3], x);

      x = record.getOverallDemand();
      x = round(x);
      _sum[4] += x;
      _max[4] = Math.max(_max[4], x);
      _min[4] = Math.min(_min[4], x);

      x = record.getPowerCutsHoursDay();
      x = round(x);
      _sum[5] += x;
      _max[5] = Math.max(_max[5], x);
      _min[5] = Math.min(_min[5], x);

      x = record.getTemperature();
      x = round(x);
      _sum[6] += x;
      _max[6] = Math.max(_max[6], x);
      _min[6] = Math.min(_min[6], x);
    }
    for (int i = 0; i < _avg.length; i++) {
      float x = _sum[i] / data.size();
      x = round(x);
      _avg[i] += x;
    }

    calculation.getChildren().clear();
    calculation.getChildren().addAll(
        initRow("SUMMATION", _sum[0], _sum[1], _sum[2], _sum[3], _sum[4], _sum[5], _sum[6]),
        initRow("AVERAGE", _avg[0], _avg[1], _avg[2], _avg[3], _avg[4], _avg[5], _avg[6]),
        initRow("MAXIMUM", _max[0], _max[1], _max[2], _max[3], _max[4], _max[5], _max[6]),
        initRow("MINIMUN", _min[0], _min[1], _min[2], _min[3], _min[4], _min[5], _min[6]));
  }

  private static float round(float n) {
    n *= 1000;
    n = (int) n;
    n /= 1000;
    return n;
  }

  private static Group initField(String text, Node field) {
    if (field instanceof TextField) {
      ((TextField) field).setPrefWidth(BODY_WIDTH / 8);
    } else if (field instanceof Button) {
      ((Button) field).setPrefWidth(BODY_WIDTH / 8);
    }
    if (field instanceof DatePicker) {
      ((DatePicker) field).setPrefWidth(BODY_WIDTH / 8);
    }
    Label label = new Label(text);
    label.setPrefWidth(BODY_WIDTH / 8);
    label.setLayoutY(-22);
    Group g = new Group(label, field);
    // g.setLayoutX(layoutX);
    // g.setLayoutY(layoutY);
    return g;
  }

  private static HBox initRow(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8) {
    HBox row = new HBox(
        initTableCell(t1),
        initTableCell(t2),
        initTableCell(t3),
        initTableCell(t4),
        initTableCell(t5),
        initTableCell(t6),
        initTableCell(t7),
        initTableCell(t8));

    row.setPrefHeight(ROW_HEIGHT);
    row.setPrefWidth(ROW_WIDTH);
    row.setAlignment(Pos.CENTER);
    return row;
  }

  private static Label initTableCell(Object text) {
    Label label = new Label(text.toString());
    label.setWrapText(true);
    label.setAlignment(Pos.CENTER);
    label.setPrefHeight(ROW_HEIGHT);
    label.setPrefWidth(ROW_WIDTH / 8);
    label.setFont(Font.font("System", FontWeight.BOLD, 15));
    return label;
  }
}