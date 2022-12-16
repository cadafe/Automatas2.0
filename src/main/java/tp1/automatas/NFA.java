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
			
			if (states.belongTo(tupla.first().getName()) != null) {
				//Gets the hash mapped for the current state if exists
				if (this.delta.containsKey(tupla.first())) {
					stateArcs = this.delta.get(tupla.first());
				} else {
					stateArcs =  new HashMap<Character, StateSet>();
				}
				//Gets the StateSet mapped for the current char
				if (stateArcs.containsKey(tupla.second())) {
					currentStateSet = stateArcs.get(tupla.second());
				}
				currentStateSet.addState(tupla.third());
				stateArcs.put(tupla.second(), currentStateSet);
				this.delta.put(tupla.first(), stateArcs);
				
			} else {
				throw new IllegalArgumentException("Transition's states must be in NFA 'states' attribute");
			}
		}

		assert repOk();
	}

	@Override
	public boolean accepts(String string) throws AutomatonException {
		assert repOk();
		if (string == null) throw new IllegalArgumentException("String can't be null");
		if (!verifyString(string)) 
			throw new IllegalArgumentException("The string's characters must belong to automaton's alphabet");
		
		State s = this.initialState();
		return assistantAccepts(s, string);
	}

	public boolean assistantAccepts(State s, String str) throws AutomatonException {
		StateSet ss = new StateSet();
		StateSet ssl = new StateSet();
		if(str != null) {
			ss = delta(s, str.charAt(0));
			ssl = delta(s, '/');
			if(ssl.size() > 0) {
				ss.union(assistantAccepts2(ssl, str.charAt(0)));
			}
			if(ss.size() > 0) {
				for (State state : ss) {
					if(str.length()==1) {
						if(state.isFinal()) {
							return true;
						}
					} else {
						assistantAccepts(state, str.substring(1));
					}
				}
			} else {
				return false;
			}
		}
		return false;
	} 

	public StateSet assistantAccepts2(StateSet ssl, Character c) throws AutomatonException {
		StateSet ss = new StateSet();
		for (State state : ssl) {
			ss.union(delta(state, c));
		}
		return ss;
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
					if (!alphabet.contains(ch)) {
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
