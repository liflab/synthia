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

public class PoissonInteger extends RandomPicker<Integer>
{
	protected float m_lambda;
	
	protected double m_c;
	
	protected double m_alpha;
	
	protected double m_beta;
	
	protected double m_k;
	
	protected static final transient int s_maxIterations = 10000; 
	
	public PoissonInteger(/*@ non_null @*/ Number lambda)
	{
		super();
		m_lambda = lambda.floatValue();
		m_c = 0.767 - 3.36/ m_lambda;
		m_beta = Math.PI / Math.sqrt(3 * m_lambda);
		m_alpha = m_beta * m_lambda;
		m_k = Math.log(m_c) - m_lambda - Math.log(m_beta);
	}
	
	protected PoissonInteger(float lambda, double c, double alpha, double beta, double k)
	{
		super();
		m_lambda = lambda;
		m_c = c;
		m_alpha = alpha;
		m_beta = beta;
		m_k = k;
	}
	
	@Override
	public Integer pick() 
	{
		if (m_lambda < 30)
		{
			return smallPoisson();
		}
		return bigPoisson();
	}
	
	protected int smallPoisson()
	{
		double L = Math.exp(-m_lambda);
		int k = 0;
		double p = 1;
		do
		{
			k++;
			double u = m_random.nextDouble();
			p *= u;
		} while (p > L);
		return k - 1;
	}
	
	protected int bigPoisson()
	{
		for (int i = 0; i < s_maxIterations; i++)
		{
			double u = m_random.nextDouble();
			double x = (m_alpha - (Math.log(1 - u) / u)) / m_beta;
			long n = (long) Math.floor(x + 0.5);
			if (n < 0)
			{
				continue;
			}
			double v = m_random.nextDouble();
			double y = m_alpha - m_beta * x;
			double lhs = y + Math.log(v / Math.pow(1 + Math.exp(y), 2));
			double rhs = m_k + n * Math.log(m_lambda) - Math.log(factorial(n));
			if (lhs <= rhs)
			{
				return (int) n;
			}
		}
		return 0;
	}

	@Override
	public PoissonInteger duplicate(boolean with_state) 
	{
		PoissonInteger gf = new PoissonInteger(m_lambda, m_c, m_alpha, m_beta, m_k);
		gf.m_seed = m_seed;
		return gf;
	}
	
	protected static long factorial(long n)
	{
		long x = 1;
		for (long i = n; i > 0; i--)
		{
			x *= i;
		}
		return x;
	}

}
