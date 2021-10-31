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
package examples.apache;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.sequence.Knit;
import ca.uqac.lif.synthia.sequence.MarkovChain;
import ca.uqac.lif.synthia.string.AsString;
import ca.uqac.lif.synthia.string.RandomString;
import ca.uqac.lif.synthia.string.StringPattern;
import ca.uqac.lif.synthia.tree.MarkovReader;
import ca.uqac.lif.synthia.tree.StringNodePicker;
import ca.uqac.lif.synthia.util.AsLong;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Tick;
import examples.graphs.BarabasiAlbert;

/**
 * Main program that generates the simulated log file interleaving multiple
 * visitor instances.
 * 
 * <h4>Site map</h4>
 * The generation of the site map recycles the Barabási–Albert model from
 * {@linkplain BarabasiAlbert another example}. A scale-free graph of a
 * randomly selected number of nodes is first generated; the graph results in
 * a few nodes with high degree (corresponding to "main" pages) and a majority
 * of nodes with low degree. A {@link StringPattern} picker produces filenames
 * for each node using randomly generated strings and appending the
 * <tt>html</tt> extension to each of them. An example of such a map is shown
 * below:
 * <p>
 * <img src="{@docRoot}/doc-files/apache/map.png" width="50%" alt = "Example of site map" />
 * <p>
 * The site map is then turned into a {@link MarkovChain}, where each
 * undirected edge between vertices A and B stands both for a link between A and
 * B, and vice versa. Each outgoing edge of a given vertex is given the same
 * probability.
 * 
 * <h4>Visitors</h4>
 * 
 * A visitor is a {@link LogLinePicker} that is fed with a picker for its IP
 * address, another one providing page names, and a last one providing a
 * timestamp. 
 * <p>
 * The IP addresses for each
 * visitor are pseudo-randomly generated using a {@link StringPattern} picker,
 * with different probabilities associated to different regions. For the
 * purpose of the simulation:
 * <ul>
 * <li>addresses of the form <tt>11.x.x.x</tt> are considered to be in the USA
 * and have a 1/2 probability of being generated</li>
 * <li>addresses of the form <tt>10.x.x.x</tt> are considered to be in Canada
 * and have a 1/3 probability of being generated</li>
 * <li>addresses in the range form <tt>20.x.x.x</tt>-<tt>60.x.x.x</tt> are
 * considered to be in Europe and have a 1/6 probability of being generated</li>
 * </ul>
 * The picker for page names is an instance of the Markov chain defined
 * previously. It should be noted that the {@link VisitorPicker} gives a
 * distinct copy of the chain to each visitor; hence, each visitor does its own
 * independent random walk, but the site map they use is the same for all.
 * <p>
 * Finally, the picker for the timestamp is an instance of {@link Tick}, which
 * randomly increments a timestamp counter every time it is called. Note that
 * the same <tt>Tick</tt> instance is shared by all visitors, which ensures
 * that the global timestamp increments on each page load, regardless of which
 * visitor requested the page.
 * 
 * <h4>Generating the log</h4>
 * To simulate the action of multiple visitors in the site, it then suffices to
 * pass the {@link VisitorPicker} to an instance of {@link Knit}, which takes
 * care of creating new visitors and interleaving the progression of each of
 * them. Each call to {@link Knit#pick() pick()} selects one visitor, takes a
 * transition, and outputs the log line resulting from the corresponding
 * simulated page request.
 * <p>
 * A typical run of the program looks like this:
 * <pre>
 * 11.205.232.45 - - [26-Oct-1985 01:21:00 EDT 0] "GET J0.html HTTP/2" 200 1000
 * 11.130.222.207 - - [26-Oct-1985 01:21:04 EDT 0] "GET Xv.html HTTP/2" 200 1000
 * 11.130.222.207 - - [26-Oct-1985 01:21:09 EDT 0] "GET 9y.html HTTP/2" 200 1000
 * 11.16.2.198 - - [26-Oct-1985 01:21:17 EDT 0] "GET Xv.html HTTP/2" 200 1000
 * 11.130.222.207 - - [26-Oct-1985 01:21:27 EDT 0] "GET t0.html HTTP/2" 200 1000
 * 45.86.208.44 - - [26-Oct-1985 01:21:29 EDT 0] "GET b7.html HTTP/2" 200 1000
 * 10.112.251.183 - - [26-Oct-1985 01:21:34 EDT 0] "GET b7.html HTTP/2" 200 1000
 * 11.130.222.207 - - [26-Oct-1985 01:21:40 EDT 0] "GET 9y.html HTTP/2" 200 1000
 * 11.16.2.198 - - [26-Oct-1985 01:21:41 EDT 0] "GET 9y.html HTTP/2" 200 1000
 * 45.86.208.44 - - [26-Oct-1985 01:21:45 EDT 0] "GET 9y.html HTTP/2" 200 1000
 * &hellip;
 * </pre>
 * 
 * <h4>Exercises</h4>
 * <ol>
 * <li>Modify the scenario so that each page is assigned a randomly selected
 * size, instead of the constant "1000" that appears on each line. For a given
 * page, make sure that the same size is always shown.</li>
 * <li>Modify the Markov chain to add a sink state accessible from every state,
 * and which will cause a visitor to "leave" the site (i.e. no longer produce
 * any new page request).</li>
 * <li>Modify the scenario so that some pages actually do not exist, and result
 * in a return code of 404 instead of 200 (next to last element of each log
 * line).</li>
 * </ol>
 * @ingroup Examples
 */
public class GenerateLog
{

	public static void main(String[] args)
	{
		int seed = 10;
		
		// Generate a site map, then turn it into a Markov chain
		Picker<Integer> size = new RandomInteger(15, 20).setSeed(seed);
		RandomBoolean coin = new RandomBoolean();
		coin.setSeed(0);
		BarabasiAlbert<String> map_gen = new BarabasiAlbert<String>(
				new StringNodePicker(new StringPattern("{$0}.html", new RandomString(new RandomInteger(2,3)).setSeed(seed))), 
				size, coin);
		MarkovChain<String> chain = new MarkovReader<String,String>() {
			public Picker<String> getPicker(String s) {
				return new Constant<String>(s);
			}
		}.asMarkovChain(map_gen.pick(), RandomFloat.instance);
		
		// Generate a pattern of IP addresses
		StringPattern ip = new StringPattern("{$0}.{$1}.{$2}.{$3}",
				new Choice<String>(RandomFloat.instance)
					.add("10", 1d/3).add("11", 1d/2).add(new AsString(new RandomInteger(20, 61)), 1d/6),
				new AsString(new RandomInteger(0, 256)), 
				new AsString(new RandomInteger(0, 256)), 
				new AsString(new RandomInteger(0, 256))
				);
		
		// Interleave multiple visitor instances
		Knit<LogLine> all = new Knit<LogLine>(new VisitorPicker(
				new AsLong(new Tick(499152060000L, new RandomInteger(1000, 10000))), ip, chain),
				new RandomBoolean(0.5), new RandomBoolean(0.1), RandomFloat.instance);
		
		// Print the first few log lines
		for (int i = 0; i < 25; i++)
		{
			System.out.println(all.pick());
		}
	}

}
