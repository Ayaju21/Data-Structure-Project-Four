package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Screens {

	public static Stage getEditScreen(Electricity record) {
		Stage edit = new Stage();
		Parent root = FormPage.initFormPage(edit, record);

		Scene scene = new Scene(root);
		edit.setTitle("Modify data");
		edit.setScene(scene);
		edit.setResizable(false);
		return edit;
	}

	public static Stage getAddScreen() {
		Stage add = new Stage();

		Parent root = FormPage.initFormPage(add, null);

		Scene scene = new Scene(root);
		add.setTitle("Add New Data");
		add.setScene(scene);
		add.setResizable(false);
		return add;
	}

	public static Stage getMainScreen() {
		Stage main = new Stage();

		Parent root = MainPage.initMainScreen(main);

		Scene scene = new Scene(root);
		main.setTitle("Mangement Screen");
		main.setScene(scene);
		main.setResizable(false);
		return main;
	}

	public static Stage getStructureScreen() {
		Stage main = new Stage();
		Parent root = StructurePage.initStructureScreen(main);

		Scene scene = new Scene(root);
		main.setTitle("AVL Tree Structure");
		main.setScene(scene);
		main.setResizable(false);
		return main;
	}

	public static Stage getStatisticsScreen() {
		Stage main = new Stage();
		Parent root = StatisticsPage.initStatisticsScreen(main);

		Scene scene = new Scene(root);
		main.setTitle("Statistics Screen");
		main.setScene(scene);
		main.setResizable(false);
		return main;
	}

}
