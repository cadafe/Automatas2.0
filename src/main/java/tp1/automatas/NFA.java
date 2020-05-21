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
	public boolean accepts(String string) throws AutomatonException {
		
		
		Set<State> st = new HashSet<State>();
		st.add(this.initialState());
		StateSet aux = new StateSet(st);

		for (Character c : string.toCharArray()) {
			aux = aux.union(closure(aux, c, false));
		}
		return aux.containsSomeOf(this.finalStates());
	}

	public StateSet closure (StateSet ss, Character c, Boolean total) {
		// if ss is empty, returns an empty set
		if (ss.size() == 0) {
			return new StateSet();
		} else {
			StateSet temp;
			StateSet result = new StateSet();
			HashMap<Character, StateSet> arcs;
			// iterates over ss
			for (State q : ss) {
				temp = new StateSet();
				arcs = delta.get(q);
				// gets the states reached by c, if they exists
				if (arcs.containsKey(c)) 
					temp = arcs.get(c);
		
				result = result.union(temp);
			}
			if (total) {
				return ss.union(closure(result, c, true));
			} else {
				return result;
			}

		}
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
