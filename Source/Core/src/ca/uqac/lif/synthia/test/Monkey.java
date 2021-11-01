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
package ca.uqac.lif.synthia.test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Resettable;
import ca.uqac.lif.synthia.SequenceShrinkable;
import ca.uqac.lif.synthia.sequence.Record;
import ca.uqac.lif.synthia.util.Delay;

/**
 * Performs <a href="https://en.wikipedia.org/wiki/Monkey_testing">monkey
 * testing</a> by interacting with a component. The monkey works as
 * follows:
 * <ol>
 * <li>The monkey starts with a <em>discovery phase</em>. It uses a
 * <tt>Picker&lt;Action&gt;</tt> as a source of actions to be
 * performed on the component. Actions provided by this picker are
 * executed one by one.</li>
 * <li>Exceptions that may be thrown resulting from each action are trapped.
 * As soon as an exception is caught, the sequence of actions performed so
 * far is stored.</li>
 * <li>The monkey then enters in a <em>shrinking phase</em>, where shorter
 * sub-traces of the original are repeatedly generated and tested. Whenever
 * a shorter sequence still throws an exception, this sequence replaces the
 * original, and the shrinking process restarts from this new reference.</li> 
 * </ol>
 * What the "component" can be is totally abstract. All the monkey expects
 * is that it implements the {@link Resettable} interface. A typical component
 * is a graphical user interface (such as a {@link JFrame}), 
 * 
 * @author Sylvain Hallé
 */
public class Monkey
{
	/**
	 * The maximum number of shrinking phases the monkey will attempt.
	 */
	protected static final int s_maxShrinkingPhases = 5;

	/**
	 * The maximum number of attempts at producing a sequence in each
	 * shrinking phase.
	 */
	protected static final int s_maxTries = 5;

	/**
	 * The picker producing the actions to be applied.
	 */
	protected Picker<Action> m_actionPicker;

	/**
	 * The object on which the actions are applied.
	 */
	protected Resettable m_object;

	/**
	 * The "best" sequence of actions found by the monkey so far.
	 */
	protected List<Action> m_bestSequence;

	/**
	 * The exception thrown at the end of the "best" sequence of actions found
	 * by the monkey so far.
	 */
	protected Exception m_lastException;

	/**
	 * A picker passed to the action picker for the shrinking process.
	 */
	protected Picker<Float> m_decision;

	/**
	 * A print stream where the monkey outputs status messages during its
	 * execution.
	 */
	protected PrintStream m_out;

	/**
	 * The length of the sequence under which the monkey is allowed to stop
	 * searching.
	 */
	protected int m_bestThreshold;

	/**
	 * Creates a new instance of the monkey.
	 * @param object The object on which the actions are applied
	 * @param actions The picker producing the actions to be applied
	 * @param decision A picker passed to the action picker for the
	 * shrinking process
	 * @param ps A print stream where the monkey outputs status messages during
	 * its execution. Set it to <tt>null</tt> to disable messages.
	 */
	public Monkey(Resettable object, Picker<Action> actions, Picker<Float> decision, PrintStream ps)
	{
		super();
		m_object = object;
		m_actionPicker = actions;
		m_decision = decision;
		m_bestSequence = new ArrayList<Action>();
		m_out = ps;
		m_bestThreshold = 4;
		m_lastException = null;
	}

	/**
	 * Creates a new instance of the monkey.
	 * @param object The object on which the actions are applied
	 * @param actions The picker producing the actions to be applied
	 * @param decision A picker passed to the action picker for the
	 * shrinking process
	 */
	public Monkey(Resettable object, Picker<Action> actions, Picker<Float> decision)
	{
		this(object, actions, decision, null);
	}

	public boolean check()
	{
		boolean error_found = false;
		Record<Action> rec = new Record<Action>(m_actionPicker);
		for (int try_counter = 0; try_counter < s_maxTries; try_counter++)
		{
			println("Attempt " + try_counter);
			try
			{
				for (int i = 0; i < 100; i++)
				{
					Action a = rec.pick();
					a.doAction();
					print(a);
					//Delay.wait(0.005f); // Give time for the object to reset itself
				}
				println("");
			}
			catch (Exception e)
			{
				// Exception thrown
				print("\n" + e);
				error_found = true;
				m_bestSequence = rec.getSequence();
				println("\nSequence: " + m_bestSequence);
				break;
			}
			rec.clear();
		}
		SequenceShrinkable<Action> reference = rec;
		for (int shrinking_steps = 0; m_bestSequence.size() > m_bestThreshold && shrinking_steps < s_maxShrinkingPhases; shrinking_steps++)
		{
			boolean shrink_again = false;
			for (float magnitude = 0.25f; m_bestSequence.size() > m_bestThreshold && !shrink_again && magnitude <= 1; magnitude += 0.25)
			{
				Exception last_ex = null;
				for (int i = 0; i < s_maxTries; i++)
				{
					SequenceShrinkable<Action> to_try = reference.shrink(m_decision, magnitude);
					boolean success = false;
					m_object.reset();
					Delay.wait(0.25f); // Give time for the object to reset itself
					while (!to_try.isDone())
					{
						try
						{
							Action a = to_try.pick();
							a.doAction();
							print(a);
							//Delay.wait(0.005f); // Give time for the object to reset itself
						}
						catch (Exception e)
						{
							success = true;
							last_ex = e;
							break;
						}
					}
					if (success)
					{
						reference = to_try;
						if (m_bestSequence == null || to_try.getSequence().size() < m_bestSequence.size())
						{
							m_bestSequence = to_try.getSequence();
							println("\nSequence: " + m_bestSequence);
							m_lastException = last_ex;
						}
						shrink_again = true;
						break;
					}
					println("");
				}
			}
		}
		println("");
		return !error_found;
	}

	public Exception getException()
	{
		return m_lastException;
	}

	public List<Action> getShrunk()
	{
		return m_bestSequence;
	}

	/**
	 * Prints a message to the print stream.
	 * @param message The message
	 */
	protected void print(Object message)
	{
		if (m_out != null)
		{
			m_out.print(message);
		}
	}

	/**
	 * Prints a message to the print stream.
	 * @param message The message
	 */
	protected void println(Object message)
	{
		if (m_out != null)
		{
			m_out.println(message);
		}
	}
}
