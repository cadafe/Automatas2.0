package tp1.automatas;

import java.util.*;
import tp1.utils.Tupla;

/**
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class NFALambda extends FA {

	/*
	 *  Automata methods
	*/
	public NFALambda(StateSet states,	Alphabet alphabet, Set<Tupla<State,Character,State>> transitions) 
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

	// PROBLEMA CON EL CARACTER NULL
	@Override
	public boolean accepts(String string) {
		// assert repOk();
		// int lenghtStr;
		// if (string != null)
		// 	lenghtStr = string.length();
		// else
		// 	lenghtStr = 0;
		// System.out.println(string.charAt(0));
		// Stack<Tupla2<State,Integer>> s = new Stack<Tupla2<State,Integer>>();
		// Tupla2<State,Integer> t = new Tupla2<State,Integer>(this.initialState(), 0);
		// s.push(t);
		// Tupla2<State,Integer> st = null;
		// while ( !s.empty()) {
		// 	st = s.pop();
		// 	if (st.second() < lenghtStr){
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
	 * Converts the automaton to a DFA.
   *
	 * @return DFA recognizing the same language.
	 */
	// public DFA toDFA() {
	// 	assert repOk();
	// 	Iterator<Map.Entry<State, HashMap<Character, StateSet>>> i = this.delta.entrySet().iterator();

	// 	while (i.hasNext()){
	// 		Map.Entry<State, HashMap<Character, StateSet>> arcs = (Map.Entry<State, HashMap<Character, StateSet>>) i.next();
	// 		State s1 = arcs.getKey();
	// 		HashMap<Character, StateSet> s1Transitions = this.delta.get(s1);
	// 		StateSet lstates = null;

	// 		if (arcs.getValue().containsKey('/')) {
				
	// 			lstates = arcs.getValue().get('/'); //get states reached by lamda ('/')
	// 			s1Transitions.remove('/'); //deletes the lamda transition

	// 			HashMap<Character, StateSet> skTransitions = null;
				
	// 			for (State sk: lstates) {
	// 				skTransitions = this.delta.get(sk);
	// 				s1Transitions.putAll(skTransitions);

	// 				if (sk.isFinal()) {
	// 					s1.setFinal(true);
	// 				}
	// 				if (s1.isInitial()) {
	// 					sk.setInitial(true);
	// 				}

	// 				this.delta.put(sk, skTransitions);
	// 			}
	
	// 			this.delta.put(s1, s1Transitions);		
	// 		}		
			
	// 	}
	// 	DFA result = new DFA();
	// 	result.importAtt(this.states, this.alphabet, this.delta);
		
	// 	return result;
	// }

	public void buildDFA () {
		
	}

	public StateSet statesReachedBy(StateSet ss, Character c) {
		
		if (ss.size() == 0) {
			return new StateSet();
		} else {
			StateSet temp;
			StateSet result = new StateSet();
			HashMap<Character, StateSet> arcs;
			//Iteration over the state set
			for (State q : ss) {
				temp = new StateSet();
				arcs = delta.get(q);

				if (arcs.containsKey(c)) 
					temp = arcs.get(c);

				result = result.union(temp);
			}

			return ss.union(statesReachedBy(result, c));
		}
	}

	public DFA toDFA() {
		assert repOk();
		Set<Tupla<StateSet,Character,StateSet>> newTransitions = new HashSet<Tupla<StateSet,Character,StateSet>>();

		//Creates singleton StateSet with initial state 
		Set<State> aux = new HashSet<State>();
		aux.add(this.initialState());
		StateSet initSet = new StateSet(aux);



	}

	public Set<Tupla<StateSet,Character,StateSet>> prox(StateSet ss, Set<Tupla<StateSet,Character,StateSet>> xx) {
		if (ss.size() == 0) {
			return xx;
		}
		Set<Tupla<StateSet,Character,StateSet>> newTransitions = new HashSet<Tupla<StateSet,Character,StateSet>>();
		StateSet aux = ss;
		Tupla<StateSet, Character, StateSet> auxTupla = null;
		for (Character c : this.alphabet) {
			aux = statesReachedBy(aux, c);
			aux = statesReachedBy(ss, '/');
			if (aux.size() > 0) {
				auxTupla = new Tupla<StateSet, Character, StateSet>(ss, c, aux);
				newTransitions.add(auxTupla);
			}
			newTransitions = prox(aux, newTransitions);
		}
		
		return newTransitions;
	}
	
	

	/**
	 * Check that the alphabet does not contains lambda.
	 * Check that one and just one  state is marked to be a initial state.
	 * Check that all transitions are correct. All states and characters should be part of the automaton set of states and alphabet.
	 */
	@Override
	public boolean repOk() {
		//hacer

		return false;
	}

}
