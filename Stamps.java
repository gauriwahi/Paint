
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class Stamps {
	public static void main(String[] args) { 
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		StampsFrame stampsframe = new StampsFrame();
		stampsframe.setAlwaysOnTop(true);
		//stampsframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stampsframe.setTitle("Select the Stamp");
		stampsframe.setSize(500, 500);
		stampsframe.setVisible(true); 
	}
}

class StampsFrame extends JFrame implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	private static String[] stampsPath; //holds all the stamp images
	final static int NUMOFSTAMPS = 16; //total number of stamp images
	static JOptionPane stampSelector; //window that pops up and displays the stamp buttons
	static JButton[] stampButtonArray = new JButton[16]; //array of buttons to put in stampSelector
	static ImageIcon[] stampImagesArray = new ImageIcon[16];
	static JPanel stampsButtonPanel; 
	static ImageIcon selectedStampIcon; //to pass to canvas to draw
		
	public StampsFrame(){
		//construct and configure components
		//add listeners
		//Arrange components
		//implement action listener

		stampsPath = new String[NUMOFSTAMPS];
		stampsButtonPanel = new JPanel();
		stampsButtonPanel.setLayout(new GridLayout(4,4)); //put all buttons in stampSelector
		
		for(int i = 0; i < NUMOFSTAMPS; i++) { //building stampSelector
			stampsPath[i] = "resources//StampImages" + "//stamp (" + (i+1) + ").png"; //put pathnames in array
			JButton stampButton = new JButton(); //create the button for ea stamp
			stampButton.addMouseListener(this); 
			stampButton.setPreferredSize(new Dimension(10, 10));
			
			ImageIcon tempIcon = new ImageIcon(stampsPath[i]); //set and resize stamp button images
			Image tempImg = tempIcon.getImage();
			Image newimg = tempImg.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newimg);
			stampButton.setIcon(newIcon); //set image of stamp on button
			
			stampImagesArray[i] = newIcon; //adding to return to selectedStamp()
			stampButtonArray[i] = stampButton; //put button in array
			stampsButtonPanel.add(stampButton); //put all buttons in the panel
			
		}
		
		System.out.println(stampsPath[0]);
		stampsButtonPanel.setSize(400, 400);

		this.setContentPane(stampsButtonPanel);
	}
	
	public static Image selectedStamp(){ // returns which stamp user selected 
		return selectedStampIcon.getImage();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) { //to find out which stamp user selected
		// TODO Auto-generated method stub
		
		int x = e.getX();
		int y = e.getY();
		
		dispose();//close once user selects stamp
				
		Object source = e.getSource();
		
		for (int i=0; i< NUMOFSTAMPS; i++) {
			if (source == StampsFrame.stampButtonArray[i]){
				selectedStampIcon = stampImagesArray[i];
				System.out.println("Image selected: " + i);
			}
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
