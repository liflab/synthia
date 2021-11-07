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
package examples.gui;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.synthia.grammar.GrammarSequence;
import ca.uqac.lif.synthia.random.BiasedRandomFloat;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.test.Action;
import ca.uqac.lif.synthia.test.Monkey;
import ca.uqac.lif.synthia.test.Monkey.SequenceMonkey;

/**
 * A variant of {@link CalculatorMonkeyRandom} where the monkey produces actions
 * according to a formal grammar.
 * <p>
 * In this variant, the calculator only accepts syntactically valid sequences of
 * operations. This is done by calling {@link Calculator#checkSyntax()}, which
 * enables syntax checking of the button clicks. However, in such a case,
 * doing random clicks of any button at any moment is very likely to be
 * invalid, and the original monkey will only discover
 * {@link IllegalArgumentExceptions} after one or two clicks. These unstructured
 * interactions do not last long enough to uncover any other bugs.
 * <p>
 * In the present program, the monkey is given a {@link GrammarSequence} picker that
 * produces actions according to a formal grammar that characterizes syntactically
 * valid sequences of clicks. The grammar is defined in <tt>calculator-valid.bnf</tt>
 * and is defined as follows: 
 * <pre>
 * &lt;S&gt;         := &lt;number&gt; &lt;op&gt; &lt;number&gt; &lt;inop&gt; ;
 * &lt;inop&gt;      := &lt;op&gt; &lt;number&gt; &lt;inop&gt; | = ;
 * &lt;number&gt;    := &lt;integer&gt; &lt;digit&gt; &lt;rest&gt; | &lt;integer&gt; ;
 * &lt;integer&gt;   := &lt;nzdigit&gt; &lt;rest&gt; ;
 * &lt;rest&gt;      := &lt;digit&gt; &lt;rest&gt; | ε ;
 * &lt;digit&gt;     := 0 | &lt;nzdigit&gt; ;
 * &lt;nzdigit&gt;   := 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 ;
 * &lt;op&gt;        := + | − | × | ÷ ;
 * </pre>
 * Each terminal token of the grammar is associated with a
 * {@link WidgetAction.ClickAction ClickAction} on the button with corresponding
 * label. As a result, the monkey only produces syntactically valid interactions,
 * and can uncover the overflow bug. A possible execution of the program is the
 * following:
 * <pre>
 * Attempt 0
 * 3−7−97−679630
 * examples.gui.Calculator$OverflowException: Overflow
 * Sequence: [3, −, 7, −, 9, 7, −, 6, 7, 9, 6, 3, 0, =]
 * Found a examples.gui.Calculator$OverflowException: Overflow
 * Sequence : [3, −, 7, −, 9, 7, −, 6, 7, 9, 6, 3, 0, =]
 * </pre>
 * One can change the filename to <tt>calculator-invalid.bnf</tt>, which
 * contains this alternate grammar:
 * <pre>
 * &lt;S&gt;         := &lt;number&gt; | &lt;op&gt; ;
 * &lt;number&gt;    := 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | . ;
 * &lt;op&gt;        := + | − | × | ÷ | = ;                                                                               
 * </pre>
 * This grammar is equivalent to the original monkey in that any
 * button can be pressed at any time. A possible execution of the program on
 * this grammar is the following: 
 * <pre>
 * Attempt 0
 * java.lang.IllegalArgumentException: Cannot punch an operator now
 * Sequence: [÷]
 * Found a java.lang.IllegalArgumentException: Cannot punch an operator now
 * Sequence : [÷]
 * </pre>
 * As one can see, the monkey does not even get past the first click.
 * 
 * @author Sylvain Hallé
 * @see CalculatorMonkeyRandom
 * @ingroup Examples
 */
public class CalculatorMonkeyGrammar
{

	public static void main(String[] args) throws InvalidGrammarException
	{
		/* Instantiate a Bullwinkle parser with the grammar from the file. */
		BnfParser parser = new BnfParser(CalculatorMonkeyGrammar.class.getResourceAsStream("calculator-valid.bnf"));
		parser.setStartRule("<S>");
		
		Calculator window = new Calculator().disableNumberFormatException().hasOverflow().checkSyntax();
		RandomFloat b_rf = new BiasedRandomFloat(2).setSeed(0);
		RandomInteger b_ri = new RandomInteger().setSeed(0);
		GrammarSequence<Action> actions = new GrammarSequence<Action>(parser, b_ri) {
			public Action get(String token) {
				return new WidgetAction.ClickAction(window.getButton(token));
			}
		};
		Monkey m = new SequenceMonkey(window, actions, b_rf, System.out).shrink(false);
		window.setVisible(true);
		if (m.check())
		{
			System.out.println("No error found");
		}
		else
		{
			System.out.println("Found a " + m.getException());
			System.out.println("Sequence : " + m.getShrunk());
		}	
	}
}
