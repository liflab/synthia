package ca.uqac.lif.synthia.sequence;

import java.util.HashMap;
import java.util.Map;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Choice.ProbabilityChoice;

public class TimedInterleave<T> extends Knit<T>
{
	protected Map<Integer,Float> m_timestamps;
	
	protected Map<Integer,T> m_lastElement;
	
	protected float m_currentTimestamp;
	
	public TimedInterleave(Picker<Float> float_source, Number mid_probability, Number end_probability)
	{
		super(float_source, mid_probability, end_probability);
		m_timestamps = new HashMap<Integer,Float>();
		m_lastElement = new HashMap<Integer,T>();
		m_currentTimestamp = 0f;
	}
	
	public TimedInterleave<T> add(StartSettable<T> provider, Number probability)
	{
		super.add(provider, probability);
		return this;
	}
	
	@Override
	protected Picker<T> startNewSession()
	{
		float f = m_floatSource.pick();
		float sum_prob = 0;
		for (ProbabilityChoice<Picker<T>> pc : m_choices)
		{
			sum_prob += pc.getProbability();
			if (f <= sum_prob)
			{
				return ((StartSettable<T>) pc.getObject()).duplicate(false, m_currentTimestamp);
			}
		}
		return null;
	}
	
	@Override
	public T pick()
	{
		float p = m_floatSource.pick();
		if (m_sessions.isEmpty() || p < m_midProbability)
		{
			int new_sess_id = ++m_idCount;
			Picker<T> new_session = startNewSession();
			m_sessions.put(new_sess_id, new_session);
			T t = new_session.pick();
			m_lastElement.put(new_sess_id, t);
			m_timestamps.put(new_sess_id, getTimestamp(t));
		}
		// Find session with smallest timestamp
		int smallest_id = -1;
		float smallest_ts = -1;
		for (Map.Entry<Integer,Float> e : m_timestamps.entrySet())
		{
			int s_id = e.getKey();
			float s_ts = e.getValue();
			if (smallest_ts < 0 || s_ts < smallest_ts)
			{
				smallest_id = s_id;
				smallest_ts = s_ts;
			}
		}
		// smallest_id contains the ID of the session with the smallest
		// timestamp; update that session
		Picker<T> chosen_session = m_sessions.get(smallest_id);
		m_currentTimestamp = smallest_ts;
		T out = m_lastElement.get(smallest_id);
		T new_e = chosen_session.pick();
		if (new_e == null)
		{
			m_sessions.remove(smallest_id);
			if (p < m_endProbability)
			{
				int new_sess_id = ++m_idCount;
				m_sessions.put(new_sess_id, startNewSession());
				m_timestamps.remove(new_sess_id);
			}
		}
		else
		{
			m_lastElement.put(smallest_id, new_e);
			m_timestamps.put(smallest_id, getTimestamp(new_e));
		}
		return out;
	}
	
	@Override
	public TimedInterleave<T> duplicate(boolean with_state)
	{
		TimedInterleave<T> ti = new TimedInterleave<T>(m_floatSource.duplicate(true), m_midProbability, m_endProbability);
		return ti;
	}
	
	@Override
	public void reset()
	{
		super.reset();
		m_timestamps.clear();
		m_lastElement.clear();
		m_currentTimestamp = 0f;
	}
	
	/**
	 * Extracts a timestamp value from an object.
	 * @param t The object
	 * @return The timestamp
	 */
	public float getTimestamp(T t)
	{
		m_currentTimestamp += 0.1f;
		return m_currentTimestamp;
	}

	public static interface StartSettable<T> extends Picker<T>
	{
		public StartSettable<T> duplicate(boolean with_state, float start_timestamp);
	}
}
