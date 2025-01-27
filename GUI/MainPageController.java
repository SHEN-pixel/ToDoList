/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class MainPageController {

    @FXML
    private AnchorPane background;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button startButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void login(ActionEvent event) throws IOException{
        
        String username = nameTextField.getText();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SecondPage.fxml"));
        root = loader.load();
        
        SecondPageController secondPageController = loader.getController();
        secondPageController.displayName(username);
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
