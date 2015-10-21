/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Util {

	public static <T> List<T> processSetsToList(Set<T>... sets) {
		List<T> res = new ArrayList<>();
		for (Set<T> s : sets) {
			res.addAll(s);
		}
		return res;
	}

}
