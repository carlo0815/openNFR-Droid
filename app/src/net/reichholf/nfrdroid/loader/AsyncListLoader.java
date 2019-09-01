/* © 2010 Stephan Reichholf <stephan at reichholf dot net>
 * 
 * Licensed under the Create-Commons Attribution-Noncommercial-Share Alike 3.0 Unported
 * http://creativecommons.org/licenses/by-nc-sa/3.0/
 */

package net.reichholf.nfrdroid.loader;

import android.content.Context;
import android.os.Bundle;
import androidx.loader.content.AsyncTaskLoader;
import android.util.Log;

import net.reichholf.nfrdroid.nfrdroid;
import net.reichholf.nfrdroid.R;
import net.reichholf.nfrdroid.helpers.ExtendedHashMap;
import net.reichholf.nfrdroid.helpers.NameValuePair;
import net.reichholf.nfrdroid.helpers.SimpleHttpClient;
import net.reichholf.nfrdroid.helpers.enigma2.requestinterfaces.ListRequestInterface;

import java.util.ArrayList;

/**
 * @author sre
 * 
 */
public class AsyncListLoader extends AsyncTaskLoader<LoaderResult<ArrayList<ExtendedHashMap>>> {
	protected ArrayList<ExtendedHashMap> mList;
	protected ListRequestInterface mListRequestHandler;
	protected boolean mRequireLocsAndTags;
	protected SimpleHttpClient mShc;
	protected ArrayList<NameValuePair> mParams;

	/**
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	public AsyncListLoader(Context context, ListRequestInterface listRequestHandler, boolean requireLocsAndTags,
			Bundle args) {
		super(context);
		mListRequestHandler = listRequestHandler;
		mRequireLocsAndTags = requireLocsAndTags;
		nfrdroid.loadCurrentProfile(context);
		mShc = new SimpleHttpClient();
		

		if (args != null && args.containsKey("params"))
			mParams = (ArrayList<NameValuePair>) args.getSerializable("params");
		else
			mParams = new ArrayList<>();
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	protected void onStopLoading() {
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override
	public LoaderResult<ArrayList<ExtendedHashMap>> loadInBackground() {
		if (mListRequestHandler == null) {
			throw new UnsupportedOperationException(
					"Method doInBackground not re-implemented while no ListRequestHandler has been given");
		}

		if (mRequireLocsAndTags) {
			if (nfrdroid.getLocations().size() <= 1) {
				if (!nfrdroid.loadLocations(mShc)) {
					Log.e(nfrdroid.LOG_TAG, "ERROR loading locations");
				}
			}

			if (nfrdroid.getTags().size() <= 1) {
				if (!nfrdroid.loadTags(mShc)) {
					Log.e(nfrdroid.LOG_TAG, "ERROR loading tags");
				}
			}
		}

		mList = new ArrayList<>();
		LoaderResult<ArrayList<ExtendedHashMap>> result = new LoaderResult<>();

		String xml = mListRequestHandler.getList(mShc, mParams);
		if (xml != null) {
			mList.clear();
			if (mListRequestHandler.parseList(xml, mList))
				result.set(mList);
			else
				result.set(getContext().getString(R.string.error_parsing));
		} else {
			if (mShc.hasError())
				result.set(mShc.getErrorText(getContext()));
			else
				result.set(getContext().getString(R.string.error));
		}
		return result;
	}

}
