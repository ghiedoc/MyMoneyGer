/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Gillian
 */
public class TransactionFormController implements Initializable {

    private Label label;
    @FXML
    private Label incomeLbl;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label expensesLbl;
    @FXML
    private TableView<Transactions> table;
    @FXML
    private TableColumn<Transactions, String> transactionCol;
    @FXML
    private TableColumn<Transactions, String> dateCol;
    @FXML
    private TableColumn<Transactions, String> categoryCol;
    @FXML
    private TableColumn<Transactions, String> amountCol;
    @FXML
    private TableColumn<Transactions, String> remarkCol;
    @FXML
    private JFXDatePicker incomeDateTxtField;
    @FXML
    private JFXComboBox<String> incomeCategoryCbox;
    @FXML
    private JFXTextField incomeAmountTxtField;
    @FXML
    private JFXTextField incomeRemarkTxtField;
    @FXML
    private JFXButton addExpense;
    @FXML
    private JFXButton incomeResetbtn;
    @FXML
    private JFXButton expenseResetBtn;
    @FXML
    private JFXDatePicker expenseDateTxtField;
    @FXML
    private JFXTextField expenseAmountTxtField;
    @FXML
    private JFXTextField expenseRemarkTxtField;
    @FXML
    private JFXComboBox<String> expenseCategoryCbox;
    @FXML
    private PieChart incomePie;
    @FXML
    private PieChart expensePie;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private Button signOutBtn;

    user us = user.getINSTANCE();

    String username = us.getUsername();
    @FXML
    private TableView<Transactions> table_dash;
    @FXML
    private TableColumn<Transactions, String> transactionColDash;
    @FXML
    private TableColumn<Transactions, String> dateColDash;
    @FXML
    private TableColumn<Transactions, String> categoryColDash;
    @FXML
    private TableColumn<Transactions, String> amountColDash;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        String path = "C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\" + username + "_database.txt";

        // Transaction pane
        incomeCategoryCbox.getItems().addAll(
                "Allowance",
                "Scholarship Allowance",
                "Working Allowance",
                "Gifts",
                "Others"
        );
        incomeCategoryCbox.getSelectionModel().selectFirst();
        expenseCategoryCbox.getItems().addAll(
                "Food",
                "Transportation",
                "Apparel",
                "Gift",
                "Hygiene",
                "Load",
                "Utilitities",
                "Self-Development",
                "Education",
                "Health",
                "Others"
        );
        expenseCategoryCbox.getSelectionModel().selectFirst();
        // Statistics pane
        incomePie.setData(getIncomePieData(getIncomeCategoryArray(), getIncomeCategoryPercentage(path, getIncomeCategoryArray())));
        expensePie.setData(getExpensePieData(getExpenseCategoryArray(), getExpenseCategoryPercentage(path, getExpenseCategoryArray())));

        //History pane
        transactionCol.setCellValueFactory(new PropertyValueFactory<Transactions, String>("transaction"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<Transactions, String>("category"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Transactions, String>("amount"));
        remarkCol.setCellValueFactory(new PropertyValueFactory<Transactions, String>("remark"));
        table.setItems(getTransaction(path));

        //dashboard pane
        transactionColDash.setCellValueFactory(new PropertyValueFactory<Transactions, String>("transaction"));
        dateColDash.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));
        categoryColDash.setCellValueFactory(new PropertyValueFactory<Transactions, String>("category"));
        amountColDash.setCellValueFactory(new PropertyValueFactory<Transactions, String>("amount"));
        table_dash.setItems(getTransaction(path));

        // Current Label iseset sya by getting the value of current balance
        currentBalanceLabel.setText("Php: " + us.getCurrentBalance());

        //set default value in current date
        incomeDateTxtField.setValue(LocalDate.now());
        expenseDateTxtField.setValue(LocalDate.now());

        // Dashboard Labels
        incomeLbl.setText(getIncomeTotal(path, getIncomeCategoryArray()) + "");
        expensesLbl.setText(getExpenseTotal(path, getExpenseCategoryArray()) + "");
        usernameLbl.setText(username);

        //number validator
        NumberValidator numVali = new NumberValidator();
        incomeAmountTxtField.getValidators().add(numVali);
        expenseAmountTxtField.getValidators().add(numVali);

        numVali.setMessage("Only numbers are allowed");

        incomeAmountTxtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    incomeAmountTxtField.validate();
                }
            }
        });

        expenseAmountTxtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    expenseAmountTxtField.validate();
                }
            }
        });

    }

    public String[] getIncomeCategoryArray() {
        String[] incomeCategoryArray = {
            "Allowance",
            "Scholarship Allowance",
            "Working Allowance",
            "Gifts",
            "Others"
        };

        return incomeCategoryArray;
    }

    public double[] getIncomeCategoryPercentage(String path, String[] category) {
        double[] incomeCategoryAmount = new double[category.length];
        for (int i = 0; i < incomeCategoryAmount.length; i++) {
            incomeCategoryAmount[i] = 0;
        }

        for (int i = 0; i < getAllArray(path).length; i++) {
            String[] split = getAllArray(path)[i].split(",");
            if (split[0].equals("Income")) {
                for (int j = 0; j < incomeCategoryAmount.length; j++) {
                    if (category[j].equals(split[2])) {
                        incomeCategoryAmount[j] += Integer.parseInt(split[3]);
                    }
                }
            }
        }

        double[] incomeCategoryPercentage = new double[category.length];

        int incomeSum = 0;
        for (int i = 0; i < incomeCategoryAmount.length; i++) {
            incomeSum += incomeCategoryAmount[i];
        }

        for (int i = 0; i < incomeCategoryPercentage.length; i++) {
            incomeCategoryPercentage[i] = incomeCategoryAmount[i] / incomeSum * 100;
            incomeCategoryPercentage[i] = Math.round(incomeCategoryPercentage[i]);
        }

        return incomeCategoryPercentage;
    }

    public String[] getExpenseCategoryArray() {
        String[] expenseCategoryArray = {
            "Food",
            "Transportation",
            "Apparel",
            "Gift",
            "Hygiene",
            "Load",
            "Utilitities",
            "Self-Development",
            "Education",
            "Health",
            "Others"
        };
        return expenseCategoryArray;
    }

    public double[] getExpenseCategoryPercentage(String path, String[] category) {
        double[] expenseCategoryAmount = new double[category.length];

        for (int i = 0; i < expenseCategoryAmount.length; i++) {
            expenseCategoryAmount[i] = 0;
        }

        for (int i = 0; i < getAllArray(path).length; i++) {
            String[] split = getAllArray(path)[i].split(",");
            if (split[0].equals("Expense")) {
                for (int j = 0; j < expenseCategoryAmount.length; j++) {
                    if (category[j].equals(split[2])) {
                        expenseCategoryAmount[j] += Integer.parseInt(split[3]);
                    }
                }
            }
        }

        double[] expenseCategoryPercentage = new double[category.length];

        int expenseSum = 0;
        for (int i = 0; i < expenseCategoryAmount.length; i++) {
            expenseSum += expenseCategoryAmount[i];
        }
        for (int i = 0; i < expenseCategoryPercentage.length; i++) {
            expenseCategoryPercentage[i] = (expenseCategoryAmount[i] / expenseSum) * 100;
        }

        return expenseCategoryPercentage;
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

    public ObservableList<Transactions> getTransaction(String path) {
        ObservableList<Transactions> transaction = FXCollections.observableArrayList();
        for (int i = 0; i < getAllArray(path).length; i++) {
            String[] lineArray = getAllArray(path)[i].split(",");
            transaction.add(new Transactions(lineArray[0], lineArray[1], lineArray[2], lineArray[3], lineArray[4]));
        }
        return transaction;
    }

    public ObservableList<PieChart.Data> getIncomePieData(String[] Category, double[] Percentage) {
        ObservableList<PieChart.Data> incomePieData = FXCollections.observableArrayList();
        for (int i = 0; i < Category.length; i++) {
            if (Percentage[i] == 0) {
            } else {
                incomePieData.add(new PieChart.Data(Category[i], Percentage[i]));
            }
        }
        return incomePieData;
    }

    public ObservableList<PieChart.Data> getExpensePieData(String[] Category, double[] Percentage) {
        ObservableList<PieChart.Data> expensePieData = FXCollections.observableArrayList();
        for (int i = 0; i < Category.length; i++) {
            if (Percentage[i] == 0) {
            } else {
                expensePieData.add(new PieChart.Data(Category[i], Percentage[i]));
            }
        }
        return expensePieData;
    }

    public int getIncomeTotal(String path, String[] category) {
        double[] incomeCategoryAmount = new double[category.length];
        for (int i = 0; i < incomeCategoryAmount.length; i++) {
            incomeCategoryAmount[i] = 0;
        }

        for (int i = 0; i < getAllArray(path).length; i++) {
            String[] split = getAllArray(path)[i].split(",");
            if (split[0].equals("Income")) {
                for (int j = 0; j < incomeCategoryAmount.length; j++) {
                    if (category[j].equals(split[2])) {
                        incomeCategoryAmount[j] += Integer.parseInt(split[3]);
                    }
                }
            }
        }

        int incomeSum = 0;
        for (int i = 0; i < incomeCategoryAmount.length; i++) {
            incomeSum += incomeCategoryAmount[i];
        }
        return incomeSum;
    }

    public int getExpenseTotal(String path, String[] category) {
        double[] expenseCategoryAmount = new double[category.length];

        for (int i = 0; i < expenseCategoryAmount.length; i++) {
            expenseCategoryAmount[i] = 0;
        }

        for (int i = 0; i < getAllArray(path).length; i++) {
            String[] split = getAllArray(path)[i].split(",");
            if (split[0].equals("Expense")) {
                for (int j = 0; j < expenseCategoryAmount.length; j++) {
                    if (category[j].equals(split[2])) {
                        expenseCategoryAmount[j] += Integer.parseInt(split[3]);
                    }
                }
            }
        }

        int expenseSum = 0;
        for (int i = 0; i < expenseCategoryAmount.length; i++) {
            expenseSum += expenseCategoryAmount[i];
        }

        return expenseSum;
    }

    @FXML
    private void handleIncomeResetBtn(ActionEvent event) {
        incomeAmountTxtField.setText("");
        incomeRemarkTxtField.setText("");
    }

    @FXML
    private void handleExpenseResetBtn(ActionEvent event) {
        expenseAmountTxtField.setText("");
        expenseRemarkTxtField.setText("");
    }

    @FXML
    private void handleAddIcomeBtn(ActionEvent event) {
        String path = "C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\" + username + "_database.txt";
        File file = new File(path);
        String incomeDate = incomeDateTxtField.getValue().toString();
        String incomeCategory = incomeCategoryCbox.getValue().toString();
        String incomeAmount = incomeAmountTxtField.getText();
        String incomeRemark = incomeRemarkTxtField.getText();

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("Income,");
            bw.append(incomeDate + ",");
            bw.append(incomeCategory + ",");
            bw.append(incomeAmount + ",");
            bw.append(incomeRemark);
            bw.newLine();

            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(null, "Income Added!");
        } catch (Exception e) {
            System.out.println("Error");
        }

        // naguupdate si table
        table.setItems(getTransaction(path));
        table_dash.setItems(getTransaction(path));

        // naguupdate si stats pies
        incomePie.setData(getIncomePieData(getIncomeCategoryArray(), getIncomeCategoryPercentage(path, getIncomeCategoryArray())));
        expensePie.setData(getExpensePieData(getExpenseCategoryArray(), getExpenseCategoryPercentage(path, getExpenseCategoryArray())));

        // naguupdate si current balance label
        us.setCurrentBalance((Integer.parseInt(us.getCurrentBalance()) + Integer.parseInt(incomeAmount)) + "");
        currentBalanceLabel.setText("Php: " + us.getCurrentBalance());

        //naguupdate si income at expense total
        incomeLbl.setText(getIncomeTotal(path, getIncomeCategoryArray()) + "");
        expensesLbl.setText(getExpenseTotal(path, getExpenseCategoryArray()) + "");
    }

    @FXML
    private void handleAddExpenseBtn(ActionEvent event) {
        String path = "C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\" + username + "_database.txt";
        File file = new File(path);
        String expenseDate = expenseDateTxtField.getValue().toString();
        String expenseCategory = expenseCategoryCbox.getValue().toString();
        String expenseAmount = expenseAmountTxtField.getText();
        String expenseRemark = expenseRemarkTxtField.getText();

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("Expense,");
            bw.append(expenseDate + ",");
            bw.append(expenseCategory + ",");
            bw.append(expenseAmount + ",");
            bw.append(expenseRemark);
            bw.newLine();

            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(null, "Expense Added!");
        } catch (Exception e) {
            System.out.println("Error");
        }

        // naguupdate si table
        table.setItems(getTransaction(path));
        table_dash.setItems(getTransaction(path));

        // naguupdate si stats pies
        incomePie.setData(getIncomePieData(getIncomeCategoryArray(), getIncomeCategoryPercentage(path, getIncomeCategoryArray())));
        expensePie.setData(getExpensePieData(getExpenseCategoryArray(), getExpenseCategoryPercentage(path, getExpenseCategoryArray())));

        // naguupdate si current balance label
        us.setCurrentBalance((Integer.parseInt(us.getCurrentBalance()) - Integer.parseInt(expenseAmount)) + "");
        if (Integer.parseInt(us.getCurrentBalance()) <= 0) {
            JOptionPane.showMessageDialog(null, "Current Balance is below or equal to 0!");
            currentBalanceLabel.setText("Php: 0.00");
        } else {
            currentBalanceLabel.setText("Php: " + us.getCurrentBalance());
        }

        //naguupdate si income at expense total
        incomeLbl.setText(getIncomeTotal(path, getIncomeCategoryArray()) + "");
        expensesLbl.setText(getExpenseTotal(path, getExpenseCategoryArray()) + "");
    }

    @FXML
    private void handleSignOutBtn(ActionEvent event) throws IOException {
        String filePath = "C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\register.txt";
        String tempFile = "C:\\Users\\Gillian\\Documents\\NetBeansProjects\\MMSystem\\src\\mmsystem\\database\\temp.txt";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.equals(us.getDetails())) {
                    String[] detailSplit = us.getDetails().split(",");
                    if (Integer.parseInt(us.getCurrentBalance()) <= 0) {
                        bw.append(detailSplit[0] + "," + detailSplit[1] + "," + detailSplit[2] + "," + detailSplit[3] + "," + detailSplit[4] + "," + detailSplit[2] + "," + "0");
                    } else {
                        bw.append(detailSplit[0] + "," + detailSplit[1] + "," + detailSplit[2] + "," + detailSplit[3] + "," + detailSplit[4] + "," + detailSplit[2] + "," + us.getCurrentBalance());
                    }
                    bw.newLine();
                } else {
                    bw.append(line);
                    bw.newLine();
                }
            }

            br.close();
            bw.flush();
            bw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
//            BufferedReader br = new BufferedReader(new FileReader(filePath));
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                if (line.equals(us.getDetails())) {
//                    String[] detailSplit = us.getDetails().split(",");
//                    bw.append(detailSplit[0] + "," + detailSplit[1] + "," + detailSplit[2] + "," +  us.getCurrentBalance());
//                    bw.newLine();
//                } else {
//                    bw.append(line);
//                    bw.newLine();
//                }
//            }
//
//            br.close();
//            bw.flush();
//            bw.close();
//            oldFile.delete();
//            File dump = new File(filePath);
//            newFile.renameTo(dump);
//            JOptionPane.showMessageDialog(null, "Logged Out!");
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        Parent changeMain = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene changeMainScene = new Scene(changeMain);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeMainScene);
        mainStage.show();
    }

}
