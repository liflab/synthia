/*
    Synthia, a data structure generator
    Copyright (C) 2019-2020 Laboratoire d'informatique formelle
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
package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.Picker;

public abstract class AffineTransform<T extends Number> implements Picker<T>
{
	protected float m_m;
	
	protected float m_b;
	
	/*@ non_null @*/ protected Picker<T> m_picker;
	
	public AffineTransform(/*@ non_null @*/ Picker<T> picker, /*@ non_null @*/ Number m, /*@ non_null @*/ Number b)
	{
		super();
		m_m = m.floatValue();
		m_b = b.floatValue();
	}
	
	@Override
	public void reset()
	{
		m_picker.reset();
	}
	
	protected float pickFloat()
	{
		return m_picker.pick().floatValue() * m_m + m_b;
	}
	
	public static class AffineTransformInteger extends AffineTransform<Integer>
	{
		public AffineTransformInteger(/*@ non_null @*/ Picker<Integer> picker, /*@ non_null @*/ Number m, /*@ non_null @*/ Number b)
		{
			super(picker, m, b);
		}

		@Override
		public Integer pick()
		{
			return (int) super.pickFloat();
		}

		@Override
		public AffineTransformInteger duplicate(boolean with_state) 
		{
			AffineTransformInteger ati = new AffineTransformInteger(m_picker.duplicate(with_state), m_m, m_b);
			return ati;
		}
	}
	
	public static class AffineTransformFloat extends AffineTransform<Float>
	{
		public AffineTransformFloat(/*@ non_null @*/ Picker<Float> picker, /*@ non_null @*/ Number m, /*@ non_null @*/ Number b)
		{
			super(picker, m, b);
		}

		@Override
		public Float pick()
		{
			return pickFloat();
		}

		@Override
		public AffineTransformFloat duplicate(boolean with_state) 
		{
			AffineTransformFloat atf = new AffineTransformFloat(m_picker.duplicate(with_state), m_m, m_b);
			return atf;
		}
	}
}
