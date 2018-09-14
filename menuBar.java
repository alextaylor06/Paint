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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class menuBar extends Paint{
    
     static void start(Canvas canvas, BorderPane pane, GraphicsContext gc, Stage primaryStage){
         
        //Makes new menu and adds all option to the dropdown
        MenuBar menu = new MenuBar(); 
        menu.prefWidthProperty().bind(primaryStage.widthProperty()); Menu fileMenu = new Menu("File");         
        MenuItem open = new MenuItem("Open");
        MenuItem saveas = new MenuItem("Save As");
        MenuItem save = new MenuItem("Save");
        MenuItem exit= new MenuItem("Exit");
        //OPens file from file selector
        open.setOnAction((e)->{
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
        });
        //Declares a new save object
        Save a=new Save();
        //Save As
        saveas.setOnAction((e)->{
           a.saveAs(primaryStage, canvas);
        });
        //Save
        save.setOnAction((e)->{
            a.save(primaryStage, canvas);
        });
        //Exits application
            exit.setOnAction((e)->{
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
                
                //Sets everything up and adds button
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_CENTER);
                hBox.setSpacing(40.0);
                hBox.getChildren().addAll(yesBtn, noBtn);
                VBox vBox = new VBox();
                vBox.setSpacing(40.0);
                vBox.getChildren().addAll(exitLabel, hBox);
                dialogStage.setScene(new Scene(vBox));
                dialogStage.show();
                //Actions for buttons
                yesBtn.setOnAction (ae -> Platform.exit());
                noBtn.setOnAction (ae -> dialogStage.close ());
                
            });
            //Sets everything on the file
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        pane.setTop(menu);  
        fileMenu.getItems().add(open);
        fileMenu.getItems().add(saveas);
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(exit);
        menu.getMenus().addAll(fileMenu); //Opens menu for file menu
    }
}
