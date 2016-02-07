import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class AddImage extends JPanel implements ActionListener {
	//fields
	JButton go;
	   
	   JFileChooser chooser;
	   String choosertitle;
	   
	   //constructor
	   public AddImage(){
		   go = new JButton("Add Image");
		   go.addActionListener(this);
		   add(go);
	   }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		int result;
        
	    chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(choosertitle);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //
	    // disable the "All files" option.
	    //
	    //chooser.setAcceptAllFileFilterUsed(true);
	    //    
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): " 
	         +  chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " 
	         +  chooser.getSelectedFile());
	      }
	    else {
	      System.out.println("No Selection ");
	      }
		
	}
	
	 public Dimension getPreferredSize(){
		    return new Dimension(200, 200);
		    }
	 
	 public static void main(String s[]) {
		    JFrame frame = new JFrame("");
		    AddImage panel = new AddImage();
		    frame.addWindowListener(
		      new WindowAdapter() {
		        public void windowClosing(WindowEvent e) {
		          System.exit(0);
		          }
		        }
		      );
		    frame.getContentPane().add(panel,"Center");
		    frame.setSize(panel.getPreferredSize());
		    frame.setVisible(true);
		    }
	
	
	

}
