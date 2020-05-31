package tp1.utils;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.automatas.State;
import tp1.automatas.StateSet;

public class DotReaderTest {

	private static DotReader dotReader;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		dotReader = new DotReader("src/test/java/tp1/dfa1");
		dotReader.parse();
	}

	@Test
	public void test1() {
		//arrange
		StateSet s = dotReader.getNodes();
		//assert
		assertTrue(s.size() == 3);
	}

	@Test
	public void test2() {
		//arrange
		StateSet s = dotReader.getNodes();

		//act
		State n = s.belongTo("q1");

		//assert
		assertTrue(n!=null);
	}

	@Test
	public void test3() {
		//arrange
		StateSet s = dotReader.getNodes();

		//act
		State n = s.belongTo("q0");

		//assert
		assertTrue(n.isInitial());
	}

	@Test
	public void test4() {
		//arrange
		StateSet s = dotReader.getNodes();

		//act
		State n = s.belongTo("q2");

		//assert
		assertTrue(n.isFinal());
	}

	@Test
	public void test5() {
		//arrange
		StateSet states = dotReader.getNodes();
		Set<Tupla<State,Character,State>> arcs = dotReader.getArcs();
		Tupla<State,Character,State>  arc= new Tupla<State, Character, State>(states.belongTo("q1"),new Character('a'),states.belongTo("q2"));

		//assert
		assertTrue(arcs.contains(arc));
	}

}
