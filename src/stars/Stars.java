/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stars;
import java.util.ArrayList;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
/**
 *
 * @author Victor
 */
public class Stars extends Application
{

    @Override
    public void start(Stage stage) 
    {
        stage.setTitle("Star");
        Pane root = new Pane();
        
        int cLen = 500;
        int cWid = 500;
        
        Canvas c = new Canvas(cLen, cWid);
        root.getChildren().add(c);
        
        Scene sc = new Scene(root,cLen,cWid);
        sc.setFill(null);
        GraphicsContext gc = c.getGraphicsContext2D(); 
        
        //This method draws a star
        //drawStar(gc, sc, 10, 250);
        
        //This method draws the dragon curve
        //Point2D start = new Point2D(250, 250);
        //drawDragon(gc, start, 17, 1);
        
        stage.setScene(sc);
        stage.show();
    }
    
    public void drawStar(GraphicsContext gc, Scene sc, int size, int rad)
    {
        double theta = 0;
        
        Point2D centre = new Point2D(sc.getWidth()/2, sc.getHeight()/2);
        int lines = 2 + size * 3;
        double rotate = 2*PI/lines;
        Point2D[] pts = new Point2D[lines];
        
        for(int i = 0; i < lines; i++)
        {
            double x = centre.getX() + rad*cos(theta);
            double y = centre.getY() + rad*sin(theta);
            pts[i] = new Point2D(x,y);
            theta += rotate;
        }
        
        gc.beginPath();
        gc.moveTo(pts[0].getX(), pts[0].getY());
        int ind = 2 + size;
        for(int i = 0; i < lines; i++)
        {
            int next;
            if(ind + size + 1 <= lines) 
                next = ind + size + 1;
            else
                next = (ind + size + 1)%lines;
            
            Point2D p1 = pts[ind-1];
            gc.lineTo(p1.getX(), p1.getY());
            
            ind = next;
        }
        gc.stroke();
    }
    
    public void drawDragon(GraphicsContext gc, Point2D start, int size, int len)
    {
        Point2D next = new Point2D(start.getX() + len, start.getY());
        size--;
        ArrayList<Point2D> al = new ArrayList<>(); 
        al.add(start);
        al.add(next);
        int turnPointIndex = al.size() - 1;
        
        for(int i=0; i<size;i++)
        {
            Point2D pivot = al.get(turnPointIndex);
            for(int j = turnPointIndex - 1; j >= 0; j-- )
            {
                Point2D p = al.get(j);
                double dx = -1 * (p.getY() - pivot.getY());
                double dy = (p.getX() - pivot.getX());
                Point2D n = new Point2D(pivot.getX() + dx, pivot.getY() + dy);
                al.add(n);
            }
            turnPointIndex = al.size() - 1;
        }
        
        gc.beginPath();
        gc.moveTo(start.getX(), start.getY());
        for(int i=1; i<al.size(); i++)
        {
            gc.lineTo(al.get(i).getX(), al.get(i).getY());
        }
        gc.stroke();
        
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
