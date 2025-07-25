/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personal_notes_manager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Niham
 */
public class ChangepasswordController implements Initializable {

    @FXML
    private TextField cpee;
    @FXML
    private PasswordField cpep;
    @FXML
    private PasswordField cpcp;
    @FXML
    private Label cpwarns;
    @FXML
    private Button cpcb;
    @FXML
    private Hyperlink cpfp;
    @FXML
    private Button cpgb;
    
    private Connection connection;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void confirmcp(ActionEvent event) {
        String email = cpee.getText();
        String newPassword = cpep.getText();
        String confirmPassword = cpcp.getText();

        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            cpwarns.setText("Please fill in all fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            cpwarns.setText("Passwords do not match.");
            return;
        }

        try {
            
            Connection con = DBConnection.getConnection();

            // Check if email exists
            PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Email found, update password
                PreparedStatement updateStmt = con.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, email);
                int updated = updateStmt.executeUpdate();

                if (updated > 0) {
                    cpwarns.setText("Password changed successfully.");
                } else {
                    cpwarns.setText("Something went wrong.");
                }

                updateStmt.close();
            } else {
                cpwarns.setText("Email not found.");
            }

            rs.close();
            checkStmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            cpwarns.setText("Database error.");
        }
    }

    @FXML
    private void fcptofp(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("forgotpassword.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void gobackcp(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}