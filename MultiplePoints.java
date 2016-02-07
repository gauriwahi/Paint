import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.LinkedList;

public class MultiplePoints {


	Stroke s;
	Color c;
	Path2D.Double path;
	
	public MultiplePoints(Path2D.Double path, Stroke s, Color c){
		
		this.path = path;
		this.s = s;
		this.c = c;
	}
	
	/*public LinkedList<Integer>getX(){
		return this.x;
	}
	public LinkedList<Integer>getY(){
		return this.y;
	}*/
	public Path2D getPath(){
		return this.path;
	}
	public Color getColour(){
		return this.c;
	}
	public Stroke getStroke(){
		return this.s;
	}
}
