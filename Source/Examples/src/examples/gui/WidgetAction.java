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

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.AbstractButton;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.test.Action;
import ca.uqac.lif.synthia.util.Delay;

/**
 * A {@link Action} that performs a concrete action on a widget.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the widget on which the action can be performed
 * @ingroup Examples
 */
public abstract class WidgetAction<T> implements Action
{
	/**
	 * A robot that can be used to interact with a GUI.
	 */
	protected static Robot s_robot;
	
	/**
	 * The object on which the action is performed.
	 */
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
	
	/**
	 * Creates a new widget action.
	 * @param o The object on which the action is performed
	 */
	public WidgetAction(T o)
	{
		super();
		m_object = o;
	}
	
	/**
	 * Action that clicks on a specific button when called. This action can be
	 * done on any {@link javax.swing.AbstractButton AbstractButton}, including
	 * standard buttons and checkboxes.
	 * @ingroup API
	 */
	public static class ClickAction extends WidgetAction<AbstractButton>
	{
		/**
		 * Creates a new click action.
		 * @param o The button on which the action is performed
		 */
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
	
	/**
	 * Action that types a string inside a textbox. The action simulates
	 * keyboard typing and sends the string one keystroke at a time. A
	 * configurable delay can be set between each keystroke.
	 * @ingroup API
	 */
	public static class TypeAction extends WidgetAction<JTextComponent>
	{
		/**
		 * A picker providing the text that is to be typed.
		 */
		protected Picker<String> m_text;
		
		/**
		 * A picker deciding on the delay (in seconds) to insert between each
		 * keystroke.
		 */
		protected Picker<Number> m_delay;
		
		/**
		 * The duration of a key press on the keyboard (i.e. the time between key
		 * down and key up).
		 */
		protected static float s_keyDuration = 0.1f;
		
		/**
		 * Creates a new type action.
		 * @param jtc The text component in which the text is to be typed
		 * @param text A picker providing the text that is to be typed
		 * @param delay A picker deciding on the delay (in seconds) to insert
		 * between each keystroke
		 */
		public TypeAction(JTextComponent jtc, Picker<String> text, Picker<Number> delay)
		{
			super(jtc);
			m_text = text;
			m_delay = delay;
		}

		@Override
		public void doAction()
		{
			String text = m_text.pick();
			m_object.requestFocusInWindow();
			Delay.wait(m_delay.pick().floatValue());
			for (int i = 0; i < text.length(); i++)
			{
				String c = text.substring(i, i + 1);
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
