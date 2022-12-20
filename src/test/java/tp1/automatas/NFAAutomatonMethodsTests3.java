package tp1.automatas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class NFAAutomatonMethodsTests3 {
    
    private static NFA nfa;
	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

    @BeforeClass
	public static void setUpBeforeClass() throws Exception{
		// NFA
		DotReader dotReader = new DotReader("src/test/java/tp1/nfa2");
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
		assertTrue(nfa.accepts("aaaaaaaa"));
	}

	@Test
	public void testAccept2() throws Exception {
		assertTrue(nfa.accepts("a"));
	}

	@Test
	public void testAccept3() throws Exception {
		assertTrue(nfa.accepts("aa"));
	}

	@Test
	public void testNoAccept() throws Exception {
		assertFalse(nfa.accepts(""));
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
		assertTrue(finS.size()==1);
		assertTrue(finS.belongTo("q2") != null);
	}
}
