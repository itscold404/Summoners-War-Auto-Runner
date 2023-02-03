import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JComponent; 
import java.util.Timer;
import java.util.TimerTask;

class gui extends JFrame{
	private JTextField choose_time;
	private JLabel time_prompt;
	private static JLabel time_left_display;
	private JButton ok;
	private JButton cairos;
	private JButton raid;
	private boolean cairos_bool;
	private boolean raid_bool;
	int mins = 0; //min user chosses
	int hours = 0;//hours user chose
	int sec = 0;
	private JFrame frame2;
	private JFrame frame3;
	private static final int map = JComponent.WHEN_IN_FOCUSED_WINDOW;
	private boolean see_screen;
	mouser mouse = new mouser();
	
	//SET MINIMUM AND MAXIMUM WAIT TIME BEFORE BATTLE REPEATS HERE
	
	int min_wait = 1; //minimum wait time in seconds before finding finish screen in seconds
	int max_wait = 69; //maximum wait time in seconds before finding finish screen in seconds
	
	public gui(){
		/**
		Create start screen with options to select what game mode to automate
		 */
		super("helping you save your life");
		
		cairos = new JButton("Cairos Dungeon/Rift Beast");
		cairos.setBounds(150,60,200,30);
		add(cairos);

		raid = new JButton("Raid");
		raid.setBounds(150,100,200,30);
		add(raid);
		
		//upon press of cairos or raid, create a new frame to allow user to enter how much
		//time they want to save
		ActionListener timeRecorder = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checking for which button is pressed and setting true to confirm what function user wants
				if(((JButton)e.getSource()).equals(cairos)) {
					cairos_bool = true;
				}
				if(((JButton)e.getSource()).equals(raid)) {
					raid_bool = true;
				}
				
				frame2= new JFrame();
		        frame2.setSize(400, 200); 
		        
		        ok = new JButton("Confirm");
				ok.setBounds(180,120,100,30);
				frame2.add(ok);
				theHandler handler = new theHandler();
				ok.addActionListener(handler);
				
				time_prompt = new JLabel("how much time do you want to save? Enter time as \"hours:minutes\"");
				time_prompt.setBounds(20,20,400,40);
				frame2.add(time_prompt);
				
				choose_time = new JTextField();
				choose_time.setBounds(140,60,60,20);
				frame2.add(choose_time);
				
				frame2.getContentPane().setLayout(null);
		        frame2.setVisible(true);
		        frame2.setDefaultCloseOperation(EXIT_ON_CLOSE);
		        dispose();
			}
		};
		
		cairos.addActionListener(timeRecorder);
		raid.addActionListener(timeRecorder);
		
	}
	
	private class theHandler implements ActionListener 
	{
		/**
		When the ok button is pressed to confirm the amount of time the user wants
		to save, create a new frame to display the count down from the entered time. 
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == ok)
			{
				//retrieve the time the user entered in the textbox
				String raw_time = choose_time.getText();
				int locH = raw_time.indexOf(":");
				hours = Integer.parseInt(raw_time.substring(0, locH));
				mins = Integer.parseInt(raw_time.substring(locH+1));
				sec = 0;

				//create the new frame and display that time in the format of "hours:minutes:seconds"
				Timer timer = new Timer();
				Timer mouse_delay = new Timer();
				
				frame2.dispose();
				
				frame3 = new JFrame();
				frame3.setSize(400, 200); 
				
				time_left_display = new JLabel(hours + " : " + mins + " : " + sec + " left on the timer");
				frame3.add(time_left_display);
				
				//while there is still time left on the timer, keep counting down. if there is no time left
				//on the timer, display "battle complete"
				timer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						Container parent = time_left_display.getParent();
						parent.remove(time_left_display);
						parent.revalidate();
						parent.repaint();
						
						time_left_display = new JLabel(hours + " : " + mins + " : " + sec + " left on the timer");
						time_left_display.setBounds(50,60,400,40);
						frame3.add(time_left_display);
						
						frame3.setVisible(true);
						
						sec--;
						
						if(mins == 0 && sec < 0 && hours > 0) {
							sec = 59;
							mins = 59;
							hours--;
						}
						
						if(mins == 0 && hours == 0 && sec == -2) {
							parent = time_left_display.getParent();
							parent.remove(time_left_display);
							parent.revalidate();
							parent.repaint();
							
							time_left_display = new JLabel("battle complete");
							time_left_display.setBounds(50,60,400,40);
							frame3.add(time_left_display);
							
							timer.cancel();
							mouse_delay.cancel();
							
							cairos_bool = false;
							raid_bool = false;
						}
						
						if(sec < 0 && mins > 0){
							sec = 59;
							mins--;
						}
						
						if(mins < 0 && hours > 0) {
							hours--;
							mins = 59;
						}
					}
				}, 1000, 1000);
				
				frame3.getContentPane().setLayout(null);
		        frame3.setDefaultCloseOperation(EXIT_ON_CLOSE);
		        
				mouse_delay.schedule(new TimerTask() {
					public void run() {
						int rand = (int)(Math.random() * (max_wait - min_wait +1) + min_wait) * 1000;
						try {
							if(mouse.check("out of energy")) {
								mouse.moveMouse("shop");
								// buy energy from shop
								mouse.moveMouse("60 crystals");
								
								//exit out and click replay
								mouse.moveMouse("yes");
								mouse.moveMouse("ok");
								mouse.moveMouse("close shop");
								if(cairos_bool)
									mouse.moveMouse("start battle for cairos");
								if(raid_bool)
									mouse.moveMouse("start battle for raid");
							}
							
							Thread.sleep(rand);
							
							if(mouse.check("battle ended")) {
								System.out.println("battle ended detected");
								mouse.moveMouse("replay");
								Thread.sleep((int)(Math.random() * (3 - 2 +1) + 2) * 1000);
								if(mouse.check("sort needed"))
									mouse.moveMouse("skip sort reward");
								if(cairos_bool)
									mouse.moveMouse("start battle for cairos");
								if(raid_bool)
									mouse.moveMouse("start battle for raid");
							}
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}, 1000, 1000);
		        	
			}
			
		}
	}
}

public class runner {

	public static void main(String[] args){
		gui GUI = new gui();
		GUI.getContentPane().setLayout(null);
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(600,300);
		GUI.setVisible(true);
	}
}
