package tp1.automatas;

import java.util.Set;

import static org.junit.Assert.assertThrows;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class DFAAutomatonMethodsTests1 {

	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

	private static StateSet s2;
	private static Alphabet a2;
	private static Set<Tupla<State,Character,State>> t2;

	private static StateSet s3;
	private static Alphabet a3;
	private static Set<Tupla<State,Character,State>> t3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		// DFA
		DotReader dotReader = new DotReader("src/test/java/tp1/dfa1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
		

		// NFA
		DotReader dotReader2 = new DotReader("src/test/java/tp1/nfa1");
		dotReader2.parse();

		s2 = dotReader2.getNodes();
		a2 = dotReader2.getSymbols();
		t2 = dotReader2.getArcs();

		// NFALambda
		DotReader dotReader3 = new DotReader("src/test/java/tp1/nfalambda1");
		dotReader3.parse();

		s3 = dotReader3.getNodes();
		a3 = dotReader3.getSymbols();
		t3 = dotReader3.getArcs();
	}

	@Test
	public void testBrokenConstructor() throws IllegalArgumentException, AutomatonException {
		assertThrows(IllegalArgumentException.class, ()-> {
			new DFA(s, a, t);
		});
	}

	@Test
	public void testBrokenConstructor2() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new DFA(s2, a2, t2);
		});
	}

	@Test
	public void testBrokenConstructor3() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new DFA(s3, a3, t3);
		});
	}
}
