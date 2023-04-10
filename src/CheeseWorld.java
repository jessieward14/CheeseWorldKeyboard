//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import sun.jvm.hotspot.memory.Space;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

/***
 * Step 0 for keyboard control - Import
 */
import java.awt.event.*;

/***
 * Step 1 for keyboard control - implements KeyListener
 */
public class CheeseWorld implements Runnable, KeyListener {

    //Variable Definition Section

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 650;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    //Declare the variables needed for images
    public Image cheesePic;
    public Image mousePic;
    public Image tomPic;


    //Declare the character objects
    public Mouse mouse1;
    public Backround1 theCheese;
    public Backround2 theCheese2;

    public Bird user;
    public Pillar [] obstacles;
    public Pillar[] obstacles2;

    public int[] obstacles2Heights;

    public boolean gameOver;
    public boolean gameStart=false;
    public Image gameOverpic;
    public Backround1 gameOverScreen;




    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        CheeseWorld myApp = new CheeseWorld();   //creates a new instance of the game
        new Thread(myApp).start();               //creates a threads & starts up the code in the run( ) method
    }

    // Constructor Method - setup portion of the program
    // Initialize your variables and construct your program objects here.
    public CheeseWorld() {

        setUpGraphics();

        /***
         * Step 2 for keyboard control - addKeyListener(this) to the canvas
         */
        canvas.addKeyListener(this);

        //load images
        cheesePic = Toolkit.getDefaultToolkit().getImage("download.jpg");
        mousePic = Toolkit.getDefaultToolkit().getImage("jerry.gif");
        tomPic = Toolkit.getDefaultToolkit().getImage("FlappyBird.png");



        //create (construct) the objects needed for the game
        mouse1 = new Mouse(200, 300, 4, 4, mousePic);
        theCheese = new Backround1(1, 0, 3, 0, cheesePic);
        theCheese2 = new Backround2(1000, 0, 3, 0, cheesePic);
        user = new Bird(250, 250, 0, 0, tomPic);
        gameOverpic = Toolkit.getDefaultToolkit().getImage("gameover.png");
        gameOverScreen = new Backround1(500,500,0,0,gameOverpic);

        obstacles2Heights=new int[100];

        obstacles= new Pillar [100];//top obstacles
        for(int i = 0; i< obstacles.length; i=i+1) {
            obstacles[i] = new Pillar((i*300)+600,  0,  3, 0);
            obstacles[i].height = (int)(Math.random()*270)+30;
            obstacles2Heights[i]= (obstacles[i].height + 170);
            obstacles[i].pic=Toolkit.getDefaultToolkit().getImage("top.png");

            //use a tall pillar pic
            }

        obstacles2=new Pillar[100];//bottom obstacles
        for(int i = 0; i< obstacles2.length; i=i+1) {
            obstacles2[i] = new Pillar((i*300)+600,  obstacles2Heights[i],  3, 0);
            obstacles2[i].pic = Toolkit.getDefaultToolkit().getImage("bottom.jpg");

            obstacles2[i].height=500;
            System.out.println("obstacles2Heights "+ obstacles2Heights);
            System.out.println(" obstacles2[i].height "+  obstacles2[i].height);
        }
            // CheeseWorld()

        }

        public void randomPillar() {
            for (int i = 0; i < obstacles.length; i = i + 1) {

                obstacles[i].dx = 4;

                if (obstacles[i].xpos< -10){
                    obstacles[i].xpos=1000;
                }


            }
//                if (obstacles[i].isAlive == false) {
//                    double r = Math.random();
//                    if (r < 0.05) {
//                        obstacles[i].isAlive = true;
//                        obstacles[i+1].isAlive = true;
//
//
//
//                    }
//                }
//                if (obstacles[i].isAlive == true) {
//                    obstacles[i].dx = 3;
//                    obstacles[i+1].dx = 3;
//
//                    if (obstacles[i].xpos < -10) {
//                        obstacles[i].isAlive = false;
//                        obstacles[i+1].isAlive = false;
//
//                    }
//                }
//
//
//            }
        }
//*******************************************************************************
//User Method Section

    // main thread
    // this is the code that plays the game after you set things up
    public void moveThings() {
        mouse1.move();
        theCheese.move();
        theCheese2.move();
        user.move();
for (int i=0; i<obstacles.length; i=i+1){
obstacles[i].move();
}
        for (int i=0; i<obstacles2.length; i=i+1){
            obstacles2[i].move();
        }
    }

    public void checkIntersections() {

    }

    public void run() {
        while (true) {
            if(gameStart==true) {
                moveThings();
            }//move all the game objects
            checkIntersections();   // check character crashes
            render();               // paint the graphics
            pause(10);
            collisions();
            // sleep for 20 ms
           // randomPillar();

        }
    }


    public void collisions(){
        for(int i=0;i< obstacles.length;i++){
            if(user.rec.intersects(obstacles[i].rec)){
                System.out.println("he's dead");
                gameStart=false;
                gameOver=true;
                
            }
            if (user.rec.intersects(obstacles2[i].rec)){
                gameOver=true;
                gameStart=false;
                System.out.println("he's dead pt 2");


            }
        }

    }

    //paints things on the screen using bufferStrategy
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw characters to the screen
        g.drawImage(mouse1.pic, mouse1.xpos, mouse1.ypos, mouse1.width, mouse1.height, null);
        g.drawImage(theCheese.pic, theCheese.xpos, theCheese.ypos, theCheese.width, theCheese.height, null);
        g.drawImage(theCheese.pic, theCheese2.xpos, theCheese.ypos, theCheese.width, theCheese.height, null);

        g.drawImage(user.pic, user.xpos, user.ypos, user.width, user.height, null);

        for(int i = 0; i< obstacles.length; i=i+1){
            g.drawImage(obstacles[i].pic, obstacles[i].xpos,obstacles[i].ypos, obstacles[i].width, obstacles[i].height, null);
        }
        for(int i = 0; i< obstacles2.length; i=i+1){
            g.drawImage(obstacles2[i].pic, obstacles2[i].xpos,obstacles2[i].ypos, obstacles2[i].width, obstacles2[i].height, null);
        }
        if (gameOver==true){
            g.drawImage(gameOverpic,300,200,400, 200, null);

        }


            g.dispose();
        bufferStrategy.show();

    }

    /***
     * Step 3 for keyboard control - add required methods
     * You need to have all 3 even if you aren't going to use them all
     */
    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 32) { // spacebar
           user.jumping = true;
           user.dy=-60;
           gameStart=true;
           // paint the graphics

        }
        if(gameOver==true&&keyCode == 32){
            gameOver = false;
            gameStart= true;

        }


//        if (keyCode == 68) { // d
//            user.right = true;
//        }
//        if (keyCode == 65) { // a
//            user.left = true;
//        }
//
//        if (keyCode == 83) { // s
//            user.down = true;
//        }
//        if (keyCode == 87) { // w
//            user.up = true;
//        }
    }//keyPressed()

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
//        if (keyCode == 68) { // d
//            user.right = false;
//        }
//        if (keyCode == 65) { // a
//            user.left = false;
//        }
//        if (keyCode == 83) { // s
//            user.down = false;
//        }
//        if (keyCode == 87) { // w
//            user.up = false;
//        }

    }//keyReleased()

    public void keyTyped(KeyEvent event) {
        // handles a press of a character key (any key that can be printed but not keys like SHIFT)
        // we won't be using this method, but it still needs to be in your program
    }//keyTyped()


    //Graphics setup method
    public void setUpGraphics() {
        frame = new JFrame("CheeseWorld");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!


        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

}//class
