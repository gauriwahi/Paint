# Paint
Matharu, Gary
Wahi, Gauri
Bandzar, Robin


------------------------------
    SPECIAL REQUIREMENTS
------------------------------
N/A


------------------------------
	   DESIGN
------------------------------
We have used Java Swing to create the Canvas, the shapes, and the stamps. The canvas has a white background with fixed dimensions. We used one class to join everything together. 
Stamps has a 4 by 4 menu where the user can select a stamp, and drag and draw the image onto the canvas. 
Shapes can either be filled, or can only have a border. 
Images have to be dragged and drawn onto the canvas, instead importing.
Tooltips to inform the user what an icon is meant to do. 

The A2 class contains the canvas, and the drawing methods, so the items can be pasted onto the canvas. (This was done with java Graphics).

For undo and redo we used a linked list to store Object type as we are drawing multiple types of imagery. We created classes which encapsulated features like x coordinate, y coordinate and colour so that all objects can be redrawn in the repaint call. The redo stack is wiped when an interaction with the canvas happens because the undo and redo history work in a linear manner. Undo/redo cases do not branch out in a tree format because that is not what users are accustomed to.

The overall structure of our application was an attempt at the Command design pattern. The main classes MenuBar and A2 both depend on the Command Class and the instances that it stores. 

------------------------------
    ADDITIONAL FEATURES
------------------------------
- Stamps - the user is allowed to select a stamp from a menu, and use them anywhere to draw
- Images - instead of importing one image, and resizing it from other features, the user is allowed to drag and draw the image
- Shapes - the user is allowed to drag and draw shapes, and decide whether to fill or just use a border
- We have two coloured boxes on the main screen which signify the colour used to fill, and the colour used as a border
- Icon on the corner to define our paint program
- zoom in/out is added by scaling the graphic2D instance by a factor inputed through button presses from the Zoom in and out buttons.
- Colour Swatches for both stroke and fill — By using the JColourchooser users can select from a large selection of colours and by making it a popup instead of having it visible in the application we keep the visual clutter to a minimum.
— Drawings are shown realtime to the user so that they know what shape they are potentially creating before committing. 
— JToolBar — allows the user to customize the way they prefer the layout to be for their drawing workspace.
------------------------------
       MEETING TIMES
—-----------------------------

October 27th, 2015
Briefly began discussing the assignment. Brainstormed ideas about what features our program should have.

November 4th, 2015 - November 20, 2015
Started adding elements, and separating them into classes, such as Canvas, MenuBar, etc.

November 21, 2015- November 26, 2015
Began adding colour swatches, and fixing/cleaning up the code so pencil and shapes work together.

November 27th, 2015 - December 4th, 2015
Adding the final touches to the program, cleaning up the code, and debugging

----------------------------
Responsibilities
----------------------------

Robin: Graphics for pencil and applying the thickness, shapes, icons, contributed with the layout and other functions as well, including layout. Designed the final theme, and describing the functionality of the program along with figuring out a sensible and logical layout. The overall coding of the program, and how it should be done. 

Gary: Movable Toolbar, Stamps, Stamps panel, Save/Open/Add image function, contribution to other functions as well, including layout. 

Gauri: Making a drawable canvas, contribution to Stamps, Text, scrollbar for thickness, Background, and contribution to layout, and describing the program, and helping with the layout, and other functions. 

—------------------------
Communication
-------------------------

Most of the communication was done through our Facebook and WhatsApp group where we discussed and posted our problems, and our different versions of the code. 
During our free periods we would meet and work together on different elements of the code, and then combine it once everything was done. 
The Facebook group was mostly used to post our work, and the images required, whereas the whatsapp group was to post videos especially if we were working separately.



