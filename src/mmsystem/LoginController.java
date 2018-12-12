/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
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
public class LoginController implements Initializable {

    @FXML
    private JFXTextField usernameTxtField;
    @FXML
    private JFXPasswordField passwordTxtField;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXButton addAccountBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLoginBtn(ActionEvent event) throws IOException  {
        if (isLoginCorrect()) {
            user us = user.getINSTANCE();
            us.setUsername(usernameTxtField.getText());
            System.out.println("Login Successfully!");
            Parent changeMain = FXMLLoader.load(getClass().getResource("TransactionForm.fxml"));
            Scene changeMainScene = new Scene(changeMain);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(changeMainScene);
            mainStage.show();

        } else {
            System.out.println("Wrong username or password");

        }
    }

    @FXML
    private void handleAddAccountBtn(ActionEvent event) throws IOException {
        Parent changeToReg = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene changeRegScene = new Scene(changeToReg);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeRegScene);
        mainStage.show();
    }
    
    private boolean isLoginCorrect() {
        String path = "C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\register.txt";
        System.out.println(Arrays.toString(getAllArray(path)));
        
        System.out.println(Arrays.toString(getAllArray(path)));
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (String eachLine : getAllArray(path)) {
                System.out.println(eachLine);
                String[] split = eachLine.split(",");
                if (usernameTxtField.getText().equals(split[3]) && passwordTxtField.getText().equals(split[4])) {
                    // pag nakalogin na kukunin si details
                    user us = user.getINSTANCE();
                    us.setDetails(eachLine);
                    // then kukunin yung part ng current balance sa text then iseset yun as current balance
                    String[] detailSplit = eachLine.split(",");
                    us.setCurrentBalance(detailSplit[6]);
                    return true;
                }
            }
            JOptionPane.showMessageDialog(null, "Wrong username or password!");
            return false;
        } catch (Exception e) {
            System.out.println("Error");
        }

        return false;
        
    }
    
    public String[] getAllArray(String path) {
        String[] stringArray = new String[getLineCount(path)];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (int i = 0; i < stringArray.length; i++) {
                stringArray[i] = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        return stringArray;
    }

    public int getLineCount(String path) {
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                counter++;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return counter;
    }
    
}
