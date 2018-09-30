/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Alex
 * Select tool will set image of selected area and move it and mouse is dragged
 */
public class selectTool extends Draw{
    int x,y,width,height;
    boolean drawn;
    Image moveImg;
    void move(int width1, int height1, int x1, int y1,Canvas canvas,GraphicsContext gc){
      x=x1+1;
      y=y1+2;       //Gets dimensions of rectangle drawn
      width=width1-2;
      height=height1-2;
      
        WritableImage image =new WritableImage(1800,950);   //Creates new image of whole canvas
        PixelReader th=image.getPixelReader();
        WritableImage img=new WritableImage(th,x,y,width,height);
        SnapshotParameters params=new SnapshotParameters();
        params.setViewport(new Rectangle2D(x,y,width,height));//Creates image of the selected area
        moveImg =canvas.snapshot(params,img);
        
    }

       void clicked(GraphicsContext gc)
       {
        gc.drawImage((Image) moveImg, x,y,width, height);//Places selected image at the same location
       }
       void dragged(GraphicsContext gc,MouseEvent e)
       {
            gc.setFill(Color.WHITE);            //Makes white rect left behind
            gc.fillRect(x-3, y-3, width+5, height+5);       //Draws image where you drag, places  and deletess line around it
            gc.drawImage((Image) moveImg, e.getX(),e.getY(),width, height);
           
       }
    
    
}
