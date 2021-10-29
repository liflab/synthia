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

import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.CannotShrinkException;

/**
 * Applies an affine transform to a value produced by another picker.
 * It takes two parameters <i>m</i> and <i>b</i>. If <i>x</i> is
 * the value produced by the picker, the affine transforms returns
 * <i>mx</i>+<i>b</i>.
 * <p>
 * This class is abstract; it has two concrete descendants,
 * {@link AffineTransformInteger} and {@link AffineTransformFloat}.
 * @param <T> The type of number produced (i.e. <tt>Float</tt>,
 * <tt>Integer</tt>, etc.)
 * @ingroup API
 */
public abstract class AffineTransform<T extends Number> extends Mutator<T> implements Shrinkable<T>
{
	/**
	 * The slope of the affine transform
	 */
	protected float m_m;
	
	/**
	 * The intercept of the affine transform
	 */
	protected float m_b;
	
	/**
	 * Creates a new instance of affine transform
	 * @param picker The underlying number picker
	 * @param m The slope of the affine transform
	 * @param b The intercept of the affine transform
	 */
	public AffineTransform(/*@ non_null @*/ Picker<? extends T> picker, /*@ non_null @*/ Number m, /*@ non_null @*/ Number b)
	{
		super(picker);
		m_m = m.floatValue();
		m_b = b.floatValue();
	}
	
	@Override
	public void reset()
	{
		m_picker.reset();
	}
	
	/**
	 * Applies the affine transform to the next floating point number
	 * produced by the underlying picker
	 * @return The transformed float
	 */
	protected float pickFloat()
	{
		return m_picker.pick().floatValue() * m_m + m_b;
	}
	
	/**
	 * Affine transform producing <tt>int</tt>s
	 */
	public static class AffineTransformInteger extends AffineTransform<Integer>
	{
		/**
		 * Creates a new instance of affine transform
		 * @param picker The underlying number picker
		 * @param m The slope of the affine transform
		 * @param b The intercept of the affine transform
		 */
		public AffineTransformInteger(/*@ non_null @*/ Picker<? extends Integer> picker, /*@ non_null @*/ Number m, /*@ non_null @*/ Number b)
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
		
		@SuppressWarnings("unchecked")
		@Override
		public AffineTransformInteger shrink(Integer o)
		{
			if (!(m_picker instanceof Shrinkable))
			{
				throw new CannotShrinkException(m_picker);
			}
			int source_value = (int) Math.floor((o - m_b) / m_m);
			return new AffineTransformInteger(((Shrinkable<Integer>) m_picker).shrink(source_value), m_m, m_b);
		}
	}
	
	/**
	 * Affine transform producing <tt>float</tt>s
	 */
	public static class AffineTransformFloat extends AffineTransform<Float>
	{
		/**
		 * Creates a new instance of affine transform
		 * @param picker The underlying number picker
		 * @param m The slope of the affine transform
		 * @param b The intercept of the affine transform
		 */
		public AffineTransformFloat(/*@ non_null @*/ Picker<? extends Float> picker, /*@ non_null @*/ Number m, /*@ non_null @*/ Number b)
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
			 return new AffineTransformFloat(m_picker.duplicate(with_state), m_m, m_b);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public AffineTransformFloat shrink(Float o)
		{
			if (!(m_picker instanceof Shrinkable))
			{
				throw new CannotShrinkException(m_picker);
			}
			float source_value = (o - m_b) / m_m;
			return new AffineTransformFloat(((Shrinkable<Float>) m_picker).shrink(source_value), m_m, m_b);
		}
	}
}
