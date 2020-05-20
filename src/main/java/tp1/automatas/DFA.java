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
		//hacer todo
		return false;
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
	public DFA intersection(DFA other) throws AutomatonException {
		return null;
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
		return null;
	}

}
