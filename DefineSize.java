import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;


public class DefineSize {
	public static void main(String[] args)
	{
		// use look and feel for my system (Win32)
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
		}

		DefineSizeFrame frame = new DefineSizeFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("New File");
		frame.pack();

		// put the frame in the middle of the display
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setVisible(true);
		
		//System.out.println(DefineSizeFrame.width.getText());
		
	}
	

}

class DefineSizeFrame extends JFrame implements ActionListener{
	
	static final long serialVersionUID = 42L;
	
	static JTextField width, height;
	JLabel widthLabel, heightLabel, pixels, pixels2;
	JButton apply;
	
	//constructor
	public DefineSizeFrame(){
		width = new JTextField();
		width.setPreferredSize(new Dimension(100, 25));
		//System.out.println(width.getText());
		width.setEditable(true);
		height = new JTextField("");
		height.setPreferredSize(new Dimension(100, 25));
		height.setEditable(true);
		
		//labels
		widthLabel = new JLabel("Width: ");
		heightLabel = new JLabel("Height: ");
		pixels = new JLabel("px");
		pixels2 = new JLabel("px");
		//button
		apply = new JButton("Apply");
		apply.setMaximumSize(apply.getPreferredSize());
		apply.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//listeners
		apply.addActionListener(this);
		
		
		//panel
		JPanel widthPanel = new JPanel();
		widthPanel.add(widthLabel, BorderLayout.WEST);
		widthPanel.add(width, BorderLayout.CENTER);
		widthPanel.add(pixels, BorderLayout.EAST);
		
		
		JPanel heightPanel = new JPanel();
		heightPanel.add(heightLabel, BorderLayout.WEST);
		heightPanel.add(height, BorderLayout.CENTER);
		heightPanel.add(pixels2, BorderLayout.EAST);
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(widthPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 25)));
		panel.add(heightPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 25)));
		panel.add(apply);
		panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
		
		
		this.setContentPane(panel);
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == apply){
			dispose();
		}
	}
	
}
