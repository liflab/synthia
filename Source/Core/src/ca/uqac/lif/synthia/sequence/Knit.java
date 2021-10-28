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
package ca.uqac.lif.synthia.sequence;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

/**
 * Picker producing an "interleaved" sequence of objects from calls to multiple
 * other pickers. The <tt>Knit</tt> picker must be instantiated by passing to
 * it a picker of pickers <tt>p</tt> (that is, a
 * <tt>Picker&lt;Picker&lt;T&gt;&gt;</tt>). Upon every call to {@link #pick()},
 * <tt>Knit</tt> proceeds as follows:
 * <ol>
 * <li>It flips a coin to decide whether to create a new instance of
 * <tt>Picker&lt;T&gt;</tt>; if so, it calls <tt>p.pick()</tt> and adds the
 * resulting picker instance to its set of "living" pickers.</li>
 * <li>It selects one of the living pickers, and returns the object resulting
 * from a call to {@link #pick()} on that picker.</li>
 * <li>If this picker cannot produce a new value (e.g. it throws a
 * {@link NoMoreElementException}), it is considered "dead" and is removed from
 * the set of living pickers. In such a case, <tt>Knit</tt> flips a coin to
 * to decide whether to create a new instance of <tt>Picker&lt;T&gt;</tt> to
 * replace it; if so, it calls <tt>p.pick()</tt> and adds the
 * resulting picker instance to its set of "living" pickers.</li>
 * </ol>
 * @author Sylvain Hallé
 *
 * @param <T> The type of the objects to produce
 */
public class Knit<T> implements Picker<T>
{
	/**
	 * Maximum number of tries to get a new element from an instance before
	 * giving up.
	 */
	protected static final transient int s_maxTries = 1000;

	/**
	 * A picker producing pickers.
	 */
	/*@ non_null @*/ protected Picker<? extends Picker<T>> m_instancePicker;

	/**
	 * A picker deciding whether to start a new instance at any moment.
	 */
	/*@ non_null @*/ protected Picker<Boolean> m_newInstance;

	/**
	 * A picker deciding whether to start a new instance when one has just
	 * finished.
	 */
	/*@ non_null @*/ protected Picker<Boolean> m_renewInstance;

	/**
	 * A picker used to pick a living instance.
	 */
	/*@ non_null @*/ protected Picker<Float> m_floatSource;

	/**
	 * The picker instances that are currently "alive".
	 */
	/*@ non_null @*/ protected List<Picker<T>> m_instances;

	/**
	 * Creates a new instance of the picker.
	 * @param instance_picker A picker producing picker instances
	 * @param new_instance A picker deciding whether to start a new instance at
	 * any moment
	 * @param renew_instance A picker deciding whether to start a new instance
	 * when one has just finished
	 * @param float_source A picker used to pick a living instance
	 */
	public Knit(Picker<? extends Picker<T>> instance_picker, Picker<Boolean> new_instance,
			Picker<Boolean> renew_instance, Picker<Float> float_source)
	{
		super();
		m_instancePicker = instance_picker;
		m_newInstance = new_instance;
		m_renewInstance = renew_instance;
		m_floatSource = float_source;
		m_instances = new ArrayList<Picker<T>>();
	}

	@Override
	public void reset()
	{
		m_instancePicker.reset();
		m_newInstance.reset();
		m_renewInstance.reset();
		m_floatSource.reset();
		m_instances.clear();
	}

	@Override
	public T pick()
	{
		if (m_instances.isEmpty() || m_newInstance.pick())
		{
			// Spawn a new instance
			Picker<T> new_instance = m_instancePicker.pick().duplicate(false);
			m_instances.add(new_instance);
		}
		for (int i = 0; i < s_maxTries; i++)
		{
			int index = (int) Math.floor(((float) m_instances.size()) * m_floatSource.pick());
			Picker<T> current_instance = m_instances.get(index);
			try
			{
				return current_instance.pick();
			}
			catch (NoMoreElementException e)
			{
				m_instances.remove(index);
				if (m_instances.isEmpty() || m_renewInstance.pick())
				{
					// Spawn a new instance
					Picker<T> new_instance = m_instancePicker.pick().duplicate(false);
					m_instances.add(new_instance);
				}
			}
		}
		throw new NoMoreElementException();
	}

	@Override
	public Knit<T> duplicate(boolean with_state)
	{
		Knit<T> k = new Knit<T>(m_instancePicker.duplicate(with_state), m_newInstance.duplicate(with_state), m_renewInstance.duplicate(with_state), m_floatSource.duplicate(with_state));
		if (with_state)
		{
			for (Picker<T> p : m_instances)
			{
				k.m_instances.add(p.duplicate(with_state));
			}
		}
		return k;
	}
	
	@Override
	public String toString()
	{
		return "Knit";
	}
}