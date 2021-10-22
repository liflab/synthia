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
package oscilloscope;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A simple {@link JFrame} that simulates the operation of an oscilloscope.
 * This class produces a window, showing a square grid on which two-dimensional
 * points can be added and displayed. The class is used to display the
 * patterns produced by the various pickers showcased in this package. An
 * example of the display produced by the oscilloscope is:
 * <p>
 * <img src="{@docRoot}/doc-files/oscilloscope/Lissajous_r_6_5_p_pi_4.png" alt="Oscilloscope window" />
 */
public class Oscilloscope extends JFrame
{
	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;
	
	protected final Screen m_screen;
	
	protected static final Color s_darkGreen = new Color(0, 192, 0);
	
	public Oscilloscope()
	{
		super();
		m_screen = new Screen();
		add(m_screen, BorderLayout.CENTER);
		setTitle("Oscilloscope");
    setSize(300, 300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Oscilloscope addPoints(List<float[]> points)
	{
		for (float[] p : points)
		{
			// We center the screen and flip it upside down
			m_screen.addPoint((p[0] + 1) / 2f, (1 - p[1]) / 2f);
		}
		return this;
	}
	
	protected static class Screen extends JPanel implements ActionListener
	{
		/**
		 * Dummy UID.
		 */
		private static final long serialVersionUID = 1L;
		
		protected Set<Point> m_points;

		public Screen()
		{
			super();
			m_points = new HashSet<Point>();
			this.setPreferredSize(new Dimension(300, 300));
		}
		
		protected void addPoint(float x, float y)
		{
			Point p = new Point(x, y);
			m_points.add(p);
		}

		private void doDrawing(Graphics g)
		{
			double width = this.getWidth();
			double height = this.getHeight();
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(Color.black);
			g2d.fillRect(0, 0, (int) width, (int) height);
			g2d.setPaint(Color.darkGray);
			for (int i = 0; i <= height; i += height / 6)
			{
				g2d.drawLine(0, i, (int) width, i);
			}
			for (int i = 0; i <= width; i += width / 6)
			{
				g2d.drawLine(i, 0, i, (int) height);
			}
			for (Point p : m_points)
			{
				int x = (int) (width * p.getX());
				int y = (int) (height * p.getY());
				if (x % ((int) (width / 6)) == 0 || y % ((int) (height / 6)) == 0)
				{
					g2d.setPaint(s_darkGreen);
				}
				else
				{
					g2d.setPaint(Color.green);
				}
				g2d.drawLine(x, y, x, y);
			}
		}

		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			doDrawing(g);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}
	}
	
	protected static class Point
	{
		float m_x;
		
		float m_y;
		
		public Point(float x, float y)
		{
			super();
			m_x = x;
			m_y = y;
		}
		
		public float getX()
		{
			return m_x;
		}
		
		public float getY()
		{
			return m_y;
		}
	}
}
