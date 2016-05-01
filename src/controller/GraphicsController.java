package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.Board;

/**
 * The GraphicsController class communicates with the user and
 * reacts to input by calling appropriate methods in the
 * model and view packages
 *
 */
public class GraphicsController implements MouseListener{	
	
	private view.UserInterface UI;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JButton submit;
	private JButton newGame;
	
	/**
	 * Instantiates a GraphicsController and configures the window
	 */
	public GraphicsController(){
		
		//Sets window height and width based on device graphics settings
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		int width = (int) (gd.getDefaultConfiguration().getBounds().getWidth()/2);
		int height = (int) (19*gd.getDefaultConfiguration().getBounds().getHeight()/20);
		
		new model.Board(4*width/5, height, true);
		
		view.Layout layout = new view.Layout(width, height);
		view.UserInterface UI = new view.UserInterface();
		UI.setLayout(layout);
		this.UI = UI;
		
		
		JButton red = new JButton();
		red.setBackground(Color.RED);
		red.setPreferredSize(new Dimension(25,25));
		red.addMouseListener(this);
		buttons.add(red);
		
		JButton blue = new JButton();
		blue.setBackground(Color.BLUE);
		blue.setPreferredSize(new Dimension(25,25));
		blue.addMouseListener(this);
		buttons.add(blue);
		
		JButton green = new JButton();
		green.setBackground(Color.GREEN);
		green.setPreferredSize(new Dimension(25,25));
		green.addMouseListener(this);
		buttons.add(green);
		
		JButton magenta = new JButton();
		magenta.setBackground(Color.MAGENTA);
		magenta.setPreferredSize(new Dimension(25,25));
		magenta.addMouseListener(this);
		buttons.add(magenta);
		
		JButton yellow = new JButton();
		yellow.setBackground(Color.YELLOW);
		yellow.setPreferredSize(new Dimension(25,25));
		yellow.addMouseListener(this);
		buttons.add(yellow);
		
		JButton black = new JButton();
		black.setBackground(Color.BLACK);
		black.setPreferredSize(new Dimension(25,25));
		black.addMouseListener(this);
		buttons.add(black);
		
		submit = new JButton();
		submit.setText("Check");
		submit.setPreferredSize(new Dimension(75,25));
		submit.addMouseListener(this);
		
		newGame = new JButton();
		newGame.setText("New Game");
		newGame.setPreferredSize(new Dimension(100,25));
		newGame.addMouseListener(this);
			
		this.UI.add(newGame,"NEW GAME");
		this.UI.add(red, "RED");
		this.UI.add(blue, "BLUE");
		this.UI.add(yellow, "YELLOW");
		this.UI.add(black, "BLACK");		
		this.UI.add(magenta, "MAGENTA");
		this.UI.add(green, "GREEN");	
		this.UI.add(submit, "GUESS");		
		
		
		//Creates new JFrame and sets state to visible
		JFrame window = new JFrame();
		window.setSize(width, height);
		window.add(this.UI);
		UI.addMouseListener(this);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setAlwaysOnTop(true);
	}

	
	/**
	 * Determines if mouse was clicked while overtop a node
	 * Triggers update of the user interface
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		/* 
		 * Map the x and y coordinate of the click to the node
		 * Ignore if there is no node
		 * Otherwise process the move
		 * 
		 * */
				
		if (buttons.contains(e.getSource())){
			model.Board.setColor(((JButton)e.getSource()).getBackground());
			return;
		}
		
		if (e.getSource() instanceof JButton && ((JButton)e.getSource()).getText().equals("New Game")){
			model.Board.actual().reset();
			submit.setVisible(true);
			UI.reset();
			UI.revalidate();
			UI.update();
			return;
		}
		
		if (e.getSource() instanceof JButton && ((JButton)e.getSource()).getText().equals("Check")){
			if (Board.actual().makeGuess()){
				UI.guessMade = true;
				Board.setColor(Color.LIGHT_GRAY);
			}			
			UI.revalidate();
			UI.update();
			UI.guessMade = false;
			
			if (Board.actual().guessNum() >= 12) {
				submit.setVisible(false);
				UI.revalidate();
				UI.update();
			}
			return;
		}
		
		Point clicked = new Point(e.getX(), e.getY());
		int position = -1;
		
		for (int x = model.Board.actual().guessNum() * 6; x < model.Board.actual().guessNum() * 6 + 6; x++){
			if (model.Board.getNode(x).distanceTo(clicked) < 25){
				position = x;	
				break;
			}
		}
		
		if (position == -1 || model.Board.getColor() == Color.LIGHT_GRAY) return;
		
		Color color = model.Board.getColor();
		model.Board.actual().addPiece(model.Board.getNode(position).getX(),model.Board.getNode(position).getY(), position, color); 
		UI.update();
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		e.consume();
	}	
}
