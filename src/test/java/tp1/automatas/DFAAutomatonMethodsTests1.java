package tp1.automatas;

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

	// Tests for DFA2
	@Test
	public void testAccept() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.accepts("bbbbbb"));
	}

	@Test
	public void testNoAccept() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertFalse(dfa.accepts("bbbbb"));
	}

	@Test
	public void testComplement1() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertFalse(dfa.complement().accepts("bb"));
	}

	@Test
	public void testComplement2() throws Exception {
		DFA dfa = new DFA(s,a,t);
		assertTrue(dfa.complement().accepts("b"));
	}
}
