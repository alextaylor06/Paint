/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class menuBar {
    boolean saved;
     void menu(Canvas canvas, BorderPane pane, GraphicsContext gc, Stage primaryStage, Scene scene1) throws FileNotFoundException{
         
         primaryStage.setTitle("Pain(t)");
         
        
        //Makes new menu and adds all option to the dropdown
        MenuBar menu = new MenuBar(); 
        menu.prefWidthProperty().bind(primaryStage.widthProperty()); Menu fileMenu = new Menu("File");         
        MenuItem open = new MenuItem("Open");
        MenuItem saveas = new MenuItem("Save As");
        MenuItem save = new MenuItem("Save");
        MenuItem exit= new MenuItem("Exit");
        //OPens file from file selector
        
       fileFunctions b= new fileFunctions();
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));//Open will execute if clicked on or Ctrl+C
        open.setOnAction((e)->{
            b.open(primaryStage, canvas, gc, saved);
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
        fileMenu.getItems().add(exit);
        menu.getMenus().addAll(fileMenu); //Opens menu for file menu
    }
     void draw( Canvas canvas,BorderPane pane, GraphicsContext gc, Stage primaryStage) throws FileNotFoundException {
    //Load all icons for buttons   
    Image line1 = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\line.png"));
    Image draw = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\draw.png"));
    Image rectangle = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\rectangle.png"));
    Image circle = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\circle.png"));
     //Makes all buttons 
    ToggleButton linebtn = new ToggleButton ("", new ImageView(line1));
    ToggleButton drawbtn = new ToggleButton("",new ImageView(draw));
    ToggleButton rectbtn = new ToggleButton("",new ImageView(rectangle));
    ToggleButton circlebtn = new ToggleButton("",new ImageView(circle));
    
    
   //Sets all buttons to same size and group
    ToggleButton[] toolsArr = { drawbtn,linebtn, rectbtn, circlebtn, };  
    ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMaxWidth(50);
            tool.setMaxHeight(50);
            tool.setMinWidth(100);
            tool.setMinHeight(50);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        //Sets color pickers and defaults
    ColorPicker cpLine = new ColorPicker(Color.BLACK);
    ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);
    Slider slider = new Slider(1, 50, 3);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    Label fill_color = new Label("Fill");
    Label line_width = new Label("3.0");
    //Makes Box for buttons to go into and adds them all
    VBox btns = new VBox(10);
    btns.getChildren().addAll(drawbtn, linebtn, rectbtn, circlebtn,  cpLine, fill_color, cpFill, line_width, slider);
    btns.setPadding(new Insets(5));
    btns.setStyle("-fx-background-color: #fff");
    btns.setPrefWidth(50);
    //Delaring shapes that can be used
     Line line = new Line();
     Rectangle rect = new Rectangle();
     Circle circ = new Circle();                 
     //Starts drawing for 
      canvas.setOnMousePressed((MouseEvent e) -> {
          saved=false;
          
          if(drawbtn.isSelected()) {        //Free Hand drawing
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
            }
            else if(linebtn.isSelected()) {     //Line Drawing
                gc.setStroke(cpLine.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());
            }
            else if(rectbtn.isSelected()) {     //Rectangle/Square Drawing
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                rect.setX(e.getX());                
                rect.setY(e.getY());
            }
            else if(circlebtn.isSelected()) {       //Circle Drawing
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }
    });
        canvas.setOnMouseDragged(e->{
            if(drawbtn.isSelected()) {              //Only for draw, dragging draws along the way
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });
        canvas.setOnMouseReleased(e->{ 
            primaryStage.setTitle("Pain(t)*");
            if(drawbtn.isSelected()) {//Stops drawing
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            }
            else if(linebtn.isSelected()) {//Makes line from the start to finsih position
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());  
            }
            //Makes Rectangle from the start to finish position
            else if(rectbtn.isSelected()) {
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                //rect.setX((rect.getX() > e.getX()) ? e.getX(): rect.getX());
                if(rect.getX() > e.getX()) {
                    rect.setX(e.getX());
                }
                //rect.setY((rect.getY() > e.getY()) ? e.getY(): rect.getY());
                if(rect.getY() > e.getY()) {
                    rect.setY(e.getY());
                }
                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());  
            }
            //Makes circle from the start to finish position
            else if(circlebtn.isSelected()) {
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY())) / 2);
                if(circ.getCenterX() > e.getX()) {
                    circ.setCenterX(e.getX());
                }
                if(circ.getCenterY() > e.getY()) {
                    circ.setCenterY(e.getY());
                }
                gc.fillOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
                gc.strokeOval(circ.getCenterX(), circ.getCenterY(), circ.getRadius(), circ.getRadius());
            }
        });
        // color picker
        cpLine.setOnAction(e->{
                gc.setStroke(cpLine.getValue());
        });
        cpFill.setOnAction(e->{
                gc.setFill(cpFill.getValue());
        });
        
        // slider
        slider.valueProperty().addListener(e->{
            double width = slider.getValue();
            line_width.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });
    pane.setLeft(btns);
}
}