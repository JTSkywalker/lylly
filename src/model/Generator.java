/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;
import java.util.List;


public interface Generator<E> {

	public List<E> genTasksFromTill(Calendar start, Calendar end);

}
