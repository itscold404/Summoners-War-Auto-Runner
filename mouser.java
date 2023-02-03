import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.DataBufferByte;
import java.awt.event.AWTEventListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class mouser {
	double weight63 = 7.5; //weight for the 63% of area 
	double weight31 = 3.5; //weight for 31% of area
	int weight6 = 1; //weight for the outer 6% of area
	Robot bot;
	
	public mouser(){
		try {
			bot= new Robot();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveMouse(String name){ 
		//takes name of the image that you want to click and moves mouse there
		// make sure this calls safeCordFinder()
		//coordinates of paint and actual coordinates used by program is a ~1.5:1 ratio
		// (divide the coordinates obtained by paint by 1.5)
		// the coordinates shown below are the already converted cords (no need to convert them)
		int min_wait = 1500; //wait time before each mouse clicks to give time for windows to load
		int max_wait = 2000;
		int x = 0;
		int y = 0;
		
		switch(name) {
			case "plus": 
//				max_x = 725; min_x = 716; min_y = 186; max_y = 195;
				x = findSafeCord(713,738);
				y = findSafeCord(183,204);
				break;
				
			case "shop":
//				max_x = 580; min_x = 425; min_y = 415; max_y = 460;
				x = findSafeCord(425,580);
				y = findSafeCord(415,460);
				break;
			
			case "gift box":
//				max_x = 800; min_x = 640; min_y = 420; max_y = 460;
				x = findSafeCord(640,800);
				y = findSafeCord(420,460);
				break;
				
			case "collect":
//				max_x = 815; min_x = 716; min_y = 255; max_y = 285;
				x = findSafeCord(716,815);
				y = findSafeCord(255,285);
				break;
				
			case "60 crystals"://optiuon of buying energy with 60 crystals in shop
//				max_x = 865; min_x = 670; min_y = 250; max_y = 530;
				x = findSafeCord(670,865);
				y = findSafeCord(250,530);
				break;
				
			case "yes"://yes or no to buying crystals
//				max_x = 590; min_x = 440; min_y = 415; max_y = 455;
				x = findSafeCord(440,590);
				y = findSafeCord(415,455);
				break;
				
			case "ok"://ok on the screen purchase successful
//				max_x = 680; min_x = 543; min_y = 408; max_y = 456;
				x = findSafeCord(543,680);
				y = findSafeCord(408,456);
				break;
				
			case "close shop"://closing the energy shop
//				max_x = 1120; min_x = 1090; min_y = 100; max_y = 130;
				x = findSafeCord(1090,1120);
				y = findSafeCord(100,130);
				break;
				
			case "replay": //this appears after battle ended
//			    max_x = 730; min_x = 568; min_y = 575; max_y = 615;
				x = findSafeCord(568,730);
				y = findSafeCord(575,615);
				break;
				
			case "start battle for cairos": //before battle starts
//				max_x = 1099; min_x = 905; min_y = 460; max_y = 534;
				x = findSafeCord(905,1099);
				y = findSafeCord(460,534);
				break;
				
			case "start battle for raid":
//              max_x = 1136; min_x = 952; min_y = 564; max_y = 638; 
				x = findSafeCord(952,1136);
				y = findSafeCord(580,638);
				break;
				
			case "skip sort reward":
//              max_x = 867; min_x = 660; min_y = 600; max_y = 678;
				
				x = findSafeCord(440,578);
				y = findSafeCord(400, 452);
				break;
				
			default:
				System.out.println("parameter in moveMouse could not be found: " + name);
			
		}
		
		try{
			bot.mouseMove(x,y);
			Thread.sleep((int)(Math.random() * (max_wait - min_wait +1) + min_wait)); 
			clickMouse();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean check(String s) { 
		//check pixle color at (565x369)376x246 and (572x371)381x247 (817x373)545x249
		
		boolean tf = false;
		Rectangle capture;
		BufferedImage check_screen;
		BufferedImage Image;
		File raw_file;
		Color color1;
		Color actual1;
		Color color2;
		Color actual2;
		Color color3;
		Color actual3;
		try {
			capture =  new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); 
				
			Image = bot.createScreenCapture(capture); 
				    
			switch(s){
				case "battle ended":
					raw_file = new File("C:\\Users\\linda\\eclipse-workspace\\SWProject\\replay.png");
					check_screen = ImageIO.read(raw_file);
					
					color1 = new Color(check_screen.getRGB(817,373)); //paint coord1
					actual1 = new Color(Image.getRGB(545, 249));//converted cord1
					
					color2 = new Color(check_screen.getRGB(572,371)); //paint coord2
					actual2 = new Color(Image.getRGB(381, 247));// converted cord2
					
					color3 = new Color(check_screen.getRGB(565,369)); //paint coord3
					actual3 = new Color(Image.getRGB(376, 246)); //converted cord3
					
					if(color1.equals(actual1) && color2.equals(actual2) && color3.equals(actual3)) 
						tf = true;
					
					break;
					
				case "out of energy":
					raw_file = new File("C:\\Users\\linda\\eclipse-workspace\\SWProject\\notenoughenergy1.png");
					check_screen = ImageIO.read(raw_file);
					
					// the pixels used from the paint file are: 754x398    801x401     906x407
					color1 = new Color(check_screen.getRGB(754,398)); //paint coord1
					actual1 = new Color(Image.getRGB(502, 265));//converted cord1
					
					color2 = new Color(check_screen.getRGB(801,401)); //paint coord1
					actual2 = new Color(Image.getRGB(534, 267));//converted cord1
					
					color3 = new Color(check_screen.getRGB(906,407)); //paint coord1
					actual3 = new Color(Image.getRGB(604,271));//converted cord1
					
					if(color1.equals(actual1) && color2.equals(actual2) && color3.equals(actual3)) 
						tf = true;
					break;
					
				case "sort needed":
					raw_file = new File("C:\\Users\\linda\\eclipse-workspace\\SWProject\\SortReward.png");
					check_screen = ImageIO.read(raw_file);
					
					color1 = new Color(check_screen.getRGB(812,434)); //paint coord1
					actual1 = new Color(Image.getRGB(541, 289));//converted cord1
					
					color2 = new Color(check_screen.getRGB(1002,423)); //paint coord1
					actual2 = new Color(Image.getRGB(668, 282));//converted cord1
					
					color3 = new Color(check_screen.getRGB(1254,433)); //paint coord1
					actual3 = new Color(Image.getRGB(836,288));//converted cord1
					
					if(color1.equals(actual1) && color2.equals(actual2) && color3.equals(actual3)) 
						tf = true;
//					System.out.println(tf);
//					System.out.println(check_screen.getRGB(812,434));
//					System.out.println(Image.getRGB(541, 289));
//					System.out.println(check_screen.getRGB(1002,423));
//					System.out.println(Image.getRGB(668, 282));
//					System.out.println(check_screen.getRGB(1254,433));
//					System.out.println(Image.getRGB(836,288));
//					System.out.println(color3.equals(actual3));
					break;
				}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return tf;
	}
	
	public void clickMouse() {
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public int findSafeCord(int min, int max) {
		
		int eightx = (int)(Math.random() * 1000 * weight63);
		int threex = (int)(Math.random() * 1000 * weight31);
		int one5x = (int)(Math.random() * 1000 * weight6);
		double flip = Math.random();
		int wLength = max - min;
		int emin = 0;
		int emax = 0;
		
		if(eightx >= threex && eightx >= one5x) {
			emax = max - (int)(wLength * .185);
			emin = min + (int)(wLength * .185);
		}
		
		else if(threex >= eightx && threex >= one5x) {
			if(flip >= .5) {
				emax = max - (int)(wLength * .03);
				emin = min + (int)(wLength * .815);
			}
			
			else{
				emax = max - (int)(wLength * .815);
				emin = min + (int)(wLength * .03);
			}
			
		}
		
		else {
			if(flip >= .5) {
				emax = max;
				emin = max - (int)(wLength * .03);
			}
			
			else{
				emax = min + (int)(wLength * .03);
				emin = min;
			}
		}
		
	return (int)(Math.random() * (emax - emin +1) + emin);
	}
}
