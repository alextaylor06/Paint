/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.FileNotFoundException;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class menuBar {
    boolean saved;
    Draw h;
    
     void menu( Canvas canvas,BorderPane pane, GraphicsContext gc,Stage primaryStage, StackPane stack,Scene scene) throws FileNotFoundException{
        h=new Draw();
         h.draw(canvas, pane, gc, primaryStage, stack,scene);//Calls objects giving variables they need 
        //Sets Scene
        //pane.setCenter(canvas);
         primaryStage.setTitle("Pain(t)");
         
        
        //Makes new menu and adds all option to the dropdown
        MenuBar menu = new MenuBar(); 
        
        menu.prefWidthProperty().bind(primaryStage.widthProperty()); 
        Menu fileMenu = new Menu("File");      
        Menu editMenu = new Menu("Edit");     
        MenuItem open = new MenuItem("Open");
        MenuItem saveas = new MenuItem("Save As");
        MenuItem save = new MenuItem("Save");
        MenuItem exit= new MenuItem("Exit");
        MenuItem undoit=new MenuItem("Undo");
        MenuItem redo=new MenuItem("Redo");
        //OPens file from file selector
        
       fileFunctions b= new fileFunctions();
        
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));//Open will execute if clicked on or Ctrl+C
        open.setOnAction((e)->{
            b.open(primaryStage, canvas, gc, saved);
            h.base(canvas);
        });
        undoit.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));//Open will execute if clicked on or Ctrl+C
        undoit.setOnAction((e)->{
           h. Undo(gc);
        });
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));//Open will execute if clicked on or Ctrl+C
        redo.setOnAction((e)->{
           h. redo(gc, canvas);
        });
        //Declares a new save object
        Save a=new Save();
        //Save As
        saveas.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        saveas.setOnAction((e)->{
           a.saveAs(primaryStage, canvas);
           saved=true;
           primaryStage.setTitle("Pain(t)");
        });
        //Save
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));//Save will open on click or ctrl+s
        save.setOnAction((e)->{
            a.save(primaryStage, canvas);
            saved=true;
            primaryStage.setTitle("Pain(t)");
        });
         exit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));//Exit will open on click or ctrl+e
            exit.setOnAction((e)->{
                b.exit(primaryStage, canvas, a, saved);
            });
        
      
            //Sets everything on the file
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        pane.setTop(menu);  
        fileMenu.getItems().add(open);
        fileMenu.getItems().add(saveas);
        fileMenu.getItems().add(save);
        editMenu.getItems().add(undoit);
        editMenu.getItems().add(redo);
        fileMenu.getItems().add(exit);
        menu.getMenus().addAll(fileMenu, editMenu); //Opens menu for file menu
    }
}