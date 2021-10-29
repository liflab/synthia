/**
 * @ingroup Examples
 */
package examples.grammar;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.synthia.grammar.GrammarSentence;
import ca.uqac.lif.synthia.random.RandomInteger;

public class GrammarDemo {

	public static void main(String[] args) throws InvalidGrammarException
	{
		BnfParser parser = new BnfParser(GrammarDemo.class.getResourceAsStream("grammar.bnf"));
		parser.setStartRule("<S>");
		GrammarSentence gp = new GrammarSentence(parser, new RandomInteger());
		for (int i = 0; i < 10; i++)
		{
			System.out.println(gp.pick());
		}
	}

}
