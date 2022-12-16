package tp1.automatas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class DFAAutomatonMethodsTests5 {
    private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/tp1/dfa5");
		dotReader.parse();


		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
	} 

    // Tests for DFA5

	@Test
	public void testRepOk() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.repOk());
	}

	@Test
	public void testAccept() throws Exception {
		DFA dfa0 = new DFA(s,a,t);
		assertTrue(dfa0.accepts("aa"));
	}

    @Test
	public void testNoAccept() throws Exception {
		DFA dfa1 = new DFA(s,a,t);
		assertFalse(dfa1.accepts("aaa"));
	}

	@Test
	public void testAccept2() throws Exception {
		DFA dfa0 = new DFA(s,a,t);
		assertTrue(dfa0.accepts("aab"));
	}

	@Test
	public void testAccept3() throws Exception {
		DFA dfa0 = new DFA(s,a,t);
		assertTrue(dfa0.accepts("aabb"));
	}

	@Test
	public void testNoAccept2() throws Exception {
		DFA dfa1 = new DFA(s,a,t);
		assertFalse(dfa1.accepts("aaba"));
	}
}
