package tp1.automatas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class NFAAutomatonMethodsTests2 {
    
    private static NFA nfa;
	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

    @BeforeClass
	public static void setUpBeforeClass() throws Exception{
		// NFA
		DotReader dotReader = new DotReader("src/test/java/tp1/nfa1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
		nfa = new NFA(s, a, t);

    }

    @Test
	public void testBrokenRepOk() throws IllegalArgumentException, AutomatonException {
		assertTrue(nfa.repOk());
	}

    @Test
	public void testAccept() throws Exception {
		assertTrue(nfa.accepts("ab"));
	}

	@Test
	public void testAccept2() throws Exception {
		assertTrue(nfa.accepts("aba"));
	}

	@Test
	public void testAccept3() throws Exception {
		assertTrue(nfa.accepts("abaaa"));
	}

	@Test
	public void testNoAccept() throws Exception {
		assertFalse(nfa.accepts("abb"));
	}

	@Test
	public void testNoAccept2() throws Exception {
		assertFalse(nfa.accepts("a"));
	}

    @Test
	public void testNoAccept3() throws Exception {
		assertFalse(nfa.accepts("b"));
	}

    @Test
	public void testNoAccept4() throws Exception {
		assertFalse(nfa.accepts(""));
	}

	@Test
	public void testNoAccept5() throws Exception {
		assertFalse(nfa.accepts("aaaaaa"));
	}

	@Test
	public void testNoAccept6() throws Exception {
		assertFalse(nfa.accepts("bbbbb"));
	}

	@Test
	public void testNoAccept7() throws Exception {
		assertFalse(nfa.accepts("abab"));
	}

	@Test
	public void testInitialState() {
		State initS = nfa.initialState();
		assertEquals(initS.getName(), "q0");
		assertTrue(initS.isInitial());
	}

	@Test
	public void testFinalStates() throws AutomatonException {
		StateSet finS = nfa.finalStates();
		assertTrue(finS.size()==2);
		assertTrue(finS.belongTo("q2") != null);
		assertTrue(finS.belongTo("q4") != null);
	}
}