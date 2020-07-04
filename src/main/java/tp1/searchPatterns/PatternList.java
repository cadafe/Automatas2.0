package tp1.searchPatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tp1.automatas.Alphabet;
import tp1.automatas.AutomatonException;
import tp1.automatas.DFA;
import tp1.automatas.State;
import tp1.automatas.StateSet;
import tp1.utils.Tupla;

public class PatternList {
	
	/*
	This list stores deterministic finite automata,
	each of them represents a pattern of characters whose occurrence
	we want to check in a given text file (in hexadecimal format)
	 * */
	private List<DFA> patterns;
	
	private DFA patternsDFA;
	
	private static Set<Character>  sigma = new HashSet<Character>(Arrays.asList('0','1','2','3','4','5','6','7','8','9', 'a', 'b', 'c', 'd', 'e' , 'f'));

	
	public PatternList(List<DFA> list) throws AutomatonException {
		this.patterns = list;
		try {
			patternsDFA =buildAutomaton();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Builds and returns the automaton that recognizes any of the patterns in the
	 * pattern list
	 * 
	 * @return the automaton that recognizes any of the patterns in the pattern list
	 * @throws Exception
	 */
	private DFA buildAutomaton() throws Exception {
		
		Alphabet alph = new Alphabet(sigma);

		DFA dfa1 = alph.sigmaStar();

		DFA dfaUnion = forAllUnion();

		return (dfa1.concat(dfaUnion)).concat(dfa1);
	}
	

	/*
	 * This method returns the automaton union 
	 * of the automata contained in the Patterns list
	 * */
	private DFA forAllUnion() throws AutomatonException, Exception {
		
		List<DFA> a = new ArrayList<DFA>(patterns);

		DFA allUnion = a.remove(0);

		for (DFA dfa : a) {
			allUnion = allUnion.union(dfa);
		}

		return allUnion;
	}
	
	
	/* @return true if any of the patterns in 
	 * the pattern list occurs in the given string, 
	 * false in otherwise
	 * @param line: string to be  scanned.
	 * */
	public boolean scan(String line) throws AutomatonException {
		if (patternsDFA ==null)	
			throw new AutomatonException();
		return patternsDFA.accepts(line);
	}

	
}
