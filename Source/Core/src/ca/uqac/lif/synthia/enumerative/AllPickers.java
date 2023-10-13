/*
    Synthia, a data structure generator
    Copyright (C) 2019-2023 Laboratoire d'informatique formelle
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
package ca.uqac.lif.synthia.enumerative;

import java.util.Arrays;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.NoMoreElementException;

/**
 * Enumerates all the
 * of combinations of picked values from an array of enumerative pickers. For example, an
 * AllPickers containing an array of 2 {@link AllBooleans} will generates one array
 * in the following order :
 * <ol>
 *   <li>[<tt>false</tt>, <tt>false</tt>]</li>
 *   <li>[<tt>true</tt>, <tt>false</tt>]</li>
 *   <li>[<tt>false</tt>, <tt>true</tt>]</li>
 *   <li>[<tt>true</tt>, <tt>true</tt>]</li>
 * </ol>
 * After that, the picker will throw a {@link NoMoreElementException} if the pick method is called
 * one more time.
 * @ingroup API
 */
public class AllPickers implements Bounded<Object[]>
{
  /**
   * The array of pickers used to generate all the possible combinations.
   */
  protected Bounded<?>[] m_enumPickers;

  /**
   * An array to store the combination to return.
   */
  protected Object[] m_values;

  /**
   * An array to store the last returned combination.
   */
  protected Object[] m_last;

  /**
   * Private constructor used to duplicate the picker.
   * @param enum_pickers The m_enumPickers attribute of the AllPickers instance to duplicate.
   * @param first_pick The m_firstPick attribute of the AllPickers instance to duplicate.
   * @param values The m_values attribute of the AllPickers instance to duplicate.
   * @param done The m_done attribute of the AllPickers instance to duplicate.
   */
  public AllPickers(Bounded<?>[] enum_pickers)
  {
    m_enumPickers = enum_pickers;
    setup();
  }

  protected void setup()
  {
    m_values = new Object[m_enumPickers.length];
    m_last = new Object[m_enumPickers.length];
    for (int i = 0; i < m_last.length; i++)
    {
      Bounded<?> p = m_enumPickers[i];
      if (p.isDone())
      {
        m_last = null;
        break;
      }
      m_last[i] = p.pick();
    }
    copyLast();
  }

  protected void copyLast()
  {
    if (m_last != null)
    {
      m_values = Arrays.copyOf(m_last, m_last.length);
    }
    else
    {
      m_values = null;
    }
  }

  @Override
  public boolean isDone()
  {
    if (m_last == null)
    {
      return true;
    }
    if (m_values != null)
    {
      return false;
    }
    boolean all_done = true;
    for (int i = 0; i < m_enumPickers.length; i++)
    {
      Bounded<?> p = m_enumPickers[i];
      if (!p.isDone())
      {
        all_done = false;
        m_last[i] = p.pick();
        copyLast();
        return false;
      }
      else
      {
        p.reset();
        m_last[i] = p.pick();
      }
    }
    if (all_done)
    {
      m_last = null;
      return true;
    }
    return false;
  }

  @Override
  public void reset()
  {
    m_values = new Object[m_enumPickers.length];
    for (Bounded<?> m_enumPicker : m_enumPickers)
    {
      m_enumPicker.reset();
    }
    setup();
  }

  @Override
  public Object[] pick()
  {
    if (m_values != null)
    {
      Object[] o = m_values;
      m_values = null;
      return o;
    }
    if (isDone())
    {
      throw new NoMoreElementException();
    }
    if (m_values == null)
    {
      throw new NoMoreElementException();
    }
    Object[] o = m_values;
    m_values = null;
    return o;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public AllPickers duplicate(boolean with_state)
  {
    Bounded[] enum_picker_copy = new Bounded[m_enumPickers.length];
    for (int i = 0; i < m_enumPickers.length; i++)
    {
      enum_picker_copy[i] = (Bounded<?>) m_enumPickers[i].duplicate(with_state);
    }
    AllPickers copy = new AllPickers(enum_picker_copy);
    if (with_state)
    {
      if (m_last != null)
      {
        copy.m_last = Arrays.copyOf(m_last, m_last.length);
      }
      if (m_values != null)
      {
        copy.m_values = Arrays.copyOf(m_values, m_values.length);
      }
    }
    return copy;
  }
}
