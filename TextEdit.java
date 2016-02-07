import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



import java.awt.event.*;



public class TextEdit {
	public static void main(String[] args)
	{
		// use look and feel for my system (Win32)
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
		}

		TextFrame frame = new TextFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Edit Text");
		frame.pack();

		// put the frame in the middle of the display
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setVisible(true);
		
	}
	

}

class TextFrame extends JFrame implements ActionListener{
	
	
	static final long serialVersionUID = 42L;
	//fields
	
	static JTextField messageField;
	JButton customize;
	JButton apply;
	
//to return and draw on canvas
    static String tt;
	String message;
	Font messageFont;
	Color foreground;
	Color background;
	static ImageIcon icon;
	CustomDialog cd;
	
	
	//constructor of TextFrame
	public TextFrame(){
		//this.message = message;
		message = "Click to Edit Text";
		messageFont = new Font("Arial", Font.PLAIN, 30);
		foreground = Color.black;
		background = Color.white;
		
		
		
		//custom dialog (it's a private class inside TextFrame)
		cd = new CustomDialog(this);
		
		messageField = new JTextField();
		messageField.setPreferredSize(new Dimension(300, 200));
		messageField.setEditable(false);
		messageField.setHorizontalAlignment(SwingConstants.CENTER);
		messageField.setAlignmentX(Component.CENTER_ALIGNMENT);
		updateMessage();
		
		customize = new JButton("Customize");
		customize.setMaximumSize(customize.getPreferredSize());
		customize.setAlignmentX(Component.CENTER_ALIGNMENT);

		apply = new JButton("Apply");
		apply.setMaximumSize(customize.getPreferredSize());
		apply.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		/*add the listeners*/
		customize.addActionListener(this);
		apply.addActionListener(this);
		messageField.addMouseListener(new PopUpClass());
		
		
		//customize
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(customize);
		p.add(Box.createRigidArea(new Dimension(0, 25)));
		p.add(messageField);
		p.add(Box.createRigidArea(new Dimension(0, 25)));
		p.add(apply);
		p.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		// make panel this JFrame's content pane

		this.setContentPane(p);
		
		
	}
	
	
	
	
	
	/*EVENT LISTENER*/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		
		Object source = e.getSource();

		if (source == customize)
		{
			int i = cd.showCustomDialog(this, messageFont, foreground, background);
			if (i == CustomDialog.APPLY_OPTION)
			{
				messageFont = cd.getFont();
				foreground = cd.getForeColor();
			
				updateMessage();
			}

			
		}

		else if (source == apply)
			dispose();
	}
	
	
	//methods
	public void updateMessage()
	{
		messageField.setText(message);
		messageField.setFont(messageFont);
		messageField.setForeground(foreground);
		messageField.setBackground(background);
		//tt = messageField.getText();
		
	}
	
	
	public static String returnText(){
		System.out.println(tt);
		return tt;
	}
	
	//private inner class
	private class PopUpClass extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent me)
		{
			String tmp = JOptionPane.showInputDialog(messageField, "Enter new message");
			if (tmp != null && tmp.length() > 0)
			{
				message = tmp;
				tt = messageField.getText();
				//System.out.println(messageField.getText());
				//tt = tmp;
				//System.out.println(tt);
				updateMessage();
			}
		}
		
		
	}
	
	/*Another box to edit the font of the text, and the color*/
	private class CustomDialog extends JDialog implements ActionListener, ItemListener{
		
		
		static final long serialVersionUID = 42L;
		
		//fields
		public static final int APPLY_OPTION = 0;
		public static final int CANCEL_OPTION = 1;
		//response of the user
		int response;
		
		//text size availability
		final String[] stringSize = {"10", "12", "16", "18", "22", "32", "64", "100"};
		JCheckBox italic;
		JCheckBox bold;
		JComboBox sizeCombo;
		JComboBox fontCombo;
		JTextField example;
		JButton ok;
		JButton cancel;
		JButton foreground;
		
		
		public CustomDialog(Frame name){
			super(name, "Customize Text Properties", true);
			this.setResizable(false);
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			String[] fontList = ge.getAvailableFontFamilyNames();
			fontCombo = new JComboBox(fontList);

			italic = new JCheckBox("Italic");
			bold = new JCheckBox("Bold");

			sizeCombo = new JComboBox(stringSize);
			((JLabel) sizeCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
			sizeCombo.setSelectedIndex(4);
			sizeCombo.setPreferredSize(new Dimension(45, 21)); // tweek size

			example = new JTextField(" Preview ");
			example.setHorizontalAlignment(SwingConstants.CENTER);
			example.setFont(new Font("sanserif", Font.PLAIN, 28));
			example.setEditable(false);

			ok = new JButton("Apply");
			cancel = new JButton("Cancel");
			ok.setPreferredSize(cancel.getPreferredSize());

			foreground = new JButton("Edit Color");
			
			foreground.setPreferredSize(new Dimension(100, 50));
			
			//add the listeners
			fontCombo.addActionListener(this);
			italic.addItemListener(this);
			bold.addItemListener(this);
			sizeCombo.addActionListener(this);
			ok.addActionListener(this);
			cancel.addActionListener(this);
			foreground.addActionListener(this);
			
			
			JPanel p0 = new JPanel();
			p0.add(fontCombo);
			p0.setBorder(new TitledBorder(new EtchedBorder(), "Font family"));

			JPanel p1a = new JPanel();
			p1a.add(italic);
			p1a.add(bold);
			p1a.setBorder(new TitledBorder(new EtchedBorder(), "Font style"));

			JPanel p1b = new JPanel();
			p1b.add(sizeCombo);
			p1b.add(new JLabel("pt."));
			p1b.setBorder(new TitledBorder(new EtchedBorder(), "Font size"));

			JPanel p1 = new JPanel();
			p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
			p1.add(p1a);
			p1.add(p1b);
			p1.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel p2 = new JPanel(); // use FlowLayout
			p2.add(foreground);
		
			p2.setBorder(new TitledBorder(new EtchedBorder(), "Message color"));
			p2.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel p3 = new JPanel();
			p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
			p3.add(example);
			p3.setPreferredSize(new Dimension(250, 60));
			p3.setMaximumSize(new Dimension(250, 60));
			p3.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel p4 = new JPanel();
			p4.add(ok);
			p4.add(cancel);
			p4.setAlignmentX(Component.CENTER_ALIGNMENT);

			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.add(p0);
			p.add(Box.createRigidArea(new Dimension(0, 10)));
			p.add(p1);
			p.add(Box.createRigidArea(new Dimension(0, 10)));
			p.add(p2);
			p.add(Box.createRigidArea(new Dimension(0, 10)));
			p.add(p3);
			p.add(Box.createRigidArea(new Dimension(0, 10)));
			p.add(p4);
			p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			

			Dimension d1 = p3.getPreferredSize();
			Dimension d2 = p1.getPreferredSize();
			p1.setPreferredSize(new Dimension(d1.width, d2.height));
			p1.setMaximumSize(new Dimension(d1.width, d2.height));
			d2 = p2.getPreferredSize();
			p2.setPreferredSize(new Dimension(d1.width, d2.height));
			p2.setMaximumSize(new Dimension(d1.width, d2.height));

			this.setContentPane(p);
			this.pack();
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			Object source = e.getSource();
			Font tmp = example.getFont();
			int style = tmp.getStyle();

			if (source == italic)
				if (italic.isSelected())
					style = style | Font.ITALIC; // turn italic on
				else
					style = style & ~Font.ITALIC; // turn italic off
			else if (source == bold)
				if (bold.isSelected())
					style = style | Font.BOLD; // turn bold on
				else
					style = style & ~Font.BOLD; // turn bold off

			example.setFont(new Font(tmp.getFamily(), style, tmp.getSize()));
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			Object source = e.getSource();
			if(source == ok){
				response = APPLY_OPTION;
				this.setVisible(false);
			}
			
			else if(source == cancel){
				response = CANCEL_OPTION;
				this.setVisible(false);
			}
			
			else if(source == sizeCombo){
				//get the number from the source
				JComboBox number = (JComboBox) source;
				String numberItem = (String) number.getSelectedItem();
				Font temp = example.getFont();
				//then set the font
				int newSize = Integer.parseInt(numberItem);
				example.setFont(new Font(temp.getFamily(), temp.getStyle(), newSize));
			}
			
			else if(source == fontCombo){
				JComboBox font = (JComboBox) source;
				String s = (String) font.getSelectedItem();
				Font tmp = example.getFont();
				example.setFont(new Font(s, tmp.getStyle(), tmp.getSize()));
			}
			
			else if (source == foreground)
			{
				Color tmp = JColorChooser.showDialog(this, "Choose text color", example.getForeground());
				MenuBar.shapeLBG.setBackground(tmp);
				if (tmp != null)
					example.setForeground(tmp);
					
			}

			
			
		}
		public Font getFont()
		{
			return example.getFont();
		}

		public Color getForeColor()
		{
			return example.getForeground();
		}


		public int showCustomDialog(Frame f, Font fontArg, Color foreColorArg, Color backColorArg)
		{
			this.setLocationRelativeTo(f);

			String s = fontArg.getName();
			fontCombo.setSelectedItem((Object) s);

			int style = fontArg.getStyle();
			if ((style & Font.ITALIC) == 0)
				italic.setSelected(false);
			else
				italic.setSelected(true);
			if ((style & Font.BOLD) == 0)
				bold.setSelected(false);
			else
				bold.setSelected(true);

			int size = fontArg.getSize();
			sizeCombo.setSelectedItem((Object) ("" + size));

			

			example.setFont(fontArg);
			example.setForeground(MenuBar.shapeLBG.getBackground());
			

			// show the dialog

			this.setVisible(true);

		
			return response;
		
	}
		
	}
	
	
	
}
