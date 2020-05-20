package tp1.automatas;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class StateSet implements Iterable<State>{

	private Set<State> states;

	public StateSet() {
		states = new HashSet<State>();
	}

	public StateSet(Set<State> states) {
		this.states = states;
	}

	public State addState(String name)throws AutomatonException{
		if(name == null || name =="")
			throw new AutomatonException("Node name invalid");

		for(State f : states) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		State freshState = new State(name, false, false);
		states.add(freshState);
		return freshState;
	}

	public State addState(String name, boolean isInitial, boolean isFinal) throws AutomatonException{
		if(name == null || name =="")
			throw new AutomatonException("Node name invalid");

		for(State f : states) {
			if(f.getName().equals(name)) {
				f.setInitial(isInitial);
				f.setFinal(isFinal);
				return f;
			}
		}
		State freshState = new State(name, isInitial, isFinal);
		states.add(freshState);
		return freshState;
	}

	public void deleteState(String name) {
		for(State f : states) {
			if(f.getName().equals(name)) {
				states.remove(f);
				return;
			}
		}
	}

	public State belongTo(String name) {
		for(State f : states) {
			if(f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}

	public int size() {
		return states.size();
	}

	public State get(int index) {
		int i = 0;
		for (State state : states) {
			if (i == index) return state;
			i += 1;
		}
		return null;
	}

	public StateSet union(StateSet ss) {
		states.addAll(ss.states);
		return this;
	}

	@Override
	public Iterator<State> iterator() {
		return states.iterator();
	}

	@Override
	public String toString() {
		return states.toString();
	}

	public boolean contains(State state) {
		return states.contains(state);
	}
}
