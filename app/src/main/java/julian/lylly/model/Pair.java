/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.util.Objects;
import java.util.logging.Logger;


public class Pair<F,S> {

	private F first;
	private S second;

	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public S getSecond() {
		return second;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(this.first);
		hash = 71 * hash + Objects.hashCode(this.second);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Pair<?, ?> other = (Pair<?, ?>) obj;
		if (!Objects.equals(this.first, other.first)) {
			return false;
		}
		if (!Objects.equals(this.second, other.second)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Pair{" + "first=" + first + ", second=" + second + '}';
	}

}
