/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Alex
 * The shapes for draw function
 */
public class shapes extends Draw{
      void start(ToggleButton[] toolsArr, MouseEvent e, GraphicsContext gc, Line line, Circle circ,Rectangle rect,Ellipse elps,ColorPicker cpLine,ColorPicker cpFill,Scene scene)
    {
        if(toolsArr[0].isSelected()) {        //Free Hand drawing
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
                gc.strokeOval(e.getX(), e.getX(), elps.getRadiusX(), elps.getRadiusY());
            }
            else if(toolsArr[1].isSelected()) {     //Line Drawing
                gc.setStroke(cpLine.getValue());
                line.setStartX(e.getX());
                line.setStartY(e.getY());
                
            }
            else if(toolsArr[2].isSelected()) {     //Rectangle/Square Drawing
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                rect.setX(e.getX());                
                rect.setY(e.getY());
            }
            else if(toolsArr[3].isSelected()) {       //Circle Drawing
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                circ.setCenterX(e.getX());
                circ.setCenterY(e.getY());
            }else if(toolsArr[4].isSelected()) {    //Ellipse
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                elps.setCenterX(e.getX());
                elps.setCenterY(e.getY());
            }else if(toolsArr[6].isSelected()) {        //Free Hand drawing
               
                gc.setStroke(Color.WHITE);
                gc.beginPath();
                gc.strokeOval(e.getX(), e.getX(), elps.getRadiusX(), elps.getRadiusY());
            }
        
    }
    
    void drag(ToggleButton[] toolsArr, MouseEvent e, GraphicsContext gc, Line line, Circle circ,Rectangle rect,Ellipse elps)
    {
        
            if(toolsArr[0].isSelected()) {              //Only for draw, dragging draws along the way
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
            else if(toolsArr[1].isSelected()) {//Makes line from the start to finsih position
                
               line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());  
                
            }else if(toolsArr[2].isSelected()){
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
            }
            else if(toolsArr[3].isSelected()) {
                double x =circ.getCenterX();
                double y =circ.getCenterY();
                circ.setRadius((Math.abs(e.getX() - circ.getCenterX()) + Math.abs(e.getY() - circ.getCenterY()))/2 );
                if(x > e.getX()) {
                    x=(e.getX());
                }
                if(y > e.getY()) {
                    y=(e.getY());
                }
                gc.fillOval(x, y, circ.getRadius(), circ.getRadius());
                gc.strokeOval(x, y, circ.getRadius(), circ.getRadius());
            }else if(toolsArr[4].isSelected()) {
                double x =elps.getCenterX();
                double y =elps.getCenterY();
                elps.setRadiusX(Math.abs(e.getX() - elps.getCenterX()));
                elps.setRadiusY(Math.abs(e.getY() - elps.getCenterY()));
                
                if(x > e.getX()) {
                    x=(e.getX());
                }
                if(y > e.getY()) {
                    y=(e.getY());
                }
                
                gc.strokeOval(x, y, elps.getRadiusX(), elps.getRadiusY());
                gc.fillOval(x, y, elps.getRadiusX(), elps.getRadiusY());
            }else if(toolsArr[6].isSelected()) {              //Only for draw, dragging draws along the way
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
    }
  
    
    
}
