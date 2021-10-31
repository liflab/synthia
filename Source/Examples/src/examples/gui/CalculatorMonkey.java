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

import ca.uqac.lif.synthia.random.AffineTransform.AffineTransformFloat;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.widget.Delay;
import ca.uqac.lif.synthia.widget.GuiAction;
import ca.uqac.lif.synthia.widget.WidgetAction;

public class CalculatorMonkey
{

	public static void main(String[] args)
	{
		CalculatorWindow window = new CalculatorWindow();
		Delay d = new Delay(new AffineTransformFloat(RandomFloat.instance, 2f, 0f));
		Choice<GuiAction> monkey = new Choice<GuiAction>(RandomFloat.instance);
		float p = 1f / 15;
		monkey.add(new WidgetAction.ClickAction(window.getButton("0")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("1")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("2")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("3")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("4")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("5")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("6")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("7")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("8")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("9")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("+")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("-")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("*")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("/")), p);
		monkey.add(new WidgetAction.ClickAction(window.getButton("=")), p);
		while (true)
		{
			monkey.pick().doAction();
			d.pick();
		}
	}

}
