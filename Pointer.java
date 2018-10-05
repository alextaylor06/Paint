/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint;

import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;    
/**
 *
 * @author Alex
 */
public class Pointer extends Draw{
    //Selects Cursor for being on canvas
    void cursors(ToggleButton[] toolsArr,Image[] i,Scene scene)
    {
            if(toolsArr[0].isSelected()){
                scene.setCursor(new ImageCursor(i[1]));//If draw button is selcted
            }
            else if(toolsArr[1].isSelected()){//Line button
                scene.setCursor(new ImageCursor(i[0]));
            }
            else if(toolsArr[2].isSelected()){//Rectangel Button
                scene.setCursor(new ImageCursor(i[2]));
            }
            else if(toolsArr[3].isSelected()){//Cirle button
                scene.setCursor(new ImageCursor(i[3]));
            }
           else if(toolsArr[4].isSelected()){//Elipse Button
                scene.setCursor(new ImageCursor(i[4]));
            }
            else if(toolsArr[6].isSelected()){//Erase button
              
                scene.setCursor(new ImageCursor(i[6]));
            }
            
            else if(toolsArr[7].isSelected()){//Outline colordropper
                
                scene.setCursor(new ImageCursor(i[7]));
            }
            else if(toolsArr[8].isSelected()){//Fill color dropper
                
                scene.setCursor(new ImageCursor(i[7]));
            }
           
           else if(toolsArr[10].isSelected()){//Triangle
                
                scene.setCursor(new ImageCursor(i[8]));
            }else scene.setCursor(new ImageCursor());
            
    }
    
}
