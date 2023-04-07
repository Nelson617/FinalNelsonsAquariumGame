//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener, MouseInputListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1120;
    final int HEIGHT = 790;

    public int x;


    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image SF1Pic;
    public Image SF2Pic;
    public Image SF3Pic;
    public Image SF4Pic;
    public Image SF5Pic;
    public Image snowmanPic;
    public Image snowprintsPic;
    public Image background;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Snowflake SF1;
    private Snowflake SF2;
    private Snowflake SF3;
    private Snowflake SF4;
    private Snowflake SF5;
    private Snowflake snowman;
    private Snowflake snowprints;
    private Snowflake [] snowprintsArray;
    private Snowflake[] snowflakes;
    private Image[] SnowflakePictures;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {
        x = 11;
        setUpGraphics();


        //variable and objects
        //create (construct) the objects needed for the game and load up
        SF1Pic = Toolkit.getDefaultToolkit().getImage("snowflake1a.png");//load the picture
        background = Toolkit.getDefaultToolkit().getImage("snowing2.jpeg");
        SF2Pic = Toolkit.getDefaultToolkit().getImage("Snowflake2.png");
        SF3Pic = Toolkit.getDefaultToolkit().getImage("Snowflake3a.png");
        SF4Pic = Toolkit.getDefaultToolkit().getImage("Snowflake4.png");
        SF5Pic = Toolkit.getDefaultToolkit().getImage("Snowflake5.png");
        snowmanPic = Toolkit.getDefaultToolkit().getImage("snowman.png");
        snowprintsPic = Toolkit.getDefaultToolkit().getImage("snowprints.png");
        SnowflakePictures = new Image[5];

        SnowflakePictures[0] = SF1Pic;
        SnowflakePictures[1] = SF2Pic;
        SnowflakePictures[2] = SF3Pic;
        SnowflakePictures[3] = SF4Pic;
        SnowflakePictures[4] = SF5Pic;

        snowprintsArray = new Snowflake[20];
        for (int i = 0; i < snowprintsArray.length; i++) {
            snowprintsArray[i] = new Snowflake(35, 50);
            snowprintsArray[i].dx = (int) (Math.random() * 10) + 10;
            snowprintsArray[i].dy = (int) (Math.random() * 10) + 10;
            snowprintsArray[i].pic =snowprintsPic;
            snowprintsArray[i].isAlive = false;
        }
        snowprintsArray[0].isAlive = true;

        snowflakes = new Snowflake[(int) (Math.random() * 500)];
        for (int i = 5; i < snowflakes.length; i++) {
            snowflakes[i] = new Snowflake(35, 50);
            snowflakes[i].dx = (int) (Math.random() * 10) + 10;
            snowflakes[i].dy = (int) (Math.random() * 10) + 10;
            int PicNum = (int) (Math.random() * 5);
            snowflakes[i].pic = SnowflakePictures[PicNum];


        }


        SF1 = new Snowflake(35, 50);
        SF2 = new Snowflake(900, 50);
        SF3 = new Snowflake(35, 50);
        SF4 = new Snowflake(35, 50);
        SF5 = new Snowflake(35, 50);
        snowman = new Snowflake(35, 625);
        snowprints = new Snowflake(35, 50);
        SF2.dx = 15;
        SF2.dy = -20;
        SF1.dx = 25;
        SF1.dy = 15;
        SF3.dx = 20;
        SF3.dy = 20;
        SF4.dy = 17;
        SF4.dx = 28;
        SF5.dy = 39;
        SF5.dx = 23;
        snowman.dx = 20;
        snowman.dy = 0;
        snowprints.dx = 5;
        snowprints.dy = 10;

        snowflakes[0] = SF1;
        snowflakes[1] = SF2;
        snowflakes[2] = SF3;
        snowflakes[3] = SF4;
        snowflakes[4] = SF5;

    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }


    public void moveThings() {
        //calls the move( ) code in the objects
//		change();
//		size();
//		crash();
        checkIntersections();
     //u   System.out.println("");
//        SF1.bounce();
//        SF2.bounce();
//        SF3.bounce();
//        SF4.bounce();
//        SF5.bounce();
        snowman.bounce();
        snowprints.wrap();
        for (int x = 0; x < snowflakes.length; x++) {
            //   System.out.println(snowflakes[x].dx +" dy: " + snowflakes[x].dy);
            snowflakes[x].bounce();
        }
        for (int x = 0; x < snowprintsArray.length; x++) {
            //   System.out.println(snowflakes[x].dx +" dy: " + snowflakes[x].dy);
            snowprintsArray[x].bounce();
        }

    }


//	public void crash()
//	{
//		if(SF3.rec.intersects(SF2.rec) && SF2.isAlive == true && SF1.isAlive == true)
////		if(SF1.rec.intersects(jack.rec) && SF1.isCrashing == false)
//		{
////			SF1.isCrashing = true;
//			System.out. println("crash");
////			SF3.dx = 1* SF3.dx;
//			SF3.dy = -SF3.dy;
////			SF2.dx = 1* SF2.dx;
//			SF2.dy = -SF2.dy;
//			SF2.isAlive = false;
//		}
//
//		if(!SF1.rec.intersects(SF2.rec))
//		{
//			//when true
//		}
//		else{
//			//when false
//		}
//	}

//	public void change()
//	{
//		if(SF1.rec.intersects(SF3.rec));
//
//		System.out. println("crash");
//		SF1.dx = 1* SF1.dx;
//		SF1.dy = -SF1.dy;
//		SF3.dx = 1* SF3.dx;
//		SF3.dy = -SF3.dy;
//}

//	public void size()
//	{
//		if(SF1.rec.intersects(jimmy.rec))
//		{
//			SF1.height = 2*SF1.height;
//			SF1.width = 2*SF1.width;
//			jack.height = 2*jack.height;
//			jack.width = 2*jack.width;
//
//		}
//	}


    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {

        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(true);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of the astronaut
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        for (int x = 0; x < snowflakes.length; x++) {
            if (snowflakes[x].isAlive) {
                g.drawImage(snowflakes[x].pic, snowflakes[x].xpos, snowflakes[x].ypos, snowflakes[x].width, snowflakes[x].height, null);

            }
        }
            for (int x = 0; x < snowprintsArray.length; x++) {
                if (snowprintsArray[x].isAlive) {
                    g.drawImage(snowprintsArray[x].pic, snowprintsArray[x].xpos, snowprintsArray[x].ypos, snowprintsArray[x].width, snowprintsArray[x].height, null);
                }
            }
        if (SF2.isAlive == true)
            if (SF1.isAlive == true)
                if (SF3.isAlive == true)
                    if (SF4.isAlive == true)
                        if (SF5.isAlive == true)
                            if (snowman.isAlive == true) {
                                g.drawImage(SF2Pic, SF2.xpos, SF2.ypos, SF2.width, SF2.height, null);
                                g.draw(new Rectangle(SF2.xpos, SF2.ypos, SF2.width, SF2.height));
                            }
        g.drawImage(SF1Pic, SF1.xpos, SF1.ypos, SF1.width, SF1.height, null);
        g.drawImage(SF2Pic, SF2.xpos, SF2.ypos, SF2.width, SF2.height, null);
        g.drawImage(SF3Pic, SF3.xpos, SF3.ypos, SF3.width, SF3.height, null);
        g.drawImage(SF4Pic, SF4.xpos, SF4.ypos, SF4.width, SF4.height, null);
        g.drawImage(SF5Pic, SF5.xpos, SF5.ypos, SF5.width, SF5.height, null);
        g.drawImage(snowmanPic, snowman.xpos, snowman.ypos, snowman.width, snowman.height, null);
        g.drawImage(snowprintsPic, snowprints.xpos, snowprints.ypos, snowprints.width, snowprints.height, null);

        g.draw(new Rectangle(SF1.xpos, SF1.ypos, SF1.width, SF1.height));
        g.draw(new Rectangle(SF2.xpos, SF2.ypos, SF2.width, SF2.height));
        g.draw(new Rectangle(SF3.xpos, SF3.ypos, SF3.width, SF3.height));
        g.draw(new Rectangle(SF4.xpos, SF4.ypos, SF4.width, SF4.height));
        g.draw(new Rectangle(SF5.xpos, SF5.ypos, SF5.width, SF5.height));
        g.draw(new Rectangle(snowman.xpos, snowman.ypos, snowman.width, snowman.height));
        g.draw(new Rectangle(snowprints.xpos, snowprints.ypos, snowprints.width, snowprints.height));


//		g.draw(new Rectangle(jack.xpos, jack.ypos, jack.width, jack.height));
        g.dispose();

        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println(code);

        if (code == 87) {
           // snowman.dy = -snowman.dy;
            snowman.dy = -5;
            int c = Math.abs(code);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void checkIntersections() {

        if (snowman.rec.intersects(SF1.rec)) {
            SF1.isAlive = false;
        }
        if(snowprints.rec.intersects(snowman.rec)) {

          //  for (int x = 0; x < snowprints.dx; x++)
          //   {
                 snowprints.height = snowprints.height + 15;
                 snowprints.width = snowprints.width +15;
               // snowprints[x]. isalive = true;

          //  }
        }

        for (int x = 0; x < snowflakes.length; x++) {
            if (snowman.rec.intersects(snowflakes[x].rec)) {
                snowflakes[x].isAlive = false;
            }


            
//                    public void mousemoved;
//                System.out.println(e.getX());
//                snowmanPic.xpos=e.getX();
//                snowmanPic.ypos=e.getY();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       // System.out.println(e.getID());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println(e.getID());
        snowman.dy = 0;
        snowman.dx = 20;


    }

    @Override
    public void mouseExited(MouseEvent e) {
        snowman.dx = 0;
        snowman.dy =0;

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(e.getID());

        snowman.xpos=e.getX();
                snowman.ypos=e.getY();
//
    }
}
