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

/**
 * Generates integer numbers following a Poisson distribution.
 * The <a href="https://en.wikipedia.org/wiki/Poisson_distribution">Poisson
 * distribution</a> is a discrete probability distribution that expresses
 * the probability of a given number of events occurring in a fixed interval
 * of time or space if these events occur with a known constant rate and
 * independently of the time since the last event.
 * The distribution is parameterized by a value, &lambda;, which
 * expresses the probability of occurrence of an event in a given time
 * interval. The probability of generating a nonnegative integer <i>k</i> is
 * &lambda;<sup><i>k</i></sup><i>e</i><sup>-&lambda;</sup>/<i>k</i>!.
 * <p>
 * The class uses two algorithms for generating Poisson integers, depending
 * on the value of &lambda;. For small values (&le; 30), a simple algorithm
 * attributed to Knuth is used. For larger values (&gt; 30), it uses an
 * algorithm described by
 * <a href="https://www.johndcook.com/blog/2010/06/14/generating-poisson-random-values/">John
 * D. Cook</a>.
 */
public class PoissonInteger extends RandomPicker<Integer>
{
	/**
	 * The &lambda; parameter of the underlying Poisson distribution
	 */
	protected float m_lambda;
	
	/**
	 * Value of constant <i>c</i> in Cook's algorithm
	 */
	protected double m_c;
	
	/**
	 * Value of constant &alpha; in Cook's algorithm
	 */
	protected double m_alpha;
	
	/**
	 * Value of constant &beta; in Cook's algorithm
	 */
	protected double m_beta;
	
	/**
	 * Value of constant <i>c</i> in Cook's algorithm
	 */
	protected double m_k;
	
	/**
	 * Maximum number of iterations in Cook's algorithm. The original
	 * implementation is <em>theoretically</em> unbounded; this parameter
	 * makes sure that it ends after a maximum number of iterations.
	 */
	protected static final transient int s_maxIterations = 10000; 
	
	/**
	 * Creates a new instance of the picker 
	 * @param lambda The &lambda; parameter of the underlying Poisson
	 * distribution
	 */
	public PoissonInteger(/*@ non_null @*/ Number lambda)
	{
		super();
		m_lambda = lambda.floatValue();
		m_c = 0.767 - 3.36/ m_lambda;
		m_beta = Math.PI / Math.sqrt(3 * m_lambda);
		m_alpha = m_beta * m_lambda;
		m_k = Math.log(m_c) - m_lambda - Math.log(m_beta);
	}
	
	/**
	 * Creates a new instance of the picker, with pre-computed values for the
	 * constants in Cook's algorithm. 
	 * @param lambda The &lambda; parameter of the underlying Poisson
	 * distribution
	 * @param c Value of constant <i>c</i> in Cook's algorithm
	 * @param alpha Value of constant &alpha; in Cook's algorithm
	 * @param beta Value of constant &beta; in Cook's algorithm
	 * @param k Value of constant <i>k</i> in Cook's algorithm
	 */
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
	
	/**
	 * Generates a Poisson integer using Knuth's algorithm, which is efficient
	 * for small values of &lambda;
	 * @return A Poisson integer
	 */
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
	
	/**
	 * Generates a Poisson integer using Cook's algorithm, which is efficient
	 * for larger values of &lambda;
	 * @return A Poisson integer
	 */
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
	
	/**
	 * Computes the factorial of a number. This is a very straightforward
	 * implementation that is not expected to be efficient.
	 * @param n The number to calculate the factorial of
	 * @return The factorial of that number
	 */
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
