package rgou.utils;

/**
 * Represents a pair of elements.
 *
 * @param <A> the type of the first element
 * @param <B> the type of the second element
 */
public class Pair<A, B> {
	private A first;
	private B second;

	/**
	 * Constructs a Pair with the specified first and second elements.
	 *
	 * @param first  the first element of the pair
	 * @param second the second element of the pair
	 */
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Gets the first element of the pair.
	 *
	 * @return the first element
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * Sets the first element of the pair.
	 *
	 * @param first the new value for the first element
	 */
	public void setFirst(A first) {
		this.first = first;
	}

	/**
	 * Gets the second element of the pair.
	 *
	 * @return the second element
	 */
	public B getSecond() {
		return second;
	}

	/**
	 * Sets the second element of the pair.
	 *
	 * @param second the new value for the second element
	 */
	public void setSecond(B second) {
		this.second = second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
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
		Pair<?, ?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
}
