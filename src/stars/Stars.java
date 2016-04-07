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
        
        int cLen = 800;
        int cWid = 1000;
        
        Canvas c = new Canvas(cWid, cLen);
        root.getChildren().add(c);
        
        Scene sc = new Scene(root,cWid, cLen);
        sc.setFill(null);
        GraphicsContext gc = c.getGraphicsContext2D(); 
        
        //This method draws a star in the middle of your screen
        //drawStar(gc, sc, 30, 250);
        
        Point2D start = new Point2D(250, 250);
        
        //This method draws the dragon curve
        //drawDragon(gc, start, 18, 1);
        
        //This method draws the Levy C curve
        //drawC(gc, start, 20, 0.5);
        
        stage.setScene(sc);
        stage.show();
    }
    
    public void drawStar(GraphicsContext gc, Scene sc, int size, double rad)
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
    
    public void drawDragon(GraphicsContext gc, Point2D start, int size, double len)
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
    
    public void drawC(GraphicsContext gc, Point2D start, int size, double length)
    {
        double theta = PI/4;
        Point2D next = new Point2D(start.getX() + length, start.getY());
        size--;
        ArrayList<Point2D> al = new ArrayList<>();
        al.add(start);
        al.add(next);
        
        int pivotIndex = al.size() - 1;
        for(int j = 0; j<size; j++)
        {
            for(int i = 1; i<al.size(); i++)
            {
                Point2D pivot = al.get(0);
                double x = al.get(i).getX() - pivot.getX();
                double y = al.get(i).getY() - pivot.getY();
                double dx = cos(theta)*x - sin(theta)*y;
                double dy = sin(theta)*x + cos(theta)*y;
                Point2D n = new Point2D(pivot.getX() + dx, pivot.getY() + dy);
                al.set(i,n);
            }

            for(int i = pivotIndex - 1; i >= 0; i--)
            {
                double dx = al.get(pivotIndex).getX() - al.get(i).getX();
                Point2D n = new Point2D(al.get(pivotIndex).getX() + dx, al.get(i).getY());
                al.add(n);
            }
            pivotIndex = al.size() - 1;
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
