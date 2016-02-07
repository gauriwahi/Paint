import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;


public class Drawn {

	Shape shape;
	Color foreground, background;
	Stroke stroke;
	Boolean hasFill,hasStroke;
	
	public Drawn(Shape s, Stroke stroke, Color f, Color b,Boolean hasFill, Boolean hasStroke){
		shape = s;
		foreground = f;
		background = b;
		this.stroke = stroke;
		this.hasFill = hasFill;
		this.hasStroke = hasStroke;
	}
	
	public Shape getShape(){
		return shape;
	}
	public Color bgColour(){
		return background;
	}
	public Color fgColour(){
		return foreground;
	}
	public Stroke getStroke(){
		return this.stroke;
	}
}
