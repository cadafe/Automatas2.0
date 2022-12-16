package tp1.automatas;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class DFAAutomatonMethodsTests2 {

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

	@Test
	public void testRepOk() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.repOk());
	}

	// Tests for DFA2
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
		DFA dfaComplement = dfa2.complement();
		assertFalse(dfaComplement.accepts("bb"));
	}

	@Test
	public void testComplement2() throws Exception {
		DFA dfa3 = new DFA(s,a,t);
		DFA dfaComplement = dfa3.complement();
		assertTrue(dfaComplement.accepts("b"));
	}

}
