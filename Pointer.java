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
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Alex
 */
public class Pointer extends Draw{
    
    void cursors(ToggleButton[] toolsArr,Image[] i,Scene scene)
    {
            toolsArr[0].setOnMousePressed((MouseEvent e) -> {
                scene.setCursor(new ImageCursor(i[1]));
            });
            toolsArr[1].setOnMousePressed((MouseEvent e) -> {
                scene.setCursor(new ImageCursor(i[0]));
            });
            toolsArr[2].setOnMousePressed((MouseEvent e) -> {
                scene.setCursor(new ImageCursor(i[2]));
            });
            toolsArr[3].setOnMousePressed((MouseEvent e) -> {
                scene.setCursor(new ImageCursor(i[3]));
            });
            toolsArr[4].setOnMousePressed((MouseEvent e) -> {
                scene.setCursor(new ImageCursor(i[4]));
            });
            toolsArr[6].setOnMousePressed((MouseEvent e) -> {
              
                scene.setCursor(new ImageCursor(i[6]));
            });
            toolsArr[5].setOnMousePressed((MouseEvent e) -> {
                
                scene.setCursor(new ImageCursor());
            });
    }
    
}
