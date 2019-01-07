import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
public class MazeProgram extends JPanel implements KeyListener,MouseListener
{
    JFrame frame;
    //declare an array to store the maze
    int x=0,y=1;
    String currentRow[];
    ArrayList<String[]> horizontalRows = new ArrayList<String[]>();
    String mazeArray[][];
    int rows,columns = 0;
    int direction=2;
    int moves;
    boolean won=false;
    ArrayList<Wall> walls;
    
    
    public MazeProgram()
    {
        setBoard();
        frame=new JFrame();
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);
        frame.setVisible(true);
        frame.addKeyListener(this);
        //this.addMouseListener(this);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.blue);    //this will set the background color
        g.fillRect(0,0,1000,800);
        
        //drawBoard here!
        g.setColor(Color.WHITE);
        for (int a=0; a<mazeArray[0].length; a++){
            for (int b=0; b<mazeArray.length; b++){
                if (mazeArray[b][a]!=null && mazeArray[b][a].equals("*")){
                    g.fillRect(a*10, b*10, 10, 10);
                }
            }
        }
        g.setColor(Color.PINK);
        g.fillRect(x*10+3, y*10+3, 5, 5);
        /*if (direction==1){
         g.fillRect(x*10+5, y*10+5, 1, 1);
         }else if (direction==2){
         g.fillRect(x*10+18, y*10+11, 1, 1);
         }else if (direction==3){
         g.fillRect(x*10+11, y*10+18, 1, 1);
         }else if (direction==4){
         g.fillRect(x*10+4, y*10+11, 1, 1);
         }*/
        
        
        if (won==true){
            if (moves<=90) {
                g.drawString("GREAT JOB!!!", 500, 550);
            }else {
                g.drawString("It looks like you were lost for awhile. Glad you found the way out.", 500, 550);
            }
        }
        g.drawString("Moves: "+moves, 500, 500);
        
        for (int i=0; i<walls.size(); i++){
            
            if ((walls.get(i).getVisible())==true){
                g.setColor(Color.PINK);
                g.fillPolygon(walls.get(i).getX(), walls.get(i).getY(), 4);
            }else if ((walls.get(i).getVisible())==false){
                //g.setColor(Color.BLACK);
                //g.fillPolygon(walls.get(i).getX(), walls.get(i).getY(), 4);
            }
            g.setColor(Color.BLACK);
            g.drawPolygon(walls.get(i).getX(), walls.get(i).getY(), 4);
            
            
        }
    }
    public void setBoard()
    {
        //choose your maze design
        
        //pre-fill maze array here
        
        File name = new File("maze1.txt");
        int r=0;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name));
            String text;
            while( (text=input.readLine())!= null)
            {
                rows++;
                columns = text.length();
            }
            
        }
        catch (IOException io)
        {
            System.err.println("File error");
        }
        mazeArray=new String[rows][columns];
        
        System.out.println("Rows: "+rows+" columns: "+columns);
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(name));
            String text;
            int i=0;
            while( (text=input.readLine())!= null)
            {
                System.out.println(text);
                for (int j = 0; j<text.length(); j++){
                    mazeArray[i][j] = ""+text.charAt(j);
                }
                i++;
                //your code goes in here to chop up the maze design
                //fill maze array with actual maze stored in text file
            }
            //mazeArray = new String[r][horizontalRows.get(0).length];
            
            
        }
        catch (IOException io)
        {
            System.err.println("File error");
        }
        
        walls = new ArrayList<Wall>();
        walls.add(new Wall(new int[]{500,900,850,550},new int[]{400,400,350,350},true));//top
        walls.add(new Wall(new int[]{850,900,900,850},new int[]{350,400,0,50},true));//right
        walls.add(new Wall(new int[]{550,850,900,500},new int[]{50,50,0,0},true));//bottom
        walls.add(new Wall(new int[]{500,550,550,500},new int[]{400,350,50,0},true));//left
        walls.add(new Wall(new int[]{550,850,800,600},new int[]{350,350,300,300},true));//top
        walls.add(new Wall(new int[]{800,850,850,800},new int[]{300,350,50,100},true ));//right
        walls.add(new Wall(new int[]{600,800,850,550},new int[]{100,100,50,50},true));//bottom
        walls.add(new Wall(new int[]{550,600,600,550},new int[]{350,300,100,50},true));//left
        walls.add(new Wall(new int[]{600,800,750,650},new int[]{100,100,150,150},true));//top3
        walls.add(new Wall(new int[]{800,800,750,750},new int[]{100,300,250,150},true));//right3
        walls.add(new Wall(new int[]{600,800,750,650},new int[]{300,300,250,250},true));//bottom3
        walls.add(new Wall(new int[]{600,650,650,600},new int[]{300,250,150,100},true));//left3
        walls.add(new Wall(new int[]{650,650,750,750},new int[]{150,250,250,150},true));//back3
        walls.add(new Wall(new int[]{600,800,800,600},new int[]{300,300,100,100},false));//back
        walls.add(new Wall(new int[]{550,850,850,550},new int[]{350,350,50,50},false));//front
        
        drawWalls();
        
        
    }
    public void keyPressed(KeyEvent e)
    {
        if (won==true) {
            return;
        }
        switch( e.getKeyCode() ) {
            case (37):
                System.out.println("left");
                direction--;
                if (direction==0)
                    direction=4;
                break;
            case (38):
                System.out.println("up");
                switch(direction){
                    case(1)://north
                        y--;
                        if (mazeArray[y][x].equals("*")){
                            y++;
                        }else {
                            moves++;
                            drawWalls();
                        }
                        break;
                    case(2)://east
                        x++;
                        if ((x==columns)||(mazeArray[y][x].equals("*"))){
                            x--;
                        }else {
                            moves++;
                            drawWalls();
                        }
                        break;
                    case(3)://south
                        y++;
                        if (mazeArray[y][x].equals("*")){
                            y--;
                        }else {
                            moves++;
                            drawWalls();
                        }
                        break;
                    case(4)://west
                        x--;
                        if ((x==-1)||(mazeArray[y][x].equals("*"))){
                            x++;
                        }else {
                            moves++;
                            drawWalls();
                        }
                        break;
                }
                break;
            case (39):
                System.out.println("right");
                direction++;
                if (direction==5)
                    direction=1;
                if (mazeArray[y][x].equals("*")){
                    x--;
                }
                break;
        }
        if ((x==columns-1)&&(y==rows-2)){
            System.out.println("YAY YOU WON!");
            won=true;
        }
        drawWalls();
        repaint();
        System.out.println("MOVES: "+moves);
    }
    public void keyReleased(KeyEvent e)
    {
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public static void main(String args[])
    {
        MazeProgram app=new MazeProgram();
    }
    
    public void drawWalls(){
        //add a bunch of walls based on positions in the array
        //draw the walls
        if (direction==1){
            
            if ((y-1<=0)||(mazeArray[y-1][x].equals("*"))){
                walls.get(14).setVisible(true);
            }else {
                walls.get(14).setVisible(false);
            }
            if ((x+1>=columns-1)||(mazeArray[y][x+1].equals("*"))){
                walls.get(1).setVisible(true);
            }else walls.get(1).setVisible(false);
            if ((x-1<=0)||(mazeArray[y][x-1].equals("*"))){
                walls.get(3).setVisible(true);
            }else walls.get(3).setVisible(false);
            
            if ((y-2<=0)||(mazeArray[y-2][x].equals("*"))){
                walls.get(13).setVisible(true);
            }else {
                walls.get(13).setVisible(false);
            }
            if ((x+1>=columns-1)||(y-1<=0)||(mazeArray[y-1][x+1].equals("*"))){
                walls.get(5).setVisible(true);
            }else walls.get(5).setVisible(false);
            if ((x-1<=0)||(y-1<=0)||(mazeArray[y-1][x-1].equals("*"))){
                walls.get(7).setVisible(true);
            }else walls.get(7).setVisible(false);
            
            if ((y-3<=0)||(mazeArray[y-3][x].equals("*"))){
                walls.get(12).setVisible(true);
            }else {
                walls.get(12).setVisible(false);
            }
            if ((x+1>=columns-1)||(y-2<=0)||(mazeArray[y-2][x+1].equals("*"))){
                walls.get(9).setVisible(true);
            }else walls.get(9).setVisible(false);
            if ((x-1<=0)||(y-2<=0)||(mazeArray[y-2][x-1].equals("*"))){
                walls.get(11).setVisible(true);
            }else walls.get(11).setVisible(false);
            
        }else if (direction==2){
            
            if ((x+1>=columns-1)||(mazeArray[y][x+1].equals("*"))){
                walls.get(14).setVisible(true);
            }else {
                walls.get(14).setVisible(false);
            }
            if ((x+1>=columns-1)&&(y==rows-2)) {
                walls.get(14).setVisible(false);
            }
            if ((y+1>=rows-1)||(mazeArray[y+1][x].equals("*"))){
                walls.get(1).setVisible(true);
            }else walls.get(1).setVisible(false);
            if ((y-1<=0)||(mazeArray[y-1][x].equals("*"))){
                walls.get(3).setVisible(true);
            }else walls.get(3).setVisible(false);
            
            if ((x+2>=columns-1)||(mazeArray[y][x+2].equals("*"))){
                walls.get(13).setVisible(true);
            }else {
                walls.get(13).setVisible(false);
            }
            if ((x+2>=columns-1)&&(y==rows-2)){
                walls.get(13).setVisible(false);
            }
            if ((y+1>=rows-1)||(x+1>=columns-1)||(mazeArray[y+1][x+1].equals("*"))){
                walls.get(5).setVisible(true);
            }else walls.get(5).setVisible(false);
            if ((y-1<=0)||(x+1>=columns-1)||(mazeArray[y-1][x+1].equals("*"))){
                walls.get(7).setVisible(true);
            }else walls.get(7).setVisible(false);
            
            if ((x+3>=columns-1)||(mazeArray[y][x+3].equals("*"))){
                walls.get(12).setVisible(true);
            }else {
                walls.get(12).setVisible(false);
            }
            if ((x+3>=columns-1)&&(y==rows-2)){
                walls.get(12).setVisible(false);
            }
            if ((y+1>=rows-1)||(x+2>=columns-1)||(mazeArray[y+1][x+2].equals("*"))){
                walls.get(9).setVisible(true);
            }else walls.get(9).setVisible(false);
            if ((y-1<=0)||(x+2>=columns-1)||(mazeArray[y-1][x+2].equals("*"))){
                walls.get(11).setVisible(true);
            }else walls.get(11).setVisible(false);
            
            
        }else if (direction==3){
            
            if ((y+1>=rows-1)||(mazeArray[y+1][x].equals("*"))){
                walls.get(14).setVisible(true);
            }else {
                walls.get(14).setVisible(false);
            }
            if ((x-1<=0)||(mazeArray[y][x-1].equals("*"))){
                walls.get(1).setVisible(true);
            }else walls.get(1).setVisible(false);
            if ((x+1>=columns-1)||(mazeArray[y][x+1].equals("*"))){
                walls.get(3).setVisible(true);
            }else walls.get(3).setVisible(false);
            
            if ((y+2>=rows-1)||(mazeArray[y+2][x].equals("*"))){
                walls.get(13).setVisible(true);
            }else {
                walls.get(13).setVisible(false);
            }
            if ((x-1<=0)||(y+1>=rows-1)||(mazeArray[y+1][x-1].equals("*"))){
                walls.get(5).setVisible(true);
            }else walls.get(5).setVisible(false);
            if ((x+1>=columns-1)||(y+1>=rows-1)||(mazeArray[y+1][x+1].equals("*"))){
                walls.get(7).setVisible(true);
            }else walls.get(7).setVisible(false);
            
            if ((y+3>=rows-1)||(mazeArray[y+3][x].equals("*"))){
                walls.get(12).setVisible(true);
            }else {
                walls.get(12).setVisible(false);
            }
            if ((x-1<=0)||(y+2>=rows-1)||(mazeArray[y+2][x-1].equals("*"))){
                walls.get(9).setVisible(true);
            }else walls.get(9).setVisible(false);
            if ((x+1>=columns-1)||(y+2>=rows-1)||(mazeArray[y+2][x+1].equals("*"))){
                walls.get(11).setVisible(true);
            }else walls.get(11).setVisible(false);
            
        }else if (direction==4){
            
            if ((x-1<=0)||(mazeArray[y][x-1].equals("*"))){
                walls.get(14).setVisible(true);
            }else {
                walls.get(14).setVisible(false);
            }
            if ((y-1<=0)||(mazeArray[y-1][x].equals("*"))){
                walls.get(1).setVisible(true);
            }else walls.get(1).setVisible(false);
            if ((y+1>=rows-1)||(mazeArray[y+1][x].equals("*"))){
                walls.get(3).setVisible(true);
            }else walls.get(3).setVisible(false);
            
            if ((x-2<=0)||(mazeArray[y][x-2].equals("*"))){
                walls.get(13).setVisible(true);
            }else {
                walls.get(13).setVisible(false);
            }
            if ((y-1<=0)||(x-1<=0)||(mazeArray[y-1][x-1].equals("*"))){
                walls.get(5).setVisible(true);
            }else walls.get(5).setVisible(false);
            if ((y+1>=rows-1)||(x-1<=0)||(mazeArray[y+1][x-1].equals("*"))){
                walls.get(7).setVisible(true);
            }else walls.get(7).setVisible(false);
            
            if ((x-3<=0)||(mazeArray[y][x-3].equals("*"))){
                walls.get(12).setVisible(true);
            }else {
                walls.get(12).setVisible(false);
            }
            if ((y-1<=0)||(x-2<=0)||(mazeArray[y-1][x-2].equals("*"))){
                walls.get(9).setVisible(true);
            }else walls.get(9).setVisible(false);
            if ((y+1>=rows-1)||(x-2<=0)||(mazeArray[y+1][x-2].equals("*"))){
                walls.get(11).setVisible(true);
            }else walls.get(11).setVisible(false);
        }
        
        repaint();
        for (int i=0; i<walls.size(); i++){
            //System.out.println(walls.get(i).getVisible());
        }
        
        
    }
    
}
