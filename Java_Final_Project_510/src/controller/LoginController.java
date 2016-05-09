package controller;

import main.main;
import databaseDAO.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {
	// This filed are to get data from FXML
	@FXML
	private TextField user_name;

	@FXML
	private TextField password;

	private Stage dialogStage;

	/**
	 * This method is t o load data in database
	 *
	 * @param dialogStage
	 */

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * This is to load the login page
	 */
	public void LoginFromError() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/LoginFXML.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Login");
		} catch (Exception e) {
			System.out.println("Could not open professor view = " + e);
		}

	}

	/**
	 * This method is to process data from the login screen
	 */
	public void login() {

		String user_name = this.user_name.getText();
		String password = this.password.getText();

		if (password == null || password.trim().equals("")) {
			return;
		}
		if (user_name == null || user_name.trim().equals("") || !user_name.matches("[a-zA-Z]+( +[a-zA-Z]+)*")) {
			return;
		}

		/**
		 * If the user name and password is admin and admin if loads the below
		 * method
		 */

		if (user_name.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			try {
				AnchorPane root;
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/AdminView.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				main.stage.setScene(scene);
				main.stage.setTitle("ADMIN_PAGE");
			} catch (Exception e) {
				System.out.println("Could not open professor view = " + e);
			}

		}

		/**
		 * This is an else method to pass data to the database
		 */
		else {
			StudentDAO su = new StudentDAO();
			su.loginstudentfinder(user_name, password);

		}
	}

	/**
	 * This method is to load the student login page
	 */

	public void loadStudentLogin() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/StudentLoginOperationsFXML.fxml"));
			System.out.println("I cressed this");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Student_Operation");

		} catch (Exception e) {
			System.out.println("Could not login view boo = " + e);
		}

	}

	/**
	 * This method is to pass the value to error login
	 */

	public void errorlogin() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ErrorMsg.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("ERROR!!!!!!!!!!!!!");
		} catch (Exception e) {
			System.out.println("error view = " + e);
		}

	}

	public void cancel() {
		System.exit(0);
	}

}
