package examples.quickcheck;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.GiveUpException;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;

/**
 * @ingroup Examples
 * @author sylvain
 *
 * @param <T>
 */
public class Assert<T>
{
	protected static final int MAX_TRIES = 1000;
	
	protected Shrinkable<T> m_input;

	protected List<T> m_shrunk;
	
	protected Picker<Float> m_decision;

	public Assert(Shrinkable<T> input, Picker<Float> decision)
	{
		super();
		m_input = input;
		m_shrunk = new ArrayList<T>();
		m_decision = decision;
	}
	
	public List<T> getIterations()
	{
		return m_shrunk;
	}
	
	public T getInitial()
	{
		if (m_shrunk.isEmpty())
		{
			return null;
		}
		return m_shrunk.get(0);
	}
	
	public T getShrunk()
	{
		if (m_shrunk.isEmpty())
		{
			return null;
		}
		return m_shrunk.get(m_shrunk.size() - 1);
	}

	public boolean check()
	{
		T o = null;
		boolean found = false;
		try
		{
			for (int i = 0; i < MAX_TRIES; i++)
			{
				o = m_input.pick();
				if (!evaluate(o))
				{
					found = true;
					break;
				}
			}
			m_shrunk.add(o);
		}
		catch (GiveUpException e)
		{
			return true;
		}
		catch (NoMoreElementException e)
		{
			return true;
		}
		if (!found)
		{
			return true;
		}
		Shrinkable<T> p = m_input.shrink(o, m_decision);
		try
		{
			for (int i = 0; i < MAX_TRIES; i++)
			{
				for (int j = 0; j < MAX_TRIES; j++)
				{
					o = p.pick();
					if (!evaluate(o))
					{
						m_shrunk.add(o);
						break;
					}
				}
				p = p.shrink(o, m_decision);
			}
		}
		catch (NoMoreElementException e)
		{
			// Nothing to do
		}
		return false;
	}

	protected boolean evaluate(T o)
	{
		return true;
	}
}
