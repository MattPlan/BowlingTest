/*
    A simple bowling score calculator
    
    Copyright (C) 2020 Sylvain Hallé
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    
    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package stev.bowling;

/**
 * Simple file showing the usage of the {@link Game} and {@link Frame} objects
 * to calculate and display the score in a bowling game. 
 */
public class GameDemo
{
	/**
	 * The main method of the program
	 * @param args Command line arguments (ignored)
	 */
	public static void main(String[] args)
	{
		Game g = new Game();
		g.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
		g.addFrame(new NormalFrame(2).setPinsDown(1, 10));
		g.addFrame(new NormalFrame(3).setPinsDown(1, 5).setPinsDown(2, 0));
		g.addFrame(new NormalFrame(4).setPinsDown(1, 1).setPinsDown(2, 9));
		g.addFrame(new NormalFrame(5).setPinsDown(1, 10));
		g.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 0));
		g.addFrame(new NormalFrame(7).setPinsDown(1, 0).setPinsDown(2, 6));
		g.addFrame(new NormalFrame(8).setPinsDown(1, 10));
		g.addFrame(new NormalFrame(9).setPinsDown(1, 2).setPinsDown(2, 8));
		g.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 3));
		System.out.println(g);
	}
}
