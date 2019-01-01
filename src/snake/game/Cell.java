/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake.game;

import java.awt.Color;
import processing.core.PApplet;

/**
 *
 * @author Dilshan
 */
public class Cell {
    
    private Color color;
    private int X;
    private int Y;  

    public static int WIDTH;
    public static int HEIGHT;
    
    public Cell(Color color, int X, int Y) {
        this.color = color;
        this.X = X;
        this.Y = Y;
    }

    public void draw(PApplet app){
        app.fill(color.getRGB());
        app.rect(X*WIDTH, Y*HEIGHT, WIDTH, HEIGHT);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }
    
}
