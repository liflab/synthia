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

import ca.uqac.lif.synthia.random.BiasedRandomFloat;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.widget.GuiAction;
import ca.uqac.lif.synthia.widget.Monkey;
import ca.uqac.lif.synthia.widget.WidgetAction;

/**
 * Performs <a href="https://en.wikipedia.org/wiki/Monkey_testing">monkey
 * testing</a> over a simple calculator, using Synthia's {@link Monkey}.
 * <p>
 * The monkey is instantiated with an equal probability of clicking on any
 * of the calculator's 16 buttons at any moment. It runs until the calculator
 * throws an exception, after which it will attempt to <em>shrink</em> the
 * sequence --i.e., to find a subsequence that still causes the exception to
 * be thrown.
 * <p>
 * The picker for actions requires a <tt>Picker&lt;Float&gt;</tt> in order
 * to generate a subsequence. Instead of supplying it with a uniform
 * {@link RandomFloat} in the interval [0,1], we rather pass a
 * {@link BiasedRandomFloat} that gives preference to values closer to 1.
 * This has for effect that sub-sequences will contain more actions from
 * the <em>end</em> of the reference sequence instead of the beginning.
 * This is based on the intuition that errors are more likely to occur
 * because of actions close to the occurrence of the exception. 
 * <p>
 * Depending on the seed and on the faults enabled in the {@link Calculator},
 * four outcomes are likely:
 * <ol>
 * <li><a href="#noerror">no error found</a></li>
 * <li><a href="#number">number format exception</a></li>
 * <li><a href="#overflow">overflow</a></li>
 * <li><a href="#divzero">division by zero</a></li>
 * </ol>
 * 
 * <a name="noerror"></a><h4>No error found</h4>
 * If the monkey finds no error, its printout will look like this:
 * <pre>
 * Attempt 0
 * 48÷14==5−1÷36620×60603÷73=64÷6.3816=6=1÷95÷=9698821269.5845÷3439101.4270=93515÷2×7×5892×−426501=5061
 * Attempt 1
 * 72121=689=5÷16÷3.÷69087÷500÷.−=1617=×1=−÷5.92÷85−016=6=906840366.030−665494282×−0006464÷×240=9−22568
 * Attempt 2
 * ×1÷8−95÷438454.×2.4941082×678÷−80=3.÷148×.4288÷6223−629400−7=065544739.5.65×7.2×660−36.×59×22945449×
 * Attempt 3
 * ×498×505×−5445.1.08=040622×562749×105×6644×66÷÷−×264897−7=.84−9−60972067464.4−0003=979.×3567÷−8×55=4
 * Attempt 4
 * 870−6÷−111−7×.23398−452÷=21×0170.×6.=×.53÷=947=82679÷6627÷38.2÷61..2×4=809−÷2−×22×58147480×1−×9788−1
 * 
 * No error found
 * </pre>
 * 
 * <a name="number"></a><h4>Number format exception</h4>
 * 
 * If the number format fault is enabled, a possible execution of the monkey is the following:
 * <pre>
 * Attempt 0
 * 48÷14==5−1÷36620×60603÷73=64÷6.3816=6=1÷95÷=9698821269.5845÷3439101.4270=93515÷2×7×5892×−426501=5061
 * Attempt 1
 * 72121=689=5÷16÷3.÷69087÷500÷.
 * java.lang.NumberFormatException: For input string: "."
 * Sequence: [7, 2, 1, 2, 1, =, 6, 8, 9, =, 5, ÷, 1, 6, ÷, 3, ., ÷, 6, 9, 0, 8, 7, ÷, 5, 0, 0, ÷, ., −]
 * 8÷369÷−
 * 285.67−
 * 72693÷−
 * 719÷65−
 * 729.8÷−
 * 72119516360÷5.−
 * 7211=6÷÷9870÷.
 * Sequence: [7, 2, 1, 1, =, 6, ÷, ÷, 9, 8, 7, 0, ÷, ., −]
 * 77−
 * ÷7−
 * 28−
 * 767
 * 71−
 * 2=÷987−
 * 7÷÷980−
 * 716÷70−
 * 2÷970÷−
 * 72687÷−
 * 7216÷÷870÷−
 * 2116÷÷987.−
 * 721=÷÷9870−
 * 7211=÷÷97.−
 * 21=6÷÷98÷.
 * Sequence: [2, 1, =, 6, ÷, ÷, 9, 8, ÷, ., −]
 * ÷−
 * =−
 * ÷−
 * 2−
 * 6−
 * 198÷−
 * 2=6÷−
 * 2=÷8−
 * 1÷÷÷−
 * 6÷9÷−
 * =6÷98÷.
 * Sequence: [=, 6, ÷, 9, 8, ÷, ., −]
 * =−
 * ÷−
 * ÷.
 * .
 * Sequence: [., −]
 * 
 * Found a java.lang.NumberFormatException: For input string: "."
 * Sequence : [., −]
 * </pre>
 * Note how the monkey finds a first long sequence producing the exception,
 * and then iterates over smaller sequences. Ultimately, only two clicks
 * suffice to trigger the exception.
 * 
 * <a name="overflow"></a><h4>Overflow</h4>
 * 
 * If the overflow fault is enabled using {@link Calculator#hasOverflow()}, a
 * possible execution is:
 * <pre>
 * Attempt 0
 * 48÷14++6−1÷3×63.1×.×0−÷7−+6446=382×+6+2÷95÷+9698821369
 * examples.gui.Calculator$OverflowException: Overflow
 * Sequence: [4, 8, ÷, 1, 4, +, +, 6, −, 1, ÷, 3, ×, 6, 3, ., 1, ×, ., ×, 0, −, ÷, 7, −, +, 6, 4, 4, 6, =, 3, 8, 2, ×, +, 6, +, 2, ÷, 9, 5, ÷, +, 9, 6, 9, 8, 8, 2, 1, 3, 6, 9, =]
 * ÷×3×64=2×69÷=
 * 48÷133104369
 * Sequence: [4, 8, ÷, 1, 3, 3, 1, 0, 4, 3, 6, 9, =]
 * 80=
 * 13=
 * 40=
 * 83=
 * 84=
 * 4÷334=
 * 48316=
 * 41346=
 * ÷3049=
 * 48÷13=
 * 48÷33043=
 * 48131043
 * Sequence: [4, 8, 1, 3, 1, 0, 4, 3, =]
 * 3=
 * 8=
 * 34
 * 4=
 * 1=
 * 830=
 * 343=
 * 404=
 * 403=
 * 403=
 * 13103=
 * 41304=
 * 43104=
 * 81310=
 * 48314=
 * 481310438=
 * 4=
 * 4=
 * 1=
 * 3=
 * 413=
 * 430=
 * 131=
 * 103=
 * 480=
 * 13143=
 * 48104=
 * 41304=
 * 41314=
 * 83103=
 * 481310434=
 * 4=
 * 40
 * 1=
 * 3=
 * 1310
 * 114=
 * 313=
 * 414=
 * 133=
 * 41104=
 * 41314=
 * 41343=
 * 13104=
 * 13143=
 * 48131043
 * Found a examples.gui.Calculator$OverflowException: Overflow
 * Sequence : [4, 8, 1, 3, 1, 0, 4, 3, =]
 * </pre>
 * 
 * <a name="divzero"></a><h4>Division by zero</h4>
 * 
 * The last type of error is a division by zero, which produces an execution
 * like the following:
 * <pre>
 * Attempt 0
 * 48÷14++6−1÷3×63.1×.×0−÷7−+6446=382×+6+2÷95÷+9698821369=5855÷35−91.2=437.+9−62542×7×6993×0526501=6.×2
 * Attempt 1
 * 73222+×9÷+542×÷−=÷×9.974500÷=0+2627+×1+0÷5=93÷850.16+6+÷.
 * java.lang.IllegalArgumentException: Division by zero
 * Sequence: [7, 3, 2, 2, 2, +, ×, 9, ÷, +, 5, 4, 2, ×, ÷, −, =, ÷, ×, 9, ., 9, 7, 4, 5, 0, 0, ÷, =, 0, +, 2, 6, 2, 7, +, ×, 1, +, 0, ÷, 5, =, 9, 3, ÷, 8, 5, 0, ., 1, 6, +, 6, +, ÷, ., ×]
 * 2×÷÷.
 * Sequence: [2, ×, ÷, ÷, ., ÷, 0, 2, +, ×, +, =, 3, ×]
 * 2÷×
 * 2+×
 * 2=×
 * 2=×
 * ÷÷×
 * ×.÷++3×
 * 2×÷÷2××
 * 2÷÷0
 * Sequence: [2, ÷, ÷, 0, +, =, ×]
 * ×
 * 0
 * ×
 * ×
 * ×
 * 2÷×
 * 2=×
 * 2=×
 * ÷+×
 * ÷+×
 * 2÷+=×
 * 2÷0
 * Sequence: [2, ÷, 0, +, ×]
 * +
 * +
 * ×
 * ×
 * ×
 * 2÷
 * 2÷
 * +×
 * 2×
 * 2×
 * 0+×
 * 2÷×
 * 0+×
 * 0+×
 * 0+×
 * 2÷0×
 * 2
 * ×
 * 0
 * 2
 * 0×
 * 0×
 * +×
 * +×
 * +×
 * ÷+×
 * 2+×
 * 2+×
 * 2+×
 * ÷0
 * Sequence: [÷, 0, ×]
 * 
 * Found a java.lang.IllegalArgumentException: Division by zero
 * Sequence : [÷, 0, ×]
 * </pre>
 * Note how, again, the monkey zeroes in on the shortest possible sequence that
 * triggers the error (division, zero, followed by any operator).
 * 
 * @author Sylvain Hallé
 */
public class CalculatorMonkey
{
	public static void main(String[] args)
	{
		RandomFloat rf = new RandomFloat().setSeed(50000);
		Calculator window = new Calculator().disableNumberFormatException().hasOverflow();
		Choice<GuiAction> actions = new Choice<GuiAction>(rf);
		for (String label : Calculator.BUTTON_LABELS)
		{
			actions.add(new WidgetAction.ClickAction(window.getButton(label)), 1f / 16);
		}
		RandomFloat b_rf = new BiasedRandomFloat(2).setSeed(0);
		Monkey m = new Monkey(window, actions, b_rf, System.out);
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
