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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        nptitle.setEditable(false);
        npnotes.setEditable(false);
}
    

    @FXML
    private void noteedit(ActionEvent event) {
        
        nptitle.setEditable(true);
        npnotes.setEditable(true);
        System.out.println("Fields are now editable");
        
}
        
    @FXML
    private void npupdate(ActionEvent event) {
    String newTitle = nptitle.getText();
    String newContent = npnotes.getText();

    String sql = "UPDATE notes SET title = ?, content = ? WHERE title = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, newTitle);
        stmt.setString(2, newContent);
        stmt.setString(3, originalTitle); 

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Note updated successfully.");
            originalTitle = newTitle; 
        } else {
            System.out.println("No matching note found to update.");
        }

        nptitle.setEditable(false);
        npnotes.setEditable(false);

    } catch (SQLException e) {
        System.out.println("Error updating note: " + e.getMessage());
    }
}       

    @FXML
    private void npdelete(ActionEvent event) throws IOException {
        String titleToDelete = nptitle.getText();

        String sql = "DELETE FROM notes WHERE title = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, titleToDelete);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Note deleted successfully.");

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("notepad.fxml"));
            stage.setScene(new Scene(root));
            stage.show();

            ((Node) event.getSource()).getScene().getWindow().hide();
        } else {
            System.out.println("No note found to delete.");
        }

    } catch (SQLException e) {
        System.out.println("Error deleting note: " + e.getMessage());
    }
}

    
    @FXML
    private void npgoback(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("notepad.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
}

    
    public void loadNoteByTitle(String title) {
    String sql = "SELECT content FROM notes WHERE title = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            originalTitle = title; 
            nptitle.setText(title);
            npnotes.setText(rs.getString("content"));
            nptitle.setEditable(false);
            npnotes.setEditable(false);
        }

    } catch (SQLException e) {
        System.out.println("Error loading note: " + e.getMessage());
    }
}

    private String originalTitle;
    
}
