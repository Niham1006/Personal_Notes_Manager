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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;


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
    private Button logoutBtn;
    @FXML
    private ListView<String> notelist;
    @FXML
    private Hyperlink npcp;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadNotes();
    }    

    @FXML
    private void addnote(ActionEvent event) {
        
        
        String title = npititle.getText();
    String content = npinotes.getText();

    if (title.isEmpty() || content.isEmpty()) {
        System.out.println("Title or content is empty!");
        return;
    }

    String sql = "INSERT INTO notes (title, content, user_id) VALUES (?, ?, ?)";
try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql)) {

    stmt.setString(1, title);
    stmt.setString(2, content);
    stmt.setInt(3, Session.getUserId()); // logged-in user ID

    stmt.executeUpdate();
    System.out.println("Note saved.");
    
            // Reload the notes immediately after save to update the UI
        loadNotes();

        // Optionally, clear inputs after save
        npititle.clear();
        npinotes.clear();
    

} catch (SQLException e) {
    e.printStackTrace();
}

    }

    @FXML
    private void previewbtn(ActionEvent event) throws IOException {
        
         String selectedTitle = sltnote.getSelectionModel().getSelectedItem();

    if (selectedTitle == null || selectedTitle.isEmpty()) {
        System.out.println("No note selected to preview.");
        return; // Do nothing or later show alert
    }

    // Pass the title to the preview page via FXMLLoader
    FXMLLoader loader = new FXMLLoader(getClass().getResource("preview.fxml"));
    Parent root = loader.load();

    // Access the controller and pass the data
    PreviewController previewController = loader.getController();
    previewController.loadNoteByTitle(selectedTitle); // You’ll add this method next

    // Show preview window
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

    // Hide current window
    ((Node) event.getSource()).getScene().getWindow().hide();

    System.out.println("Previewing: " + selectedTitle);
        
    }
    
    private void loadNotes() {
        System.out.println("Loading notes for user ID: " + Session.getUserId());

     notelist.getItems().clear();
    sltnote.getItems().clear();

    String sql = "SELECT title FROM notes WHERE user_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, Session.getUserId());

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String title = rs.getString("title");
            notelist.getItems().add(title);
            sltnote.getItems().add(title);
        }

    } catch (SQLException e) {
        System.out.println("Error loading notes: " + e.getMessage());
    }
}

   @FXML
private void logout(ActionEvent event) throws IOException {
    // Load login page FXML
    Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
    Scene scene = new Scene(root);

    // Get current stage and set the new scene
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();

    System.out.println("Logged out and returned to login page");
} 

    @FXML
    private void gotocp(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        System.out.println("Goto to Change Password");
        
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
        
    }

    
}
