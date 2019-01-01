
package snake.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;
import processing.core.*;

public class SnakeGame extends PApplet {

    static int WIDTH = 500;
    static int HEIGHT = 500;
    
    static int CELLWIDTH = WIDTH/10;
    static int CELLHEIGHT = HEIGHT/10;
    
    static int C_WIDTH = WIDTH/CELLWIDTH;
    static int C_HEIGHT = HEIGHT/CELLHEIGHT;
    
    Color color_snake = new Color(35, 128, 35);
    
    Color color_food = new Color(255, 210, 0);
    
    Vector<Cell> snake;
    
    Vector<Cell> foods;
    
    Random random = new Random();
    
    int snake_dir = 0;
    
    int moves[][] = {{0,-1}, {0,1}, {-1,0}, {1,0}};
    
    int game_speed = 100;
    
    int score = 0;
    
    int game_resume_time = 1000;
    
    public static void main(String[] args) {
       PApplet.main(new String[]{"snake.game.SnakeGame"});
    }
    
    @Override
    public void settings(){
        size(WIDTH, HEIGHT+40);
    }
    
    @Override
    public void setup() {
      Cell.WIDTH = CELLWIDTH;
      Cell.HEIGHT = CELLHEIGHT;
      
      initGame();
    }

    private void initGame() {
      snake_dir = 0;
      score = 0;
      
      snake = new Vector<>();
      foods = new Vector<>();
      
      int y = random.nextInt(C_WIDTH);
      snake.add(new Cell(color_snake, y, C_HEIGHT-3));
      snake.add(new Cell(color_snake, y, C_HEIGHT-2));
      snake.add(new Cell(color_snake, y, C_HEIGHT-1));
    
      createFoods();
   
    }
    
    boolean keylock = false;
    
    boolean gameOver = false;
    
    long ltime;
    
    long gameOverTime;
    
    @Override
    public void draw() {
        
        if(gameOver){
           gameOver();
           
           if(millis() - gameOverTime > game_resume_time){
               gameOver = false;
               initGame();
           }
           
           return;
        }
        
        if(keyPressed){
            
            if(!keylock){
                
                if(keyCode == KeyEvent.VK_UP){
                    snake_dir = 0;
                }
                else if(keyCode == KeyEvent.VK_DOWN){
                    snake_dir = 1;
                }
                else if(keyCode == KeyEvent.VK_LEFT){
                    snake_dir = 2;
                }
                else if(keyCode == KeyEvent.VK_RIGHT){
                    snake_dir = 3;
                }
                
                System.out.println(snake_dir);
            }
            
            keylock = true;
        }
        else{
            keylock = false;
        }
        
        if(millis()-ltime > game_speed){
            ltime = millis();
            moveSnake();
            gameLogic();
        }
        
        background(50, 60, 50);
        stroke(50,85,50);
        
        for (int w = 0; w < WIDTH; w+=CELLWIDTH) {
            line(w,0,w,HEIGHT);
        }
        
        for (int h = 0; h < HEIGHT; h+=CELLHEIGHT) {
            line(0,h,WIDTH,h);
        }
        
        for (Cell s : snake) {
            s.draw(this);
        }
        
        for (Cell f : foods) {
            f.draw(this);
        }
        
        noStroke();
        fill(50, 50, 70);
        rect(0, 500, 540, 500);
        
        fill(255, 255, 0);
        textFont(new PFont(new Font("Arial", Font.BOLD, 30), true)); 
        text(score +"/" + C_WIDTH * C_HEIGHT, WIDTH-150, HEIGHT+30);
    }

    private void moveSnake() {
        
        for (int s = snake.size()-1; s > 0; s--) {
            snake.get(s).setX(snake.get(s-1).getX());
            snake.get(s).setY(snake.get(s-1).getY());
        }
        
        int x = snake.get(0).getX() + moves[snake_dir][0];
        int y = snake.get(0).getY() + moves[snake_dir][1];
        
        snake.get(0).setX(x);
        snake.get(0).setY(y);
    }

    private void createFoods() {
        
        while(foods.size() < 3){
            int x = random.nextInt(C_WIDTH);
            int y = random.nextInt(C_HEIGHT);
            
            boolean onSnake = false;
            
            for (Cell s : snake) {
                
                if(s.getX() == x && s.getY() == y){
                    onSnake = true;
                }
                
            }
            
            if(!onSnake){
               foods.add(new Cell(color_food, x,y)); 
            }
            
        }
    }

    private void gameLogic() {
        
        int hx = snake.get(0).getX();
        int hy = snake.get(0).getY();
        
        if(hx < 0 || hx >= C_WIDTH && hy < 0 || hy >= C_WIDTH){
            gameOver = true;
            gameOverTime = millis();
        }
        
        for (int i = 1; i < snake.size(); i++) {
            
            if(snake.get(i).getX() == hx && snake.get(i).getY() == hy){
                gameOver = true;
                gameOverTime = millis(); 
            }
        }
        
        for (int i = 0; i < foods.size(); i++) {
            
            if(foods.get(i).getX() == snake.get(0).getX() && foods.get(i).getY() == snake.get(0).getY()){
                foods.removeElementAt(i);
                createFoods();
                
                int x = snake.get(snake.size()-1).getX();
                int y = snake.get(snake.size()-1).getY();
                
                snake.add(new Cell(color_snake, x, y));
                
                score++;
            }
        }
    }

    private void gameOver() {
       fill(0,0,0,150);
       rect(0, 0, WIDTH, HEIGHT);
       
       fill(190, 40, 60);
       textFont(new PFont(new Font("Arial", Font.BOLD, 40), true)); 
       text("GAME OVER",120,250);
    }

     
}