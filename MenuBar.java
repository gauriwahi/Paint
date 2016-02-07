import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MenuBar extends JMenuBar implements ActionListener {
	
	private static String OS = System.getProperty("os.name").toLowerCase(); // for icons 

	ButtonGroup toolbox;
	JButton undo, redo, zoomin, zoomout;
	JToggleButton line, rectangle, circle, eraser, text, freestyle, stamps, imageButton, background;
	Command c;
	Graphics2D graphic;
	Boolean dragged;
	static JButton shapeLBG, shapeLFG;
	JPanel colourChooserPanel, addImagePanel;
	JColorChooser colourChooser; // holds the colour choosers for fg and bg
	Color bg, fg; // colours of the foreground and background passed between the
					// jcolourchooser and the buttons
	
	JCheckBox fill, stroke;
	Shape shape;
	File file;
	public Image image;
	BufferedImage buffImg;
	
	double zoom = 1;
	
	//color for background
	Color bgColor;
	//to store text
	String tt;
	Color textColor;
	Font textFont;

	static JMenuItem newFile;

	JMenuItem open;

	JMenuItem save;

	JMenuItem saveAs;

	Image imageStamp, imageImage; 
	Color menuBg = new Color(84,84,84);//add to
	Color selectedBg = new Color(36,36,36);//add to
	static JToolBar toolBar;

	private File selectedFile;
	private int widthMenuButton = 50;

	public MenuBar() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
		// dim.height / 2 - frame.getSize().height / 2);
		toolBar = new JToolBar(JToolBar.VERTICAL);
		//add to
		
		
		toolBar.setBackground(menuBg);
		//add to
		c = new Command();

		/* File */
		JMenu file = new JMenu("File");

		newFile = new JMenuItem("New");
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		file.add(newFile);
		newFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				A2.main(null);
				System.out.println("new file");
				
				
			}
		});
		file.add(new JSeparator());
		open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		file.add(open);
		open.addActionListener(this);
		file.add(new JSeparator());
		/**/// Level 2
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		file.add(save);
		save.addActionListener(this);
		saveAs = new JMenuItem("Save As");
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
		file.add(saveAs);
		saveAs.addActionListener(this);

	
		this.add(file);

		// menu size stuff
		int numOfIcons = 25;
		int widthToolBoxButton = (int) (dim.getWidth() / numOfIcons);
		// int height = (int) (dim.getHeight()/(numOfIcons));

		colourChooser = new JColorChooser();
		colourChooserPanel = new JPanel();
		colourChooserPanel.add(colourChooser);
		shapeLBG = new JButton();

		//this.add(shapeLBG);

		shapeLBG.setBackground(Color.white);
		shapeLBG.setOpaque(true);
		shapeLBG.setMaximumSize(new Dimension(widthToolBoxButton / 2, widthToolBoxButton / 2));
		shapeLBG.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				colourChooserPanel.setVisible(true);
				bg = JColorChooser.showDialog(colourChooser, "Fill Colour", shapeLBG.getBackground());
				if (bg != null)
					shapeLBG.setBackground(bg);
			}
		});

		fill = new JCheckBox();
		fill.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!fill.isSelected()) {
					shapeLBG.setEnabled(false);
				} else {
					shapeLBG.setEnabled(true);
				}
			}
		});
		this.add(fill);

		shapeLFG = new JButton();
		shapeLFG.setBackground(Color.black);
		shapeLFG.setOpaque(true);
		shapeLFG.setMaximumSize(new Dimension(widthToolBoxButton / 2, widthToolBoxButton / 2));
		//this.add(shapeLFG);
		// needs popup when doing a stroke and stroke is disabled
		shapeLFG.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				colourChooserPanel.setVisible(true);
				Color fg = JColorChooser.showDialog(colourChooser, "Stroke Colour", shapeLFG.getBackground());
				if (fg != null)
					shapeLFG.setBackground(fg);
			}
		});

		stroke = new JCheckBox();
		stroke.setSelected(true);
		stroke.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (!stroke.isSelected()) {
					//
					shapeLFG.setEnabled(false);
				} else {
					shapeLFG.setEnabled(true);
				}
			}
		});
		this.add(stroke);
		
		/*ToolBar and Button Construction START*/
		
		/*used to detect machine OS and set icons accordingly START*/
		String slash ="\\\\";
		if (OS.indexOf("win") >= 0) {
            System.out.println("This is Windows");
        } else if (OS.indexOf("mac") >= 0) {
            System.out.println("This is Mac");
            slash="////";
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) {
        	System.out.println("This is Unix");
            slash="////";
        }
		/*used to detect machine OS and set icons accordingly START*/
		
		/*start of line */
		line = new JToggleButton();
		line.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "line.png"));
		line.setBorderPainted(false);
		line.addActionListener(this);
		line.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(line);
		/*end of line*/
		
		/*start of rectangle */
		rectangle = new JToggleButton();
		rectangle.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons"+ slash + "rectangle.png"));
		rectangle.addActionListener(this);
		rectangle.setBorderPainted(false);
		rectangle.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(rectangle);
		/*end of rectangle*/
		
		/*start of circle*/
		circle = new JToggleButton();
		circle.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons"+ slash + "circle.png"));
		circle.setBorderPainted(false);
		circle.addActionListener(this);
		circle.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(circle);
		/*end of circle*/
		

		/* free style button */
		freestyle = new JToggleButton();
		freestyle.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons"+ slash + "freestyle.png"));
		freestyle.setBorderPainted(false);
		freestyle.addActionListener(this);
		freestyle.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(freestyle);
		/* end of freestyle button */
		
		
		
		/* Text */
		text = new JToggleButton();
		text.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "type.png"));
		text.addActionListener(this);
		text.setBorderPainted(false);
		text.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(text);
		/* end of text */
		
		/*Background*/
		background = new JToggleButton();
		background.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "bucket.png"));
		background.addActionListener(this);
		background.setBorderPainted(false);
		background.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(background);
		
		/*end of backgrounf*/
		
		
		
		JSeparator seperator = new JSeparator(SwingConstants.HORIZONTAL);
		seperator.setPreferredSize(new Dimension(2, 2));
		//toolBar.add(seperator);
		
		fill.setText("Fill");
		fill.setBackground(Color.darkGray);
		fill.setForeground(Color.white);
		
		stroke.setText("Border");
		stroke.setBackground(Color.darkGray);
		stroke.setForeground(Color.white);
		
		
		
		
		JSeparator seperator2 = new JSeparator(SwingConstants.HORIZONTAL);
		seperator2.setPreferredSize(new Dimension(2, 2));
		//toolBar.add(seperator2);
		
		/* eraser */
		eraser = new JToggleButton();
		eraser.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons"+ slash + "eraser.png"));
		eraser.addActionListener(this);
		eraser.setBorderPainted(false);
		eraser.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(eraser);
		/* end of eraser */

		/*image insertion*/
        imageButton = new JToggleButton();
        imageButton.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "image.png"));
        imageButton.addActionListener(this);
        imageButton.setBorderPainted(false);
        imageButton.setMaximumSize(new Dimension(widthToolBoxButton,widthToolBoxButton));
        toolBar.add(imageButton);
		/*end image insertion */

		/* start of stamp */
		stamps = new JToggleButton();
		stamps.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "stamp.png"));
		stamps.addActionListener(this);
		stamps.setBorderPainted(false);
		stamps.setMaximumSize(new Dimension(widthToolBoxButton, widthToolBoxButton));
		toolBar.add(stamps);
		/* end of stamp */

		/* zoom */
		zoomin = new JButton();
		zoomin.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "zoom_in.png"));
		zoomin.addActionListener(this);
		zoomin.setBorderPainted(false);
		zoomin.setMaximumSize(new Dimension(widthMenuButton, widthMenuButton));
		zoomin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (zoom<10){
					zoom+=0.5;
					
				}
				}
		});
		

		zoomout = new JButton();
		zoomout.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "zoom_out.png"));
		zoomout.addActionListener(this);
		zoomout.setBorderPainted(false);
		zoomout.setMaximumSize(new Dimension(widthMenuButton , widthMenuButton));
		zoomout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (zoom>=1){
					zoom-=0.5;
				}
				}
		});
		
		toolBar.add(zoomin);
		toolBar.add(zoomout);
		toolBar.add(fill); 
		
		toolBar.add(shapeLBG);
		toolBar.add(stroke);
		toolBar.add(shapeLFG);
		/* end of zoom */

		/* undo and redo */
		undo = new JButton("Undo");
		undo.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "undo.png"));
		undo.addActionListener(this);
		undo.setEnabled(false);
		redo = new JButton("redo");
		redo.addActionListener(this);
		redo.setEnabled(false);
		
		redo.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "redo.png"));

		undo.setIcon(new ImageIcon("resources" + slash + "ToolBarIcons" + slash + "undo.png"));

		this.add(undo);
		this.add(redo);
		/* end of undo and redo */

		/* toolbox toggle group menu */
		toolbox = new ButtonGroup();
		toolbox.add(rectangle);
		toolbox.add(circle);
		toolbox.add(line);
		toolbox.add(freestyle);
		toolbox.add(eraser);
		toolbox.add(text);
		toolbox.add(imageButton);
		toolbox.add(stamps);
		toolbox.add(background);
		//toolbox.add(zoomin);
		//toolbox.add(zoomout);
		
		/*Button Construction END*/

		
		//add to
		for (Enumeration<AbstractButton> buttons = toolbox.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (!button.isSelected()) {
				button.setBorderPainted(false);
				button.setBackground(menuBg);
			}else{
				button.setBackground(selectedBg);
			}
		}
	}
	
	public static JToolBar getToolBar() {//need to add toolbar in A2.java
		return toolBar;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		if (e.getSource() instanceof JButton) {
			System.out.println("enter the undo");
			JButton temp = (JButton) e.getSource();
			if (temp.equals(undo)) {
				c.undo();
			}else if(temp.equals(zoomin)){
			}else if(temp.equals(zoomout)){
			} else {
				c.redo();
			}
		} else if (e.getSource() instanceof JToggleButton) {
			JToggleButton temp = (JToggleButton) e.getSource();

			if (temp.equals(line)) {
				// draw line
				System.out.println("line drawn");
				stroke.setEnabled(false);
				//line.setBorderPainted(true);
			} else if (temp.equals(rectangle)) {
				//rectangle.setBorderPainted(true);
				fill.setEnabled(true);
				stroke.setEnabled(true);
			} else if (temp.equals(eraser)) {
				//eraser.setBorderPainted(true);
			} else if (temp.equals(text)) {
				//text.setBorderPainted(true);
				TextEdit.main(null);
				stroke.setEnabled(false);
			} else if (temp.equals(zoomin)) {
				//zoomin.setBorderPainted(true);
				// ip.makeBigger();
			} else if (temp.equals(zoomout)) {
			//	zoomout.setBorderPainted(true);
			} else if (temp.equals(freestyle)) {
				fill.setEnabled(true);
				stroke.setEnabled(false);
				//freestyle.setBorderPainted(true);
				ScrollBarThickness.main(null);
			} else if (temp.equals(circle)) {
				stroke.setEnabled(true);
				fill.setEnabled(true);
			//	circle.setBorderPainted(true);
			} else if (temp.equals(stamps)) {
				//stamps.setBorderPainted(true);
				Stamps.main(null);
				fill.setEnabled(false);
				stroke.setEnabled(false);
			} else if (temp.equals(imageButton)) {
				//imageButton.setBorderPainted(true);
				selectedImage(); // open file choser
				fill.setEnabled(false);
				stroke.setEnabled(false);
			}
			else if(temp.equals(background)){
				fill.setEnabled(true);
				stroke.setEnabled(false);
			}
			
			for (Enumeration<AbstractButton> buttons = toolbox.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();

				if (!button.isSelected()) {
					button.setBorderPainted(false);
					button.setBackground(menuBg); //add to
				}else{
					UIManager.put("ToggleButton.select", selectedBg);//add to
					SwingUtilities.updateComponentTreeUI(button);//add to
				}
			}
		} else if (e.getSource() instanceof JMenuItem) {
			JMenuItem temp = (JMenuItem) e.getSource(); 
			
			if (temp.equals(newFile)) {
				
			} else if (temp.equals(open)) {
				
			} else if(temp.equals(save)) {
				Launch.saveCanvas();
			} else if (temp.equals(saveAs)) {
				Launch.savedBefore = false;
				Launch.saveCanvas();
			}
		}
		/* enable undo/redo if */
		if (c.shapes.isEmpty()) {
			undo.setEnabled(false);
		} else {
			undo.setEnabled(true);
		}
		if (c.undid.isEmpty()) {
			redo.setEnabled(false);
		} else {
			redo.setEnabled(true);
		}

		// repaintThis();

	}

	public void selectedImage() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	selectedFile = chooser.getSelectedFile(); //image file read in
	    	System.out.println("You chose to open this image file: " + chooser.getSelectedFile().getName());
			try {
				buffImg = ImageIO.read(selectedFile);
				
			} catch (IOException ioe) {
			}
	    }
	}
	
	public void toDraw(Graphics2D g, int oldX, int oldY, int currentX, int currentY, LinkedList<Integer> xCoord,
			LinkedList<Integer> yCoord) {
		this.graphic = g;
		graphic.setBackground(shapeLBG.getBackground());
		graphic.setPaint(shapeLFG.getBackground());

		int x = currentX;
		int y = currentY;
		int ox = oldX;
		int oy = oldY;

		System.out.println("\n X1: " + oldX + " " + currentX + " Y" + oldY + " " + currentY + " ");
		if (currentX < oldX) {
			currentX = oldX - x;
			oldX = x;
		} else {
			currentX = x - oldX;
		}
		if (currentY < oldY) {
			currentY = oldY - y;
			oldY = y;
		} else {
			currentY = y - oldY;
		}
		System.out.println("\n X2: " + oldX + " width: " + currentX + " Y" + oldY + " height:" + currentY + " ");

		if (this.line.isSelected()) {
			shape = new Line2D.Double(ox, oy, x, y);
			// graphic.draw(shape);
		} else if (this.circle.isSelected()) {
			shape = new Ellipse2D.Double(oldX, oldY, currentX, currentY);
			// graphic.fill(shape);
		} else if (this.rectangle.isSelected()) {
			shape = new Rectangle2D.Double(oldX, oldY, currentX, currentY);
			// graphic.draw(shape);
		} else if (this.freestyle.isSelected()) {
			// iterate through xCoord and yCoord to create the final line -- nah
			// to-do set graphic.setStroke with values from external input --
			// nah
			// to-do set graphic.setPaint with rgb button -- nah

			// ?if it is a shape we can add it into drawn if not we need to
			// store a new object type

		} else if (this.text.isSelected()) {
			tt = TextFrame.messageField.getText();
			textColor = TextFrame.messageField.getForeground();
			textFont = TextFrame.messageField.getFont();

		} else if (this.zoomin.isSelected()) {
			System.out.print("ZOOM");

		} else if (this.stamps.isSelected()) {
			imageStamp = StampsFrame.selectedStamp();
		}else if (this.imageButton.isSelected()) {
		}
		else if(this.background.isSelected()){
			shape = new Rectangle2D.Double(0, 0, Launch.canvas.getWidth(), Launch.canvas.getHeight());
		}

		
		
		/* ADDING SHAPE TO HISTORY LIST START*/
		
		if (this.circle.isSelected() || this.rectangle.isSelected() || this.line.isSelected()) {
			Drawn newShape = new Drawn(shape, graphic.getStroke(),  graphic.getColor(),graphic.getBackground(),
					fill.isSelected(), stroke.isSelected());
			c.addShape(newShape);
		}

		if (this.stamps.isSelected()) {
			ImageStore images = new ImageStore(imageStamp, oldX, oldY, 150, 150);
			c.addShape(images);
		}
		
		if (this.imageButton.isSelected()) {
			ImageStore imageImage = new ImageStore(buffImg, oldX, oldY, currentX, currentY);
			c.addShape(imageImage);
		}
		
		if (this.eraser.isSelected() || this.freestyle.isSelected()) {

			if (xCoord.isEmpty() || yCoord.isEmpty()) {
				System.out.println("no points");
			} else {
				if(this.eraser.isSelected()) graphic.setColor(Color.white);
				System.out.println("we out there freestyling");
				Path2D.Double path = new Path2D.Double();
				path.moveTo(xCoord.get(0), yCoord.get(0));
				System.out.println("moar points" + "size X: " + xCoord.size());
				for (int i = 0; i < xCoord.size() - 2; i += 2) {

					path.quadTo(xCoord.get(i), yCoord.get(i), xCoord.get(i + 1), yCoord.get(i + 1));
				}
				
				MultiplePoints points = new MultiplePoints(new Path2D.Double(path), graphic.getStroke(),
						graphic.getColor());
				c.addShape(points);
				path.reset();
				
			}
		}
		
		if(this.text.isSelected()){
			TextStore store = new TextStore(tt, oldX, oldY, textColor, textFont);
			c.addShape(store);
			//System.out.println(x);
			
		}
		
		if(this.background.isSelected()){
			Drawn newShape = new Drawn(shape, graphic.getStroke(),  graphic.getColor(),graphic.getBackground(),
					fill.isSelected(), stroke.isSelected());
			c.addShape(newShape);
			
		}
		/* ADDING SHAPE TO HISTORY LIST END*/

		// clear the point lists
		//xCoord.clear();
		//yCoord.clear();
		c.undid.clear();// empty the redo stack
		/* enable undo/redo if */
		if (c.shapes.isEmpty()) {
			undo.setEnabled(false);
		} else {
			undo.setEnabled(true);
		}
		if (c.undid.isEmpty()) {
			redo.setEnabled(false);
		} else {
			redo.setEnabled(true);
		}

	
	}

	/*public void isDragged(Boolean dragged) {
		this.dragged = dragged;
	}

	public void toDrawDragged(Graphics2D g, int oldX, int oldY, int currentX, int currentY) {

		this.graphic = g;
		graphic.setBackground(shapeLBG.getBackground());
		graphic.setPaint(shapeLFG.getBackground());

		if (this.freestyle.isSelected()) {
			shape = new Line2D.Double(oldX, oldY, currentX, currentY);
			// graphic.draw(shape);
		}

	}*/

	public void repaintThis() {
		/*
		 * Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); if
		 * (this.graphic != null) { graphic.setPaint(Color.white);
		 * graphic.fillRect(0, 0, dim.width, dim.height);
		 * graphic.setPaint(shapeLFG.getBackground());// set to current swatch
		 * // colour repaint();
		 * 
		 * for (Drawn s : c.shapes) { graphic.setPaint(s.fgColour());
		 * graphic.setStroke(s.getStroke()); graphic.setColor(s.bgColour());
		 * graphic.draw(s.getShape()); } graphic.draw(new
		 * Rectangle2D.Double(0,0,1,1)); repaint(); }
		 */
	}
	

}


