package stev.bowling;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

	// TEST NormalFrame
	// Constructor
	@Test
	public void testNormalFrame_constructor_positiveValue() {
		new NormalFrame(5);
	}
	
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
		new NormalFrame(-5);
	}
	
	//countRolls()
	@Test
	public void testNormalFrame_countRolls_onNewFrame_returns0() {
		assertEquals("Wrong rolls count : ", 0, normalFrame.countRolls());
	}
	
	@Test
	public void testNormalFrame_countRolls_oneRoll_returns1() {
		normalFrame.setPinsDown(1, 1);
		assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
	}
	
	@Test
	public void testNormalFrame_countRolls_twoRolls_returns2() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 3);
		assertEquals("Wrong rolls count : ", 2, normalFrame.countRolls());
	}
	
	@Test
	public void testNormalFrame_countRolls_gutterOnFirstRoll_returns1() {
		normalFrame.setPinsDown(1, 0);
		assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
	}
	
	@Test
	public void testNormalFrame_countRolls_gutterOnSecondRoll_returns2() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 0);
		assertEquals("Wrong rolls count : ", 2, normalFrame.countRolls());
	}
	
	@Test
	public void testNormalFrame_countRolls_strike_returns1() {
		normalFrame.setPinsDown(1, 10);
		assertEquals("Wrong rolls count : ", 1, normalFrame.countRolls());
	}
	
	@Test
	public void testNormalFrame_countRolls_onReset_returns0() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.reset();
		assertEquals("Wrong rolls count : ", 0, normalFrame.countRolls());
	}
	
	
	// TEST LastFrame
	// Constructor
	@Test 
	public void testLastFrame_constructor_requiredValue10() {
		new LastFrame(10);
	}
	
	@Test 
	public void testLastFrame_constructor_positiveValueLowerThan10AndHigherThan0() {
		new LastFrame(1);
	}
	
	@Test (expected = BowlingException.class)
	public void testLastFrame_constructor_positiveValueHigerThan10() {
		new LastFrame(20);
	}
	
	@Test (expected = BowlingException.class)
	public void testLastFrame_constructor_zeroValue() {
		new LastFrame(0);
	}
	
	@Test (expected = BowlingException.class)
	public void testLastFrame_constructor_negativeValues() {
		new LastFrame(-1);
	}

}
