

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ScrollBarThickness {
	public static void main(String [] args){
		ScrollBarThicknessFrame frame = new ScrollBarThicknessFrame();
		/*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("DemoScrollBarBB");*/
		frame.pack();

		// put the frame in the middle of the display
		//new ScrollBarThicknessFrame();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setVisible(true);
	}

}

class ScrollBarThicknessFrame extends JFrame implements AdjustmentListener, ActionListener{

	static final long serialVersionUID = 42L;

	private JScrollBar scrollbar;
	static JTextField scrollbarValue;
	private int count;
	private JButton apply;
	//constructor
	public ScrollBarThicknessFrame(){
		this.setSize(new Dimension(500, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
		//creating the scrollbar
		scrollbar = new JScrollBar(SwingConstants.HORIZONTAL);
		scrollbar.setValue(10);
		scrollbar.setMinimum(5);
		scrollbar.setMaximum(100);
		scrollbar.setPreferredSize(new Dimension(200, 20));
		
		//displaying the scrollbar
		final int SIZE = 4;
		final Color LIGHT_GRAY = new Color(225, 225, 225);
		scrollbarValue = new JTextField(SIZE);
		scrollbarValue.setEditable(false);
		scrollbarValue.setBackground(LIGHT_GRAY);
		scrollbarValue.setHorizontalAlignment(SwingConstants.CENTER);
		scrollbarValue.setText(" " + scrollbar.getValue());
		
		//button
		apply = new JButton("Apply");
		
		//adding listeners
		scrollbar.addAdjustmentListener(this);
		apply.addActionListener(this);
		
		
		//adding components to the layout
		JPanel panel = new JPanel();
		panel.add(new JLabel("Changing width of pen"));
		panel.add(scrollbar);
		panel.add(scrollbarValue);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Width of tools"));
		
		JPanel contentPane = new JPanel();
		contentPane.add(panel);
		contentPane.add(apply);
		contentPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		this.setContentPane(contentPane);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		JScrollBar source = (JScrollBar)e.getSource();

		// get the scrollbar's current value
		int newValue = source.getValue();

		// print it!
		System.out.print(newValue + " ");

		// start a new line every 25 updates
		if (++count % 25 == 0)
			System.out.println();

		// update the text field
		scrollbarValue.setText("" + newValue);
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == apply){
			dispose();
			
		}
		
	}
	
}
