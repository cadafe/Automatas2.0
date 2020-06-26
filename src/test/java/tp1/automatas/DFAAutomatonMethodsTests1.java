package tp1.automatas;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class DFAAutomatonMethodsTests1 {

	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/tp1/dfa2");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
	}

	// Tests for DFA2
	@Test
	public void testRepOk() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.repOk());
	}

	// // Tests for DFA2
	@Test
	public void testAccept() throws Exception {
		DFA dfa0 = new DFA(s,a,t);
		assertTrue(dfa0.accepts("bbbbbb"));
	}

	@Test
	public void testNoAccept() throws Exception {
		DFA dfa1 = new DFA(s,a,t);
		assertFalse(dfa1.accepts("bbbbb"));
	}

	@Test
	public void testComplement1() throws Exception {
		DFA dfa2 = new DFA(s,a,t);
		assertFalse(dfa2.complement().accepts("bb"));
	}

	@Test
	public void testComplement2() throws Exception {
		DFA dfa3 = new DFA(s,a,t);
		DFA dfaComplement = dfa3.complement();
		assertTrue(dfaComplement.accepts("b"));
	}

	// Tests StateSet deep clone 
	// @Test
	// public void testSSClone() throws AutomatonException {
	// 	State q1 = new State("q1", true, false);
	// 	State q2 = new State("q2", false, false);
	// 	State q3 = new State("q3", false, true);
	// 	StateSet ss1 = new StateSet();
	// 	ss1.addState(q1);
	// 	ss1.addState(q2);
	// 	ss1.addState(q3);
	// 	StateSet ss2 = ss1.cloneSS();
	// 	assertTrue("Pre edition ss equality assertion", ss1.getSet().equals(ss2.getSet()));
	// 	ss2.deleteState(q1.getName());
	// 	assertFalse("Post edit ss difference assertion", ss1.getSet().equals(ss2.getSet()));
	// }

	// // Tests Alphabet deep clone
	// @Test
	// public void testAlphabetClone() throws AutomatonException {
	// 	Alphabet a1 = new Alphabet();
	// 	a1.addSymbol('a');
	// 	a1.addSymbol('b');
	// 	a1.addSymbol('c');
	// 	Alphabet a2 = a1.cloneAlpha();
	// 	assertTrue("Pre edition alph equality assertion", a1.getSet().equals(a2.getSet()));
	// 	a2.removeSymbol('c');
	// 	assertFalse("Post edit alph difference assertion", a1.getSet().equals(a2.getSet()));
	// }

	@Test 
	public void testDeltaClone() throws AutomatonException {
		DFA a = new DFA();
		HashMap<Character, StateSet> arc1 = new HashMap<Character, StateSet>();
		HashMap<Character, StateSet> arc2 = new HashMap<Character, StateSet>();
		HashMap<State, HashMap<Character, StateSet>> delta1 = new HashMap<State, HashMap<Character, StateSet>>();
		StateSet ss1 = new StateSet();
		StateSet ss2 = new StateSet();
		State q1 = new State("q1", true, false);
		State q2 = new State("q2", false, true);
		State q3 = new State("q3", true, false);
		State q4 = new State("q4", false, true);

		ss1.addState(q2);
		ss2.addState(q4);
		
		arc1.put('c', ss1);
		arc2.put('d', ss2);

		delta1.put(q1, arc1);
		delta1.put(q3, arc2);

		HashMap<State, HashMap<Character, StateSet>> delta2 = a.cloneDelta(delta1);
		assertTrue("before edit", delta1.get(q1).get('c').getSet().equals(delta2.get(q1).get('c').getSet()));
		StateSet ss3 = new StateSet();
		ss3.addState(q3);
		HashMap<Character, StateSet> arc3 = new HashMap<Character, StateSet>();
		arc3.put('c', ss3);
		delta2.put(q1, arc3);
		assertFalse("after edit", delta1.get(q1).get('c').contains(q3));
		assertTrue("delta1 reamins original states", delta1.get(q1).get('c').contains(q2));
	}
}
