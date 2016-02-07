
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.event.*;

public class A2 {

	// the main class
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception e) {
			e.printStackTrace();
		}
		Launch frame = new Launch();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("RGG Paint");
		frame.pack();

		// put the frame in the middle of the display
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		// frame.setVisible(true);
	}
}

class Launch extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	private int currentX, currentY, oldX, oldY;

	// fields
	JButton pencil, rectangle, clear;
	static Canvas canvas;

	static boolean savedBefore;
	Shape shape;

	// mouseListeners
	MouseListener pencilL, rectangleL;
	ImagePanel ip;
	JScrollPane sp;

	// constructor
	public Launch() {

		// buttons
		pencil = new JButton("Pencil");
		rectangle = new JButton("Rectangle");
		clear = new JButton("clear");
		// then add the controls on the top
		MenuBar menu = new MenuBar();

		canvas = new Canvas(menu);

		// make the frame
		JFrame frame = new JFrame("RGG Paint");
		ImageIcon appicon = new ImageIcon("resources//appicon.png");
		frame.setIconImage(appicon.getImage());
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());

		// add things to content
		content.add(canvas, BorderLayout.CENTER);

		frame.setJMenuBar(menu);
		menu.addMouseListener(this);
		canvas.addMouseListener(this);// listens for interaction on the canvas
		canvas.addMouseMotionListener(this);

		// scrollbar
		//we need to use the makebiggerfunction in imagepanel to zoom into the canvas (it takes an image now, but 
		//try adding it for canvas) -- GAURI
		sp = new JScrollPane();
		//sp.setViewportView(canvas); -- --- uncomment this later if you like roro
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new JButton());

		
		//content.add(sp, BorderLayout.CENTER); ---- tester

		content.add(MenuBar.getToolBar(), BorderLayout.WEST);
		frame.setSize(1000, 1000);
		if(MenuBar.newFile.isSelected()){
			//checks if there is something drawn
			if(menu.c.shapes.size() > 0){
				
				JOptionPane saveDialog = new JOptionPane();
				JOptionPane.showConfirmDialog(saveDialog, "Would you like to save?");
				
				
			}
			else{
				//canvas.clear();
			}
		}
		
		
		//
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	// EVENT LISTENERS
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		// int x = e.getX();
		// int y = e.getY();

		System.out.println("hihihihihi");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		oldX = e.getX();
		oldY = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// canvas.pencil();


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
	public void actionPerformed(ActionEvent e) {
		/*
		 * // TODO Auto-generated method stub Object source = e.getSource();
		 * if(source == pencil){ canvas.pencil(); } else if(source == clear){
		 * canvas.clear(); } else if(source == rectangle){
		 * canvas.removeMouseListener(pencilL); canvas.rectangle();
		 * canvas.setBackground(Color.black);
		 * 
		 * }
		 */
		// canvas.pencil();

	}

	// inner class
	// Canvas Class
	class Canvas extends JComponent implements MouseMotionListener, MouseListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image image;
		private Graphics2D graphic, graphicRec;
		private int currentX, currentY, oldX, oldY;
		private Shape shape;

		// for rectangle
		private int currentRecX, currentRecY, oldRecX, oldRecY;
		Point startDrag, endDrag;
		MenuBar menu;
		Shape s;
		int moveX = 0;
		int moveY = 0;
		LinkedList<Integer> dragX = new LinkedList();
		LinkedList<Integer> dragY = new LinkedList();
		AffineTransform transformer;
		private Image img;

		// constructor for Canvas
		public Canvas(MenuBar menu) {
			this.menu = menu;
			setDoubleBuffered(false);
			addMouseMotionListener(this);
			addMouseListener(this);

		}

		protected void paintComponent(Graphics g) {
			if (image == null) {
				// image to draw null ==> we create
				image = createImage(getSize().width, getSize().height);
				// image = menu.image;
				graphic = (Graphics2D) image.getGraphics();

				// enable antialiasing
				graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				Image img = menu.image;
				// clear draw area
				clear();
			}
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			g.drawImage(image, 0, 0, null);
			graphic.setPaint(Color.white);
			graphic.fillRect(0, 0, dim.width, dim.height);
			//adding
			transformer = new AffineTransform();
			transformer.scale(menu.zoom,menu.zoom);
			graphic.setTransform(transformer);
			canvas.setSize((int)(500*menu.zoom), (int)(500*menu.zoom));
			
			for (Object s : menu.c.shapes) {

				if (s.getClass() == Drawn.class) {
					// add a flag for []fill []stroke
					/* with strokes */
					if (((Drawn) s).hasFill && ((Drawn) s).hasStroke) {
						graphic.setPaint(((Drawn) s).fgColour());
						graphic.draw(((Drawn) s).getShape());
						graphic.setPaint(((Drawn) s).bgColour());
						graphic.fill(((Drawn) s).getShape());
						/* with fill */
					} else if (((Drawn) s).hasStroke) {
						graphic.setPaint(((Drawn) s).fgColour());
						graphic.draw(((Drawn) s).getShape());
					} else if (((Drawn) s).hasFill) {
						graphic.setPaint(((Drawn) s).bgColour());
						graphic.fill(((Drawn) s).getShape());
					} else {

					}
				} else if (s.getClass() == ImageStore.class) {
					// add flag for posiiton or some crap
					graphic.drawImage(((ImageStore) s).getImage(), ((ImageStore) s).xPos, ((ImageStore) s).yPos,
							((ImageStore) s).width, ((ImageStore) s).height, null);
				} else if (s.getClass() == MultiplePoints.class) {
					// check if eraser is selected or if this is free style
					graphic.setStroke(((MultiplePoints) s).getStroke());
					graphic.setColor(((MultiplePoints) s).getColour());
					graphic.setPaint(((MultiplePoints) s).getColour());
					// iterate through the points for the sketched line and
					// draw the connecting lines
					graphic.draw(((MultiplePoints) s).getPath());

				}
				else if(s.getClass() == TextStore.class){
					graphic.setColor(((TextStore)s).color);
					graphic.drawString(((TextStore)s).text, ((TextStore)s).xPos, ((TextStore)s).yPos);
					graphic.setFont(((TextStore)s).font);
					
				}
				else if(s.getClass() == BackgroundStore.class){
					graphic.setPaint(((Drawn) s).bgColour());
					graphic.fill(((Drawn) s).getShape());
				}
				else {

				}
			}
			// if x,y drag linkedlists aren't empty then printout current points
			// shows on screen stuff
			if (dragX.size() > 1 && (menu.fill.isSelected()||menu.stroke.isSelected())) {
				int x = dragX.getLast();
				int currentXX = dragX.getLast();
				int y = dragY.getLast();
				int currentYY = dragY.getLast();
				int ox = dragX.getFirst();
				int oldXX = dragX.getFirst();
				int oy = dragY.getFirst();
				int oldYY = dragY.getFirst();

				if (currentXX < oldXX) {
					currentXX = oldXX - x;
					oldXX = x;
				} else {
					currentXX = x - oldXX;
				}
				if (currentYY < oldYY) {
					currentYY = oldYY - y;
					oldYY = y;
				} else {
					currentYY = y - oldYY;
				}

				// drawing the interative

				Shape temp = new Line2D.Double(ox, oy, x, y);

				if (menu.freestyle.isSelected() || menu.eraser.isSelected()) {
					// drawing happens in next if statement

				} else if (menu.rectangle.isSelected()) {
					temp = new Rectangle2D.Double(oldXX, oldYY, currentXX, currentYY);
				} else if (menu.circle.isSelected()) {
					temp = new Ellipse2D.Double(oldXX, oldYY, currentXX, currentYY);
				} else if (menu.line.isSelected()) {
					temp = new Line2D.Double(ox, oy, x, y);
				} else {

				}

				/* real time stamps */
				if (menu.stamps.isSelected()) {
					graphic.drawImage(menu.imageStamp, oldXX, oldYY, currentXX, currentYY, null);
				}
				
				else if(menu.background.isSelected()){
					temp = new Rectangle2D.Double(0, 0, getSize().getWidth(), getSize().getHeight());
				}
				
				else if (menu.freestyle.isSelected()) {
					graphic.setPaint(MenuBar.shapeLFG.getBackground());// set to
																	// stroke
																	// colour
					Path2D.Double path = new Path2D.Double();
					path.moveTo(dragX.get(0), dragY.get(0));
					graphic.setPaint(MenuBar.shapeLFG.getBackground());
					// path.setWindingRule(path.WIND_EVEN_ODD);
					/*graphic.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10,
							new float[] { 4, 2 }, 0));*/
					for (int i = 0; i < dragX.size() - 2; i += 2) {

						path.quadTo(dragX.get(i), dragY.get(i), dragX.get(i + 1), dragY.get(i + 1));
					}
					// System.out.println("the end is near");
					graphic.draw(path);
					path.reset();

				} else if (menu.eraser.isSelected()) {
					graphic.setPaint(Color.white);// set to stroke colour
					graphic.setColor(Color.white);// set to stroke colour

					Path2D.Double path = new Path2D.Double();
					path.moveTo(dragX.get(0), dragY.get(0));
					// path.setWindingRule(path.WIND_EVEN_ODD);
				
					for (int i = 0; i < dragX.size() - 2; i += 2) {

						path.quadTo(dragX.get(i), dragY.get(i), dragX.get(i + 1), dragY.get(i + 1));
					}
					// System.out.println("the end is near");
					graphic.draw(path);
					path.reset();

				} else {
					/* real time shapes */
					/* with strokes */
					if (menu.stroke.isSelected() && menu.fill.isSelected()) {
						graphic.setPaint(MenuBar.shapeLFG.getBackground());
						graphic.draw(temp);
						graphic.setPaint(MenuBar.shapeLBG.getBackground());
						graphic.fill(temp);
						/* with fill */
					} else if (menu.stroke.isSelected()) {
						graphic.setPaint(MenuBar.shapeLFG.getBackground());
						graphic.draw(temp);
					} else if (menu.stroke.isSelected()) {
						graphic.setPaint(MenuBar.shapeLBG.getBackground());
						graphic.fill(temp);
					} else {

					}
				}

			} // end of drawing on screen realtime

			repaint();

		}

		public void clear() {

			graphic.setPaint(Color.white);
			graphic.fillRect(0, 0, getSize().width, getSize().height);
			// graphic.drawImage(menu.image,x, y, this);
			// graphic.fillRect(x, y, img.getWidth(this), img.getHeight(this));
			graphic.setPaint(Color.black);
			// repaint();

			// for rectangle as well
			// graphicRec.setPaint(Color.white);

			// graphicRec.fillRect(0, 0, getSize().width, getSize().height);
			// graphicRec.setPaint(Color.black);
			// repaint();
		}

		public void pencil(int oldX, int oldY, int currentX, int currentY) {
			menu.toDraw(graphic, oldX, oldY, currentX, currentY, dragX, dragY);
			dragX.clear();
			dragY.clear();
			// menu.repaintThis();
			// repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("canvasaction");
			// store the points

			dragX.add(e.getX());
			dragY.add(e.getY());
			// pencil(0, 0, e.getX(), e.getY());
			// System.out.println("\n hi i was moveddd");

			// when released send the points into drag function which checks for
			// state of buttons
		}

		public void mouseReleased(MouseEvent e) {
			
			currentX = e.getX();
			currentY = e.getY();
			if ((menu.fill.isSelected() || menu.stroke.isSelected())&&(dragX.size()>1)&&!menu.stamps.isSelected()) {
				// System.out.print("Naruto relese");
				canvas.pencil(dragX.getFirst(), dragY.getFirst(), dragX.getLast(), dragY.getLast());
				// System.out.println("release canvas");
			}else{
				canvas.pencil(currentX,currentY, currentX, currentY);
			}
			dragX.clear();
			dragY.clear();
			// repaint();
			oldX = currentX;
			oldY = currentY;

			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			moveX = e.getX();
			moveY = e.getY();

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	public static void saveCanvas() {
		
		String path = "";
		String filename;
		String userSelName = null;
		
		
		
		
		BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		
		
	
		
		canvas.paint(g);
		
		g.dispose();
		
		//START get path of where to save
		JFileChooser dirchooser = new JFileChooser();

		if(!savedBefore) { 
			//dirchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int returnVal = dirchooser.showSaveDialog(null); //should be Launch.this but cant since its static
		    savedBefore = true;
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		        try {		        	
		        	String x = dirchooser.getSelectedFile().getAbsolutePath();
		        	userSelName = dirchooser.getSelectedFile().getName();
		        } catch (Exception ex) {
		            System.out.println("there was an error selecting dir to save");
		        }
		    }
			//END get path of where to save
		}

		try {
			System.out.println("the dir to save:  " + dirchooser.getCurrentDirectory());
			File temp = dirchooser.getCurrentDirectory();
			String temp2 = temp.getAbsolutePath();
			path = (new File(/*STRING OF WHERE TO SAVE FILE */ temp2).getCanonicalPath());
			
			//need this to replace existing file with new one
		//	if (temp.exists()) {
		//		Path pathtodel = FileSystems.getDefault().getPath(temp2, userSelName);
		//		Files.delete(pathtodel);
		//	}
			//^
		
			filename = path + File.separator + userSelName + ".jpg";

			ImageIO.write(image, "jpg", new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("dragged in the launch");

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	// remove this class if scroll panel doesn't work
	public class ImagePanel extends JPanel {
		static final long serialVersionUID = 42L;

		// fields
		Image img;
		Canvas canvas;
		private int x;
		private int y;
		private int width;
		private int height;
		private int xOffset;
		private int yOffset;

		final int INC = 10;

		// constructor
		public ImagePanel(Image img) {
			//canvas = canvas;
			this.img = img;
			width = img.getWidth(this);
			height = img.getHeight(this);
			this.setBackground(Color.white);
			xOffset = 0;
			yOffset = 0;

		}

		// methods
		public void paintComponent(Graphics g) {
			super.paintComponent(g); // paint background
			x = this.getWidth() / 2 - width / 2 + xOffset / 2;
			y = this.getHeight() / 2 - height / 2 + yOffset / 2;
			//g.drawImage(img, x, y, width, height, this);
			//g.create(x, y, width, height)
		}

		public void makeBigger() {
			width = width + INC;
			height = height + INC;
			ip.setPreferredSize(new Dimension(width, height));
			this.repaint();
		}

		public void makeSmaller() {
			width = width - INC;
			height = height - INC;
			ip.setPreferredSize(new Dimension(width, height));
			this.repaint();
		}
	}
}
