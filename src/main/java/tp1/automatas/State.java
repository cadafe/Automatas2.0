package tp1.automatas;

/**
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class State {

	//state name
	private String name;

	//true when the state is marked to be a final state
	private boolean isFinal;

	//true when the state is marked to be a initial state
	private boolean isInitial;

	public State(String name, boolean isInitial, boolean isFinal) {
		String name2 = new String(name);
		this.name = name2;
		this.isInitial = isInitial;
		this.isFinal = isFinal;
	}
	public State(State s) {
		String name2 = new String(s.getName());
		this.name = name2;
		this.isInitial = s.isInitial();
		this.isFinal = s.isFinal();
	}

	public String getName() {
		return this.name;
	}

	public boolean isFinal(){
		return isFinal;
	}

	public boolean isInitial(){
		return isInitial;
	}

	public void setInitial(boolean e) {
		this.isInitial = e;
	}

	public void setFinal(boolean e) {
		this.isFinal = e;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "(name=" + name + ", isFinal=" + isFinal + ", isInitial=" + isInitial + ")";
	}

}
