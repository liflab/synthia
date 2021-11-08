/*
    Synthia, a data structure generator
    Copyright (C) 2019-2021 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package examples.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.uqac.lif.synthia.Resettable;
import ca.uqac.lif.synthia.test.Monkey;

/**
 * A simple calculator. The code for this window is an adaptation of the
 * <a href="https://javacodex.com/Swing/Calculator">Java Codex</a>
 * example.
 * <p>
 * <img src="{@docRoot}/doc-files/gui/Calculator.png" alt="Calculator window" />
 * <p>
 * The original code on Java Codex is interesting since it contains a genuine
 * fault that can be detected using the {@link Monkey}: clicking on
 * <kbd>&#x2212;</kbd> followed by <kbd>=</kbd> throws a
 * {@link NumberFormatException}. This error is not intentional and corresponds
 * to a case that the source code does not properly take into account.
 * For the purpose of the example, this fault can be disabled from the
 * code by calling {@link #disableNumberFormatException()}.
 * <p>
 * The calculator has been modified so that other exceptions can be thrown:
 * <ul>
 * <li>Dividing by zero throws an {@link IllegalArgumentException}, which is
 * expected</li>
 * <li>Any result producing a value greater than 100,000 produces an
 * {@link OverflowException}. The calculator can obviously manipulate larger
 * values; this limitation is artificial. It is enabled by calling 
 * {@link #hasOverflow()}.</li>
 * <li>If {@link #checkSyntax()} is called, the calculator rejects sequences
 * of buttons that correspond to a syntactically invalid expression. For
 * example, <tt>2.5.4&div;3</tt> is invalid (two decimal periods in the
 * first operand), as is <tt>+3=</tt> (missing first operand) or
 * <tt>2+5&minus;+</tt> (two successive operators without an operand in
 * between).</li>
 * </ul>
 * @ingroup Examples
 */
public class Calculator extends JFrame implements Resettable
{
	/**
	 * The array of button labels.
	 */
	public static final String[] BUTTON_LABELS = {"7", "8", "9", "\u00f7", 
			"4", "5", "6", "\u00d7", "1", "2", "3", "\u2212", "0", ".", "=", "+"};

	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A map associating each button to its label.
	 */
	protected Map<String,JButton> m_buttons;

	/**
	 * The calculator panel contained within the frame.
	 */
	protected CalculatorPanel m_panel;

	/**
	 * A flag specifying if the calculator checks the format of numbers.
	 */
	protected boolean m_checkFormat;
	
	/**
	 * A flag specifying if the calculator checks the syntax of operations.
	 */
	protected boolean m_checkSyntax;

	/**
	 * A flag specifying if the calculator produces an overflow exception.
	 */
	protected boolean m_hasOverflow;

	/**
	 * Creates a new calculator window.
	 */
	public Calculator()
	{
		super();
		m_buttons = new HashMap<String,JButton>();
		setTitle("Calculator");
		setSize(200, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = getContentPane();
		m_panel = new CalculatorPanel();
		contentPane.add(m_panel);
		m_checkFormat = false;
		m_hasOverflow = false;
		m_checkSyntax = false;
	}

	/**
	 * Instructs the calculator to throw an {@link OverflowException} when
	 * producing a number over 100,000.
	 * @return This calculator
	 */
	public Calculator hasOverflow()
	{
		m_hasOverflow = true;
		return this;
	}

	/**
	 * Instructs the calculator to check the format of numbers and ignore
	 * parsing errors.
	 * @return This calculator
	 */
	public Calculator disableNumberFormatException()
	{
		m_checkFormat = true;
		return this;
	}
	
	/**
	 * Instructs the calculator to check the syntax of operations.
	 * @return This calculator
	 */
	public Calculator checkSyntax()
	{
		m_checkSyntax = true;
		return this;
	}


	/**
	 * Gets the button instance with given label.
	 * @param label The label
	 * @return The button instance
	 */
	public JButton getButton(String label)
	{
		return m_buttons.get(label);
	}

	@Override
	public void reset()
	{
		m_panel.reset();
	}

	/**
	 * A main method allowing the calculator to be run as a stand-alone
	 * application.
	 * @param args Command-line arguments
	 */
	public static void main(String[] args)
	{
		Calculator c = new Calculator();
		for (String s : args)
		{
			if (s.compareTo("-s") == 0)
			{
				c.checkSyntax();
			}
		}
		c.setVisible(true);
	}

	/**
	 * The actual panel containing the interface of the calculator.
	 */
	public class CalculatorPanel extends JPanel implements ActionListener, Resettable 
	{
		/**
		 * Dummy UID.
		 */
		private static final long serialVersionUID = 1L;

		private JTextField display = new JTextField("0");
		private double result = 0;
		private String operator = "=";
		private boolean calculating = true;
		
		/**
		 * A flag indicating if a number has been entered since the last operator.
		 */
		private boolean m_hasNumber;

		public CalculatorPanel() 
		{
			setLayout(new BorderLayout());
			display.setEditable(false);
			add(display, "North");
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4, 4));
			for (String label : BUTTON_LABELS)
			{
				JButton b = new JButton(label);
				m_buttons.put(label, b);
				panel.add(b);
				b.addActionListener(this);
			}
			add(panel, "Center");
			m_hasNumber = false;
		}

		@Override
		public void actionPerformed(ActionEvent evt) 
		{
			String cmd = evt.getActionCommand();
			if ('0' <= cmd.charAt(0) && cmd.charAt(0) <= '9' || cmd.equals(".")) 
			{
				m_hasNumber = true;
				if (calculating)
				{
					display.setText(cmd);
				}
				else
				{
					if (m_checkSyntax && cmd.equals(".") && display.getText().contains("."))
					{
						throw new IllegalArgumentException("Invalid number format");
					}
					display.setText(display.getText() + cmd);
				}
				calculating = false;
			}
			else 
			{
				if (m_checkSyntax && !m_hasNumber)
				{
					throw new IllegalArgumentException("Cannot punch an operator now");
				}
				if (!cmd.equals("="))
				{
					m_hasNumber = false;
				}
				if (calculating) 
				{
					if (cmd.equals("-")) 
					{
						display.setText(cmd);
						calculating = false;
					}
					else
					{
						operator = cmd;
					}
				} 
				else 
				{
					double x = 0;
					try
					{
						x = Double.parseDouble(display.getText());
					}
					catch (NumberFormatException e)
					{
						if (!m_checkFormat)
						{
							throw e;
						}
					}
					calculate(x);
					operator = cmd;
					calculating = true;
				}
			}
		}

		private void calculate(double n)
		{
			if (m_hasOverflow && (n > 100000 || result > 100000))
			{
				// Artificial overflow
				display.setText("OF");
				throw new OverflowException();
			}
			switch (operator)
			{
			case "+":
				result += n;
				break;
			case "\u2212":
				result -= n;
				break;
			case "\u00d7":
				result *= n;
				break;
			case "\u00f7":
				if (n == 0)
				{
					display.setText("Error");
					throw new IllegalArgumentException("Division by zero");
				}
				result /= n;
				break;
			case "=":
				result = n;
				break;
			}
			if (m_hasOverflow && (n > 100000 || result > 100000))
			{
				// Artificial overflow
				display.setText("OF");
				throw new OverflowException();
			}
			display.setText("" + result);
		}

		@Override
		public void reset()
		{
			result = 0;
			operator = "=";
			calculating = true;
			display.setText("0");
			m_hasNumber = false;
		}
	}

	/**
	 * Exception that is thrown when the calculator produces an overflow.
	 */
	public static class OverflowException extends RuntimeException
	{
		/**
		 * Dummy UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new overflow exception.
		 */
		public OverflowException()
		{
			super("Overflow");
		}
	}

	/**
	 * Exception that is thrown when the calculator produces an underflow.
	 */
	public static class UnderflowException extends RuntimeException
	{
		/**
		 * Dummy UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new underflow exception.
		 */
		public UnderflowException()
		{
			super("Underflow");
		}
	}
}
