/* © 2010 Stephan Reichholf <stephan at reichholf dot net>
 * 
 * Licensed under the Create-Commons Attribution-Noncommercial-Share Alike 3.0 Unported
 * http://creativecommons.org/licenses/by-nc-sa/3.0/
 */

package net.reichholf.nfrdroid.helpers.enigma2.requesthandler;

import net.reichholf.nfrdroid.helpers.enigma2.URIStore;
import net.reichholf.nfrdroid.parsers.enigma2.saxhandler.E2SignalHandler;

/**
 * @author sre
 * 
 */
public class SignalRequestHandler extends AbstractSimpleRequestHandler {
	public SignalRequestHandler() {
		super(URIStore.SIGNAL, new E2SignalHandler());
	}
}
