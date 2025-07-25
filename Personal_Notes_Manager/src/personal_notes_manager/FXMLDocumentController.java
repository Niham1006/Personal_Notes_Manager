/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package personal_notes_manager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Hyperlink;

/**
 *
 * @author Niham
 */
public class FXMLDocumentController implements Initializable {
    

    @FXML
    private Button login;
    @FXML
    private TextField logusr;
    @FXML
    private PasswordField logpass;
    @FXML
    private Label supconfredgreen;
    @FXML
    private Label supconf1;
    @FXML
    private Button forsignup;
    @FXML
    private Hyperlink fp;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginclick(ActionEvent event) throws IOException {
        
    String username = logusr.getText();
    String password = logpass.getText();

    if (username.isEmpty() || password.isEmpty()) {
        supconfredgreen.setStyle("-fx-text-fill: red;");
        supconfredgreen.setText("Please enter username and password.");
        return;
    }

    String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int userId = rs.getInt("id");
            String userNameFromDB = rs.getString("username");

            // 🔐 Set Session
            Session.setUser(userId, userNameFromDB);

            // Redirect to Notepad
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("notepad.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            Window window = ((Node) event.getSource()).getScene().getWindow();
            window.hide();

        } else {
            supconfredgreen.setStyle("-fx-text-fill: red;");
            supconfredgreen.setText("Invalid credentials.");
        }

    } catch (SQLException e) {
        supconfredgreen.setStyle("-fx-text-fill: red;");
        supconfredgreen.setText("Database error: " + e.getMessage());
    }
        //System.out.println("Clicked Login");
        
    }

    @FXML
    private void gotosignup(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        System.out.println("Switched to Signup");
        
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
        
        
    }

    @FXML
    private void gotofp(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("forgotpassword.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        System.out.println("Goto to Forgot Password");
        
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
        
    }
    
}
