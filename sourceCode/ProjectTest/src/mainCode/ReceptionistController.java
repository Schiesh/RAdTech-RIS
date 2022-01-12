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
import java.util.ResourceBundle;
import java.util.Scanner;

public class ReceptionistController implements Initializable {

    @FXML
    private TextField tf_ssn;

    @FXML
    private TextField tf_fName;

    @FXML
    private TextField tf_lName;

    @FXML
    private TextField tf_eMail;

    @FXML
    private TextField tf_phone;

    @FXML
    private DatePicker dp_date;

    @FXML
    private TableView<Patients> tbl_patientRecords;

    @FXML
    private TableColumn<Patients, String> tv_SSN;

    @FXML
    private TableColumn<Patients, String> tv_fName;

    @FXML
    private TableColumn<Patients, String> tv_lName;

    @FXML
    private TableColumn<Patients, String> tv_eMail;

    @FXML
    private TableColumn<Patients, String> tv_phone;

    @FXML
    private TableView<Patients> tbl_Schedule;

    @FXML
    private TableColumn<Patients, String> tv_fNameSchedule;

    @FXML
    private TableColumn<Patients, String> tv_lNameSchedule;

    @FXML
    private TableColumn<Patients, String> tv_physicianName;

    @FXML
    private TableColumn<Patients, LocalDate> tv_appDate;

    @FXML
    private TableColumn<Patients, String> tv_appTime;

    @FXML
    private TableView<Patients> tbl_currPatient;

    @FXML
    private TableColumn<Patients, String> tv_currFName;

    @FXML
    private TableColumn<Patients, String> tv_currLName;

    @FXML
    private TextField tf_fNameSearch;

    @FXML
    private TextField tf_lNameSearch;

    @FXML
    private TextField tf_physicianName;

    @FXML
    private Label lb_noPatient;

    @FXML
    private Label lb_DocDNE;

    @FXML
    private TextField tf_appTime;

    private ObservableList<Patients> list = FXCollections.observableArrayList();
    private ObservableList<Patients> currPatient = FXCollections.observableArrayList();
    private ObservableList<Patients> patientAppointment = FXCollections.observableArrayList();
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

        // Setting up Patient Records "list"
        try {
            String path2 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
            path2 += File.separator + "TextFiles" + File.separator + "Patients.txt";
            File patientFile = new File(path2);
            Scanner scan = new Scanner(patientFile);
            while (scan.hasNextLine()) {
                String patient = scan.nextLine();
                String[] arg = patient.split(" ");
                list.add(new Patients(arg[0], arg[1], arg[2], arg[3], arg[4]));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();}

        tv_SSN.setCellValueFactory(new PropertyValueFactory<Patients, String>("p_ID"));
        tv_fName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientFName"));
        tv_lName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientLName"));
        tv_eMail.setCellValueFactory(new PropertyValueFactory<Patients, String>("p_email"));
        tv_phone.setCellValueFactory(new PropertyValueFactory<Patients, String>("p_phone"));

        tbl_patientRecords.setItems(list);

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

        tv_fNameSchedule.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientFName"));
        tv_lNameSchedule.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientLName"));
        tv_physicianName.setCellValueFactory(new PropertyValueFactory<Patients, String>("phys_Name"));
        tv_appDate.setCellValueFactory(new PropertyValueFactory<Patients, LocalDate>("date"));
        tv_appTime.setCellValueFactory(new PropertyValueFactory<Patients, String>("op_Time"));

        tbl_Schedule.setItems(patientAppointment);

    }

    @FXML
    public void addPatient(String id, String firstName, String lastName, String email, String phone)
    {
        list.add(new Patients(id, firstName, lastName, email, phone));
    }

    @FXML
    public void searchPatient(String fName, String lName)
    {
        currPatient.add(new Patients(fName, lName));
    }

    @FXML
    public void addAppointment(String fName, String lName, String physicianName, LocalDate appDate, String appTime)
    {
        patientAppointment.add(new Patients(fName, lName, physicianName, appDate, appTime));
    }

    @FXML
    public void buttonAddPatient() throws IOException
    {
        String temp_id = tf_ssn.getText();
        String temp_fName = tf_fName.getText();
        String temp_lName = tf_lName.getText();
        String temp_eMail = tf_eMail.getText();
        String temp_phone = tf_phone.getText();

        addPatient(temp_id, temp_fName, temp_lName, temp_eMail, temp_phone);

        String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        path1 += File.separator + "TextFiles" + File.separator + "Patients.txt";
        File patientFile = new File(path1);
        FileWriter fw = new FileWriter(patientFile, true);
        PrintWriter pw = new PrintWriter(fw);
        Scanner scan = new Scanner(patientFile);

        pw.print("\n" + temp_id + " " + temp_fName + " " + temp_lName + " " + temp_eMail + " " + temp_phone);

        pw.close();
    }

    @FXML
    public void buttonSchedulePatient() throws IOException
    {
        int j = 0;
        lb_DocDNE.setText(null);
        String temp_fName = currPatient.get(0).getPatientFName();
        String temp_lName = currPatient.get(0).getPatientLName();
        String temp_physicianName = tf_physicianName.getText();
        LocalDate temp_date = dp_date.getValue();
        String temp_appTime = tf_appTime.getText();

        for (int i = 0; i <= userList.size() - 1; i++)
        {

            if (userList.get(i).getLName().equals(temp_physicianName) && userList.get(i).getTitle().equals("Physician"))
            {

                addAppointment(temp_fName, temp_lName, temp_physicianName, temp_date, temp_appTime);

                String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
                path1 += File.separator + "TextFiles" + File.separator + "Appointments.txt";
                File appointmentFile = new File(path1);
                FileWriter fw = new FileWriter(appointmentFile, true);
                PrintWriter pw = new PrintWriter(fw);
                Scanner scan = new Scanner(appointmentFile);

                pw.print("\n" + temp_fName + " " + temp_lName + " " + temp_physicianName + " " + temp_date + " " + temp_appTime);

                pw.close();
                j++;
            }
        }
        if (j == 0)
        {
            lb_DocDNE.setText("Physician does not exist.");
        }
    }

    @FXML
    public void buttonSearchPatient() throws IOException
    {
        currPatient.clear();

        String temp_currFName = tf_fNameSearch.getText();
        String temp_currLName = tf_lNameSearch.getText();

        searchPatient(temp_currFName, temp_currLName);
        int j = 0;
        lb_noPatient.setText(null);
        //System.out.println(currPatient.toString());

        for (int i = 0; i <= list.size() - 1; i++)
        {

            if (list.get(i).getPatientFName().equals(temp_currFName) && list.get(i).getPatientLName().equals(temp_currLName))
            {
                tv_currFName.setCellValueFactory(new PropertyValueFactory<Patients, String>("patientFName"));
                tv_currLName.setCellValueFactory(new PropertyValueFactory<Patients, String>("PatientLName"));

                tbl_currPatient.setItems(currPatient);
                j++;
            }
        }
        if (j == 0)
        {
            lb_noPatient.setText("Please try again.");
        }
    }
}