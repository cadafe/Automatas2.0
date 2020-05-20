package tp1.automatas;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class NFAAutamatonMethodsTests {

  private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/tp1/nfa1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
	}

	// Tests for NFA1
	@Test
	public void testRepOk() throws Exception {
		NFA nfa = new NFA(s,a,t);
		assertTrue(nfa.repOk());
	}

	// Tests for NFA1
	@Test
	public void testAccept() throws Exception {
		NFA nfa = new NFA(s,a,t);
		assertTrue(nfa.accepts("aba"));
  }

  @Test
	public void testAccept2() throws Exception {
		NFA nfa = new NFA(s,a,t);
		assertTrue(nfa.accepts("abaa"));
	}

	@Test
	public void testNoAccept() throws Exception {
		NFA nfa = new NFA(s,a,t);
		assertFalse(nfa.accepts("abb"));
  }

  @Test
	public void testNoAccept2() throws Exception {
		NFA nfa = new NFA(s,a,t);
		assertFalse(nfa.accepts("aa"));
	}
}
