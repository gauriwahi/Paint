import java.awt.Color;
import java.awt.Shape;
import java.util.LinkedList;
import java.util.Stack;


public class Command {
	LinkedList<Object> shapes = new LinkedList<Object>();
	Stack<Object> undid = new Stack();
	Object curr;
	
	public Command(){
		
	}
	
	//store commands
	public void addShape(Object s ){
		shapes.add(s);
		System.out.print("added");
	}
	
	//undo draw graphic
	//moves shape onto the undo stack called undid
	public void undo(){
		if(!shapes.isEmpty()){
			curr=shapes.removeLast();
			undid.add(curr);
			System.out.println("undooo");
		}
	}
	
	
	public void redo(){
		if(!undid.isEmpty()){
			shapes.add(undid.pop());
			System.out.println("reddooo");
		}
	}
}
