package stev.bowling;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

	
	//---------------------------------------------------------------------------------------------
	//-----------------------------------------NormalFrame-----------------------------------------
	//---------------------------------------------------------------------------------------------
	
	//-----------------------------------NormalFrame constructor-----------------------------------
	
	/**
	 * A game of bowling is represented in this implementation by ten frames comprised of nine 
	 * NormalFrame (from 1 to 9) and one LastFrame (10).
	 * For the constructor of NormalFrame, all other values than the ones in range 1-9 to initialize 
	 * the frames are expected to throw a BowlingException.
	 */
	
	/**
	 * Test that new NormalFrame(1) does not throw any BowlingException.
	 */
	@Test
	public void testNormalFrame_constructor_positiveValue() {
		new NormalFrame(1);
	}
	
	/**
	 * Test that new NormalFrame(10) throws a BowlingException. 
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_constructor_valueHigherThan9() {
		new NormalFrame(10);
	}
	
	@Test (expected = BowlingException.class)
	public void testNormalFrame_constructor_zeroValue() {
		new NormalFrame(0);
	}
	
	@Test (expected = BowlingException.class)
	public void testNormalFrame_constructor_negativeValue() {
		new NormalFrame(-1);
	}
	
	
	//-------------------------NormalFrame.setPinsDown(int roll, int score)------------------------
	
	/**
	 * There are two rolls per frame. The method setPinsDown(int roll, int score) is setting the
	 * number of pins down (the score) for a given roll in the frame.
	 * The number of the roll is either 1 or 2 and the score is between 0 and 10.
	 */
	
	/**
	 * Test that a value like 1 or 2 for the roll's number in NormalFrame.setPinsDown does not throw 
	 * a BowlingException.
	 */
	@Test
	public void testNormalFrame_setPinsDown_normalRollNumber() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 1);
	}
	
	/**
	 * Test that a value other than 1 or 2 for the roll's number in NormalFrame.setPinsDown throws
	 * a BowlingException.
	 */
	@ParameterizedTest
	@CsvSource({"-1,0", "0,5", "1,11", "3,5"})
	public void test_setPinsDown_parameters_exception(int x, int y) {
		assertThrows("Out of bound parameters : ", BowlingException.class, () -> new NormalFrame(0).setPinsDown(x, y));
	}
	
//	@Test (expected = BowlingException.class)
//	public void testNormalFrame_setPinsDown_negativeRollNumber() {
//		normalFrame.setPinsDown(-1, 1);
//	}
//	
//	@Test (expected = BowlingException.class)
//	public void testNormalFrame_setPinsDown_zeroRollNumber() {
//		normalFrame.setPinsDown(0, 1);
//	}
//	
//	@Test (expected = BowlingException.class)
//	public void testNormalFrame_setPinsDown_higherThan2RollNumber() {
//		normalFrame.setPinsDown(3, 1);
//	}
	
	
	//-------------------------------------NormalFrame.reset()-------------------------------------
	@Test
	public void testNormalFrame_reset_afterSetPinsDown_returns0() {
		normalFrame.setPinsDown(1, 2).setPinsDown(2, 3);
		normalFrame.reset();
		assertEquals("Wrong number of rolls : ", 0, normalFrame.countRolls());
		assertEquals("Wrong number of pins down : ", 0, normalFrame.countPinsDown());
//		assertEquals("Wrong number of pins down on first throw : ", 0, normalFrame.countPinsDown(1));
//		assertEquals("Wrong number of pins down on second throw : ", 0, normalFrame.countPinsDown(2));
	}
	

	//-----------------------------------NormalFrame.countRolls()----------------------------------
	
	/**
	 * There are two rolls per frame. The method countRolls() is returning an integer between 0 and 2
	 * depending on the number of roll the player already made for this frame.
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
	 * Test that NormalFrame.countRolls() returns a BowlingException when a score above 10 has
	 * been set for the first roll.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_countRolls_scoreAbove10_returnsException() {
		normalFrame.setPinsDown(1, 11);
		
		//assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
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
	 * Test that NormalFrame.countRolls() returns a BowlingException when a score between 1 and 9 
	 * has been set for the two rolls of the frame. The sum of the two score exceeds 10.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_countRolls_twoRollsWithSumHigherThan10_returnsException() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 10);
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
	
	
	//-----------------------------------NormalFrame.getFrameNumber()----------------------------------
	
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
	
	
	/*-------------------------------------------------------------------------------------------
	-------------------------------------------LastFrame-----------------------------------------
	-------------------------------------------------------------------------------------------*/

	//-----------------------------------LastFrame constructor-----------------------------------
	
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
	public void testLastFrame_constructor_requiredValue10() {
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
	
	
	/**
	 * Test that new LastFrame(10) does not throw an exception.
	 */
	@Test
	public void testLastFrame_constructor_rightParameter() {
		 new LastFrame(10);
	}

}
