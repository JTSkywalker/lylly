/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;


public class Tree<T> {

	private List<T> children;
	private T parent;

	public List<T> getChildren() {
		return children;
	}

	public T getParent() {
		return parent;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}
	
}
