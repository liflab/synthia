package quickcheck;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.GiveUpException;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

public class Assert<T>
{
	protected static final int MAX_TRIES = 1000;
	
	protected Shrinkable<T> m_input;

	protected List<T> m_shrunk;

	public Assert(Shrinkable<T> input)
	{
		super();
		m_input = input;
		m_shrunk = new ArrayList<T>();
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
		Shrinkable<T> p = m_input.shrink(o);
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
				p = p.shrink(o);
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
