package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Seedable;
import ca.uqac.lif.synthia.random.RandomInteger;

import java.util.ArrayList;
import java.util.List;

/**
 * Picker who implements EnumerativePicker. This picker enumerates the integer values
 * from x to y and throws a {@link NoMoreElementException} if the picker picks
 * another value after the picker has finished to enumerates the values.
 */
public class AllIntegers implements Bounded<Integer>, Seedable
{
	/**
	 * The minimal value the picker can generate.
	 */
	protected int m_min;

	/**
	 * The maximal value the picker can generate.
	 */
	protected int m_max;

	/**
	 * The actual value to generate.
	 */
	protected int m_actualValue;

	/**
	 * Flag to check if the picker scrambles the values to generate or not.
	 */
	protected final boolean m_scramble;

	/**
	 * List to store the values to generate if the scramble flag is set to <tt>true</tt>.
	 */
	protected List<Integer> m_valuesToScramble;

	/**
	 * RandomInteger to generate random index if the scramble flag is set to <tt>true</tt>.
	 */
	protected RandomInteger m_indexGenerator;

	/**
	 * Private constructor used to duplicate the picker.
	 * @param min The m_min value to copy in a new AllIntegers instance.
	 * @param max The m_max value to copy in a new AllIntegers instance.
	 * @param actual_value The m_actualValue value to copy in a new AllIntegers instance.
	 * @param scramble The m_scramble value to copy in a new AllIntegers instance.
	 */
	private AllIntegers(int min, int max, int actual_value, boolean scramble
			, List<Integer> values_to_scramble, RandomInteger index_generator)
	{
		m_min = min;
		m_max = max;
		m_actualValue = actual_value;
		m_scramble = scramble;
		m_valuesToScramble = values_to_scramble;
		m_indexGenerator = index_generator;
	}

	/**
	 * Private method to initialize m_valueToScramble if the scramble flag if set to <tt>true</tt>.
	 */
	private void InitializeList()
	{
		for (int i = m_min; i <= m_max; i++)
		{
			m_valuesToScramble.add(i);
		}
	}

	/**
	 * Constructor with <tt>false</tt> as a default value for the scramble flag.
	 * @param min The minimal value to generate.
	 * @param max The maximal value to generate.
	 */
	public AllIntegers(int min, int max)
	{
		this(min, max, false);
	}

	/**
	 * Constructor without default value for the scramble flag.
	 * @param min The minimal value to generate.
	 * @param max The maximal value to generate.
	 * @param scramble False to generate values in order or True to generate scrambled values.
	 */
	public AllIntegers(int min, int max, boolean scramble)
	{
		m_min = min;
		m_max = max;
		m_actualValue = min;
		m_scramble = scramble;
		m_valuesToScramble = new ArrayList<>();
		m_indexGenerator = new RandomInteger(0, 1);

		if(scramble)
		{
			InitializeList();
			m_indexGenerator = new RandomInteger(0, m_valuesToScramble.size());
		}
	}

	@Override
	public boolean isDone()
	{
		if (!m_scramble)
		{
			return m_actualValue > m_max;
		}
		return m_valuesToScramble.size() == 0;
	}

	@Override
	public void reset()
	{
		if(!m_scramble)
		{
			m_actualValue = m_min;
		}
		else
		{
			m_valuesToScramble.clear();
			InitializeList();
			m_indexGenerator.reset();
			m_indexGenerator.setInterval(0, m_valuesToScramble.size());
		}

	}

	@Override
	public Integer pick()
	{
		if (isDone())
		{
			throw new NoMoreElementException();
		}

		int picked_value;

		if (!m_scramble)
		{
			picked_value = m_actualValue;
			m_actualValue++;
		}
		else
		{
			picked_value = scrambledPick();
		}

		return picked_value;
	}

	/**
	 * Private method used by the public pick method to pick scrambled values if the scramble flag
	 * is set to <tt>true</tt>.
	 * @return A scrambled Integer object.
	 */
	private Integer scrambledPick()
	{
		Integer picked_index = m_indexGenerator.pick();
		Integer picked_value =   m_valuesToScramble.get(picked_index);

		m_valuesToScramble.remove(picked_value);
		m_indexGenerator.setInterval(0, m_valuesToScramble.size()) ;

		return picked_value;
	}

	@Override
	public AllIntegers duplicate(boolean with_state)
	{
		AllIntegers copy = new AllIntegers(m_min, m_max, m_actualValue, m_scramble,
				new ArrayList<>(m_valuesToScramble), m_indexGenerator.duplicate(with_state));

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}

	@Override
	public AllIntegers setSeed(int seed)
	{
		if (m_scramble)
		{
			m_indexGenerator.setSeed(seed);
		}
		return this;
	}
}
