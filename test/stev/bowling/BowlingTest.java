package stev.bowling;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The following tests have been produced using the framework JUnit5.
 * 
 * @author Matthieu Planas PLAM18029907 - Florian Zeni ZENF16129806
 */

public class BowlingTest {

	private Game game;
	private Frame normalFrame;
	private Frame lastFrame;

	@BeforeEach
	public void setUp() throws Exception {
		game = new Game();
		normalFrame = new NormalFrame(1);
		lastFrame = new LastFrame(10);
	}

	@AfterEach
	public void tearDown() throws Exception {
		game = null;
		normalFrame = null;
		lastFrame = null;
	}

	/*---------------------------------------------------------------------------------------------
	//-----------------------------------------NormalFrame-----------------------------------------
	//-------------------------------------------------------------------------------------------*/

	@Nested
	public class NormalFrameTests {

		// -----------------------------------NormalFrame
		// constructor-----------------------------------

		/**
		 * A game of bowling is represented in this implementation by ten frames
		 * comprised of nine NormalFrame (from 1 to 9) and one LastFrame (10). For the
		 * constructor of NormalFrame, all other values than the ones in range 1-9 to
		 * initialize the frames are expected to throw a BowlingException.
		 */

		/**
		 * Test that new NormalFrame(x) with x inside the range 1-9 does not throw a
		 * BowlingException.
		 * 
		 * @param x the frame number
		 */
		@ParameterizedTest
		@ValueSource(ints = { 1, 5, 9 })
		public void testNormalFrame_constructor_validParameters(int x) {
			new NormalFrame(x);
		}

		/**
		 * Test that new NormalFrame(x) with x outside the range 1-9 throws a
		 * BowlingException.
		 * 
		 * @param x the frame number
		 */
		@ParameterizedTest
		@ValueSource(ints = { Integer.MIN_VALUE, -1, 0, 10, Integer.MAX_VALUE })
		public void testNormalFrame_constructor_wrongParameters(int x) {
			assertThrows("Out of bound parameters : ", BowlingException.class, () -> new NormalFrame(x));
		}

		// --------------------------------
		// NormalFrame.getFrameNumber()--------------------------------

		/**
		 * There are 10 frames in a standard bowling game. The method getFrameNumber()
		 * should return an integer between 1 and 10 which is equal to the number of the
		 * frame.
		 */

		/**
		 * Test that NormalFrame.getFrameNumber() returns the correct number equals to
		 * the frame number.
		 */
		@Test
		public void testNormalFrame_getFrameNumber() {
			int[] expected = new int[9];
			int[] result = new int[expected.length];

			for (int i = 1; i < 10; i++) {
				expected[i - 1] = i; // index i-1 because it starts at 0 in Java
				result[i - 1] = new NormalFrame(i).getFrameNumber();
			}

			assertArrayEquals("Results of getFrameNumber don't match expected values : ", expected, result);
		}

		// -------------------------NormalFrame.setPinsDown(int roll, int
		// score)------------------------

		/**
		 * There are two rolls per frame. The method setPinsDown(int roll, int score) is
		 * setting the number of pins down (the score) for a given roll in the frame.
		 * The number of the roll is either 1 or 2 and the score is between 0 and 10 for
		 * a normalFrame.
		 */

		/**
		 * Test that a value like 1 or 2 for the roll's number in
		 * NormalFrame.setPinsDown and a number for the score inside the range 0-10 does
		 * not throw a BowlingException.
		 * 
		 * @param x the first roll number for the frame y the number of pins down (the
		 *          score) for the first roll a the second roll number b the score for
		 *          the second roll
		 */
		@ParameterizedTest
		@CsvSource({ "1,0,2,0", "1,1,2,5", "1,0,2,10" })
		public void testNormalFrame_setPinsDown_validParameters(int x, int y, int a, int b) {
			normalFrame.setPinsDown(x, y).setPinsDown(a, b);
		}

		/**
		 * Test that a value other than 1 or 2 for the roll's number in
		 * NormalFrame.setPinsDown throws a BowlingException. Plus, also test that
		 * number outside the range 0-9 for the score throws a BowlingException.
		 * 
		 * @param x the roll number for the frame y the number of pins down (the score)
		 */
		@ParameterizedTest
		@CsvSource({ "-1,1", "0,5", "3,5", "1,11", "1,-2" })
		public void testNormalFrame_setPinsDown_wrongParameters(int x, int y) {
			assertThrows("Out of bound parameters : ", BowlingException.class, () -> normalFrame.setPinsDown(x, y));
		}

		/**
		 * Test that declaring two times a score for the same roll of a normalFrame is
		 * throwing a BowlingException.
		 */
		@Test
		public void testNormalFrame_setPinsDown_twoDeclarationsForOneRoll() {
			assertThrows("Invalid number for rolls : ", BowlingException.class,
					() -> new NormalFrame(1).setPinsDown(1, 1).setPinsDown(1, 3));
		}

		/**
		 * Test that declaring a third roll for a normalFrame is throwing a
		 * BowlingException.
		 */
		@Test
		public void testNormalFrame_setPinsDown_3rdRoll() {
			assertThrows("Invalid number for rolls : ", BowlingException.class,
					() -> new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 1).setPinsDown(3, 1));
		}

		/**
		 * Test that switching order of declaration for roll's number throws a
		 * BowlingException.
		 */
		@Test
		public void testNormalFrame_setPinsDown_inversedRollNumber() {
			assertThrows("Wrong order for rolls : ", BowlingException.class,
					() -> new NormalFrame(1).setPinsDown(2, 1).setPinsDown(1, 1));
		}

		/**
		 * Test that declaring a score higher than 10 with the sum of the rolls' score
		 * for a frame is throwing a BowlingException.
		 */
		@Test
		public void testNormalFrame_setPinsDown_sumHigherThan10() {
			assertThrows("Sum of scores is higher than 10 in one frame : ", BowlingException.class,
					() -> new NormalFrame(1).setPinsDown(1, 8).setPinsDown(2, 8));
		}

		/**
		 * Test that we can't declare a score in the second roll if the score in the
		 * first roll is equal to 10. Throws a BowlingException.
		 */
		@Test
		public void testNormalFrame_setPinsDown_firstRollStrike() {
			assertThrows("Can't set the score in 2nd roll if it is of 10 in 1st roll : ", BowlingException.class,
					() -> new NormalFrame(1).setPinsDown(1, 10).setPinsDown(2, 0));
		}

		// ---------------------------------NormalFrame.countPinsDown()---------------------------------

		/**
		 * There are two rolls per frame. The method countPinsDown() returns the score
		 * of the frame between 0 and 10. After a call to the method setPinsDown(int
		 * roll, int score) this method returns the sum of the score of the two rolls.
		 */

		/**
		 * Test that after the initialization of a frame, countPinsDown returns 0 as the
		 * number of pins down and does not throw a BowlingException.
		 */
		@Test
		public void testNormalFrame_countPinsDown_afterInitialization() {
			assertEquals("Wrong number of pins down after initialization : ", 0, new NormalFrame(1).countPinsDown());
		}

		/**
		 * Test that after setting a score for the first roll, countPinsDown returns the
		 * correct number of pins down and does not throw a BowlingException.
		 */
		@Test
		public void testNormalFrame_countPinsDown_afterScoreForFirstRoll() {
			assertEquals("Wrong number of pins down after first roll : ", 2,
					normalFrame.setPinsDown(1, 2).countPinsDown());
		}

		/**
		 * Test that after setting a score for the two rolls of a frame, countPinsDown
		 * returns the correct sum of pins down and does not throw a BowlingException.
		 */
		@Test
		public void testNormalFrame_countPinsDown_afterScoreForAFrame() {
			assertEquals("Wrong number of pins down after two rolls : ", 5,
					normalFrame.setPinsDown(1, 2).setPinsDown(2, 3).countPinsDown());
		}

		/**
		 * Test that NormalFrame.countPinsDown() returns 0 after a reset of the frame.
		 */
		@Test
		public void testNormalFrame_countPinsDown_onReset_returns0() {
			normalFrame.setPinsDown(1, 5).setPinsDown(2, 3);
			normalFrame.reset();
			assertEquals("Wrong number of pins down after a reset : ", 0, normalFrame.countPinsDown());
		}

		// -----------------------------------NormalFrame.countRolls()----------------------------------

		/**
		 * There are two rolls per frame. The method countRolls() is returning an
		 * integer between 0 and 2 depending on the number of roll the player already
		 * made for this frame. After a reset of the frame, it should return 0.
		 */

		/**
		 * Test that NormalFrame.countRolls() returns 0 when the normalFrame is created.
		 */
		@Test
		public void testNormalFrame_countRolls_onNewFrame_returns0() {
			assertEquals("Wrong rolls count : ", 0, normalFrame.countRolls());
		}

		/**
		 * Test that NormalFrame.countRolls() returns 1 when a score between 1 and 9 has
		 * been set for the first roll.
		 */
		@Test
		public void testNormalFrame_countRolls_oneRoll_returns1() {
			normalFrame.setPinsDown(1, 1);
			assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
		}

		/**
		 * Test that NormalFrame.countRolls() returns 2 when a score between 1 and 9 has
		 * been set for the two rolls of the frame. The sum of the two score doesn't
		 * exceed 10.
		 */
		@Test
		public void testNormalFrame_countRolls_twoRollsWithSumLowerThan10_returns2() {
			normalFrame.setPinsDown(1, 1).setPinsDown(2, 3);
			assertEquals("Wrong rolls count : ", 2, normalFrame.countRolls());
		}

		/**
		 * Test that NormalFrame.countRolls() returns 1 when a score of 0 has been set
		 * for the first roll. That is when the player made a gutter.
		 */
		@Test
		public void testNormalFrame_countRolls_gutterOnFirstRoll_returns1() {
			normalFrame.setPinsDown(1, 0);
			assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
		}

		/**
		 * Test that NormalFrame.countRolls() returns 2 when a score of 0 has been set
		 * for the two rolls of a frame. That is when the player made two gutters.
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

		// ------------------------------NormalFrame.getPinsDown(int
		// roll)------------------------------

		/**
		 * This method getPinsDown(int roll) should return the number of pins down for a
		 * particular roll of a given frame. Throws a BowlingException if the roll
		 * number is not valid.
		 */

		/**
		 * Test that NormalFrame.getPinsDown(int roll) returns the correct number of
		 * pins down after setting a score for the first roll.
		 */
		@Test
		public void testNormalFrame_getPinsDown_scoreForFirstRoll() {
			normalFrame.setPinsDown(1, 1);
			assertEquals("Wrong number of pins down for the first roll : ", 1, normalFrame.getPinsDown(1));
		}

		/**
		 * Test that NormalFrame.getPinsDown(int roll) returns the correct number of
		 * pins down after setting a score for the two rolls.
		 */
		@Test
		public void testNormalFrame_getPinsDown_scoreForTwoRolls() {
			normalFrame.setPinsDown(1, 1).setPinsDown(2, 2);
			assertEquals("Wrong number of pins down for the second roll : ", 2, normalFrame.getPinsDown(2));
		}

		/**
		 * Test that NormalFrame.getPinsDown(int roll) throws a BowlingException if the
		 * roll number is invalid.
		 * 
		 * @param x the roll number
		 */
		@ParameterizedTest
		@ValueSource(ints = { 3, 0, -1, Integer.MAX_VALUE })
		public void testNormalFrame_getPinsDown_invalidRollNumber(int x) {
			assertThrows("Invalid roll number : ", BowlingException.class, () -> normalFrame.getPinsDown(x));
		}

		/**
		 * Test that NormalFrame.getPinsDown(int roll) throw a BowlingException after a
		 * call to the reset() method as the score doesn't exist yet.
		 */
		@Test
		public void testNormalFrame_getPinsDown_afterReset() {
			normalFrame.setPinsDown(1, 1).setPinsDown(2, 2);
			normalFrame.reset();
			assertThrows("There souldn't be any roll left in this frame", BowlingException.class,
					() -> normalFrame.getPinsDown(1));
		}

		// -------------------------------------NormalFrame.reset()-------------------------------------

		/**
		 * The reset() method is clearing all informations linked to a frame except its
		 * number. It can be called before or after any other methods.
		 */

		/**
		 * Test that using NormalFrame.reset() after any method does not throw a
		 * BowlingException and that we get the correct informations about the frame.
		 */
		@Test
		public void testNormalFrame_reset_afterSetPinsDown() {
			normalFrame.setPinsDown(1, 2).setPinsDown(2, 3);
			normalFrame.reset();
			assertAll(() -> assertEquals("Wrong number of rolls : ", 0, normalFrame.countRolls()),
					() -> assertEquals("Wrong number of pins down : ", 0, normalFrame.countPinsDown()),
					() -> assertEquals("Wrong framenumber : ", 1, normalFrame.getFrameNumber()));
		}

		/**
		 * Test that using NormalFrame.reset() before any method does not throw a
		 * BowlingException and that we get the correct informations about the frame.
		 */
		@Test
		public void testNormalFrame_reset_beforeSetPinsDown() {
			normalFrame.reset();
			normalFrame.setPinsDown(1, 2).setPinsDown(2, 3);
			assertAll(() -> assertEquals("Wrong number of rolls : ", 2, normalFrame.countRolls()),
					() -> assertEquals("Wrong number of pins down : ", 5, normalFrame.countPinsDown()),
					() -> assertEquals("Wrong framenumber : ", 1, normalFrame.getFrameNumber()));
		}

		// ------------------------------------NormalFrame.toString()-----------------------------------

		/**
		 * Test that for a NormalFrame the toString() method returns a string made of
		 * two characters.
		 */
		@Test
		public void testNormalFrame_toString() {
			assertAll(
					() -> assertEquals("Wrong number of characters with a total score lower than 10", 2,
							new NormalFrame(1).setPinsDown(1, 1).setPinsDown(2, 2).toString().length()),
					() -> assertEquals("Wrong number of characters with a strike for NormalFrame", 2,
							new NormalFrame(1).setPinsDown(1, 10).toString().length()),
					() -> assertEquals("Wrong number of characters with a spare for NormalFrame", 2,
							new NormalFrame(1).setPinsDown(1, 5).setPinsDown(2, 5).toString().length()));
		}
	}

	/*---------------------------------------------------------------------------------------------
	--------------------------------------------LastFrame------------------------------------------
	---------------------------------------------------------------------------------------------*/

	@Nested
	public class LastFrameTests {

		// ------------------------------------LastFrame
		// constructor------------------------------------

		/**
		 * A game of bowling is represented in this implementation by ten frames
		 * comprised of nine NormalFrame (from 1 to 9) and one LastFrame (10). For the
		 * constructor of LastFrame, all other values than 10 to initialize the frames
		 * are expected to throw a BowlingException.
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
		 * 
		 * @param x the frame number
		 */
		@ParameterizedTest
		@ValueSource(ints = { 1, 5, 8, 20, 0, -1, Integer.MAX_VALUE })
		public void testLastFrame_constructor_wrongParameters(int x) {
			assertThrows("Out of bound parameters : ", BowlingException.class, () -> new LastFrame(x));
		}

		// ----------------------------------LastFrame.getFrameNumber()---------------------------------

		/**
		 * There are 10 frames in a standard bowling game. The method getFrameNumber()
		 * should return an 10 for a LastFrame.
		 */

		/**
		 * Test that LastFrame.getFrameNumber() returns the correct number equals to the
		 * frame number.
		 */
		@Test
		public void testLastFrame_getFrameNumber() {
			int expected = 10;
			int result = new LastFrame(10).getFrameNumber();

			assertEquals("Result of getFrameNumber for LastFrame doesn't match expected value : ", expected, result);
		}

		// ---------------------------LastFrame.setPinsDown(int roll, int
		// score)------------------------

		/**
		 * There are two rolls per frame. The method setPinsDown(int roll, int score) is
		 * setting the number of pins down (the score) for a given roll in the frame.
		 * The number of the roll is either 1 or 2 and the score is between 0 and 10 for
		 * a normalFrame. As it is the last frame of the game, there can be 3 rolls and
		 * the score can be higher than 10.
		 */

		/**
		 * Test that a value like 1 or 2 for the roll's number in LastFrame.setPinsDown
		 * and a number for the score inside the range 0-10 does not throw a
		 * BowlingException.
		 * 
		 * @param x the first roll number for the frame y the number of pins down (the
		 *          score) for the first roll a the second roll number b the score for
		 *          the second roll
		 */
		@ParameterizedTest
		@CsvSource({ "1,0,2,0", "1,1,2,5", "1,0,2,10" })
		public void testLastFrame_setPinsDown_validParameters(int x, int y, int a, int b) {
			lastFrame.setPinsDown(x, y).setPinsDown(a, b);
		}

		/**
		 * Test that a value other than 1 or 2 for the roll's number in
		 * LastFrame.setPinsDown throws a BowlingException. Plus, also test that number
		 * outside the range 0-9 for the score throws a BowlingException.
		 * 
		 * @param x the roll number for the frame y the number of pins down (the score)
		 */
		@ParameterizedTest
		@CsvSource({ "-1,1", "0,5", "4,5", "1,11", "1,-2" })
		public void testLastFrame_setPinsDown_wrongParameters(int x, int y) {
			assertThrows("Out of bound parameters : ", BowlingException.class, () -> lastFrame.setPinsDown(x, y));
		}

		/**
		 * Test that we can declare a 3rd roll for a lastFrame if the score of the two
		 * first rolls is equal or higher than 10. Testing several configurations :
		 * strike/gutter, strike/score, strike/spare, spare/score.
		 * 
		 * @param x the first roll number for the frame y the number of pins down (the
		 *          score) for the first roll a the second roll number b the score for
		 *          the second roll r the third roll number s the score for the third
		 *          roll
		 */
		@ParameterizedTest
		@CsvSource({ "1,10,2,0,3,5", "1,10,2,5,3,4", "1,10,2,5,3,5", "1,8,2,2,3,3" })
		public void testLastFrame_setPinsDown_validParametersFor3rdRoll(int x, int y, int a, int b, int r, int s) {
			lastFrame.setPinsDown(x, y).setPinsDown(a, b).setPinsDown(r, s);
		}

		/**
		 * Test that we can't declare a 3rd roll for a lastFrame if the score of the two
		 * first rolls is lower than 10. Throws a BowlingException.
		 * 
		 * @param x the first roll number for the frame y the number of pins down (the
		 *          score) for the first roll a the second roll number b the score for
		 *          the second roll r the third roll number s the score for the third
		 *          roll
		 */
		@ParameterizedTest
		@CsvSource({ "1,1,2,5,3,1", "1,0,2,0,3,4", "1,9,2,0,3,1", "1,0,2,9,3,3" })
		public void testLastFrame_setPinsDown_invalidParametersFor3rdRoll(int x, int y, int a, int b, int r, int s) {
			assertThrows("Out of bound parameters : ", BowlingException.class,
					() -> lastFrame.setPinsDown(x, y).setPinsDown(a, b).setPinsDown(r, s));
		}

		/**
		 * Test that declaring two times a score for the same roll of a LastFrame is
		 * throwing a BowlingException.
		 */
		@Test
		public void testLastFrame_setPinsDown_twoDeclarationsForOneRoll() {
			assertThrows("Invalid number for rolls : ", BowlingException.class,
					() -> new NormalFrame(1).setPinsDown(1, 1).setPinsDown(1, 3));
		}

		/**
		 * Test that switching order of declaration for roll's number throws a
		 * BowlingException.
		 */
		@Test
		public void testLastFrame_setPinsDown_inversedRollNumber() {
			assertThrows("Wrong order for rolls : ", BowlingException.class,
					() -> new LastFrame(10).setPinsDown(3, 1).setPinsDown(2, 1).setPinsDown(1, 1));
		}

		/**
		 * Test that we can't declare a score for the 2nd roll before having declared
		 * one for the 1st roll. Throws a BowlingException.
		 */
		@Test
		public void testLastFrame_setPinsDown_declaringSecondRollFirst() {
			assertThrows("Can't start setting the score with the second roll : ", BowlingException.class,
					() -> new LastFrame(10).setPinsDown(2, 1));
		}

		/**
		 * Test that we can't declare a score for the 3rd roll before having declared a
		 * total score equal or higher than 10 in the two first rolls. Throws a
		 * BowlingException.
		 */
		@Test
		public void testLastFrame_setPinsDown_declaringThirdRollFirst() {
			assertThrows("Can't start setting the score with the second roll : ", BowlingException.class,
					() -> new LastFrame(10).setPinsDown(3, 1));
		}

		/**
		 * Test that declaring a score higher than 10 with the sum of the two rolls'
		 * score for a frame is throwing a BowlingException.
		 */
		@Test
		public void testLastFrame_setPinsDown_sumHigherThan10TwoRolls() {
			assertThrows("Sum of scores is higher than 10 in one frame : ", BowlingException.class,
					() -> new LastFrame(10).setPinsDown(1, 8).setPinsDown(2, 8));
		}

		// ----------------------------------LastFrame.countPinsDown()----------------------------------

		/**
		 * There are two rolls and sometimes three for a LastFrame. The method
		 * countPinsDown() returns the score of the frame between 0 and 30. After a call
		 * to the method setPinsDown(int roll, int score) this method returns the sum of
		 * the score of the two or three rolls.
		 */

		/**
		 * Test that after the initialization of a frame, countPinsDown returns 0 as the
		 * number of pins down and does not throw a BowlingException.
		 */
		@Test
		public void testLastFrame_countPinsDown_afterInitialization() {
			assertEquals("Wrong number of pins down after initialization : ", 0, new LastFrame(10).countPinsDown());
		}

		/**
		 * Test that after setting a score for the first roll, countPinsDown returns the
		 * correct number of pins down and does not throw a BowlingException.
		 */
		@Test
		public void testLastFrame_countPinsDown_afterScoreForFirstRoll() {
			assertEquals("Wrong number of pins down after first roll : ", 2,
					lastFrame.setPinsDown(1, 2).countPinsDown());
		}

		/**
		 * Test that after setting a score for the two rolls of a frame, countPinsDown
		 * returns the correct sum of pins down and does not throw a BowlingException.
		 */
		@Test
		public void testLastFrame_countPinsDown_afterScoreForTwoRolls() {
			assertEquals("Wrong number of pins down after two rolls : ", 5,
					lastFrame.setPinsDown(1, 2).setPinsDown(2, 3).countPinsDown());
		}

		/**
		 * Test that after setting a score for the two rolls of a frame allowing a third
		 * roll, countPinsDown returns the correct sum of pins down and does not throw a
		 * BowlingException.
		 */
		@Test
		public void testLastFrame_countPinsDown_afterScoreForThreeRolls() {
			assertEquals("Wrong number of pins down after three rolls : ", 15,
					lastFrame.setPinsDown(1, 5).setPinsDown(2, 5).setPinsDown(3, 5).countPinsDown());
		}

		/**
		 * Test that LastFrame.countPinsDown() returns 0 after a reset of the frame.
		 */
		@Test
		public void testLastFrame_countPinsDown_onReset_returns0() {
			lastFrame.setPinsDown(1, 5).setPinsDown(2, 5).setPinsDown(3, 5);
			lastFrame.reset();
			assertEquals("Wrong number of pins down after a reset for lastFrame : ", 0, lastFrame.countPinsDown());
		}

		// ------------------------------------LastFrame.countRolls()-----------------------------------

		/**
		 * There are two or three rolls for a LastFrame. The method countRolls() is
		 * returning an integer between 0 and 3 depending on the number of roll the
		 * player already made for this frame. After a reset of the frame, it should
		 * return 0.
		 */

		/**
		 * Test that LastFrame.countRolls() returns 0 when the LastFrame is created.
		 */
		@Test
		public void testLastFrame_countRolls_onNewFrame_returns0() {
			assertEquals("Wrong rolls count for lastFrame : ", 0, new LastFrame(10).countRolls());
		}

		/**
		 * Test that LastFrame.countRolls() returns 1 when a score between 1 and 9 has
		 * been set for the first roll.
		 */
		@Test
		public void testLastFrame_countRolls_oneRoll_returns1() {
			lastFrame.setPinsDown(1, 1);
			assertEquals("Wrong rolls count for lastFrame : ", 1, lastFrame.countRolls());
		}

		/**
		 * Test that LastFrame.countRolls() returns 2 when a score between 1 and 9 has
		 * been set for the two rolls of the frame. The sum of the two score doesn't
		 * exceed 10.
		 */
		@Test
		public void testLastFrame_countRolls_twoRollsWithSumLowerThan10_returns2() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 3);
			assertEquals("Wrong rolls count for lastFrame : ", 2, lastFrame.countRolls());
		}

		/**
		 * Test that LastFrame.countRolls() returns 3 when the score of the two first
		 * frame is equal or higher than 10 unlocking the third roll.
		 */
		@Test
		public void testLastFrame_countRolls_threeRolls_returns3() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 1);
			assertEquals("Wrong rolls count for lastFrame : ", 3, lastFrame.countRolls());
		}

		/**
		 * Test that LastFrame.countRolls() returns 1 when a score of 0 has been set for
		 * the first roll. That is when the player made a gutter.
		 */
		@Test
		public void testLastFrame_countRolls_gutterOnFirstRoll_returns1() {
			lastFrame.setPinsDown(1, 0);
			assertEquals("Wrong rolls count for lastFrame : ", 1, lastFrame.countRolls());
		}

		/**
		 * Test that LastFrame.countRolls() returns 2 when a score of 0 has been set for
		 * the two rolls of a frame. That is when the player made two gutters.
		 */
		@Test
		public void testLastFrame_countRolls_gutterOnSecondRoll_returns2() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 0);
			assertEquals("Wrong rolls count for lastFrame : ", 2, lastFrame.countRolls());
		}

		/**
		 * Test that LastFrame.countRolls() returns 0 after a reset of the frame.
		 */
		@Test
		public void testLastFrame_countRolls_onReset_returns0() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 5);
			lastFrame.reset();
			assertEquals("Wrong rolls count for lastFrame : ", 0, lastFrame.countRolls());
		}

		// -------------------------------LastFrame.getPinsDown(int
		// roll)-------------------------------

		/**
		 * This method getPinsDown(int roll) should return the number of pins down for a
		 * particular roll of a given frame. Throws a BowlingException if the roll
		 * number is not valid. A LastFrame can have three rolls.
		 */

		/**
		 * Test that LastFrame.getPinsDown(int roll) returns the correct number of pins
		 * down after setting a score for the first roll.
		 */
		@Test
		public void testLastFrame_getPinsDown_scoreForFirstRoll() {
			lastFrame.setPinsDown(1, 1);
			assertEquals("Wrong number of pins down for the first roll : ", 1, lastFrame.getPinsDown(1));
		}

		/**
		 * Test that LastFrame.getPinsDown(int roll) returns the correct number of pins
		 * down after setting a score for the two rolls.
		 */
		@Test
		public void testLastFrame_getPinsDown_scoreForTwoRolls() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 2);
			assertEquals("Wrong number of pins down for the second roll : ", 2, lastFrame.getPinsDown(2));
		}

		/**
		 * Test that LastFrame.getPinsDown(int roll) returns the correct number of pins
		 * down after setting a score for the two rolls enabling the third roll.
		 */
		@Test
		public void testLastFrame_getPinsDown_scoreForThreeRolls() {
			lastFrame.setPinsDown(1, 8).setPinsDown(2, 2).setPinsDown(3, 7);
			assertEquals("Wrong number of pins down for the third roll : ", 7, lastFrame.getPinsDown(3));
		}

		/**
		 * Test that LastFrame.getPinsDown(int roll) throws a BowlingException if the
		 * roll number is invalid.
		 * 
		 * @param x the roll number
		 */
		@ParameterizedTest
		@ValueSource(ints = { 4, 0, -1, Integer.MAX_VALUE })
		public void testLastFrame_getPinsDown_invalidRollNumber(int x) {
			assertThrows("Invalid roll number : ", BowlingException.class, () -> lastFrame.getPinsDown(x));
		}

		/**
		 * Test that LastFrame.getPinsDown(int roll) throw a BowlingException after a
		 * call to the reset() method as the score doesn't exist yet.
		 */
		@Test
		public void testLastFrame_getPinsDown_afterReset() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 5);
			lastFrame.reset();
			assertThrows("There souldn't be any roll left in this frame", BowlingException.class,
					() -> lastFrame.getPinsDown(3));
		}

		// ------------------------------------LastFrame.toString()-----------------------------------

		/**
		 * Test that for a LastFrame the toString() method returns a string made of
		 * three characters.
		 */
		@Test
		public void testLastFrame_toString() {
			assertAll(
					() -> assertEquals("Wrong number of characters with a total score lower than 10", 3,
							new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 2).toString().length()),
					() -> assertEquals("Wrong number of characters with a strike for LastFrame", 3,
							new LastFrame(10).setPinsDown(1, 10).setPinsDown(2, 0).setPinsDown(3, 10).toString()
									.length()),
					() -> assertEquals("Wrong number of characters with a spare for LastFrame", 3, new LastFrame(10)
							.setPinsDown(1, 5).setPinsDown(2, 5).setPinsDown(3, 2).toString().length()));
		}
	}
	/*---------------------------------------------------------------------------------------------
	-----------------------------------------------Game--------------------------------------------
	---------------------------------------------------------------------------------------------*/

	@Nested
	public class GameTests {

		// ----------------------------------------Game.addFrame()--------------------------------------

		/**
		 * Tests that addFrame does not return any error when called with proper
		 * parameters in the right order
		 */
		@Test
		public void testGame_addFrame_fullGame() {
			for (int i = 1; i < 10; i++) {
				game.addFrame(new NormalFrame(i));
			}
			game.addFrame(new LastFrame(10));
		}

		/**
		 * Tests that addFrame throws a BowlingException when called with object of type
		 * LastFrame as first frame
		 */
		@Test
		public void testGame_addFrame_lastFrameFirst() {
			assertThrows("Last Frame can't come first", BowlingException.class,
					() -> new Game().addFrame(new LastFrame(10)));
		}

		/**
		 * Tests that addFrame throws a BowlingException when called with a wrong
		 * NormalFrame first
		 */
		@Test
		public void testGame_addFrame_wrongNormalFramesFirst() {
			assertThrows("Wrong Normal Frame can't come first", BowlingException.class,
					() -> new Game().addFrame(new NormalFrame(5)));
			assertThrows("Wrong Normal Frame can't come first", BowlingException.class,
					() -> new Game().addFrame(new NormalFrame(8)));
		}

		/**
		 * Tests that addFrame throws a BowlingException when called with object of type
		 * NormalFrame in the wrong order
		 */
		@Test
		public void testGame_addFrame_unorderedNormalFrames() {
			game.addFrame(new NormalFrame(1));
			assertThrows("Last Frame can't come first", BowlingException.class,
					() -> game.addFrame(new NormalFrame(4)));
		}

		// -------------------------------------Game.getCumulativeScore()-----------------------------------

		/**
		 * Tests that getCumulativeScore returns the right score at each frame for
		 * normal rounds, without spares or strikes
		 */
		@Test
		public void testGame_getCumulativeScore_noSpecialScores() {
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 2).setPinsDown(2, 4));
			game.addFrame(new NormalFrame(3).setPinsDown(1, 5).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(4).setPinsDown(1, 1).setPinsDown(2, 3));
			game.addFrame(new NormalFrame(5).setPinsDown(1, 5).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(7).setPinsDown(1, 0).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(8).setPinsDown(1, 7).setPinsDown(2, 2));
			game.addFrame(new NormalFrame(9).setPinsDown(1, 1).setPinsDown(2, 8));
			game.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 4));

			assertAll(() -> assertEquals("Wrong Score Found", 9, game.getCumulativeScore(1)),
					() -> assertEquals("Wrong Score Found", 15, game.getCumulativeScore(2)),
					() -> assertEquals("Wrong Score Found", 20, game.getCumulativeScore(3)),
					() -> assertEquals("Wrong Score Found", 24, game.getCumulativeScore(4)),
					() -> assertEquals("Wrong Score Found", 29, game.getCumulativeScore(5)),
					() -> assertEquals("Wrong Score Found", 29, game.getCumulativeScore(6)),
					() -> assertEquals("Wrong Score Found", 35, game.getCumulativeScore(7)),
					() -> assertEquals("Wrong Score Found", 44, game.getCumulativeScore(8)),
					() -> assertEquals("Wrong Score Found", 53, game.getCumulativeScore(9)),
					() -> assertEquals("Wrong Score Found", 58, game.getCumulativeScore(10)));
		}

		/**
		 * Tests that getCumulativeScore returns the right score after a single strike,
		 * and returns -1, when the next throw has yet to be completed
		 */
		@Test
		public void testGame_getCumulativeScore_singleStrikeBehaviour() {
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
			assertEquals("Score That can't be determined yet", -1, game.getCumulativeScore(2));
			game.addFrame(new NormalFrame(3).setPinsDown(1, 5).setPinsDown(2, 0));
			assertEquals("Wrong Score Found", 24, game.getCumulativeScore(2));
			assertEquals("Wrong Score Found", 29, game.getCumulativeScore(3));
		}

		/**
		 * Tests that getCumulativeScore returns the right score after two strikes in a
		 * row, and returns -1, when the next throw has yet to be completed
		 */
		@Test
		public void testGame_getCumulativeScore_doubleStrikeBehaviour() {
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 10));
			assertEquals("Score That can't be determined yet", -1, game.getCumulativeScore(2));
			game.addFrame(new NormalFrame(3).setPinsDown(1, 10));
			assertEquals("Score That can't be determined yet", -1, game.getCumulativeScore(2));
			assertEquals("Score That can't be determined yet", -1, game.getCumulativeScore(3));
			game.addFrame(new NormalFrame(4).setPinsDown(1, 3).setPinsDown(2, 2));
			assertEquals("Wrong Score Found", 34, game.getCumulativeScore(2));
			assertEquals("Wrong Score Found", 49, game.getCumulativeScore(3));
			assertEquals("Wrong Score Found", 54, game.getCumulativeScore(4));
		}

		/**
		 * Tests that getCumulativeScore returns the right score after a spare, and
		 * returns -1, when the next throw has yet to be completed
		 */
		@Test
		public void testGame_getCumulativeScore_spareBehaviour() {
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 7));
			assertEquals("Score That can't be determined yet should be -1", -1, game.getCumulativeScore(1));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 4).setPinsDown(2, 3));
			assertEquals("Wrong Score Found", 14, game.getCumulativeScore(1));
			assertEquals("Wrong Score Found", 21, game.getCumulativeScore(2));
		}

		/**
		 * Tests that getCumulativeScore returns the right score for the last frame with
		 * three throws
		 */
		@Test
		public void testGame_getCumulativeScore_lastFrameBehaviour() {
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(2).setPinsDown(1, 2).setPinsDown(2, 4));
			game.addFrame(new NormalFrame(3).setPinsDown(1, 5).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(4).setPinsDown(1, 1).setPinsDown(2, 3));
			game.addFrame(new NormalFrame(5).setPinsDown(1, 5).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(6).setPinsDown(1, 0).setPinsDown(2, 0));
			game.addFrame(new NormalFrame(7).setPinsDown(1, 0).setPinsDown(2, 6));
			game.addFrame(new NormalFrame(8).setPinsDown(1, 7).setPinsDown(2, 2));
			game.addFrame(new NormalFrame(9).setPinsDown(1, 1).setPinsDown(2, 8));
			game.addFrame(new LastFrame(10).setPinsDown(1, 1).setPinsDown(2, 9).setPinsDown(3, 10));
			assertEquals("Wrong Score Found", 73, game.getCumulativeScore(10));
		}

		/**
		 * Tests that getCumulativeScore returns -1 if called before the round was
		 * completed
		 */
		@Test
		public void testGame_getCumulativeScore_beforeSetPins() {
			game.addFrame(new NormalFrame(1));
			assertEquals("Score is not known yet", -1, game.getCumulativeScore(1));
		}

		/**
		 * Tests that getCumulativeScore returns -1 after a reset, and the proper score
		 * after it was played again
		 */
		@Test
		public void testGame_getCumulativeScore_afterReset() {
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			Frame f = new NormalFrame(2).setPinsDown(1, 3).setPinsDown(2, 6).reset();
			game.addFrame(f);
			assertEquals("Score after reset", -1, game.getCumulativeScore(2));
			f.setPinsDown(1, 4).setPinsDown(2, 3);
			assertEquals("Score after round was played again", 16, game.getCumulativeScore(2));
		}

		/**
		 * Tests that getCumulativeScore throws a Bowling Exception if the requested
		 * frame does not exist
		 */
		@Test
		public void testGame_getCumulativeScore_unknownRound() {
			assertThrows("Score can't be calculated before the round is played", BowlingException.class,
					() -> game.getCumulativeScore(1));
			game.addFrame(new NormalFrame(1).setPinsDown(1, 3).setPinsDown(2, 6));
			assertThrows("Score can't be calculated before the round is played", BowlingException.class,
					() -> game.getCumulativeScore(2));
		}
	}
}
