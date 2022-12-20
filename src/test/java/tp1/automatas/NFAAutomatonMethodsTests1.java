package tp1.automatas;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class NFAAutomatonMethodsTests1 {
    
    private static NFA nfa;
	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

    private static NFA nfa2;
	private static StateSet s2;
	private static Alphabet a2;
	private static Set<Tupla<State,Character,State>> t2;

	private static StateSet s3;
	private static Alphabet a3;
	private static Set<Tupla<State,Character,State>> t3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		// DFA
		DotReader dotReader = new DotReader("src/test/java/tp1/dfa2");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
		nfa = new NFA(s, a, t);

		// NFA
		DotReader dotReader2 = new DotReader("src/test/java/tp1/nfa1");
		dotReader2.parse();

		s2 = dotReader2.getNodes();
		a2 = dotReader2.getSymbols();
		t2 = dotReader2.getArcs();
        nfa2 = new NFA(s2, a2, t2);

		// NFALambda
		DotReader dotReader3 = new DotReader("src/test/java/tp1/nfalambda1");
		dotReader3.parse();

		s3 = dotReader3.getNodes();
		a3 = dotReader3.getSymbols();
		t3 = dotReader3.getArcs();
	}

	@Test
	public void testBrokenConstructor() throws IllegalArgumentException, AutomatonException {
		assertTrue(nfa.repOk());
	}

	@Test
	public void testBrokenConstructor2() {
		assertTrue(nfa2.repOk());
	}

	@Test
	public void testBrokenConstructor3() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new NFA(s3, a3, t3);
		});
	}

}
