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
			singletonStateSet = new StateSet();
			
			if (states.belongTo(tupla.first().getName()) != null) {
				//Gets the map value for the current state if exists
				if (this.delta.containsKey(tupla.first())) {
					stateArcs = this.delta.get(tupla.first());
				} else {
					stateArcs = new HashMap<Character, StateSet>();
				}
				
				if (stateArcs.containsKey(tupla.second())) {
					// since dfa must have one transition per character per state
					throw new IllegalArgumentException("Invalid transitions for DFA");
				} else {
					singletonStateSet.addState(tupla.third());
					stateArcs.put(tupla.second(), singletonStateSet);
					this.delta.put(tupla.first(), stateArcs);
				}
			} else {
				throw new IllegalArgumentException("Transition's states must be in DFA 'states' attribute");
			}
		}

		assert repOk();
	}

	// public DFA(StateSet ss, Alphabet aa, HashMap<State, HashMap<Character, StateSet>> delt) throws CloneNotSupportedException, AutomatonException {
	// 	Alphabet cAlpha = aa.cloneAlpha();
	// 	StateSet cSS = ss.cloneSS();
	// 	this.states = cSS;
	// 	this.alphabet = cAlpha;
	// 	HashMap<State, HashMap<Character, StateSet>> clonedDelta =
	// 	new HashMap<State, HashMap<Character, StateSet>>();	
	// 	clonedDelta = cloneDelta(delt);
	// 	this.delta = clonedDelta;
	// }

	// // Deep copy of delta transitions
	// public HashMap<State, HashMap<Character, StateSet>> cloneDelta(
	// HashMap<State, HashMap<Character, StateSet>> d) throws CloneNotSupportedException {
	// 	HashMap<State, HashMap<Character, StateSet>> dcopy = new HashMap<State, HashMap<Character, StateSet>>();
	// 	for (Map.Entry<State, HashMap<Character, StateSet>> entry : d.entrySet()) {
	// 		HashMap<Character, StateSet> cArcs = new HashMap<Character, StateSet>();
	// 		cArcs = cloneArcs(entry.getValue());
	// 		State s = new State(entry.getKey());
	// 		dcopy.put(s, cArcs);
	// 	}
	// 	return dcopy;
	// }

	// // Makes a deep copy of intern map
	// public HashMap<Character, StateSet> cloneArcs(HashMap<Character, StateSet> original) throws CloneNotSupportedException {
	// 	HashMap<Character, StateSet> copy = new HashMap<Character, StateSet>();
	// 	for (Map.Entry<Character, StateSet> arc : original.entrySet()) {
	// 		StateSet clonedSS = new StateSet();
	// 		clonedSS = arc.getValue().cloneSS();
	// 		copy.put(arc.getKey(), clonedSS);
	// 	}
	// 	return copy;
	// }
	
	public DFA() {
		this.states = null;
		this.alphabet = null;
		this.delta = null;
		assert repOk();
	}

	// public DFA cloneDFA() throws CloneNotSupportedException {
	// 	Alphabet clonedAlphabet = this.alphabet.cloneAlpha();
	// 	StateSet clonedStates = this.states.cloneSS();
	// 	HashMap<State, HashMap<Character, StateSet>> clonedDelta = new HashMap<State, HashMap<Character, StateSet>>();
	// 	clonedDelta.putAll(this.delta);
	// 	DFA clonedDFA = new DFA(clonedStates, clonedAlphabet, clonedDelta);
	// 	return clonedDFA;
	// }

	//Chequear linea 111 y 116
	@Override
	public boolean accepts(String string) throws IllegalArgumentException, AutomatonException{
		assert repOk();
		if (string == null) throw new IllegalArgumentException("String can't be null");
		if (!verifyString(string)) 
			throw new IllegalArgumentException("The string's characters must belong to automaton's alphabet");

		State s = this.initialState();
		for (char c : string.toCharArray()) {
			StateSet singletonSet = new StateSet();
			singletonSet = delta(s, c);
			System.out.println("im "+c+"!");
			System.out.println(s.toString());
			if (singletonSet.size() > 0) {
				s = singletonSet.get(0);
				System.out.println("Image state"+s.toString());
			} else {
				return false;
			}
		}
		System.out.println("is final "+s.isFinal());
		return s.isFinal();
	}
	
	/**
	 * Check that one and just one  state is marked to be a initial state.
	 * Check that all transitions are correct. All states and characters should be part of the automaton set of states and alphabet.
	 * Check that there are not lambda transitions.
	 * Check that the transition relation is deterministic.
	 */
	@Override
	public boolean repOk() {
		if (states == null && alphabet == null && delta == null) {
			return true;
		}

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
	 * @throws CloneNotSupportedException
	 * 
	 *
	 * @throws AutomatonException
	 *
	 * @returns a new DFA accepting the language's complement.
	 */
	public DFA complement(DFA other) throws CloneNotSupportedException, AutomatonException {
		
		assert other.repOk();

		StateSet ss = new StateSet();
		Alphabet a = new Alphabet();
		Set<Tupla<State,Character,State>> t = new HashSet<Tupla<State,Character,State>>();

		ss = (other.states).cloneSS();
		for (State s : ss) {
			if(s.isFinal()) {
				s.setFinal(false);
			} else {
				s.setFinal(true);
			}
		}

		a = (other.alphabet).cloneAlpha();

		// New cloned other.delta
		for (State s : other.states) {
			for (Character c : other.alphabet) {
				StateSet setD = other.delta(s, c);
				if(setD.size() > 0) {
					t.add(new Tupla<State,Character,State>(s.cloneState(), c, (setD.get(0)).cloneState()));
				}
			}
		}

		return new DFA(ss, a, t);
	}

	/**
	 * Returns a new automaton which recognizes the intersection of both languages,
	 * the one accepted by 'this' and the one represented by 'other'.
	 *
	 * @throws Exception
	 *
	 * @returns a new DFA accepting the intersection of both languages.
	 */
	// public DFA intersection(DFA other) throws Exception {
		
	// 	DFA aux1 = new DFA(this.states, this.alphabet, this.delta);

	// 	DFA aux2 = new DFA(other.states, other.alphabet, other.delta);

	// 	aux1.complement();
	// 	aux2.complement();
	// 	DFA result = new DFA((aux1.union(aux2)).states, (aux1.union(aux2)).alphabet, (aux1.union(aux2).delta));
		
	// 	return result.complement();
	// }

	/**
	 * Returns a new automaton which recognizes the union of both languages, the one
	 * accepted by ’this’ and the one represented by ’other’.
	 *
	 * @throws Exception
	 * @throws AutomatonException
	 *
	 * @returns a new DFA accepting the union of both languages.
	 */
	public DFA union(DFA other) throws AutomatonException, Exception {
		assert repOk();
		assert other.repOk();
		
		// New initial state
		State newInitState = new State("q'", true, false);
		// New StateSet
		StateSet ss = new StateSet(); 
		//New Alphabet
		Alphabet a = new Alphabet();
		// New Transitions
		Set<Tupla<State, Character, State>> t = new HashSet<Tupla<State, Character, State>>();

		// New cloned StateSet 
		ss = (this.states).cloneSS();
		ss.union((other.states).cloneSS());

		for (State s : ss) {
			if(s.isInitial()) {
				s.setInitial(false);
				t.add(new Tupla<State,Character,State>(newInitState, '/', s));
			} 
		}
		
		ss.addState(newInitState);

		// New cloned Alphabet
		a = (this.alphabet).cloneAlpha();
		a.union((other.alphabet).cloneAlpha());

		// New cloned this.delta
		for (State s : this.states) {
			for (Character c : this.alphabet) {
				StateSet setD = this.delta(s, c);
				if(setD.size() > 0) {
					t.add(new Tupla<State,Character,State>(s.cloneState(), c, (setD.get(0)).cloneState()));
				}
			}
		}

		// New cloned other.delta
		for (State s : other.states) {
			for (Character c : other.alphabet) {
				StateSet setD = other.delta(s, c);
				if(setD.size() > 0) {
					t.add(new Tupla<State,Character,State>(s.cloneState(), c, (setD.get(0)).cloneState()));
				}
			}
		}

		return new DFA(ss, a, t);
	}


	/*
	 * Implementar la concatenacion entre dos DFA
	 */
	// public DFA concat(DFA dfa) throws AutomatonException, CloneNotSupportedException {
	// 	StateSet states1 = this.states;
	// 	StateSet states2 = dfa.states;
	// 	StateSet newStates = states1.union(states2);

	// 	Alphabet alphabet1 = this.alphabet;
	// 	Alphabet alphabet2 = dfa.alphabet;
	// 	Alphabet newAlphabet = alphabet1.union(alphabet2);

	// 	HashMap<State, HashMap<Character, StateSet>> delta1 = this.delta;
	// 	HashMap<State, HashMap<Character, StateSet>> delta2 = dfa.delta;
	// 	HashMap<State, HashMap<Character, StateSet>> newDelta = new HashMap<State, HashMap<Character, StateSet>>();
	// 	//falta chequear caso donde dos automatas contengan estados con el mismo nombre
	// 	newDelta.putAll(delta1);
	// 	newDelta.putAll(delta2);
	// 	//aqui consideramos que el primer automata tiene solo 1 estado final
	// 	//en caso de ser necesario en el for each hay que obtener todos los finales en un StateSet
	// 	//para luego crear correspondientes transiciones
	// 	Set<State> values1 = delta1.keySet();
	// 	State final1 = null;
	// 	for (State s1 : values1) {
	// 		if (s1.isFinal()) 
	// 			final1 = new State(s1);
	// 	}

	// 	Set<State> values2 = delta2.keySet();
	// 	State initial2 = null;
	// 	for (State s2 : values2) {
	// 		if (s2.isInitial()) 
	// 			initial2 = new State(s2);
	// 	}
	// 	StateSet singleton = new StateSet();
	// 	singleton.addState(initial2);

	// 	HashMap<Character, StateSet> innerMap = newDelta.get(final1);
	// 	innerMap.put('/', singleton);
	// 	NFALambda NFALaux = new NFALambda(newStates, newAlphabet, newDelta);
	// 	DFA finalAutomaton = NFALaux.toDFA();
	// 	return finalAutomaton;
	// }

}
