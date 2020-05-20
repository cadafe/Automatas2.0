package tp1.automatas;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class NFALambdaAutamatonMethodsTests {

  private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		DotReader dotReader = new DotReader("src/test/java/tp1/nfalambda1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();

		NFALambda nfaLambda = new NFALambda(s,a,t);
		System.out.println("STATES ------------> " + nfaLambda.getStates().toString());
    System.out.println("ALPABEHT ---------->" + nfaLambda.getAlphabet().toString());
    System.out.println("-----------------------------------------------------------");
    System.out.println("DELTA -----> " + nfaLambda.delta.toString());
    System.out.println("-----------------------------------------------------------");
    System.out.println("initial state  ------------> " + nfaLambda.initialState().toString());
		System.out.println("final state  ------------> " + nfaLambda.finalStates().toString());
		assertTrue(true);
	}

	// Tests for NFALambda1
	@Test
	public void testRepOk() throws Exception {
		NFALambda nfaLambda = new NFALambda(s,a,t);
		assertTrue(nfaLambda.repOk());
	}

	// @Test
	// public void testAccept() throws Exception {
		// NFALambda nfaLambda = new NFALambda(s,a,t);
		// System.out.println("2");
		// assertTrue(nfaLambda.accepts(" a"));
  // }
//
  @Test
	public void testAccept2() throws Exception {
		NFALambda nfaLambda = new NFALambda(s,a,t);
		assertTrue(nfaLambda.accepts("ca"));
  }

  @Test
	public void testAccept3() throws Exception {
		NFALambda nfaLambda = new NFALambda(s,a,t);
		assertTrue(nfaLambda.accepts("caaaa"));
	}

	@Test
	public void testNoAccept() throws Exception {
		NFALambda nfaLambda = new NFALambda(s,a,t);
		assertFalse(nfaLambda.accepts("ac"));
  }

  @Test
	public void testNoAccept2() throws Exception {
		NFALambda nfaLambda = new NFALambda(s,a,t);
		assertFalse(nfaLambda.accepts("a"));
	}
}