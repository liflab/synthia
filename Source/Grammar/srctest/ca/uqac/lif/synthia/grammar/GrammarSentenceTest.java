/*
    Synthia, a data structure generator
    Copyright (C) 2019-2021 Laboratoire d'informatique formelle
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
package ca.uqac.lif.synthia.grammar;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.synthia.random.RandomInteger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class GrammarSentenceTest
{
	@Test
	public void duplicateWithState() throws BnfParser.InvalidGrammarException
	{
		BnfParser b_parser = new BnfParser(GrammarSentenceTest.class.getResourceAsStream("grammar.bnf"));
		b_parser.setStartRule("<S>");
		GrammarSentence grammar_sentence = new GrammarSentence(b_parser, new RandomInteger());
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
		GrammarSentence grammar_sentence = new GrammarSentence(b_parser, new RandomInteger());
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
