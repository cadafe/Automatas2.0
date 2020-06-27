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

	public DFA(StateSet ss, Alphabet aa, HashMap<State, HashMap<Character, StateSet>> delt) {
		Alphabet cAlpha = aa.cloneAlpha();
		StateSet cSS = ss.cloneSS();
		this.states = cSS;
		this.alphabet = cAlpha;
		HashMap<State, HashMap<Character, StateSet>> clonedDelta =
		new HashMap<State, HashMap<Character, StateSet>>();	
		clonedDelta = cloneDelta(delt);
		this.delta = clonedDelta;
	}

	// Deep copy of delta transitions
	public HashMap<State, HashMap<Character, StateSet>> cloneDelta(
	HashMap<State, HashMap<Character, StateSet>> d) {
		HashMap<State, HashMap<Character, StateSet>> dcopy = new HashMap<State, HashMap<Character, StateSet>>();
		for (Map.Entry<State, HashMap<Character, StateSet>> entry : d.entrySet()) {
			HashMap<Character, StateSet> cArcs = new HashMap<Character, StateSet>();
			cArcs = cloneArcs(entry.getValue());
			State s = new State(entry.getKey());
			dcopy.put(s, cArcs);
		}
		return dcopy;
	}
	// Makes a deep copy of intern map
	public HashMap<Character, StateSet> cloneArcs(HashMap<Character, StateSet> original) {
		HashMap<Character, StateSet> copy = new HashMap<Character, StateSet>();
		for (Map.Entry<Character, StateSet> arc : original.entrySet()) {
			StateSet clonedSS = new StateSet();
			clonedSS = arc.getValue().cloneSS();
			copy.put(arc.getKey(), clonedSS);
		}
		return copy;
	}
	

	public DFA() {
		this.states = null;
		this.alphabet = null;
		this.delta = null;
		assert repOk();
	}

	public DFA cloneDFA() {
		Alphabet clonedAlphabet = this.alphabet.cloneAlpha();
		StateSet clonedStates = this.states.cloneSS();
		HashMap<State, HashMap<Character, StateSet>> clonedDelta = new HashMap<State, HashMap<Character, StateSet>>();
		clonedDelta.putAll(this.delta);
		DFA clonedDFA = new DFA(clonedStates, clonedAlphabet, clonedDelta);
		return clonedDFA;
	}

	// public Object clone() throws CloneNotSupportedException {
	// 	DFA clonedDFA = (DFA) super.clone();
	// 	HashMap<State, HashMap<Character, StateSet>> clonedDelta =
	// 	(HashMap<State, HashMap<Character, StateSet>>) this.delta.clone();
		
	// 	clonedDFA.delta = clonedDelta;
	// 	return super.clone();
	// }


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
			System.out.println("im "+c+"!");
			System.out.println(s.toString());
			if (singletonSet.size() > 0) {
				s = singletonSet.get(0);
				System.out.println("Image state"+s.toString());
			}
			else 
				return false;
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
	 * 
	 *
	 * @throws AutomatonException
	 *
	 * @returns a new DFA accepting the language's complement.
	 */
	public DFA complement() {
		
		assert repOk();
		// copy of the original dfa
		DFA complemento = new DFA(this.states, this.alphabet, this.delta);
		
		for (State s1 : complemento.states) {
			s1.setFinal(!s1.isFinal());
		}
		
		HashMap<State, HashMap<Character, StateSet>> auxDelta = complemento.delta;
		for (State state : auxDelta.keySet()) {
			state.setFinal(!state.isFinal());
		}
		for (HashMap<Character, StateSet> arcs : auxDelta.values()) {
			for (StateSet ss : arcs.values()) {
				for (State sx : ss) {
					sx.setFinal(!sx.isFinal());
				}
			}
		}
				
		complemento.delta = cloneDelta(auxDelta);
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
		
		DFA aux1 = new DFA(this.states, this.alphabet, this.delta);

		DFA aux2 = new DFA(other.states, other.alphabet, other.delta);

		aux1.complement();
		aux2.complement();
		DFA result = new DFA((aux1.union(aux2)).states, (aux1.union(aux2)).alphabet, (aux1.union(aux2).delta));
		
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

		State q1 = null;
		State c1 = null;
		State qInit = null;

		for (State s : this.states) {
			if (s.isInitial())
				s.setInitial(false);
				q1 = s;
		}

		for (State s : other.states) {
			if (s.isInitial())
				s.setInitial(false);
				c1 = s;
		}

		ss = states.union(other.states);

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

		qInit = ss.belongTo("q'");

		Tupla<State, Character, State> t1 = new Tupla<State,Character,State>(qInit, '/', q1);
		Tupla<State, Character, State> t2 = new Tupla<State,Character,State>(qInit, '/', c1);
		transitions.add(t1);
		transitions.add(t2);

		NFALambda result = new NFALambda(ss, this.alphabet, transitions);

		return result.toDFA();
	}

	public DFA sigmaStart() throws AutomatonException {

		State s = new State("q0", true, true);
		StateSet ss = new StateSet();

		ss.addState(s);

		Set<Tupla<State, Character, State>> transitions = new HashSet<Tupla<State, Character, State>>();

		for (Character c : this.alphabet.getSet()) {
			Tupla<State, Character, State> t = new Tupla<State,Character,State>(s, c, s);
			transitions.add(t);
		}

		return new DFA(ss, this.alphabet, transitions);
	}

}
