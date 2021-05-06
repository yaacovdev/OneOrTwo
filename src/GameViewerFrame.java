import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class GameViewerFrame extends JFrame {
	// the whole point is to identify if the mouse is in the ball field

	private final static String version = "alpha - 1.0.3";
	private final static String title = "One or Two!";
	private final int FRAME_LENGTH = 1200, FRAME_HEIGHT = 900;
	private int number_of_buttons = 20;
	private static ActionListener listener, turn_listener;
	private ArrayList<ButtonPanel> buttons;
	private JButton player1, player2;
	private JPanel player1panel, player2panel;
	private byte circles_left_p1, circles_left_p2;
	private byte winning_player;
	JLabel game_title;
	Color background;

	JPanel c_panel;
	JPanel n_panel;
	JPanel w_panel;
	JPanel e_panel;
	JPanel s_panel;

	/**
	*Constructor which sets up the whole game panels which include the title of the screen, then buttons to indicate the turn of the player.
	*The bottom circles are circles with connected hidden buttons that become disabled when the button is selected by the player.
	*
	*/
	public GameViewerFrame() {

		super(title + " " + version);
		background = Color.BLACK;
		circles_left_p1 = 2;
		circles_left_p2 = 0;
		this.setSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		this.setLayout(new BorderLayout());


		c_panel = new JPanel();

		n_panel = new JPanel();
		w_panel = new JPanel();
		e_panel = new JPanel();
		s_panel = new JPanel();
		c_panel.setBackground(background);
		n_panel.setBackground(background);
		w_panel.setBackground(background);
		e_panel.setBackground(background);
		s_panel.setBackground(background);

		w_panel.add(instruct());

		buttons = new ArrayList<>();

		//the two classes work together to switch the button turns
		
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				selectButton();
				turnOver(false);
			}
		}

		class TurnListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				turnOver(true);
			}
		}

		winning_player = 1;

		listener = new ButtonListener();
		turn_listener = new TurnListener();

		makeTitledSpace();
		makeTurnoverButtons();
		
		allocButtonPanels();
		buttonListeners();
		allocGameSpace();

		c_panel.setLayout(new GridLayout(5,4));
		
		
		n_panel.setPreferredSize(new Dimension(0,200));
		w_panel.setPreferredSize(new Dimension(350,0));
		e_panel.setPreferredSize(new Dimension(50,0));
		s_panel.setPreferredSize(new Dimension(0,50));
		
		
		
		add(c_panel, BorderLayout.CENTER);
		add(n_panel, BorderLayout.NORTH);
		add(w_panel, BorderLayout.WEST);
		add(e_panel, BorderLayout.EAST);
		add(s_panel, BorderLayout.SOUTH);
	}

	//instructions
	private JLabel instruct()
	{
		JLabel text = new JLabel("<html><p>Instructions:<br/>Press any of the top 2 buttons when you want to <br/>end turn. If you select 2 buttons the turn will<br/> automatically skip. Whoever selects the white<br/>button looses.<p/><html/>", SwingConstants.CENTER);
		text.setForeground(Color.WHITE);
		text.setFont(new Font("Arial", Font.PLAIN, 15));
		return text;
	}

	//game over
	private void gameOver()
	{
		game_title.setText("Game Over " + getWinner());
		//game_title.setHorizontalAlignment(SwingConstants.CENTER);
		clearAllButtons();
		clearSelectButtons();
	}
	
	private void clearSelectButtons()
	{
		player1panel.setBackground(Color.BLACK);
		player2panel.setBackground(Color.BLACK);
		player1panel.removeAll();
		player2panel.removeAll();
		player1panel.updateUI();
		player2panel.updateUI();
	}

	private String getWinner()
	{
		System.out.println("winning player" + winning_player);
		if(winning_player == 1) return "Player 2 Wins!";
		else return "Player 1 Wins!";
	}

	private void clearAllButtons()
	{
		
		for(int i = buttons.size()-1; i > -1; i--)
		{
			buttons.get(i).rep();
		}
		buttons.clear();

		
	}

	//turn over
	private void turnOver(boolean button_pressed)
	{
		if(button_pressed)
		{
			if(circles_left_p1 == 1)
			{
				circles_left_p1 = 0;
				circles_left_p2 = 2;
				changeSelectColor();
				
				winning_player = 0;
				
			}
			else if(circles_left_p2 == 1)
			{
				circles_left_p2 = 0;
				circles_left_p1 = 2;
				changeSelectColor();
				winning_player = 1;
			}
			return;
		}

		if(circles_left_p1 > 0) circles_left_p1--;
		if(circles_left_p2 > 0) circles_left_p2--;

		
		if(circles_left_p1 != 0 || circles_left_p2 != 0)
		{
			return;
		}

		if(winning_player == 1)
			{
				circles_left_p1 = 0;
				circles_left_p2 = 2;
				changeSelectColor();
				winning_player = 0;
			}
			else
			{
				circles_left_p2 = 0;
				circles_left_p1 = 2;
				changeSelectColor();
				winning_player = 1;
			}

	}

	private void changeSelectColor()
	{
		if(player1panel.getBackground().equals(Color.RED))
		{
			player1panel.setBackground(background);
			player2panel.setBackground(Color.RED);
		}
		else
		{
			player1panel.setBackground(Color.RED);
			player2panel.setBackground(background);
		}
		
	}

	//top button design
	private void makeTitledSpace()
	{
		n_panel.setLayout(new GridBagLayout());
		game_title = new JLabel(title, SwingConstants.CENTER);
		//game_title.setOpaque(true);
		
		game_title.setForeground(Color.WHITE);
		
		game_title.setFont(new Font("Ariel", Font.PLAIN, 40));
		
		//game_title
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		game_title.setPreferredSize(new Dimension(675,100));
		n_panel.add(game_title, c);
	}

	private void makeTurnoverButtons()
	{
		player1panel = new JPanel();
		player2panel = new JPanel();
		player1panel.setLayout(null);
		player2panel.setLayout(null);
		player1 = new JButton("Player 1");
		player2 = new JButton("Player 2");
		player1.addActionListener(turn_listener);
		player2.addActionListener(turn_listener);
		
		
		
		player1panel.setBackground(Color.RED);
		player2panel.setBackground(background);

		player1panel.setPreferredSize(new Dimension(200,80));
		player2panel.setPreferredSize(new Dimension(200,80));
		
		player1.setPreferredSize(new Dimension(150,50));
		player2.setPreferredSize(new Dimension(150,50));

		Insets insets1 = player1panel.getInsets();
		Dimension size1 = player1.getPreferredSize();

		player1.setBounds(25 + insets1.left, 15 + insets1.top,
		size1.width + 0, size1.height + 0);

		Insets insets2 = player2panel.getInsets();
		Dimension size2 = player2.getPreferredSize();

		player2.setBounds(25 + insets2.left, 15 + insets2.top,
		size2.width + 0, size2.height + 0);
		
		player1panel.add(player1);
		player2panel.add(player2);


		JPanel bottom_north = new JPanel();
		bottom_north.setLayout(new FlowLayout());
		bottom_north.setBackground(background);
		bottom_north.add(player1panel);
		bottom_north.add(player2panel);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 1;
		n_panel.add(bottom_north, c);


	}



	//methods regarding center pannel
	private void allocButtonPanels()
	{

		for(int i = 0; i < number_of_buttons; i++)
		{
			buttons.add(new ButtonPanel());
		}
	}

	private void buttonListeners()
	{
		for(int i = 0; i < number_of_buttons; i++)
		{
			buttons.get(i).addListener(listener);
			
		}
		Random randbutton = new Random();
		int select = Math.abs(randbutton.nextInt() % number_of_buttons);
		
		buttons.get(select).setLoosing(true);
	}

	private void allocGameSpace()
	{
		//set each button white
		for(int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).setBackground(background);
		}
		for(int i = 0; i < buttons.size(); i++)
		{
			c_panel.add(buttons.get(i));
		}
	}


	private void selectButton()
	{
		
		for(int i = 0; i < buttons.size(); i++)
		{
			
			if(buttons.get(i).armed()){
				buttons.get(i).rep();
				if(buttons.get(i).isLoosing())
				{
					gameOver();
			
				}
				
			}	
		}
	}
}





