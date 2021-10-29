package examples.hypersphere;

/**
 * @ingroup Examples
 */
public class Variations
{
	public static String printPoint(float[] p)
	{
		StringBuilder out = new StringBuilder();
		//out.append("(");
		for (int i = 0; i < p.length; i++)
		{
			if (i > 0)
			{
				out.append(",");
			}
			out.append(p[i]);
		}
		//out.append(")");
		return out.toString();
	}
}
