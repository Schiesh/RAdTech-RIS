package mainCode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class IT_AdminController implements Initializable {

    @FXML
    private TableView<Users> tableView;

    @FXML
    private TableColumn<Users, String> tv_id;

    @FXML
    private TableColumn<Users, String> tv_fName;

    @FXML
    private TableColumn<Users, String> tv_lName;

    @FXML
    private TableColumn<Users, String> tv_userName;

    @FXML
    private TableColumn<Users, String> tv_password;

    @FXML
    private TableColumn<Users, String> tv_title;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_fName;

    @FXML
    private TextField tf_lName;

    @FXML
    private TextField tf_UserName;

    @FXML
    private TextField tf_password;

    @FXML
    private CheckBox cb_radiologyTech;

    @FXML
    private CheckBox cb_receptionist;

    @FXML
    private CheckBox cb_physician;

    @FXML
    private CheckBox cb_radiologist;

    @FXML
    private CheckBox cb_admin;

    // list of Users created
    private ObservableList<Users> list = FXCollections.observableArrayList();
    private FileWriter fw;

    // method that starts up on execution and reads Users.txt and displays it on the Table View
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
            path1 += File.separator + "TextFiles" + File.separator + "Users.txt";
            File userFile = new File(path1);
            Scanner scan = new Scanner(userFile);
            while (scan.hasNextLine()) {
                String user = scan.nextLine();
                String[] arg = user.split(" ");
                list.add( new Users(arg[0], arg[1], arg[2], arg[3], arg[4], arg[5]));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tv_id.setCellValueFactory(new PropertyValueFactory<Users, String>("id"));
        tv_fName.setCellValueFactory(new PropertyValueFactory<Users, String>("fName"));
        tv_lName.setCellValueFactory(new PropertyValueFactory<Users, String>("lName"));
        tv_userName.setCellValueFactory(new PropertyValueFactory<Users, String>("userName"));
        tv_password.setCellValueFactory(new PropertyValueFactory<Users, String>("password"));
        tv_title.setCellValueFactory(new PropertyValueFactory<Users, String>("title"));

        tableView.setItems(list);
    }
    // method that takes user input and adds it to Users.txt and displays it in Table View
    @FXML
    public void buttonAddUser() throws IOException {
        String temp_id = tf_id.getText();
        String temp_fName = tf_fName.getText();
        String temp_lName = tf_lName.getText();
        String temp_userName = tf_UserName.getText();
        String temp_password = tf_password.getText();
        String temp_title = determineTitle();

        addUser(temp_id, temp_fName, temp_lName, temp_userName, temp_password, temp_title);


        String path1 = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "resources";
        path1 += File.separator + "TextFiles" + File.separator + "Users.txt";
        File userFile = new File(path1);
        FileWriter fw = new FileWriter(userFile, true);
        PrintWriter pw = new PrintWriter(fw);
        Scanner scan = new Scanner(userFile);

        pw.print("\n" + temp_id + " " + temp_fName + " " + temp_lName + " " + temp_userName + " " + temp_password + " " + temp_title);

        pw.close();



        tf_id.setText(null);
        tf_fName.setText(null);
        tf_lName.setText(null);
        tf_UserName.setText(null);
        tf_password.setText(null);

    }
    // method to add User to the array
    @FXML
    public void addUser(String id, String firstName, String lastName, String userName, String password, String title)
    {
        list.add(new Users(id, firstName, lastName, userName, password, title));


    }
    // method that determine User Title
    @FXML
    public String determineTitle()
    {
        if (cb_radiologyTech.isSelected())
        {
            return "Radiology_Tech";
        }
        else if (cb_receptionist.isSelected())
        {
            return "Receptionist";
        }
        else if (cb_physician.isSelected())
        {
            return "Physician";
        }
        else if (cb_radiologist.isSelected())
        {
            return "Radiologist";
        }
        else if (cb_admin.isSelected())
        {
            return "Admin";
        }
        return "Please Try Again!";
    }


}
