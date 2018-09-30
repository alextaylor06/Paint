/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */

public class Draw extends menuBar{
    //Declares stacks for undo
    Stack undo=new Stack();
    Stack redo=new Stack();
    double width;
    Boolean selected; //Determines if the select tool has something selected
    void draw(Canvas canvas,BorderPane pane, GraphicsContext gc,Stage primaryStage, StackPane stack,Scene scene) throws FileNotFoundException {
    base(canvas);
    selected=false;
    width=3;
    selectTool selec=new selectTool();
    //Load all icons for buttons   
    Image line1 = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\line.png"));
    Image draw = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\draw.png"));
    Image rectangle = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\rectangle.png"));
    Image circle = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\circle.png"));
    Image ellipse = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\ellipse.png"));
    Image select = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\drag.png"));
    Image eraser = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\eraser.png"));
    Image[] imageArr={line1,draw,rectangle,circle,ellipse,select,eraser};
     //Makes all buttons 
    ToggleButton linebtn = new ToggleButton ("", new ImageView(line1));
    ToggleButton drawbtn = new ToggleButton("",new ImageView(draw));
    ToggleButton rectbtn = new ToggleButton("",new ImageView(rectangle));
    ToggleButton circlebtn = new ToggleButton("",new ImageView(circle));
    ToggleButton elpsbtn = new ToggleButton("",new ImageView(ellipse));
    ToggleButton selectbtn = new ToggleButton("",new ImageView(select));
    ToggleButton erasebtn = new ToggleButton("",new ImageView(eraser));
    
   //Sets all buttons to same size and group
    ToggleButton[] toolsArr = { drawbtn,linebtn, rectbtn, circlebtn,elpsbtn,selectbtn ,erasebtn};  
    
    Pointer point=new Pointer();
    
   
    
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
    btns.getChildren().addAll(drawbtn, linebtn, rectbtn, circlebtn,  elpsbtn,selectbtn,erasebtn,cpLine, fill_color, cpFill, line_width, slider);
    btns.setPadding(new Insets(5));
    btns.setStyle("-fx-background-color: #fff");
    btns.setPrefWidth(50);
    //Delaring shapes that can be used
     Line line = new Line();
     Rectangle rect = new Rectangle();
     Circle circ = new Circle(); 
     Ellipse elps =new Ellipse();
     //Starts drawing for 
     shapes shape=new shapes();
     
     
      point.cursors(toolsArr, imageArr,scene);
    
     //If canvas is pressed
      canvas.setOnMousePressed((MouseEvent e) -> {
          saved=false;
          if (selectbtn.isSelected())
          {
              if(!selected){
                  gc.setLineWidth(1);
                gc.setStroke(Color.BLACK);      //Draws rect where selected area is
                rect.setStrokeWidth(1);
                gc.setFill(Color.TRANSPARENT);
                rect.setX(e.getX());                
                rect.setY(e.getY());
              }else selec.clicked(gc);
            }
           else { //If its selected call selectTool
              shape.start(toolsArr, e, gc, line, circ, rect,elps, cpLine,cpFill,scene);
          }
    });
    
        canvas.setOnMouseDragged(e->{
        for(int i=0;i<6;i++)    //Draws the image before it draws the line for smooth line tranisition
           {
               gc.drawImage((Image) undo.lastElement(), i*1800, 0);
           }
           if(selectbtn.isSelected()) {     //Rectangle/Square Drawing
               if(!selected){
                double x =rect.getX();
                double y =rect.getY();
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                if(x > e.getX()) {
                    x=(e.getX());
                }
                if(y > e.getY()) {
                    y=(e.getY());
                }
                gc.fillRect(x, y, rect.getWidth(), rect.getHeight());
                gc.strokeRect(x, y, rect.getWidth(), rect.getHeight());
               }else selec.dragged(gc,e);
            }
           else shape.drag(toolsArr, e, gc, line, circ, rect,elps);
        //Save the image and draw it    
        WritableImage writableImage1 =new WritableImage(1800,950); 
        Image img =canvas.snapshot(null,writableImage1);
        undo.push(img);
        for(int i=0;i<6;i++)
           {
               gc.drawImage((Image) undo.lastElement(), i*1800, 0);
           }
        undo.pop();
        });
        
        canvas.setOnMouseReleased(e->{ 
            redo.clear();
            primaryStage.setTitle("Pain(t)*");
            if(drawbtn.isSelected()) {//Stops drawing
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
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
            } else if(elpsbtn.isSelected()) {
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));
                
                if(elps.getCenterX() > e.getX()) {
                    elps.setCenterX(e.getX());
                }
                if(elps.getCenterY() > e.getY()) {
                    elps.setCenterY(e.getY());
                }
                
                gc.strokeOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(elps.getCenterX(), elps.getCenterY(), elps.getRadiusX(), elps.getRadiusY());
                
            }else if(selectbtn.isSelected()) {
                if(!selected)
                {
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
                selec.move((int)rect.getWidth(),(int)rect.getHeight(), (int)rect.getX(), (int)rect.getY(),canvas,gc);
                gc.setLineWidth(width);
                selected=true;
            }else selected=false;
               
            }else if(erasebtn.isSelected()) {//Stops drawing
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            }
            
            base(canvas);
            
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
            width = slider.getValue();
            line_width.setText(String.format("%.1f", width));
            gc.setLineWidth(width);
        });
    pane.setLeft(btns);
}
    void base(Canvas canvas)
    {
        WritableImage writableImage1 =new WritableImage(1800,950);
        Image img =canvas.snapshot(null,writableImage1);
        undo.push(img);
       
    }
    void Undo(GraphicsContext gc)
    {
       if(undo.firstElement()!=undo.lastElement()) 
       {
           redo.push(undo.lastElement());
           undo.pop();
           
           for(int i=0;i<6;i++)
           {
               gc.drawImage((Image) undo.lastElement(), i*1800, 0);
           }
       }
       
    }
    void redo(GraphicsContext gc, Canvas canvas)
    {
        if(redo.firstElement()!=null)
        {
        for(int i=0;i<6;i++)
           {
            gc.drawImage((Image) redo.lastElement(), i*1800, 0);
           }
        base(canvas);
        redo.pop();
    }
        
       
       
    }
    
}
