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

    String sql = "INSERT INTO notes (title, content) VALUES (?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, title);
        stmt.setString(2, content);

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Note added successfully.");
            npititle.clear();
            npinotes.clear();
            loadNotes();
        }

    } catch (SQLException e) {
        System.out.println("Error inserting note: " + e.getMessage());
    }  
    }

    @FXML
    private void previewbtn(ActionEvent event) throws IOException {
        
         String selectedTitle = sltnote.getSelectionModel().getSelectedItem();

    if (selectedTitle == null || selectedTitle.isEmpty()) {
        System.out.println("No note selected to preview.");
        return;
    }
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("preview.fxml"));
    Parent root = loader.load();

    PreviewController previewController = loader.getController();
    previewController.loadNoteByTitle(selectedTitle); 

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.show();

    ((Node) event.getSource()).getScene().getWindow().hide();

    System.out.println("Previewing: " + selectedTitle);
        
    }
    
    private void loadNotes() {
    ObservableList<String> titles = FXCollections.observableArrayList();

    String sql = "SELECT title FROM notes";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            titles.add(rs.getString("title"));
        }

        sltnote.setItems(titles);
        notelist.setItems(titles);

    } catch (SQLException e) {
        System.out.println("Error loading note titles: " + e.getMessage());
    }
}    
}
