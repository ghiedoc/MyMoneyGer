/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXButton registerBtn;
    @FXML
    private JFXTextField usernameTxtField;
    @FXML
    private JFXPasswordField passwordTxtField;
    @FXML
    private JFXButton registerBackBtn;
    @FXML
    private JFXTextField initialBalanceTxtField;
    @FXML
    private JFXTextField firstNameTxtField;
    @FXML
    private JFXTextField lastNameTxtField;
    @FXML
    private JFXComboBox<String> genderTxtField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        genderTxtField.getItems().addAll(
                "Male",
                "Female"
        );
        genderTxtField.getSelectionModel().selectFirst();
        
        //number validator
        NumberValidator numVali = new NumberValidator();
        
        initialBalanceTxtField.getValidators().add(numVali);
        
        numVali.setMessage("Only numbers are allowed");
        
        initialBalanceTxtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    initialBalanceTxtField.validate();
                }
            }
        });
    }    

    @FXML
    private void handleRegisterBtn(ActionEvent event) {
           
        String firstname = firstNameTxtField.getText();
        String lastname = lastNameTxtField.getText();
        String gender = genderTxtField.getValue().toString(); // getValue kung combo box
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();
        String initialBalance = initialBalanceTxtField.getText();
        
        try {
            File file = new File("C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\register.txt");
            File reg_file = new File("C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\" + username + "_database.txt");
            reg_file.createNewFile();
            
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(firstname + ",");
            bw.append(lastname + ",");
            bw.append(gender + ",");
            bw.append(username + ",");
            bw.append(password + ",");
            bw.append(initialBalance + ",");
            bw.append(initialBalance);
            bw.newLine();

            bw.close();
            fw.close();
            
            JOptionPane.showMessageDialog(null, "Registered Successfully!");            
            //go to log in form
            Parent changeToLogin = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene changeLoginScene = new Scene(changeToLogin);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(changeLoginScene);
            mainStage.show();
            
        } catch (IOException ex) {
            System.out.println("Error");
            
        }  
        
    }

    @FXML
    private void handleBackRegisterBtn(ActionEvent event) throws IOException {
        Parent changeToLogin = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene changetoLoginScene = new Scene(changeToLogin);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changetoLoginScene);
        mainStage.show();
    }
    
}
