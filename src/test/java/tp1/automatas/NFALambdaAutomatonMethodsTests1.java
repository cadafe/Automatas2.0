package tp1.automatas;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import tp1.utils.DotReader;
import tp1.utils.Tupla;

public class NFALambdaAutomatonMethodsTests1 {
    
    private static NFALambda nfal;
	private static StateSet s;
	private static Alphabet a;
	private static Set<Tupla<State,Character,State>> t;

    @BeforeClass
	public static void setUpBeforeClass() throws Exception{
		// NFALambda
		DotReader dotReader = new DotReader("src/test/java/tp1/nfalambda1");
		dotReader.parse();

		s = dotReader.getNodes();
		a = dotReader.getSymbols();
		t = dotReader.getArcs();
		nfal = new NFALambda(s, a, t);
    }

    @Test
	public void testBrokenRepOk() throws IllegalArgumentException, AutomatonException {
		assertTrue(nfal.repOk());
	}

}
