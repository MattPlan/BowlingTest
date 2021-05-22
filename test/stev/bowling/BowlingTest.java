package stev.bowling;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The following tests have been produced using the framework JUnit5.
 * 
 * @author Matthieu Planas - Florian Zeni
 */

public class BowlingTest {
	
	private Game game;
	private Frame normalFrame;
	private Frame lastFrame;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		normalFrame = new NormalFrame(1);
		lastFrame = new LastFrame(10);
//		for (int i=1; i<10; i++) {
//			game.addFrame(new NormalFrame(i));
//		}
//		game.addFrame(new LastFrame(10));	
	}

	@After
	public void tearDown() throws Exception {
		game = null;
		normalFrame = null;
		lastFrame = null;
	}

	
	/*---------------------------------------------------------------------------------------------
	//-----------------------------------------NormalFrame-----------------------------------------
	//-------------------------------------------------------------------------------------------*/
	
	//-----------------------------------NormalFrame constructor-----------------------------------
	
	/**
	 * A game of bowling is represented in this implementation by ten frames comprised of nine 
	 * NormalFrame (from 1 to 9) and one LastFrame (10).
	 * For the constructor of NormalFrame, all other values than the ones in range 1-9 to initialize 
	 * the frames are expected to throw a BowlingException.
	 */
	
	/**
	 * Test that new NormalFrame(x) with x inside the range 1-9 does not throw a BowlingException.
	 * @param x  the frame number
	 */
	@ParameterizedTest
	@ValueSource(ints = {1, 5, 9})
	public void testNormalFrame_constructor_validParameters(int x) {
		new NormalFrame(x);
	}
	
	/**
	 * Test that new NormalFrame(x) with x outside the range 1-9 throws a BowlingException.
	 * @param x  the frame number
	 */
	@ParameterizedTest
	@ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 10, Integer.MAX_VALUE})
	public void testNormalFrame_constructor_wrongParameters(int x) {
		assertThrows("Out of bound parameters : ", BowlingException.class,() -> new NormalFrame(x));
	}
	
	
	//-------------------------------- NormalFrame.getFrameNumber()--------------------------------
	
	/**
	 * There are 10 frames in a standard bowling game. The method getFrameNumber() should return an
	 * integer between 1 and 10 which is equal to the number of the frame.
	 */
	
	/**
	 * Test that NormalFrame.getFrameNumber() returns the correct number equals to the frame number.
	 */
	@Test
	public void testNormalFrame_getFrameNumber() {
		int[] expected = new int[9];
		int[] result = new int[expected.length];
		
		for (int i=1; i<10; i++) {
			expected[i-1] = i; // index i-1 because it starts at 0 in Java
			result[i-1] = new NormalFrame(i).getFrameNumber();
		}
		
		assertArrayEquals("Results of getFrameNumber don't match expected values : ", expected, result);
	}
	
	
	//-------------------------NormalFrame.setPinsDown(int roll, int score)------------------------
	
	/**
	 * There are two rolls per frame. The method setPinsDown(int roll, int score) is setting the
	 * number of pins down (the score) for a given roll in the frame.
	 * The number of the roll is either 1 or 2 and the score is between 0 and 10.
	 */
	
	/**
	 * Test that a value like 1 or 2 for the roll's number in NormalFrame.setPinsDown and a number for 
	 * the score inside the range 0-10 does not throw a BowlingException.
	 * @param x  the first roll number for the frame
	 * 		  y  the number of pins down (the score) for the first roll
	 * 		  a  the second roll number 
	 * 		  b  the score for the second roll
	 */
	@ParameterizedTest
	@CsvSource({"1,0,2,0", "1,1,2,5", "1,0,2,10"})
	public void testNormalFrame_setPinsDown_validParameters(int x, int y, int a, int b) {
		new NormalFrame(1).setPinsDown(x, y).setPinsDown(a, b);
	}
	
	/**
	 * Test that a value other than 1 or 2 for the roll's number in NormalFrame.setPinsDown throws
	 * a BowlingException.
	 * Plus, also test that number outside the range 0-9 for the score throws a BowlingException.
	 * @param x  the roll number for the frame
	 * 		  y  the number of pins down (the score)
	 */
	@ParameterizedTest
	@CsvSource({"-1,1", "0,5", "3,5", "1,11", "1,-2"})
	public void testNormalFrame_setPinsDown_wrongParameters(int x, int y) {
		assertThrows("Out of bound parameters : ", BowlingException.class, 
				() -> new NormalFrame(1).setPinsDown(x, y));
	}
	
	/**
	 * Test that switching order of declaration for roll's number throws a BowlingException.
	 */
	@Test
	public void testNormalFrame_setPinsDown_inversedRollNumber() {
		assertThrows("Wrong order for rolls : ", BowlingException.class, 
				() -> new NormalFrame(1).setPinsDown(2, 1).setPinsDown(1, 1));
	}
	
	/**
	 * Test that you can't declare a score for the 2nd roll before having declared one for the 1st
	 * roll. Throws a BowlingException.
	 */
	@Test
	public void testNormalFrame_setPinsDown_declaringSecondRollFirst() {
		assertThrows("Can't start setting the score with the second roll : ", BowlingException.class, 
				() -> new NormalFrame(1).setPinsDown(2, 1));
	}
	
	/**
	 * Test that declaring a score higher than 10 with the sum of the rolls' score for a frame is 
	 * throwing a BowlingException.
	 */
	@Test
	public void testNormalFrame_setPinsDown_sumHigherThan10() {
		assertThrows("Sum of scores is higher than 10 in one frame : ", BowlingException.class, 
				() -> new NormalFrame(1).setPinsDown(1, 8).setPinsDown(2, 8));
	}
	
	/**
	 * Test that we can't declare a score in the second roll if the score in the first roll is equal
	 * to 10. Throws a BowlingException.
	 */
	@Test
	public void testNormalFrame_setPinsDown_firstRollScoreHigherThan10() {
		assertThrows("Can't set the score in 2nd roll if it is of 10 in 1st roll : ", BowlingException.class, 
				() -> new NormalFrame(1).setPinsDown(1, 10).setPinsDown(2, 0));
	}
	
	
	//---------------------------------NormalFrame.countPinsDown()---------------------------------
	
	/**
	 * There are two rolls per frame. The method countPinsDown() returns the score of the frame 
	 * between 0 and 10. After a call to the method setPinsDown(int roll, int score) this method 
	 * returns the sum of the score of the two rolls.
	 */
	
	/**
	 * Test that after the initialization of a frame, countPinsDown returns 0 as the number of pins
	 * down and does not throw a BowlingException.
	 */
	@Test
	public void testNormalFrame_countPinsDown_afterInitialization() {
		assertEquals("Wrong number of pins down after initialization : ", 0, 
				new NormalFrame(1).countPinsDown());
	}
	
	/**
	 * Test that after setting a score for the first roll, countPinsDown returns the correct number 
	 * of pins down and does not throw a BowlingException.
	 */
	@Test
	public void testNormalFrame_countPinsDown_afterScoreFirstRoll() {
		assertEquals("Wrong number of pins down after first roll : ", 2, 
				normalFrame.setPinsDown(1,2).countPinsDown());
	}
	
	/**
	 * Test that after setting a score for the two rolls of a frame, countPinsDown returns the correct 
	 * sum of pins down and does not throw a BowlingException.
	 */
	@Test
	public void testNormalFrame_countPinsDown_afterScoreForAFrame() {
		assertEquals("Wrong number of pins down after first roll : ", 5, 
				normalFrame.setPinsDown(1,2).setPinsDown(2,3).countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 0 after a reset of the frame.
	 */
	@Test
	public void testNormalFrame_countPinsDown_onReset_returns0() {
		normalFrame.setPinsDown(1, 5).setPinsDown(2,  3);
		normalFrame.reset();
		assertEquals("Wrong number of pins down after a reset : ", 0, normalFrame.countPinsDown());
	}
	

	//-----------------------------------NormalFrame.countRolls()----------------------------------
	
	/**
	 * There are two rolls per frame. The method countRolls() is returning an integer between 0 
	 * and 2 depending on the number of roll the player already made for this frame.
	 * After a reset of the frame, it should return 0.
	 */
	
	/**
	 * Test that NormalFrame.countRolls() returns 0 when the normalFrame is created.
	 */
	@Test
	public void testNormalFrame_countRolls_onNewFrame_returns0() {
		assertEquals("Wrong rolls count : ", 0, normalFrame.countRolls());
	}
	
	/**
	 * Test that NormalFrame.countRolls() returns 1 when a score between 1 and 9 has been set for
	 * the first roll.
	 */
	@Test
	public void testNormalFrame_countRolls_oneRoll_returns1() {
		normalFrame.setPinsDown(1, 1);
		assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
	}
	
	/**
	 * Test that NormalFrame.countRolls() returns 2 when a score between 1 and 9 has been set for
	 * the two rolls of the frame. The sum of the two score doesn't exceed 10.
	 */
	@Test
	public void testNormalFrame_countRolls_twoRollsWithSumLowerThan10_returns2() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 3);
		assertEquals("Wrong rolls count : ", 2, normalFrame.countRolls());
	}
	
	/**
	 * Test that NormalFrame.countRolls() returns 1 when a score of 0 has been set for the first 
	 * roll. That is when the player made a gutter.
	 */
	@Test
	public void testNormalFrame_countRolls_gutterOnFirstRoll_returns1() {
		normalFrame.setPinsDown(1, 0);
		assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
	}
	
	/**
	 * Test that NormalFrame.countRolls() returns 2 when a score of 0 has been set for the two 
	 * rolls of a frame. That is when the player made two gutters.
	 */
	@Test
	public void testNormalFrame_countRolls_gutterOnSecondRoll_returns2() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 0);
		assertEquals("Wrong rolls count : ", 2, normalFrame.countRolls());
	}
	
	/**
	 * Test that NormalFrame.countRolls() returns 0 after a reset of the frame.
	 */
	@Test
	public void testNormalFrame_countRolls_onReset_returns0() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.reset();
		assertEquals("Wrong rolls count : ", 0, normalFrame.countRolls());
	}
	
	
	//-------------------------------------NormalFrame.reset()-------------------------------------

	/**
	 * The reset() method is clearing all informations linked to a frame except its number. It can
	 * be called before or after any other methods.
	 */
	
	/** 
	 * Test that using NormalFrame.reset() after any method does not throw a BowlingException and
	 * that we get the correct informations about the frame.
	 */
	@Test
	public void testNormalFrame_reset_afterSetPinsDown() {
		normalFrame.setPinsDown(1, 2).setPinsDown(2, 3);
		normalFrame.reset();
		assertEquals("Wrong number of rolls : ", 0, normalFrame.countRolls());
		assertEquals("Wrong number of pins down : ", 0, normalFrame.countPinsDown());
		assertEquals("Wrong framenumber : ", 1, normalFrame.getFrameNumber());
	}
	
	/** 
	 * Test that using NormalFrame.reset() before any method does not throw a BowlingException and
	 * that we get the correct informations about the frame.
	 */
	@Test
	public void testNormalFrame_reset_beforeSetPinsDown() {
		normalFrame.reset();
		normalFrame.setPinsDown(1, 2).setPinsDown(2, 3);
		assertEquals("Wrong number of rolls : ", 2, normalFrame.countRolls());
		assertEquals("Wrong number of pins down : ", 5, normalFrame.countPinsDown());
		assertEquals("Wrong framenumber : ", 1, normalFrame.getFrameNumber());
	}
	

	/*---------------------------------------------------------------------------------------------
	--------------------------------------------LastFrame------------------------------------------
	---------------------------------------------------------------------------------------------*/

	//------------------------------------LastFrame constructor------------------------------------
	
	/**
	 * A game of bowling is represented in this implementation by ten frames comprised of nine 
	 * NormalFrame (from 1 to 9) and one LastFrame (10).
	 * For the constructor of LastFrame, all other values than 10 to initialize the frames are 
	 * expected to throw a BowlingException.
	 */
	
	/**
	 * Test that new LastFrame(10) does not throw any BowlingException.
	 */
	@Test
	public void testLastFrame_constructor_validParameter() {
		 new LastFrame(10);
	}
	
	/**
	 * Test that new LastFrame(x) with x != 10 throws a BowlingException.
	 */
	@ParameterizedTest
	@ValueSource(ints = {1, 5, 8, 20, 0, -1, Integer.MAX_VALUE})
	public void testLastFrame_constructor_wrongParameters(int x) {
		assertThrows("Out of bound parameters : ", BowlingException.class,() -> new LastFrame(x));
	}

}
