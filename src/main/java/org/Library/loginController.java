package org.Library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginController {

    @FXML
    private Hyperlink forgotPass;

    @FXML
    private TextField userNameTxtbox;

    @FXML
    private PasswordField passTxtbox;

    @FXML
    private Button loginBTN;

    @FXML
    void deploy(MouseEvent event) {
        if (userNameTxtbox.getText().equals("admin") && passTxtbox.getText().equals("admin")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/Library/options.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 927, 480);
                Stage stage = new Stage();
                stage.setTitle("Library Management");
                stage.setScene(scene);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void deployGuest(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/org/Library/optionsGuest.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 927, 480);
            Stage stage = new Stage();
            stage.setTitle("Library Management");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}