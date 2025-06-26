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
    private Button gotosignup;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginclick(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("notepad.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
        
    }

    @FXML
    private void gotosignup(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
        
        
    }
    
}
