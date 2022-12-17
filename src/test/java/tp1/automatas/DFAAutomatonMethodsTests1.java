package tp1.automatas;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class DFAAutomatonMethodsTests1 {

	private static DFA dfa;
	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

	private static StateSet s2;
	private static Alphabet a2;
	private static Set<Tupla<State,Character,State>> t2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/tp1/dfa1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
		dfa = new DFA(s, a, t);

		DotReader dotReader2 = new DotReader("src/test/java/tp1/nfa1");
		dotReader2.parse();

		s2 = dotReader2.getNodes();
		a2 = dotReader2.getSymbols();
		t2 = dotReader2.getArcs();
	}

	@Test
	public void testBrokenRepOk() {
		assertFalse(dfa.repOk());
	}

	@Test
	public void testBrokenConstructor() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new DFA(s2, a2, t2);
		});
	}
}
