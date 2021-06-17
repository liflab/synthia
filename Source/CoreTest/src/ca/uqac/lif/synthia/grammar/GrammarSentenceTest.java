package ca.uqac.lif.synthia.grammar;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.synthia.random.RandomIndex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrammarSentenceTest
{
	@Test
	public void duplicateWithState() throws BnfParser.InvalidGrammarException
	{
		BnfParser b_parser = new BnfParser(GrammarSentenceTest.class.getResourceAsStream("grammar.bnf"));
		b_parser.setStartRule("<S>");
		GrammarSentence grammar_sentence = new GrammarSentence(b_parser, new RandomIndex());
		for (int i = 0; i < 10; i++)
		{
			grammar_sentence.pick();
		}
		GrammarSentence grammar_sentence_copy = grammar_sentence.duplicate(true);
		Assertions.assertEquals(grammar_sentence.pick(), grammar_sentence_copy.pick());
	}

	@Test
	public void duplicateWithoutState() throws BnfParser.InvalidGrammarException
	{
		BnfParser b_parser = new BnfParser(GrammarSentenceTest.class.getResourceAsStream("grammar.bnf"));
		b_parser.setStartRule("<S>");
		GrammarSentence grammar_sentence = new GrammarSentence(b_parser, new RandomIndex());
		for (int i = 0; i < 10; i++)
		{
			grammar_sentence.pick();
		}
		GrammarSentence grammar_sentence_copy = grammar_sentence.duplicate(false);
		Assertions.assertNotEquals(grammar_sentence.pick(), grammar_sentence_copy.pick());
		grammar_sentence.reset();
		grammar_sentence_copy.reset();
		for (int i = 0; i < 10; i++)
		{
			Assertions.assertEquals(grammar_sentence.pick(), grammar_sentence_copy.pick());
		}
	}
}
