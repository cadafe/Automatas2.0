package tp1.automatas;

import java.util.*;

import tp1.utils.Tupla;

/**
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class NFA extends FA {

	// Constructor
	public NFA(StateSet states, Alphabet alphabet, Set<Tupla<State,Character,State>> transitions) 
	throws IllegalArgumentException, AutomatonException{
		this.states = states;
		this.alphabet = alphabet;
		this.delta = new HashMap<State, HashMap<Character, StateSet>>();
		
		HashMap<Character, StateSet> stateArcs;
		StateSet currentStateSet;

		//For each tupla in transitions Set
		for (Tupla<State, Character, State> tupla : transitions) {
			currentStateSet = new StateSet();
			stateArcs =  new HashMap<Character, StateSet>();
			
			if (states.belongTo(tupla.first().getName()) != null) {
				//Gets the hash mapped for the current state if exists
				if (this.delta.containsKey(tupla.first())) {
					stateArcs = this.delta.get(tupla.first());
				}
				//Gets the StateSet mapped for the current char
				if (stateArcs.containsKey(tupla.second())) {
					currentStateSet = stateArcs.get(tupla.second());
				}
				currentStateSet.addState(tupla.third().getName());
				stateArcs.put(tupla.second(), currentStateSet);
				this.delta.put(tupla.first(), stateArcs);
				
			} else {
				throw new IllegalArgumentException("Transition's states must be in NFA 'states' attribute");
			}
		}

		assert repOk();
	}

	@Override
	public boolean accepts(String string) {
		// assert repOk();
		// assert string != null;
		// assert verifyString(string);
		// Stack<Tupla2<State,Integer>> s = new Stack<Tupla2<State,Integer>>();
		// Tupla2<State,Integer> t = new Tupla2<State,Integer>(this.initialState(), 0);
		// s.push(t);
		// Tupla2<State,Integer> st = null;
		// while ( !s.empty()) {
		// 	st = s.pop();
		// 	if (st.second() < string.length()){
		// 		StateSet states = delta(st.first(), string.charAt(st.second()));
		// 		if (states != null){
		// 			for (State stateAux: states){
		// 				Tupla2<State,Integer> tup = new Tupla2<State,Integer>(stateAux, st.second()+1);
		// 				s.push(tup);
		// 			}
		// 		}
		// 	}
		// }
		// return st.first().isFinal() && st.second() == string.length();
		return false;
	}

	/**
	 * Check that the alphabet does not contains lambda.
	 * Check that one and just one state is marked to be a initial state.
	 * Check that all transitions are correct. All states and characters should be part of the automaton set of states and alphabet.
	 */
	@Override
	public boolean repOk() {
		
		int initState = 0;

		Set<State> ss = this.delta.keySet();

		if (alphabet.contains('/'))
			return false;
		
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
						for (State state : p) {
							if (!states.contains(state))
								return false;
						}
					}
				}
			}
		}

		return true;
	}
}
