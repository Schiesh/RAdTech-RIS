package mainCode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;


public class RadiologyTechController implements Initializable{

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
    private TextField tf_XRayPath;

    @FXML
    private TextField tf_RenameXRay;

    @FXML
    private Label lb_xraySaved;

    @FXML
    private TextField tf_patientFName;

    @FXML
    private TextField tf_patientLName;

    @FXML
    private CheckBox cb_complete;

    @FXML
    private CheckBox cb_incomplete;

    @FXML
    private ImageView iv_xRay;

    private ObservableList<Patients> orderList = FXCollections.observableArrayList();
    private FileWriter writer;
    private FileChooser fileChooser = new FileChooser();
    private Scanner x;

    // Display all orders on initialization
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
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

    //This method is used to save an image to the Tech's X-Ray folder in the main program
    @FXML
    private void searchXRay()
    {
        fileChooser.setTitle("Search File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg", "*gif"));
        File selectedFile = fileChooser.showOpenDialog((Stage) tableView.getScene().getWindow());
        tf_XRayPath.setText(selectedFile.getAbsolutePath());

    }

    //This method is used to read an image from the XRays folder
    @FXML
    private void saveXRay() throws IOException {
        Path source = Paths.get(tf_XRayPath.getText());

        String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        path1 += File.separator + "XRays";

        Path newdir = Paths.get(path1);
        Files.copy(source, newdir.resolve(tf_RenameXRay.getText()));

        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        path += File.separator + "XRays" + File.separator + tf_RenameXRay.getText();

        //String path = "C:\\Users\\Russell\\IdeaProjects\\ProjectTest\\src\\XRays\\" + tf_RenameXRay.getText();

        InputStream stream = new FileInputStream(path);
        Image image = new Image(stream);
        iv_xRay.setImage(image);
        lb_xraySaved.setText("X-Ray is successfully saved.");
    }

    @FXML
    private void clearPatient()
    {
        tf_XRayPath.setText(null);
        tf_RenameXRay.setText(null);
        iv_xRay.setImage(null);
        lb_xraySaved.setText(null);
    }

    @FXML
    private void updatePatient()
    {
        for ( int i = 0; i<tableView.getItems().size(); i++) {
            tableView.getItems().clear();
        }

        String filePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        filePath += File.separator + "TextFiles" + File.separator + "XRayOrders.txt";

        String patientFName = tf_patientFName.getText();
        String patientLName = tf_patientLName.getText();
        String determineStatus = determineStatus();
        String tempFile = "temp.txt";

        String date = ""; String orderTime = ""; String fName = ""; String lName = "";
        String physName = ""; String radTech = ""; String orderStatus = "";

        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        try
        {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            x = new Scanner(new File(filePath));
            x.useDelimiter("[ \n]");

            while(x.hasNext())
            {
                date = x.next();
                orderTime = x.next();
                fName = x.next();
                lName = x.next();
                physName = x.next();
                radTech = x.next();
                orderStatus = x.next();

                if (fName.equals(patientFName) && lName.equals(patientLName))
                {
                    pw.println(date + " " + orderTime + " " + fName + " " + lName + " " + physName + " " + radTech + " " + determineStatus);

                }
                else
                {
                    pw.println(date + " " + orderTime + " " + fName + " " + lName + " " + physName + " " + radTech + " " + orderStatus);

                }
            }

            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);
        }
        catch(Exception e)
        {

        }

        try {
            String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
            path1 += File.separator + "TextFiles" + File.separator + "XRayOrders.txt";
            File userFile = new File(path1);
            Scanner scan = new Scanner(userFile);
            while (scan.hasNextLine()) {
                String user = scan.nextLine();
                String[] arg = user.split(" ");
                LocalDate date1 = LocalDate.parse(arg[0]);
                if(user.trim().length() != 0)
                {
                    orderList.add( new Patients(date1, arg[1], arg[2], arg[3], arg[4], arg[5], arg[6]));
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

    @FXML
    private String determineStatus()
    {
        if (cb_complete.isSelected())
        {
            return "Complete";
        }
        else if (cb_incomplete.isSelected())
        {
            return "Incomplete";
        }
        else
        {
            return null;
        }
    }


}
