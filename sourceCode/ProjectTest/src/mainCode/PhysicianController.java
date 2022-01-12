package mainCode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PhysicianController implements Initializable {

    @FXML
    private TableView<Patients> tv_schedule;

    @FXML
    private TableColumn<Patients, String> tc_patientFName;

    @FXML
    private TableColumn<Patients, String> tc_patientLName;

    @FXML
    private TableColumn<Patients, String> tc_physName;

    @FXML
    private TableColumn<Patients, LocalDate> tc_date;

    @FXML
    private TableColumn<Patients, String> tc_time;

    @FXML
    private TextArea physReport;

    @FXML
    private TextField searchFName;

    @FXML
    private TextField searchLName;

    @FXML
    private TextArea radReport;

    @FXML
    private TextField physLName;

    @FXML
    private TextField RadTechLName;

    @FXML
    private Label lb_patientDNE;

    @FXML
    private Label lb_patientRadReportDNE;

    @FXML
    private Label lb_PhyReportScses;

    @FXML
    private Label lb_orderScses;

    @FXML
    private TableView<Patients> tbl_currPatient;

    @FXML
    private TableColumn<Patients, String> tv_currFName;

    @FXML
    private TableColumn<Patients, String> tv_currLName;

    @FXML
    private Label lb_radTechDNE;

    @FXML
    private DatePicker dp_orderDate;

    @FXML
    private TextField tf_orderTime;

    private ObservableList<Patients> patientList = FXCollections.observableArrayList();
    private ObservableList<Patients> patientAppointment = FXCollections.observableArrayList();
    private ObservableList<Patients> currPatient = FXCollections.observableArrayList();
    private ObservableList<Users> userList = FXCollections.observableArrayList();
    private FileWriter writer;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Setting up User list to confirm Physicians
        try {
            String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
            path1 += File.separator + "TextFiles" + File.separator + "Users.txt";
            File userFile = new File(path1);
            Scanner scan = new Scanner(userFile);
            while (scan.hasNextLine()) {
                String user = scan.nextLine();
                String[] arg = user.split(" ");
                userList.add( new Users(arg[0], arg[1], arg[2], arg[3], arg[4], arg[5]));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Setting up Patient Records "patientList"
        try {
            String path2 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
            path2 += File.separator + "TextFiles" + File.separator + "Patients.txt";
            File patientFile = new File(path2);
            Scanner scan = new Scanner(patientFile);
            while (scan.hasNextLine()) {
                String patient = scan.nextLine();
                String[] arg = patient.split(" ");
                patientList.add(new Patients(arg[0], arg[1], arg[2], arg[3], arg[4]));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();}

        // Setting up Patient appointment "patientAppointment"
        try {
            String path3 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
            path3 += File.separator + "TextFiles" + File.separator + "Appointments.txt";
            File appointmentFile = new File(path3);
            Scanner scan = new Scanner(appointmentFile);
            while (scan.hasNextLine()) {
                String appointment = scan.nextLine();
                String[] arg = appointment.split(" ");
                LocalDate date = LocalDate.parse(arg[3]);
                patientAppointment.add(new Patients(arg[0], arg[1], arg[2], date, arg[4]));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();}

        tc_patientFName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientFName"));
        tc_patientLName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientLName"));
        tc_physName.setCellValueFactory(new PropertyValueFactory<Patients, String>("phys_Name"));
        tc_date.setCellValueFactory(new PropertyValueFactory<Patients, LocalDate>("date"));
        tc_time.setCellValueFactory(new PropertyValueFactory<Patients, String>("op_Time"));

        tv_schedule.setItems(patientAppointment);

    }

    @FXML
    private void searchPatient()
    {
        currPatient.clear();

        String temp_currFName = searchFName.getText();
        String temp_currLName = searchLName.getText();

        searchPatient(temp_currFName, temp_currLName);
        int j = 0;
        lb_patientDNE.setText(null);

        for (int i = 0; i <= patientList.size() - 1; i++)
        {

            if (patientList.get(i).getPatientFName().equals(temp_currFName) && patientList.get(i).getPatientLName().equals(temp_currLName))
            {
                tv_currFName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientFName"));
                tv_currLName.setCellValueFactory(new PropertyValueFactory<Patients, String>("PatientLName"));

                tbl_currPatient.setItems(currPatient);
                j++;
            }
        }
        if (j == 0)
        {
            lb_patientDNE.setText("Patient does not exist.");
        }
        searchFName.setText(null);
        searchLName.setText(null);
    }

    @FXML
    public void searchPatient(String fName, String lName)
    {
        currPatient.add(new Patients(fName, lName));
    }

    @FXML
    private void orderXRay() throws IOException {

        lb_orderScses.setText(null);
        lb_radTechDNE.setText(null);
        int j = 0;
        LocalDate temp_orderDate = dp_orderDate.getValue();
        String temp_orderTime = tf_orderTime.getText();
        String temp_fName = currPatient.get(0).getPatientFName();
        String temp_lName = currPatient.get(0).getPatientLName();
        String temp_physicianName = physLName.getText();
        String temp_radTechName = RadTechLName.getText();


        for (int i = 0; i <= userList.size() - 1; i++)
        {

            if (userList.get(i).getLName().equals(temp_radTechName) && userList.get(i).getTitle().equals("Radiology_Tech"))
            {
                String path4 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
                path4 += File.separator + "TextFiles" + File.separator + "XRayOrders.txt";
                File xRayOrderFile = new File(path4);
                FileWriter fw = new FileWriter(xRayOrderFile, true);
                PrintWriter pw = new PrintWriter(fw);
                Scanner scan = new Scanner(xRayOrderFile);

                pw.print("\n" + temp_orderDate + " " + temp_orderTime + " " + temp_fName + " " + temp_lName + " " + temp_physicianName + " " + temp_radTechName + " Incomplete");

                pw.close();
                j++;

                lb_orderScses.setText("Order Sent.");
            }
        }
        if (j == 0)
        {
            lb_radTechDNE.setText("Radiology Tech does not exist.");
        }

    }

    @FXML
    private void loadRadReport() throws FileNotFoundException {
        lb_patientRadReportDNE.setText(null);
        radReport.setText(null);

        String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        path1 += File.separator + "RadReports" + File.separator + currPatient.get(0).getPatientFName() + currPatient.get(0).getPatientLName() + ".txt";
        File file = new File(path1);

        if (file.exists())
        {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine())
            {
                radReport.appendText(scan.nextLine() + "\n");
            }

        }
        else
        {
            lb_patientRadReportDNE.setText("Radiology Report does not exist.");
        }
    }

    @FXML
    private void submitReport() throws IOException {

        String pathName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        pathName += File.separator + "PhysReports" + File.separator + currPatient.get(0).getPatientFName() + currPatient.get(0).getPatientLName() + ".txt";

        //String pathName = "C:\\Users\\Russell\\IdeaProjects\\ProjectTest\\src\\PhysReports\\" + currPatient.get(0).getPatientFName() + currPatient.get(0).getPatientLName() + ".txt";
        File patientReport = new File(pathName);
        FileWriter fw = new FileWriter(patientReport, true);
        PrintWriter pw = new PrintWriter(fw);
        Scanner scan = new Scanner(patientReport);
        pw.print(physReport.getText());
        pw.close();

        physReport.setText(null);
        lb_PhyReportScses.setText("Diagnosis Report saved successfully.");
    }

    @FXML
    private void clearPatient()
    {
        for ( int i = 0; i<tbl_currPatient.getItems().size(); i++) {
            tbl_currPatient.getItems().clear();
        }
        radReport.setText(null);
        lb_PhyReportScses.setText(null);
        lb_orderScses.setText(null);
    }

    private String orderNum()
    {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && i <= 90 || i >= 97)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}