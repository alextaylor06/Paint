
package paint;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex
 */
public class Save {
    //Global File
    File file;
    Boolean firstSave=false;
    void saveAs(Stage primaryStage, Canvas canvas) {
        //Selects file and saves the whole canvas to it
        firstSave=true;
        FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            
           file = savefile.showSaveDialog(primaryStage);
            System.out.print(file);
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "pnrtg", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
    }
     void save(Stage primaryStage,  Canvas canvas) {  //saves whatever file save as has chosen
            //Takes the file that was saved in save as and resaves it
            if(!firstSave){
                 FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            
           file = savefile.showSaveDialog(primaryStage);
            System.out.print(file);
            firstSave=true;
            }
            if (file != null) {
                try {
                    WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
                    canvas.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        }
}
