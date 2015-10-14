/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a generics-class for trees
 * @author JTSkywalker <jtskywalker@t-online.de>
 * @param <T> the type of the tree-nodes, should always be the type of
 * the class extending this class
 */
public class Tree<T> {

	private List<T> children;
	private T parent;

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

}
