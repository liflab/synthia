package examples.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A simple calculator. The code for this window is an almost direct copy-paste
 * from the <a href="https://javacodex.com/Swing/Calculator">Java Codex</a>
 * example.
 * <p>
 * <img src="{@docRoot}/doc-files/gui/Calculator.png" alt="Calculator window" />
 */
public class CalculatorWindow extends JFrame
{
	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;
	
	protected Map<String,JButton> m_buttons;
	
	public CalculatorWindow()
	{
		super();
		m_buttons = new HashMap<String,JButton>();
		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle("Calculator");
    setSize(200, 200);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    Container contentPane = getContentPane();
    contentPane.add(new CalculatorPanel());
    show();
	}
	
	public JButton getButton(String label)
	{
		return m_buttons.get(label);
	}
	
	public class CalculatorPanel extends JPanel implements ActionListener {
		 
	  /**
		 * Dummy UID.
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextField display = new JTextField("0");
	  private double result = 0;
	  private String operator = "=";
	  private boolean calculating = true;
	 
	  public CalculatorPanel() {
	    setLayout(new BorderLayout());
	 
	    display.setEditable(false);
	    add(display, "North");
	 
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(4, 4));
	 
	    String buttonLabels = "789/456*123-0.=+";
	    for (int i = 0; i < buttonLabels.length(); i++) {
	    	String label = buttonLabels.substring(i, i + 1);
	      JButton b = new JButton(label);
	      m_buttons.put(label, b);
	      panel.add(b);
	      b.addActionListener(this);
	    }
	    add(panel, "Center");
	  }
	 
	  public void actionPerformed(ActionEvent evt) {
	    String cmd = evt.getActionCommand();
	    if ('0' <= cmd.charAt(0) && cmd.charAt(0) <= '9' || cmd.equals(".")) {
	      if (calculating)
	        display.setText(cmd);
	      else
	        display.setText(display.getText() + cmd);
	      calculating = false;
	    } else {
	      if (calculating) {
	        if (cmd.equals("-")) {
	          display.setText(cmd);
	          calculating = false;
	        } else
	          operator = cmd;
	      } else {
	        double x = Double.parseDouble(display.getText());
	        calculate(x);
	        operator = cmd;
	        calculating = true;
	      }
	    }
	  }
	 
	  private void calculate(double n) {
	    if (operator.equals("+"))
	      result += n;
	    else if (operator.equals("-"))
	      result -= n;
	    else if (operator.equals("*"))
	      result *= n;
	    else if (operator.equals("/"))
	      result /= n;
	    else if (operator.equals("="))
	      result = n;
	    display.setText("" + result);
	  }
	}
}
