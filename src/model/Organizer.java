/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;


public class Organizer {

	private List<Task> toDo;
	private SortedMap< Calendar,Map<Tag,Integer> > quotas; // maybe other data structure
	private List<Prospect> openProspects;
	private List<Prospect> succProspects;
	private List<Prospect> failProspects;
	//TODO: methods
	private List<Generator> gens;

	private List<Tag> tags;
	//TODO: delegates

}
