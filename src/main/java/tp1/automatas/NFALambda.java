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

	@Override
	public boolean accepts(String string) throws AutomatonException {
		DFA aux = this.toDFA();
		return aux.accepts(string);
	}

	
	/**
	 * Converts the automaton to a DFA.
	 * @return DFA recognizing the same language.
	 * @throws AutomatonException
	 */
	public DFA toDFA() throws AutomatonException {
		assert repOk();
		Map<StateSet, State> stateRef = new HashMap<StateSet, State>();
		
		// creates singleton StateSet with initial state 
		Set<State> aux = new HashSet<State>();
		aux.add(this.initialState());
		StateSet initSet = new StateSet(aux);
		
		
		Set<Tupla<StateSet,Character,StateSet>> dfaTransitions = new HashSet<Tupla<StateSet,Character,StateSet>>();
		// apply lamda closure to initSet
		initSet = closure(initSet, '/', true);
		Stack<StateSet> toVisit = new Stack<StateSet>();
		Set<StateSet> visited = new HashSet<StateSet>();
		toVisit.push(initSet);
		// generates all transitions between statesets
		while(!toVisit.isEmpty()) {
			dfaTransitions.addAll(buildTransitions(toVisit, visited));
		}		
		// maps StateSets visited to States 
		int j = 0;
		Set<State> dfaStates = new HashSet<State>();
		Boolean initialSt = false, finalSt = false;
		for (StateSet ss : visited) {
			if (ss.contains(this.initialState())) {
				initialSt = true;
			} else {initialSt = false;}
			if (ss.containsSomeOf(this.finalStates())) {
				finalSt = true;
			} else {finalSt = false;}
			State ns = new State("q"+j, initialSt, finalSt);
			stateRef.put(ss, ns);
			dfaStates.add(ns);
			j++;
		}

		Set<Tupla<State, Character, State>> finalTransitions = new HashSet<Tupla<State, Character, State>>();
		// transforms <StateSet, Character, StateSet> transitions to <State, Char, State>
		for (Tupla<StateSet, Character, StateSet> t : dfaTransitions) {
			State fst = stateRef.get(t.first()); 
			Character snd = t.second();
			State trd = stateRef.get(t.third());
			Tupla<State, Character, State> auxT = new Tupla<State,Character,State>(fst, snd, trd);
			finalTransitions.add(auxT);
		}
		
		return new DFA(new StateSet(dfaStates), this.alphabet, finalTransitions);

	}

	
	/**
	 * Builds the transitions of the new automaton
	 * @param toVisit unvisited StateSets
	 * @param visited visited StateSets
	 * @return all transitions for StateSet on the top of toVisit, and all chars into Alphabet
	 */
	public Set<Tupla<StateSet,Character,StateSet>> buildTransitions (Stack<StateSet> toVisit, Set<StateSet> visited) {
		if (visited.contains(toVisit.peek())) {
			toVisit.pop();
			return new HashSet<Tupla<StateSet, Character, StateSet>>();
		}
		Set<Tupla<StateSet,Character,StateSet>> allTransitions = new HashSet<Tupla<StateSet,Character,StateSet>>();
		StateSet aux = toVisit.pop();
		visited.add(aux);
		Tupla<StateSet, Character, StateSet> auxTupla = null;
		
		for (Character c : this.alphabet) {
			// get the neighbors states for c char
			StateSet arriveSt = closure(aux, c, false);
			if (arriveSt.size() > 0) {
				// adds states reached by lamda starting from itself
				arriveSt = closure(aux, '/', true);
				auxTupla = new Tupla<StateSet, Character, StateSet>(aux, c, arriveSt);
				allTransitions.add(auxTupla);
				toVisit.push(arriveSt);
			}
		}
        return allTransitions;
    }
	
	/**
	 * Gets all states reached by a specific char in all the way, or just neighbors depending on 'total' param value 
	 * @param ss StateSet used as starting point
	 * @param c the symbol used to reach another states
	 * @param total used to determine if go recursive or not. 
	 * @return StateSet with the States reached by c, starting from ss.
	 */
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
	 * Check that one and just one  state is marked to be a initial state.
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
					if ((ch != '/') && (!alphabet.contains(ch))) {
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
