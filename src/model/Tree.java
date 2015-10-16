/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a generics-class for trees
 * @param <T> the type of the tree-nodes
 */
public class Tree<T extends Tree<T>> {

	private List<T> children;
	private T parent;//TODO: maybe no parent

	/**
	 *
	 * @return the children of this tree-node,
	 * an empty list if this node is a leaf
	 */
	public List<T> getChildren() {
		return children;
	}

	/**
	 * sets a list of children, the old list is overwritten.
	 * @param children the new list of children
	 */
	public void setChildren(List<T> children) {
		this.children = children;
	}
	/**
	 * adds a child
	 * @param child the child to be added
	 */
	public void addChild(T child) {
		this.children.add(child);
	}

	/**
	 * deletes all children
	 */
	public void delChildren() {
		this.children = new ArrayList<>();
	}

	/**
	 *
	 * @return the parent of this node, returns null if node is a root
	 */
	public T getParent() {
		return parent;
	}

	/**
	 * sets a new parent
	 * @param parent the new parent
	 */
	public void setParent(T parent) {
		this.parent = parent;
	}

	public boolean contains(T other) {
		if (this.equals(other)) {
			return true;
		}
		for(T child : children) {
			if (child.contains(other)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 67 * hash + Objects.hashCode(this.children);
		hash = 67 * hash + Objects.hashCode(this.parent);
		return hash;
	}

	//TODO: buggy?
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Tree<?> other = (Tree<?>) obj;
		if (!Objects.equals(this.children, other.children)) {
			return false;
		}
		if (!Objects.equals(this.parent, other.parent)) {
			return false;
		}
		return true;
	}

}
