/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personal_notes_manager;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
public class ForgotpasswordController implements Initializable {

    @FXML
    private TextField fpee;
    @FXML
    private PasswordField fpep;
    @FXML
    private PasswordField fpcp;
    @FXML
    private Label fpwarns;
    @FXML
    private Button fpcb;
    @FXML
    private Hyperlink cpfp;
    @FXML
    private Button fpgb;
    
    private Connection connection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
    connection = DBConnection.getConnection();
} catch (SQLException e) {
    System.out.println("Database connection failed: " + e.getMessage());
}

    }

    @FXML
    private void confirmfp(ActionEvent event) {
        String email = fpee.getText().trim();
        String newPassword = fpep.getText();
        String confirmPassword = fpcp.getText();

        if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            fpwarns.setText("Please fill all fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            fpwarns.setText("Passwords do not match.");
            return;
        }

        try {
    // Check if email exists
    String checkEmailQuery = "SELECT * FROM users WHERE email = ?";
    PreparedStatement checkStmt = connection.prepareStatement(checkEmailQuery);
    checkStmt.setString(1, email);
    ResultSet rs = checkStmt.executeQuery();

    if (rs.next()) {
        // Email found, update password
        String updateQuery = "UPDATE users SET password = ? WHERE email = ?";
        PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
        updateStmt.setString(1, newPassword);
        updateStmt.setString(2, email);
        updateStmt.executeUpdate();

        fpwarns.setText("Password changed successfully!");
    } else {
        fpwarns.setText("Email not found.");
    }

    rs.close();
    checkStmt.close();

} catch (SQLException e) {
    fpwarns.setText("Error: " + e.getMessage());
}

    }

    @FXML
    private void ffptocp(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
    }

    @FXML
    private void gobackfp(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")); // login page
        stage.setScene(new Scene(root));
        stage.show();

        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
    }
}