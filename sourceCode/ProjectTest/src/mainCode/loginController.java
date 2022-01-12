package mainCode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class loginController implements Initializable {

    @FXML
    private TextField tf_UserName;

    @FXML
    private TextField tf_Password;

    @FXML
    private Label lb_pta;

    private ObservableList<Users> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        String path = System.getProperty("user.home") + File.separator + "Documents";
        path += File.separator + "resources";
        File customDir = new File(path);

        if (customDir.exists()) {
            System.out.println(customDir + " already exists");
        } else if (customDir.mkdirs()) {
            System.out.println(customDir + " was created");
        } else {
            System.out.println(customDir + " was not created");
        }

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

    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String temp_userName = tf_UserName.getText();
        String temp_password = tf_Password.getText();
        lb_pta.setText(null);

        for (int i = 0; i <= list.size()-1; i++)
        {
            if (list.get(i).getUserName().equals(temp_userName) && list.get(i).getPassword().equals(temp_password) && list.get(i).getTitle().equals("Admin"))
            {
                Stage stage = (Stage) lb_pta.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("IT_Admin.fxml"));
                Stage AdminStage = new Stage();
                AdminStage.setTitle("RadTech RIS");
                AdminStage.setScene(new Scene(root, 1000, 800));
                AdminStage.setResizable(false);
                AdminStage.show();
                stage.close();
            }
            else if (list.get(i).getUserName().equals(temp_userName) && list.get(i).getPassword().equals(temp_password) && list.get(i).getTitle().equals("Receptionist"))
            {
                Stage stage = (Stage) lb_pta.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("Receptionist.fxml"));
                Stage ReceptionistStage = new Stage();
                ReceptionistStage.setTitle("RadTech RIS");
                ReceptionistStage.setScene(new Scene(root, 1000, 800));
                ReceptionistStage.setResizable(false);
                ReceptionistStage.show();
                stage.close();
            }
            else if (list.get(i).getUserName().equals(temp_userName) && list.get(i).getPassword().equals(temp_password) && list.get(i).getTitle().equals("Physician"))
            {
                Stage stage = (Stage) lb_pta.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("Physician.fxml"));
                Stage PhysicianStage = new Stage();
                PhysicianStage.setTitle("RadTech RIS");
                PhysicianStage.setScene(new Scene(root, 1200, 1000));
                PhysicianStage.setResizable(false);
                PhysicianStage.show();
                stage.close();
            }
            else if (list.get(i).getUserName().equals(temp_userName) && list.get(i).getPassword().equals(temp_password) && list.get(i).getTitle().equals("Radiologist"))
            {
                Stage stage = (Stage) lb_pta.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("Radiologist.fxml"));
                Stage RadiologistStage = new Stage();
                RadiologistStage.setTitle("RadTech RIS");
                RadiologistStage.setScene(new Scene(root, 1200, 800));
                RadiologistStage.setResizable(false);
                RadiologistStage.show();
                stage.close();
            }
            else if (list.get(i).getUserName().equals(temp_userName) && list.get(i).getPassword().equals(temp_password) && list.get(i).getTitle().equals("Radiology_Tech"))
            {
                Stage stage = (Stage) lb_pta.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("RadiologyTech.fxml"));
                Stage RadiologyTechStage = new Stage();
                RadiologyTechStage.setTitle("RadTech RIS");
                RadiologyTechStage.setScene(new Scene(root, 1200, 800));
                RadiologyTechStage.setResizable(false);
                RadiologyTechStage.show();
                stage.close();
            }
            else
            {
                lb_pta.setText("Please Try Again, Thank you.");
            }
        }
    }

}
