package tp1.automatas;

import java.util.*;

import tp1.utils.Tupla;
/**
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class DFA extends FA {

	public DFA(StateSet states, Alphabet alphabet, Set<Tupla<State, Character, State>> transitions) 
	throws IllegalArgumentException, AutomatonException {

		this.states = states;
		this.alphabet = alphabet;
		this.delta = new HashMap<State, HashMap<Character, StateSet>>();
		HashMap<Character, StateSet> stateArcs;
		StateSet singletonStateSet;

		for (Tupla<State, Character, State> tupla : transitions) {
			stateArcs = new HashMap<Character, StateSet>();
			singletonStateSet = new StateSet();

			if (states.belongTo(tupla.first().getName()) != null) {
				//Gets the hash mapped for the current state if exists
				if (this.delta.containsKey(tupla.first())) {
					stateArcs = this.delta.get(tupla.first());
				}
				if (stateArcs.containsKey(tupla.second())) {
					throw new IllegalArgumentException("Invalid transitions for DFA");
				} else {
					singletonStateSet.addState(tupla.third().getName());
					stateArcs.put(tupla.second(), singletonStateSet);
					this.delta.put(tupla.first(), stateArcs);
				}
			} else {
				throw new IllegalArgumentException("Transition's states must be in DFA 'states' attribute");
			}
		}

		assert repOk();
	}

	public DFA() {

		this.states = null;
		this.alphabet = null;
		this.delta = null;
		assert repOk();
	}

	@Override
	public boolean accepts(String string) throws IllegalArgumentException{
		assert repOk();
		if (string == null) throw new IllegalArgumentException("String can't be null");
		if (!verifyString(string)) 
			throw new IllegalArgumentException("The string's characters must belong to automaton's alphabet");

		State s = this.initialState();
		StateSet singletonSet;
		for (char c : string.toCharArray()) {
			singletonSet = delta(s, c);
			if (singletonSet.size() > 0) 
				s = singletonSet.get(0);
			else 
				return false;
		}
		return s.isFinal();
	}

	public void importAtt(StateSet st, Alphabet a, HashMap<State, HashMap<Character, StateSet>> d) {

		this.states = st;
		this.alphabet = a;
		this.delta = d;
	}

	/**
	 * Check that one and just one  state is marked to be a initial state.
	 * Check that all transitions are correct. All states and characters should be part of the automaton set of states and alphabet.
	 * Check that there are not lambda transitions.
	 * Check that the transition relation is deterministic.
	 */
	@Override
	public boolean repOk() {

		int initState = 0;

		Set<State> ss = this.delta.keySet();

		for (State s : this.states) {
			if (s.isInitial())
				initState++;
		}

		if (initState != 1)
			return false;

		for (State s : ss) {
			if (!states.contains(s)) {
				return false;
			} else {
				Map<Character, StateSet> m = delta.get(s);
				Set<Character> c = m.keySet();

				for (Character ch : c) {
					if ((!alphabet.contains(ch)) || (ch == '/')) {
						return false;
					} else {
						StateSet p = new StateSet();
						p = m.get(ch);
						if (p.size() > 1) {
							return false;
						} else {
							for (State state : p) {
								if (!states.contains(state))
									return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns a new automaton which recognizes the complementary language.
	 *
	 * @throws AutomatonException
	 *
	 * @returns a new DFA accepting the language's complement.
	 */
	public DFA complement() {
		assert repOk();
		DFA complemento = this;

		for (State s : complemento.states) {
			if (s.isFinal()) {
				s.setFinal(false);
			} else
				s.setFinal(true);
		}
		return complemento;
	}


	/**
	 * Returns a new automaton which recognizes the intersection of both languages,
	 * the one accepted by 'this' and the one represented by 'other'.
	 *
	 * @throws Exception
	 *
	 * @returns a new DFA accepting the intersection of both languages.
	 */
	public DFA intersection(DFA other) throws Exception {
		DFA result = new DFA();

		DFA aux1 = new DFA();

		DFA aux2 = new DFA();

		aux1.importAtt(this.states, this.alphabet, this.delta);
		aux2.importAtt(other.states, other.alphabet, other.delta);

		aux1.complement();
		aux2.complement();

		result.importAtt((aux1.union(aux2)).states, (aux1.union(aux2)).alphabet, (aux1.union(aux2).delta));

		return result.complement();
	}

	/**
	 * Returns a new automaton which recognizes the union of both languages, the one
	 * accepted by ’this’ and the one represented by ’other’.
	 *
	 * @throws Exception
	 * @throws AutomatonException
	 *
	 * @returns a new DFA accepting the union of both languages.
	 */
	/**
	 * ACLARACION: falta hacer en la union de estados iniciales se junten en uno solo, a traves
	 * de una transicion lambda que precede a estos y asi seguir respetando el invariante
	 */
	public DFA union(DFA other) throws AutomatonException, Exception {

		StateSet ss = new StateSet();

		ss = states.union(other.states);

		for (State s : ss) {
			if (s.isInitial())
				s.setInitial(false);
		}

		ss.addState("q'", true, false);

		Set<Tupla<State, Character, State>> transitions = new HashSet<Tupla<State, Character, State>>();
		for (State s1 : this.states) {
			HashMap<Character, StateSet> a = this.delta.get(s1);
			Set<Character> x = a.keySet();
			for (Character c : x) {
				StateSet y = a.get(c);
				for (State s2 : y) {
					Tupla<State, Character, State> t = new Tupla<State,Character,State>(s1, c, s2);
					transitions.add(t);
				}
			}
		}
		for (State s1 : other.states) {
			HashMap<Character, StateSet> a = other.delta.get(s1);
			Set<Character> x = a.keySet();
			for (Character c : x) {
				StateSet y = a.get(c);
				for (State s2 : y) {
					Tupla<State, Character, State> t = new Tupla<State,Character,State>(s1, c, s2);
					transitions.add(t);
				}
			}
		}

		NFALambda result = new NFALambda(ss, this.alphabet, transitions);

		return result.toDFA();
	}

}
