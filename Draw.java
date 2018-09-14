/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;


import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


/**
 *
 * @author Alex
 */
public class Draw{

    static void start( Canvas canvas,BorderPane pane, GraphicsContext gc) {
        //Makes all buttons 
    ToggleButton linebtn = new ToggleButton("Line");
    ToggleButton drawbtn = new ToggleButton("Draw");
    ToggleButton rectbtn = new ToggleButton("Rectangle");
    ToggleButton circlebtn = new ToggleButton("Circle");
    
   //Sets all buttons to same size and group
    ToggleButton[] toolsArr = { linebtn, rectbtn, circlebtn, };  
    ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMinWidth(90);
            tool.setToggleGroup(tools);
            tool.setCursor(Cursor.HAND);
        }
        //Sets color pickers and defaults
    ColorPicker cpLine = new ColorPicker(Color.BLACK);
    ColorPicker cpFill = new ColorPicker(Color.TRANSPARENT);
    Slider slider = new Slider(1, 50, 3);
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    Label fill_color = new Label("Fill Color");
    Label line_width = new Label("3.0");
    //Makes Box for buttons to go into and adds them all
    VBox btns = new VBox(10);
    btns.getChildren().addAll(drawbtn, linebtn, rectbtn, circlebtn,  cpLine, fill_color, cpFill, line_width, slider);
    btns.setPadding(new Insets(5));
    btns.setStyle("-fx-background-color: #fff");
    btns.setPrefWidth(150);
    //Delaring shapes that can be used
     Line line = new Line();
     Rectangle rect = new Rectangle();
     Circle circ = new Circle();                 
     //Starts drawing for 
      canvas.setOnMousePressed((MouseEvent e) -> {
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
        canvas.setOnMouseReleased(e->{ if(drawbtn.isSelected()) {//Stops drawing
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
