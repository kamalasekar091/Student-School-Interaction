package controller;

import java.util.List;

import databaseDAO.CourseDAO;
import databaseDAO.StudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.main;
import model.Course;
import model.Student;

public class CourseViewController {

	// This are the inputs from the FXMl pages

	@FXML
	private TableView<Course> course;

	@FXML
	private TableView<Course> course_student;

	@FXML
	private TextField name;

	@FXML
	private RadioButton OnlineClass;

	@FXML
	private RadioButton LiveClass;

	/**
	 * This method is to load the course details in the fxml
	 *
	 * @param course
	 *            ( this an observable list)
	 */

	public void setcourse(ObservableList<Course> course) {
		this.course.setItems(course);
	}

	/**
	 * This method to redirect to student login
	 */

	public void coursemain() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CourseMainFXML.FXML"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Student Main");

		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}



	/**
	 * This method to redirect to student login
	 */

	public void studentloginmain() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/StudentLoginOperationsFXML.FXML"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Student Main");

		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	/**
	 * This method to go to the course main
	 */

	public void main() {

		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CourseMainFXML.FXML"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Course Activities");

		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	/**
	 * This method is to load the course
	 */

	public void LoadCourse() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddStudent.FXML"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			main.stage.setScene(scene);
			main.stage.setTitle("Course Details");
			CourseViewController controller = loader.getController();
			List<Course> course;
			try (CourseDAO co = new CourseDAO()) {
				course = co.getcourse();
			}
			controller.setcourse(FXCollections.observableArrayList(course));
		} catch (Exception e) {
			System.out.println("Error while loading the FXML file: " + e);
		}

	}

	/**
	 * This method is to process teh dataa to the database
	 */

	public void updatecoursedatabase() {

		String Name = this.name.getText();
		String Course_type = null;
		Boolean Online = this.OnlineClass.isSelected();
		Boolean Live = this.LiveClass.isSelected();
		if (Online) {
			Course_type = "online";
		} else {
			Course_type = "live";
		}

		if (Name == null || Name.trim().equals("") || !Name.matches("[a-zA-Z]+( +[a-zA-Z]+)*")) {
			return;
		}
		if (Course_type == null || Course_type.trim().equals("")) {
			return;
		}

		ObservableList<Course> cou;
		cou = course.getSelectionModel().getSelectedItems();
		String Id = cou.get(0).getId();
		Course cu = new Course();
		cu.setId(Id);
		cu.setName(Name);
		cu.setType(Course_type);
		CourseDAO t = new CourseDAO();
		t.update(cu);
		CourseController ad = new CourseController();
		ad.UpdateCourse();

	}

	/**
	 * This method is to add course details to backend
	 */

	public void addStudentCoursedata() {
		System.out.println("The methos has been accessed");
		ObservableList<Course> cou;
		cou = course.getSelectionModel().getSelectedItems();
		String Id = cou.get(0).getId();
		System.out.println(Id);
		String Name = cou.get(0).getName();
		System.out.println(Name);
		Course cur = new Course();
		cur.setId(Id);
		cur.setName(Name);
		StudentDAO t = new StudentDAO();
		System.out.println("I corossed the method from add to controller");
		t.addstudentcourse(cur);
		CourseController crt = new CourseController();
		crt.ViewStudentRegisteredCourse();

	}

	/**
	 * This method is to delete course details from back end
	 */
	public void deleteCourseDatabase() {
		ObservableList<Course> cou;
		System.out.println();
		cou = course.getSelectionModel().getSelectedItems();
		CourseDAO cd = new CourseDAO();
		cd.delete(cou.get(0));
		CourseController cc = new CourseController();
		cc.DeleteCourse();
		cd.close();

	}

	/**
	 * This method is to delete course details mapped for student
	 */

	public void deleteCourseForStudent() {

		ObservableList<Course> cou;
		System.out.println();
		cou = course.getSelectionModel().getSelectedItems();
		StudentDAO cd = new StudentDAO();
		cd.deletestudentcoursedata(cou.get(0));

		CourseController cc = new CourseController();
		cc.deleteStudentRegisteredCourse();
		cd.close();

	}

}
