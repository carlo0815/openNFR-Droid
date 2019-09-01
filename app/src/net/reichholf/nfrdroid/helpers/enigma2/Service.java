/* © 2010 Stephan Reichholf <stephan at reichholf dot net>
 * 
 * Licensed under the Create-Commons Attribution-Noncommercial-Share Alike 3.0 Unported
 * http://creativecommons.org/licenses/by-nc-sa/3.0/
 */

package net.reichholf.nfrdroid.helpers.enigma2;


import net.reichholf.nfrdroid.helpers.ExtendedHashMap;

/**
 * @author sreichholf
 * 
 */
public class Service extends ExtendedHashMap {
	public static final String KEY_NAME = "servicename";
	public static final String KEY_REFERENCE = "reference";
	
	public static boolean isBouquet(String ref){
		return ref.startsWith("1:7:");
	}
	
	public static boolean isMarker(String ref){
		return ref.startsWith("1:64");
	}


}
