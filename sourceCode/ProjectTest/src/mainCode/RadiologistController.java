package mainCode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

public class RadiologistController implements Initializable {

	@FXML
	private ImageView currentXRay;

	@FXML
	private TextField tf_fName;

	@FXML
	private TextField tf_lName;

	@FXML
	private TextArea tf_report;

	@FXML
	private TableView<Patients> tableView;

	@FXML
	private TableColumn<Patients, LocalDate> tc_orderDate;

	@FXML
	private TableColumn<Patients, String> tc_orderTime;

	@FXML
	private TableColumn<Patients, String> tc_FirstName;

	@FXML
	private TableColumn<Patients, String> tc_LastName;

	@FXML
	private TableColumn<Patients, String> tc_physName;

	@FXML
	private TableColumn<Patients, String> tc_RadiologyTech;

	@FXML
	private TableColumn<Patients, String> tc_status;

	@FXML
	private Label lb_dne;

	@FXML
	private Label lb_reportSaved;

	private ObservableList<Patients> orderList = FXCollections.observableArrayList();
	private FileWriter writer;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
			path1 += File.separator + "TextFiles" + File.separator + "XRayOrders.txt";
			File userFile = new File(path1);
			Scanner scan = new Scanner(userFile);
			while (scan.hasNextLine()) {
				String user = scan.nextLine();
				String[] arg = user.split(" ");
				LocalDate date = LocalDate.parse(arg[0]);
				if(user.trim().length() != 0)
				{
					orderList.add( new Patients(date, arg[1], arg[2], arg[3], arg[4], arg[5], arg[6]));
				}

			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		tc_orderDate.setCellValueFactory(new PropertyValueFactory<Patients, LocalDate>("date"));
		tc_orderTime.setCellValueFactory(new PropertyValueFactory<Patients, String>("op_Time"));
		tc_FirstName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientFName"));
		tc_LastName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientLName"));
		tc_physName.setCellValueFactory(new PropertyValueFactory<Patients, String>("phys_Name"));
		tc_RadiologyTech.setCellValueFactory(new PropertyValueFactory<Patients, String>("radTech_Name"));
		tc_status.setCellValueFactory(new PropertyValueFactory<Patients, String>("or_Status"));

		tableView.setItems(orderList);

	}

	//This method is used by the Radiologist to pull up X-Rays
	@FXML
	private void retrieveXRay() throws IOException
	{
		String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
		path1 += File.separator + "XRays" + File.separator + tf_fName.getText() + tf_lName.getText() +".jpg";

		String path2 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
		path2 += File.separator + "XRays" + File.separator + tf_fName.getText() + tf_lName.getText() +".png";

		//String path1 = "C:\\Users\\Russell\\IdeaProjects\\ProjectTest\\src\\XRays\\" + tf_fName.getText() + tf_lName.getText() +".jpg";
		//String path2 = "C:\\Users\\Russell\\IdeaProjects\\ProjectTest\\src\\XRays\\" + tf_fName.getText() + tf_lName.getText() +".png";
		File file1 = new File(path1);
		File file2 = new File(path2);

		if (file1.exists())
		{
			InputStream stream1 = new FileInputStream(path1);
			Image image = new Image(stream1);
			currentXRay.setImage(image);
		}
		else if (file2.exists())
		{
			InputStream stream2 = new FileInputStream(path2);
			Image image = new Image(stream2);
			currentXRay.setImage(image);
		}
		else
		{
			lb_dne.setText("X-Ray does not exist, Please try again.");
		}


	}

	//This method allows the radiologist to write reports and save them into a file
	@FXML
	public void writeReport() throws IOException
	{
		String pathName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
		pathName += File.separator + "RadReports" + File.separator + tf_fName.getText() + tf_lName.getText() + ".txt";

		//String pathName = "C:\\Users\\Russell\\IdeaProjects\\ProjectTest\\src\\RadReports\\" + tf_fName.getText() + tf_lName.getText() + ".txt";
		File patientReport = new File(pathName);
		FileWriter fw = new FileWriter(patientReport, true);
		PrintWriter pw = new PrintWriter(fw);
		Scanner scan = new Scanner(patientReport);
		pw.print(tf_report.getText());
		pw.close();

		tf_report.setText(null);
		lb_reportSaved.setText("Radiology Report saved successfully.");
	}

	@FXML
	public void clearPatient()
	{
		tf_fName.setText(null);
		tf_lName.setText(null);
		lb_dne.setText(null);
		currentXRay.setImage(null);
		lb_reportSaved.setText(null);
	}
}
