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

import java.util.List;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Reactive;

/**
 * Abstract parent class for pickers using a
 * <a href="https://github.com/sylvainhalle/Bullwinkle">Bullwinkle</a> parser.
 * @author Sylvain Hallé
 *
 * @param <T> The type of objects in the sequences to produce
 */
public abstract class ParserPicker<T> implements Picker<List<T>>
{
	/**
	 * The parser whose grammar will be used to generate expressions
	 */
	/*@ non_null @*/ protected BnfParser m_parser;
	
	/**
	 * A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	/*@ non_null @*/ protected Reactive<Integer,Integer> m_indexPicker;

	/**
	 * Creates a new instance of the picker
	 * @param parser The parser whose grammar will be used to generate expressions
	 * @param picker A picker used to choose the cases from the grammar rules
	 * to expand
	 */
	public ParserPicker(/*@ non_null @*/ BnfParser parser, /*@ non_null @*/ Reactive<Integer,Integer> picker)
	{
		super();
		m_parser = parser;
		m_indexPicker = picker;
	}
	
	@Override
	public void reset()
	{
		m_indexPicker.reset();
	}
}
