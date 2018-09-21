
package paint;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
public class Paint extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {     
        //Canvas Stuff
        Canvas canvas = new Canvas(1800, 950); //Sets Canvas to size of screen to draw and print n
     
        GraphicsContext gc;                     //Sets Graphics context from canvas
        gc = canvas.getGraphicsContext2D();
        
        //Sets background of canvas/gc to white and same size as canvas
        gc.setFill(Color.WHITE);                    //Sets background of canvas/gc to white and same size as canvas
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setLineWidth(1);
      
        //Sets Scene
        BorderPane pane = new BorderPane();
        //pane.setLeft(btns);
        pane.setCenter(canvas);
    
        Scene scene = new Scene(pane, 1900, 1060);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());        //Sets scene to fullscreen
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
        
        menuBar h=new menuBar();
        h.draw(canvas, pane, gc, primaryStage);//Calls objects giving variables they need 
        h.menu(canvas,pane,gc, primaryStage, scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}