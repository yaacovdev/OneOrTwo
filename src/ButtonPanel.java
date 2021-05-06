import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

class ButtonPanel extends JPanel {

    private int x_coord, y_coord;
    private static final double circle_size = 30;
    private static final short button_size = 31;
    private boolean set, dissapear_button;

    protected static int buttons_pressed = 0;

    private JToggleButton button;
    private Ellipse2D.Double circle;
    Graphics g;

    public ButtonPanel() {
        super();
        set = false;
        dissapear_button = false;
        this.setLayout(null);
        button = new JToggleButton();
    }

    public void rep() {
        dissapear_button = true;
        repaint();
        buttons_pressed++;
    }

    public boolean armed() {
        return button.getModel().isArmed();
    }

    public void addListener(ActionListener listener)
	{
		
		button.addActionListener(listener);
	}

	public void setLoosing(boolean set)
	{
		this.set = set;
	}

	public boolean isLoosing()
	{
		return set;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g = g;
		
		Graphics2D g2 = (Graphics2D) this.g;
		
		
		if(!set)
		{
			g2.setColor(Color.RED);
		}
		else
		{
			g2.setColor(Color.WHITE);
		}


		x_coord = (int)(this.getBounds().getWidth()/3);
		y_coord = (int)(this.getBounds().getHeight()/3);	

		//draw the full ones of draw nothing depending on whether the buttons been selected
	
		if(!dissapear_button)
			circle = new Ellipse2D.Double(x_coord, y_coord, circle_size,circle_size);
		else
		{
            circle = new Ellipse2D.Double(0,0,0,0);
            g2.setColor(Color.black);
			button.setEnabled(false);
		}

		g2.draw(circle);
		g2.fill(circle);


		button.setPreferredSize(new Dimension(button_size,button_size));
		Insets insets = this.getInsets();
		Dimension size = button.getPreferredSize();
		button.setBounds(x_coord+insets.left, y_coord + insets.top, size.width, size.height);
		
		//important to leave at false to let the circle never dissapear
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);

		
		this.add(button);
		
		//g.drawRect(10, 10, 100, 100);
	}
}