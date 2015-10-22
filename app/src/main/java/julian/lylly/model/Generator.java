/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


public interface Generator<E> extends Serializable {

	public List<E> genTasksFromTill(Calendar start, Calendar end);

}
