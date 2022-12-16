package tp1.automatas;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

	public class DFAAutomatonMethodsTests3 {

		private static StateSet s;
		private static Alphabet a;
		private static Set<Tupla<State,Character,State>> t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/tp1/dfa3");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
	}

	// Tests for DFA3

	@Test
	public void testRepOk() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.repOk());
	}

	@Test
	public void testAccept() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.accepts("aaa"));
	}

	@Test
	public void testNoAccept() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertFalse(dfa.accepts("aa"));
	}
	
	// @Test
	// public void testComplement1() throws Exception {
	// 	DFA dfa = new DFA(s,a,t);
	// 	assertFalse(dfa.complement().accepts("aaa"));
	// }
	
	// @Test
	// public void testComplement2() throws Exception {
	// 	DFA dfa = new DFA(s,a,t);
	// 	assertTrue(dfa.complement().accepts("aa"));
	// }	
	
	// Other Tests
	
	@Test
	public void testFinalState1() throws Exception {
		DFA dfa = new DFA(s,a,t);
		StateSet f = dfa.finalStates();
		assertTrue(f.size()==1);
	}
	
	@Test
	public void testFinalState2() throws Exception {
		DFA dfa = new DFA(s,a,t);
		StateSet f = dfa.finalStates();
		assertTrue(f.belongTo("q1")!=null);
	}
	
	@Test
	public void testFinalState3() throws Exception {
		DFA dfa = new DFA(s,a,t);
		StateSet f = dfa.finalStates();
		assertTrue(f.belongTo("q0")==null);
	}
	
	@Test
	public void testInitialState() throws Exception {
		DFA dfa = new DFA(s,a,t);
		State i = dfa.initialState();
		assertTrue(i.getName().equals("q0"));
	}

}
