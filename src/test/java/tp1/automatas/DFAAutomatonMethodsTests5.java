package tp1.automatas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class DFAAutomatonMethodsTests5 {

	private static DFA dfa;
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

		dfa = new DFA(s, a, t);
	} 

    // Tests for DFA5

	@Test
	public void testRepOk() throws Exception {
		assertTrue(dfa.repOk());
	}

	@Test
	public void testAccept() throws Exception {
		assertTrue(dfa.accepts("aa"));
	}

	
	@Test
	public void testAccept2() throws Exception {
		assertTrue(dfa.accepts("aab"));
	}

	@Test
	public void testAccept3() throws Exception {
		assertTrue(dfa.accepts("aabb"));
	}
	
	@Test
	public void testNoAccept() throws Exception {
		assertFalse(dfa.accepts("aaa"));
	}

	@Test
	public void testNoAccept2() throws Exception {
		assertFalse(dfa.accepts("aaba"));
	}

	@Test
	public void testFinalState2() throws Exception {
		StateSet f = dfa.finalStates();
		assertTrue(f.size()==1);
		assertTrue(f.belongTo("q2")!=null);

	}
	
	@Test
	public void testFinalState3() throws Exception {
		StateSet f = dfa.finalStates();
		assertTrue(f.belongTo("q0")==null);
	}
	
	@Test
	public void testInitialState() throws Exception {
		State i = dfa.initialState();
		assertTrue(i.getName().equals("q0"));
	}

	@Test
	public void testComplement1() throws Exception {
		assertFalse(dfa.complement().accepts("aa"));
	}

	@Test
	public void testComplement2() throws Exception {
		assertFalse(dfa.complement().accepts("aab"));
	}

	@Test
	public void testComplement3() throws Exception {
		assertFalse(dfa.complement().accepts("aabb"));
	}
	
	@Test
	public void testComplement4() throws Exception {
		assertTrue(dfa.complement().accepts("a"));
	}	

	@Test
	public void testComplement5() throws Exception {
		assertTrue(dfa.complement().accepts(""));
	}	

	@Test
	public void testComplement6() throws Exception {
		assertTrue(dfa.complement().accepts("b"));
	}	
}
