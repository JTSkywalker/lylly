/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;


public class Organizer {

	private List<Task> toDo;
	private List<Phase> phases;
	private List<Prospect> openProspects;
	private List<Prospect> succProspects;
	private List<Prospect> failProspects;
	//TODO: methods

	private List<Property> interests;
	private List<Property> tags;
	private List<Property> contexts;
	//TODO: delegates

	private transient Property currContext;
	private transient Phase currPhase;
	//TODO: getter & setter

}
