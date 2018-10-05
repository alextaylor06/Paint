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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/**
 *
 * @author Alex
 * The shapes for draw function
 */
public class shapes extends Draw{
    double triSX;
     double triSY;
      void start(ToggleButton[] toolsArr, MouseEvent e, GraphicsContext gc, Line line, Circle circ,Rectangle rect,Ellipse elps,Polygon tri,ColorPicker cpLine,ColorPicker cpFill,Scene scene,String text, String font,Double size)
    {
        triSX=0;
        triSX=0;
        if(toolsArr[0].isSelected()) {        //Free Hand drawing
                gc.setStroke(cpLine.getValue());
                gc.beginPath();
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
              //   gc.strokeOval(e.getX(), e.getX(), elps.getRadiusX(), elps.getRadiusY());
            }else if(toolsArr[9].isSelected()){ //Text 
               
                //gc.setLineWidth(2);
                
                gc.setFont(Font.font(font, FontWeight.NORMAL, size));
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                gc.strokeText(text, e.getX(),e.getY());
                gc.fillText(text, e.getX(),e.getY());
                
            }else if(toolsArr[10].isSelected()){//Triangle
               
                tri.setStrokeWidth(3);
                gc.setStroke(cpLine.getValue());
                gc.setFill(cpFill.getValue());
                triSX=e.getX();
                triSY=e.getY();
                     
                
            }
        
    }
    
    void drag(ToggleButton[] toolsArr, MouseEvent e, GraphicsContext gc, Line line, Circle circ,Rectangle rect,Ellipse elps,Polygon tri,String text,String font,ColorPicker color,ColorPicker cpFill,Double size)
    {
        
            if(toolsArr[0].isSelected()) {              //Only for draw, dragging draws along the way
                gc.lineTo(e.getX()+5, e.getY()+33);
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
            }else if(toolsArr[9].isSelected()){
                //gc.setLineWidth(2);
                gc.setFont(Font.font(font, FontWeight.NORMAL, size));
                
                gc.strokeText(text, e.getX(),e.getY());
                gc.fillText(text, e.getX(),e.getY());
                
            }else if(toolsArr[10].isSelected()){
                
                double X;
                double Y;
                if(e.getX()<triSX && e.getY()<triSY){
                    X=e.getX()-(Math.abs(e.getX()-triSX));
                    Y=(Math.abs(e.getY()-triSY))+e.getY();
                     
                }else if(e.getX()<triSX && e.getY()>triSY){
                    X=e.getX()-(Math.abs(e.getX()-triSX));
                    Y=e.getY()-(Math.abs(e.getY()-triSY));
                }else if(e.getX()>triSX && e.getY()>triSY){
                    X=(Math.abs(e.getX()-triSX))+e.getX();
                    Y=e.getY()-(Math.abs(e.getY()-triSY));
                    
                }else { X=(Math.abs(e.getX()-triSX))+e.getX();
                Y=(Math.abs(e.getY()-triSY))+e.getY();}
                double[] polyX={triSX, e.getX(),X};
                double[] polyY={triSY,e.getY(),Y};
                gc.fillPolygon(polyX, polyY, 3);
                 gc.strokePolygon(polyX, polyY, 3);
                
            }
    }
}
