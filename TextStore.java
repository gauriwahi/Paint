import java.awt.Color;
import java.awt.Font;

public class TextStore {
	String text;
	Color color;
	Font font;
	int xPos, yPos = 0;
	int width, height = 0;
	
	public TextStore(String text, int xPos, int yPos, Color color, Font font){
		this.text = text;
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.color = color;
		this.font = font;
		
		
	}
	
	//methods
	public String getText(){
		return this.text;
	}
	
	
}
