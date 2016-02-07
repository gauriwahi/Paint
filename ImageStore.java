import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageStore {
	Image image;
	int xPos, yPos = 0;
	int width,height = 0;
	
	public ImageStore(Image image, int x, int y, int width, int height){
		this.image = image;
		xPos = x;
		yPos = y;
		this.width = width;
		this.height = height;
	}
	
	public Image getImage(){
		return image;
		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	
}
