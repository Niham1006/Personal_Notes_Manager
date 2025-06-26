/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Niham
 */
public class NotepadController implements Initializable {

    @FXML
    private ComboBox<String> sltnote;
    @FXML
    private TextField npititle;
    @FXML
    private TextArea npinotes;
    @FXML
    private Button npiaddbtn;
    @FXML
    private ListView<String> notelist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addnote(ActionEvent event) {
    }

    @FXML
    private void previewbtn(ActionEvent event) throws IOException {
        
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("preview.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
        
    }
    
}
