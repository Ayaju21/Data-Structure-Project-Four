package application;

import java.io.File;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainPage {

    private static int currentPage = 1;
    private static String searchField = "";
    private static final int PAGE_SIZE = 7;

    private static double screenWidth = 1400;
    private static double screenHeight = 700;

    private static final double TITLE_HEIGHT = screenHeight * 0.16;
    private static final double TITLE_WIDTH = screenWidth;

    private static final double FILE_BTNS_HEIGHT = TITLE_HEIGHT / 2;
    private static final double FILE_BTNS_WIDTH = screenWidth / 4;

    private static final double TABLE_HEIGHT = screenHeight - TITLE_HEIGHT;
    private static final double TABLE_WIDTH = screenWidth;

    private static final double TABLE_ACTIONS_HEIGHT = TABLE_HEIGHT * 0.10;
    private static final double TABLE_ACTIONS_WIDTH = TABLE_WIDTH;
    private static final double TABLE_PAGING_HEIGHT = TABLE_HEIGHT * 0.07;
    private static final double TABLE_PAGING_WIDTH = TABLE_WIDTH;

    private static final double TABLE_HEADERS_HEIGHT = TABLE_HEIGHT * 0.10;
    private static final double TABLE_HEADERS_WIDTH = TABLE_WIDTH;

    private static final double TABLE_BODY_HEIGHT = TABLE_HEIGHT
            - (TABLE_ACTIONS_HEIGHT + TABLE_HEADERS_HEIGHT + TABLE_PAGING_HEIGHT);
    private static final double TABLE_BODY_WIDTH = TABLE_WIDTH;

    private static final double TABLE_ROW_HEIGHT = TABLE_BODY_HEIGHT / PAGE_SIZE;
    private static final double TABLE_ROW_WIDTH = TABLE_WIDTH;

    private static VBox tableBody;
    private static Label pageNumberLabel;

    public static Parent initMainScreen(Stage stage) {
        // data loader
        Button loader = new Button("Load Data From File");
        loader.setFont(Font.font("System" , FontWeight.BOLD , 16));
        loader.setLayoutX(40);
        loader.setLayoutY(TITLE_HEIGHT / 4);
        loader.setPrefHeight(FILE_BTNS_HEIGHT);
        loader.setPrefWidth(FILE_BTNS_WIDTH);
        loader.setOnAction(e -> {
            FileChooser loadFileChooser = new FileChooser();
            loadFileChooser.setTitle("Load Gaza File");
            loadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv Files" , "*.csv"));
            File gazaFile = loadFileChooser.showOpenDialog(stage);
            DataBase.loadData(gazaFile);
            refreshTable();
        });
        Button saver = new Button("Save The Data To The File");
        saver.setFont(Font.font("System" , FontWeight.BOLD , 16));
        saver.setLayoutX(screenWidth - (FILE_BTNS_WIDTH + 40));
        saver.setLayoutY(TITLE_HEIGHT / 4);
        saver.setPrefHeight(FILE_BTNS_HEIGHT);
        saver.setPrefWidth(FILE_BTNS_WIDTH);
        saver.setOnAction(e -> {
            FileChooser loadFileChooser = new FileChooser();
            loadFileChooser.setTitle("Save Gaza File");
            loadFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv Files" , "*.csv"));
            File gazaFile = loadFileChooser.showSaveDialog(stage);
            DataBase.writeData(gazaFile);
        });
        // title
        Label title = new Label("Gaza Electricity Supply");
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(TITLE_WIDTH);
        title.setPrefHeight(TITLE_HEIGHT);
        title.setFont(Font.font("System" , FontWeight.BOLD , 35));

        // data table
        // table base
        TextField searchTextField = new TextField();
        searchTextField.setPrefWidth(TABLE_ACTIONS_WIDTH / 6);
        searchTextField.setOnKeyReleased(e -> {
            if ( e.getCode() == KeyCode.ENTER ) {
                handleSearch(searchTextField.getText());
            }
        });

        Button searchBtn = new Button("Search");
        searchBtn.setCursor(Cursor.HAND);
        searchBtn.setOnMouseReleased(e -> {
            handleSearch(searchTextField.getText());
        });

        HBox searchBox = new HBox(searchBtn , searchTextField);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(TABLE_ACTIONS_WIDTH / 50);

        Button refresh = new Button("Structure");
        refresh.setOnMouseReleased(e -> handleStructurePage());
        refresh.setCursor(Cursor.HAND);

        Button add = new Button("Insert");
        add.setStyle("-fx-background-color: #598;");
        add.setOnMouseReleased(e -> handleAddPage());
        add.setCursor(Cursor.HAND);

        Button statistics = new Button("Statistics Screen");
        statistics.setOnMouseReleased(e -> handleStatisticsPage());
        statistics.setCursor(Cursor.HAND);

        HBox actionBox = new HBox(refresh , add , statistics);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.setSpacing(TABLE_ACTIONS_WIDTH / 30);

        HBox tableAction = new HBox(actionBox , searchBox);
        tableAction.setAlignment(Pos.CENTER);
        tableAction.setPrefHeight(TABLE_ACTIONS_HEIGHT);
        tableAction.setPrefWidth(TABLE_ACTIONS_WIDTH);
        tableAction.setSpacing(TABLE_ACTIONS_WIDTH / 2);
        tableAction.setStyle("-fx-background-color: #aaa;");

        HBox tableHeader = new HBox(
                initTableHeaderCell("Date") ,
                initTableHeaderCell("Israeli Lines") ,
                initTableHeaderCell("Gaza Power Plant") ,
                initTableHeaderCell("Egyptian Lines") ,
                initTableHeaderCell("daily Supply available") ,
                initTableHeaderCell("Overall demand") ,
                initTableHeaderCell("Power Cuts hours day") ,
                initTableHeaderCell("Temperature"));
        tableHeader.setStyle("-fx-background-color: #289;");
        tableHeader.setPrefHeight(TABLE_HEADERS_HEIGHT);
        tableHeader.setPrefWidth(TABLE_HEADERS_WIDTH);

        tableBody = new VBox(getDataOnPage());
        tableBody.setPrefHeight(TABLE_BODY_HEIGHT);
        tableBody.setPrefWidth(TABLE_BODY_WIDTH);
        ScrollPane scrollPane = new ScrollPane(tableBody);
        // scrollPane.setPrefHeight(TABLE_BODY_HEIGHT);
        // scrollPane.setPrefWidth(TABLE_BODY_WIDTH);

        VBox tableBase = new VBox(tableAction , tableHeader , scrollPane);
        tableBase.setAlignment(Pos.CENTER);
        tableBase.setPrefHeight(TABLE_HEIGHT);

        // table paging
        Label nextPageLabel = initPageLabel(">");
        nextPageLabel.setOnMouseReleased(e -> gotToNextPage());
        Label prevPageLabel = initPageLabel("<");
        prevPageLabel.setOnMouseReleased(e -> gotToPrevPage());
        pageNumberLabel = new Label("" + currentPage);
        pageNumberLabel.setPrefWidth(170);
        pageNumberLabel.setAlignment(Pos.CENTER);
        pageNumberLabel.setFont(Font.font(30));

        HBox tablePaging = new HBox(prevPageLabel , pageNumberLabel , nextPageLabel);
        tablePaging.setAlignment(Pos.CENTER);
        tablePaging.setPrefHeight(TABLE_PAGING_HEIGHT);
        tablePaging.setPrefWidth(TABLE_PAGING_WIDTH);
        // table
        VBox table = new VBox(tableBase , tablePaging);
        table.setLayoutY(TITLE_HEIGHT);

        AnchorPane root = new AnchorPane(title , loader , saver , table);
        root.setPrefHeight(screenHeight);
        root.setPrefWidth(screenWidth);
        return root;
    }

    private static Label initPageLabel(String text) {
        Label label = new Label(text);
        label.setPrefWidth(35);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font(30));
        label.setCursor(Cursor.HAND);
        return label;
    }

    private static HBox[] getDataOnPage() {
        List<Electricity> data = Structure.searchInData(
                (currentPage - 1) * PAGE_SIZE ,
                currentPage * PAGE_SIZE - 1 ,
                searchField);

        HBox[] rows = new HBox[data.size()];
        for ( int i = 0 ; i < rows.length ; i++ ) {
            rows[i] = initTableRow(data.get(i));
        }
        return rows;
    }

    private static HBox initTableRow(Electricity record) {
        HBox row = new HBox(
                initTableRowCell("" + record.getDate()) ,
                initTableRowCell("" + record.getIsraeliLines()) ,
                initTableRowCell("" + record.getGazaPowerPlant()) ,
                initTableRowCell("" + record.getEgyptianLines()) ,
                initTableRowCell("" + record.getTotalDailySupplyAvailable()) ,
                initTableRowCell("" + record.getOverallDemand()) ,
                initTableRowCell("" + record.getPowerCutsHoursDay()) ,
                initTableRowCell("" + record.getTemperature()) ,
                initTableRowActionCell(record));
        row.setPrefWidth(TABLE_ROW_WIDTH);
        return row;
    }

    private static HBox initTableRowActionCell(Electricity record) {
        Label edit = new Label("Update");
        Label delete = new Label("Delete");

        delete.setOnMouseReleased(e -> handleDelete(record));
        delete.setTextFill(Color.RED);
        delete.setCursor(Cursor.HAND);

        edit.setOnMouseReleased(e -> handleEditPage(record));
        edit.setTextFill(Color.GREEN);
        edit.setCursor(Cursor.HAND);

        HBox actions = new HBox(edit , delete);
        actions.setAlignment(Pos.CENTER);
        actions.setSpacing(10);
        return actions;
    }

    private static Label initTableRowCell(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(TABLE_ROW_HEIGHT);
        label.setPrefWidth(TABLE_ROW_WIDTH / 9);
        label.setFont(Font.font("System" , FontWeight.BOLD , 15));
        return label;
    }

    private static Label initTableHeaderCell(String text) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(TABLE_HEADERS_HEIGHT);
        label.setPrefWidth(TABLE_HEADERS_WIDTH / 9);
        label.setFont(Font.font("System" , FontWeight.BOLD , 15));
        return label;
    }

    private static void handleAddPage() {
        Screens.getAddScreen().show();
    }

    private static void handleStatisticsPage() {
        Screens.getStatisticsScreen().show();
    }
    private static void handleStructurePage() {
        Screens.getStructureScreen().show();
    }

    private static void handleSearch(String searchText) {
        searchField = searchText;
        currentPage = 1;
        gotToPage();
    }

    private static void handleEditPage(Electricity record) {
        Screens.getEditScreen(record).show();
    }

    private static void handleDelete(Electricity record) {
        Alert confermDelete = new Alert(AlertType.CONFIRMATION);
        confermDelete.setHeaderText("Are you sure you want to delete this item ?");
        confermDelete.setTitle("Delete");
        confermDelete.showAndWait();
        if ( confermDelete.getResult() == ButtonType.OK ) {
            Structure.deleteRecord(record);
            gotToPage();
        }
    }

    private static void gotToNextPage() {
        currentPage++;
        gotToPage();
    }

    private static void gotToPrevPage() {
        if ( currentPage > 1 ) {
            currentPage--;
            gotToPage();
        }
    }

    private static void gotToPage() {
        pageNumberLabel.setText("" + currentPage);
        refreshTable();
    }

  
    public static void refreshTable() {
        if ( tableBody != null ) {
            tableBody.getChildren().clear();
            tableBody.getChildren().addAll(getDataOnPage());
        }
    }

}