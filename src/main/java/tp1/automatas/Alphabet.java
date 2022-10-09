package tp1.automatas;

import java.util.HashSet;
import java.util.Set;

import tp1.utils.Tupla;

import java.util.Iterator;


/**
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class Alphabet implements Iterable<Character> {

	private Set<Character> symbols;

	public Alphabet() {
		symbols = new HashSet<Character>();
	}

	public Alphabet(Set<Character> symbols) {
		Set<Character> alpha = new HashSet<Character>();
		alpha.addAll(symbols);
		this.symbols = alpha;
	}

	// Ver este clone
	public Alphabet cloneAlpha() {
		Alphabet a = new Alphabet();
		for (Character c : symbols) {
			a.addSymbol(c);
		}
		return (Alphabet) a;
	}

	/**
	 * Adds the specified symbol to this alphabet if it is not already present.
	 * @param c a symbol to be added to the alphabet
	 * @return true if c was added to the alphabet, false in other case
	 *
	**/
	public boolean addSymbol(Character c) {
		return symbols.add(c);
	}

	/**
	 * Removes the specified symbol from this alphabet if it is  already present.
	 * @param c a symbol to be removed from the alphabet
	 * @return true if c was removed from the alphabet, false in other case
	**/
	public boolean removeSymbol(Character c) {
		return symbols.remove(c);
	}

	/**
	 * Returns true if this alphabet contains the specified symbol.
	 * @param c a symbol that we want to check if it belongs to the set or not
	 * @return true if c belongs to this alphabet
	**/
	public boolean belongsTo(Character c) {
		return symbols.contains(c);
	}

	public Alphabet union(Alphabet alphabet){
		Set<Character> ret = this.symbols;
		for (Character character : alphabet)
			if (!ret.contains(character)) ret.add(character);
		return new Alphabet(ret); 
	}

	public Set<Character> getSet() {
		return this.symbols;
	}

	@Override
	public String toString() {
		return symbols.toString();
	}

	public boolean contains(Character c) {
		for (Character character : symbols)
			if (character == c) return true;
		return false;
	}
	@Override
	public Iterator<Character> iterator() {
		return symbols.iterator();
	}

	public DFA sigmaStar() throws AutomatonException {

		State s = new State("q0", true, true);
		StateSet ss = new StateSet();

		ss.addState(s);

		Set<Tupla<State, Character, State>> transitions = new HashSet<Tupla<State, Character, State>>();

		for (Character c : this.getSet()) {
			Tupla<State, Character, State> t = new Tupla<State,Character,State>(s, c, s);
			transitions.add(t);
		}

		return new DFA(ss, this, transitions);
	}

}