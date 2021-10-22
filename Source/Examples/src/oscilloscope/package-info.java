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

/**
 * Examples combining the {@link SineWave} picker with a two-dimensional
 * {@link PrismPicker}. When two sine waves are used as the input of the prism
 * picker, they act as a waveform generator for the x and y coordinates of
 * points. When a set of such points is generated (using a {@link ComposeList}
 * picker), and plotted, the result simulates the operation of a physical
 * device called an
 * <a href="https://en.wikipedia.org/wiki/Oscilloscope">oscilloscope</a>.
 * <p>
 * The package contains two examples:
 * <ul>
 * <li>The first shows how, by changing the wiring of pickers, one can create
 * {@linkplain Lissajous Lissajous figures} on the oscilloscope display.</li>
 * <li>The second shows how, by feeding the y-picker a time-dependent signal,
 * it is possible to demonstrate the principles of {@linkplain AMRadio
 * amplitude modulation} and {@linkplain FMRadio frequency modulation}.
 * </ul>
 */
package oscilloscope;