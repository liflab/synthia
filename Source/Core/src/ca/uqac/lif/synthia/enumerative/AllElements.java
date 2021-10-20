package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.Seedable;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.replay.Playback;

import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
public class AllElements<T> extends Playback implements EnumerativePicker, Seedable
=======
/**
 * @param <T> The type of the {@link AllElements} {@link ca.uqac.lif.synthia.Picker}.
 * @author Marc-Antoine Plourde
 */
public class AllElements<T> extends Playback<T> implements EnumerativePicker<T>, Seedable
>>>>>>> Stashed changes
{

	protected List<Integer> m_indexes;

	protected final boolean m_scramble;

	protected RandomInteger m_indexPicker;

	private AllElements(T[] values, boolean scramble, RandomInteger index_picker
			, List<Integer> indexes, boolean loop, int index, int start_index)
	{
		m_values = values;
		m_scramble = scramble;
		m_indexPicker = index_picker;
		m_indexes = indexes;
		m_loop = loop;
		m_index = index;
		m_startIndex = start_index;
	}

	public AllElements(List<T> values, boolean scramble, boolean loop)
	{
		super(values);
		m_scramble = scramble;
		m_indexes = new ArrayList<>();
		m_indexPicker = new RandomInteger(0, 1);
		setLoop(loop);

		if(scramble)
		{
			initializeIndexes();
			m_indexPicker.setInterval(0, m_values.length);
		}

	}

	private void initializeIndexes()
	{
		for (int i = 0; i < m_values.length; i++)
		{
			m_indexes.add(i);
		}
	}

	@Override
	public T pick()
	{
		if(!m_scramble)
		{
			return  super.pick();
		}
		else
		{
			return scramblePick();
		}
	}

	private T scramblePick()
	{
		if(m_indexes.isEmpty())
		{
			if(!m_loop)
			{
				throw new NoMoreElementException();
			}
			else
			{
				initializeIndexes();
			}
		}

		int picked_index = m_indexPicker.pick();
		T picked_value =  m_values[m_indexes.get(picked_index)];

		m_indexes.remove(picked_index);

		if(m_indexes.size()>0)
		{
			m_indexPicker.setInterval(0, m_indexes.size());
		}


		return picked_value;
	}

	@Override
	public void reset()
	{
		if(m_scramble)
		{
			m_indexes.clear();
			initializeIndexes();
			m_indexPicker.reset();
			m_indexPicker.setInterval(0, m_indexes.size());
		}
		else
		{
			super.reset();
		}
	}

	@Override
	public boolean isDone()
	{
		if(m_loop)
		{
			return false;
		}
		else
		{
			if(m_scramble)
			{
				return m_indexes.isEmpty();
			}
			else
			{
				return (m_index >= (m_values.length));
			}
		}
	}

	@Override
	public AllElements<T> setSeed(int seed)
	{
		m_indexPicker.setSeed(seed);
		return this;
	}

<<<<<<< Updated upstream
	@Override
	public AllElements duplicate(boolean with_state)
	{

		AllElements copy = new AllElements(m_values, m_scramble, m_indexPicker.duplicate(with_state)
				, new ArrayList<Integer>(m_indexes), m_loop, m_index, m_startIndex);
=======
	@Override public AllElements<T> duplicate(boolean with_state)
	{

		AllElements<T> copy = new AllElements<>(m_values, m_scramble, m_indexPicker.duplicate(with_state),
				new ArrayList<>(m_indexes), m_loop, m_index, m_startIndex);
>>>>>>> Stashed changes

		if(!with_state)
		{
			copy.reset();
		}

		return copy;
	}
}
