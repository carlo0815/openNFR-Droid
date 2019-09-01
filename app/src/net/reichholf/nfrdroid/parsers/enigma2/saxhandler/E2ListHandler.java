/* © 2010 Stephan Reichholf <stephan at reichholf dot net>
 * 
 * Licensed under the Create-Commons Attribution-Noncommercial-Share Alike 3.0 Unported
 * http://creativecommons.org/licenses/by-nc-sa/3.0/
 */

package net.reichholf.nfrdroid.parsers.enigma2.saxhandler;

import net.reichholf.nfrdroid.helpers.ExtendedHashMap;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * @author sre
 *
 */
public abstract class E2ListHandler extends DefaultHandler {
	protected ArrayList<ExtendedHashMap> mList;
	
	public E2ListHandler(){
		mList = null;
	}
	
	public void setList(ArrayList<ExtendedHashMap> list){
		mList = list;
	}
}