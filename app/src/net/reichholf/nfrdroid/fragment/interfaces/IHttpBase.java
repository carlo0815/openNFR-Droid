/* © 2010 Stephan Reichholf <stephan at reichholf dot net>
 * 
 * Licensed under the Create-Commons Attribution-Noncommercial-Share Alike 3.0 Unported
 * http://creativecommons.org/licenses/by-nc-sa/3.0/
 */

package net.reichholf.nfrdroid.fragment.interfaces;

import android.os.Bundle;

import net.reichholf.nfrdroid.helpers.ExtendedHashMap;
import net.reichholf.nfrdroid.helpers.NameValuePair;
import net.reichholf.nfrdroid.helpers.SimpleHttpClient;

import java.util.ArrayList;

/**
 * @author sre
 */
public interface IHttpBase {
	String getBaseTitle();

	void setBaseTitle(String baseTitle);

	String getCurrentTitle();

	void setCurrentTitle(String currentTitle);

	ArrayList<NameValuePair> getHttpParams(int loader);

	Bundle getLoaderBundle(int loader);

	String getLoadFinishedTitle();

	SimpleHttpClient getHttpClient();

	void onProfileChanged();

	void onSimpleResult(boolean success, ExtendedHashMap result);
}
