/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Stack;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
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
import  javafx.scene.image.PixelReader;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
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
    //Load all image icons for buttons   
    Image line1 = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\line.png"));
    Image draw = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\draw.png"));
    Image rectangle = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\rectangle.png"));
    Image circle = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\circle.png"));
    Image ellipse = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\ellipse.png"));
    Image select = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\drag.png"));
    Image eraser = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\eraser.png"));
    Image dropper = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\dropper.png"));
    Image triangle1 = new Image(new FileInputStream("C:\\Users\\Alex\\Documents\\NetBeansProjects\\Paint\\src\\paint\\icons\\triangle.png"));
    Image[] imageArr={line1,draw,rectangle,circle,ellipse,select,eraser,dropper, triangle1};
     //Makes all buttons set to icon
    ToggleButton linebtn = new ToggleButton ("", new ImageView(line1));
    ToggleButton drawbtn = new ToggleButton("",new ImageView(draw));
    ToggleButton rectbtn = new ToggleButton("",new ImageView(rectangle));
    ToggleButton circlebtn = new ToggleButton("",new ImageView(circle));
    ToggleButton elpsbtn = new ToggleButton("",new ImageView(ellipse));
    ToggleButton triangle = new ToggleButton("",new ImageView(triangle1));
    ToggleButton selectbtn = new ToggleButton("",new ImageView(select));
    ToggleButton erasebtn = new ToggleButton("",new ImageView(eraser));
    ToggleButton colorDropper=new ToggleButton("",new ImageView(dropper));
    ToggleButton colorDropperF=new ToggleButton("",new ImageView(dropper));
    ToggleButton textbtn=new ToggleButton("TEXT");
    //Gets Text Fonts and Choice Boxes
    ObservableList fonts = FXCollections.observableArrayList(Font.getFamilies());
    ChoiceBox selectFont;
    selectFont = new ChoiceBox();
    selectFont.setValue("SansSerif");
    selectFont.setItems(fonts);
    ChoiceBox fontSize;
          
   //Sets all buttons to same size and group
    ToggleButton[] toolsArr = { drawbtn,linebtn, rectbtn, circlebtn,elpsbtn, selectbtn ,erasebtn,colorDropper,colorDropperF,textbtn,triangle};  
     
    Pointer point=new Pointer();
    
   TextField text=new TextField();
   TextField size=new TextField();
   size.setText("30");
   text.setText("Pain(t)");
    size.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        if (!newValue.matches("\\d{0,3}([\\.]\\d{0,4})?")) {
            size.setText(oldValue);
        }
    });

    ToggleGroup tools = new ToggleGroup();
        for (ToggleButton tool : toolsArr) {
            tool.setMaxWidth(50);
            tool.setMaxHeight(40);
            tool.setMinWidth(100);
            tool.setMinHeight(40);
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
    Label textSize = new Label("20.0");
    //Makes Box for buttons to go into and adds them all
    VBox btns = new VBox(10);
    btns.getChildren().addAll(drawbtn, linebtn, rectbtn, circlebtn,  elpsbtn, triangle,selectbtn,erasebtn,cpLine,  colorDropper,fill_color,cpFill,  colorDropperF,slider,line_width,text,textbtn,selectFont,size);
    btns.setPadding(new Insets(5));
    btns.setStyle("-fx-background-color: #fff");
    btns.setPrefWidth(50);
    //Delaring shapes that can be used
     Line line = new Line();
     Rectangle rect = new Rectangle();
     Circle circ = new Circle(); 
     Ellipse elps =new Ellipse();
     Polygon tri=new Polygon();
     //Starts drawing for 
     shapes shape=new shapes();
     //If mouse is on canvas it selects the pointer from the button selected
     canvas.setOnMouseEntered((MouseEvent e) -> {
     point.cursors(toolsArr, imageArr,scene);
     });
     //If its off the canvas it sets default cursor
     canvas.setOnMouseExited((MouseEvent e) -> {
         scene.setCursor(new ImageCursor());
     });
     //If canvas is pressed
      canvas.setOnMousePressed((MouseEvent e) -> {
          gc.setLineWidth(width);
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
          
          else if(colorDropper.isSelected())
                {
                    
                    WritableImage writableImage1 =new WritableImage(1800,950); 
                    Image img =canvas.snapshot(null,writableImage1);
                  PixelReader c= img.getPixelReader();
                      Color a=c.getColor((int)e.getX()+5 ,(int)e.getY()+33);
                      gc.setStroke(a);//Changes Color of stroke to the color selected
                      cpLine.setValue(a);
                }
          else if(colorDropperF.isSelected())
                {
                   
                    WritableImage writableImage1 =new WritableImage(1800,950); 
                    Image img =canvas.snapshot(null,writableImage1);
                  PixelReader c= img.getPixelReader();
                      Color a=c.getColor((int)e.getX()+5,(int)e.getY()+33);
                      gc.setFill(a);//Changes Color of fill to the color selected
                      cpFill.setValue(a);
                }

           else { //If its selected call selectTool
              shape.start(toolsArr, e, gc, line, circ, rect,elps, tri,cpLine,cpFill,scene,text.getText(),selectFont.getValue().toString(),Double.parseDouble(size.getText()));
          }
    });
    
        canvas.setOnMouseDragged(e->{
        for(int i=0;i<6;i++)    //Draws the image before it draws the line for smooth line tranisition
           {
               gc.drawImage((Image) undo.lastElement(), i*1800, 0);
           }
           if(selectbtn.isSelected()) {     //Rectangle/Square Drawing for selectarea
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
           shape.drag(toolsArr, e, gc, line, circ, rect,elps, tri, text.getText(),selectFont.getValue().toString(),cpLine,cpFill,Double.parseDouble(size.getText()));
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
                gc.lineTo(e.getX()+5, e.getY()+33);
                gc.stroke();
                gc.closePath();
            }else if(selectbtn.isSelected()) {
                if(!selected)//If a piece of area is already selected
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
