/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


public class DevTask extends Task {

	private int devlmnt;
	private final int steps;

	public DevTask(int steps) {
		this.steps = steps;
	}

}
