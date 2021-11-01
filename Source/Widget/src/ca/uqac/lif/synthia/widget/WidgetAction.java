package ca.uqac.lif.synthia.widget;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.AbstractButton;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import ca.uqac.lif.synthia.Picker;

public abstract class WidgetAction<T> implements GuiAction
{
	protected static Robot s_robot;
	
	protected T m_object;
	
	static
	{
		try
		{
			s_robot = new Robot();
		}
		catch (AWTException e)
		{
			s_robot = null;
		}
	}
	
	public WidgetAction(T o)
	{
		super();
		m_object = o;
	}
	
	public static class ClickAction extends WidgetAction<AbstractButton>
	{
		public ClickAction(AbstractButton o)
		{
			super(o);
		}
		
		@Override
		public void doAction()
		{
			m_object.doClick();
		}
		
		@Override
		public String toString()
		{
			return m_object.getText();
		}
	}
	
	public static class TypeAction extends WidgetAction<JTextComponent>
	{
		protected String m_text;
		
		protected Picker<Number> m_delay;
		
		/**
		 * The duration of a key press on the keyboard (i.e. the time between key
		 * down and key up).
		 */
		protected static float s_keyDuration = 0.1f;
		
		public TypeAction(JTextComponent jtc, String text, Picker<Number> delay)
		{
			super(jtc);
			m_text = text;
			m_delay = delay;
		}

		@Override
		public void doAction()
		{
			m_object.requestFocusInWindow();
			Delay.wait(m_delay.pick().floatValue());
			for (int i = 0; i < m_text.length(); i++)
			{
				String c = m_text.substring(i, i + 1);
				KeyStroke ks = KeyStroke.getKeyStroke(c);
				int code = ks.getKeyCode();
				s_robot.keyPress(code);
				Delay.wait(0.05f);
				s_robot.keyRelease(code);
			}
		}
		
		@Override
		public String toString()
		{
			return "Type \"" + m_text + "\" into " + m_object;
		}
	}
}
