/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package personal_notes_manager;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Niham
 */
public class PreviewController implements Initializable {

    @FXML
    private TextField nptitle;
    @FXML
    private TextArea npnotes;
    @FXML
    private Button npedit;
    @FXML
    private Button npupdate;
    @FXML
    private Button npdelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void noteedit(ActionEvent event) {
        
        System.out.println("Clicked For Edit");
        
    }

    @FXML
    private void npupdate(ActionEvent event) {
        
        System.out.println("Clicked For Update");
        
    }

    @FXML
    private void npdelete(ActionEvent event) {
            
        System.out.println("Clicked for Note to Delete");
     
    }
    
}
