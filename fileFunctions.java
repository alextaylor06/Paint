/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Alex
 */
public class fileFunctions {
    void exit(Stage primaryStage, Canvas canvas , Save a, boolean saved)
    {
                if(!saved){
                //Sets new stage for dialog box
                    final Stage dialogStage = new Stage();
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                //Asks user if they want to quit without saving
                    Label exitLabel = new Label("Are you sure you want to exit without saving?");
                    exitLabel.setAlignment(Pos.BASELINE_CENTER);
                    //If yes button is selected application will close
                    Button yesBtn = new Button("Yes");

                    //If no button is selected dialog box will close
                    Button noBtn = new Button("No");
                    Button savebtn =new Button("Save and Exit");

                    //Sets everything up and adds button
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.BASELINE_CENTER);
                    hBox.setSpacing(40.0);
                    hBox.getChildren().addAll(yesBtn,savebtn, noBtn);
                    VBox vBox = new VBox();
                    vBox.setSpacing(40.0);
                    vBox.getChildren().addAll(exitLabel, hBox);
                    dialogStage.setScene(new Scene(vBox));
                    dialogStage.show();
                    //Actions for buttons
                    yesBtn.setOnAction (ae -> Platform.exit());
                    noBtn.setOnAction (ae -> dialogStage.close ());
                    savebtn.setOnAction (ae -> a.saveAs(primaryStage, canvas));
                }
                else Platform.exit();
    }
     void open(Stage primaryStage, Canvas canvas,GraphicsContext gc, boolean saved )
    {
        //will open file and place on canvas
            saved=false;
            FileChooser openFile = new FileChooser();
            openFile.setTitle("Open File");
            File file = openFile.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    
                    gc.drawImage(img, 0, 0);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
    }
    
}
