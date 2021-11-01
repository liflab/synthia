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

/**
 * @ingroup Examples
 */
package examples.grammar;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.synthia.grammar.GrammarSentence;
import ca.uqac.lif.synthia.random.RandomInteger;

/**
 * Generates character strings corresponding to sentences formed according
 * to a BNF grammar.
 * The simple grammar in this example, found in the file <tt>grammar.bnf</tt>,
 * is the following:
 * <pre>
 * &lt;S&gt;               := The &lt;adjective-group&gt; &lt;noun-verb&gt; the &lt;adjective-group&gt; &lt;noun&gt; . ;
 * &lt;adjective-group&gt; := &lt;observation&gt; &lt;size&gt; &lt;age&gt; &lt;color&gt; ;
 * &lt;noun-verb&gt;       := &lt;nv-singular&gt; | &lt;nv-plural&gt; ;
 * &lt;nv-singular&gt;     := &lt;noun&gt; &lt;verb&gt; ;
 * &lt;nv-plural&gt;       := &lt;nouns&gt; &lt;verbs&gt; ;
 * &lt;noun&gt;            := fox | &lt;dog&gt; | &lt;cat&gt; | bird ;
 * &lt;verb&gt;            := watches | plays with | runs after ;
 * &lt;nouns&gt;           := cats | dogs | birds | farmers ;
 * &lt;verbs&gt;           := play with | run after | watch ;
 * &lt;observation&gt;     := lovely | funny | ugly | quick | ε ;
 * &lt;size&gt;            := big | small | ε ;
 * &lt;age&gt;             := young | old | ε ;
 * &lt;color&gt;           := brown | black | white | grey | ε ;
 * &lt;dog&gt;             := poodle | pitbull | dog ;
 * &lt;cat&gt;             := siamese | Cheshire cat | burmese cat | cat ;
 * </pre>
 * <p>
 * A sample run of the program could be:
 * <pre>
 * The ugly small old black cats run after the funny brown pitbull . 
 * The small young grey fox runs after the ugly old Cheshire cat .  
 * The old grey bird watches the funny brown poodle .  
 * The funny cats watch the funny old fox .
 * &hellip; 
 * </pre>
 * @author Sylvain Hallé
 */
public class GrammarDemo
{
	public static void main(String[] args) throws InvalidGrammarException
	{
		/* Instantiate a Bullwinkle parser with the grammar from the file. */
		BnfParser parser = new BnfParser(GrammarDemo.class.getResourceAsStream("grammar.bnf"));
		parser.setStartRule("<S>");
		
		/* Create a GrammarSentence picker by passing this parser. */
		GrammarSentence gp = new GrammarSentence(parser, new RandomInteger());
		
		/* Show a few sentences derived from the grammar. */
		for (int i = 0; i < 10; i++)
		{
			System.out.println(gp.pick());
		}
	}
}
