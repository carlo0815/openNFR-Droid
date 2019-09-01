package net.reichholf.nfrdroid.asynctask;

import android.content.Context;

import net.reichholf.nfrdroid.Profile;
import net.reichholf.nfrdroid.R;
import net.reichholf.nfrdroid.helpers.ExtendedHashMap;
import net.reichholf.nfrdroid.helpers.enigma2.CheckProfile;

public class CheckProfileTask extends AsyncHttpTaskBase<Void, String, ExtendedHashMap> {
	private Profile mProfile;

	public CheckProfileTask(Profile p, CheckProfileTaskHandler taskHandler) {
		super(taskHandler);
		mProfile = p;
	}

	@Override
	protected ExtendedHashMap doInBackground(Void... params) {
		CheckProfileTaskHandler taskHandler = (CheckProfileTaskHandler) mTaskHandler.get();
		if (taskHandler == null)
			return null;
		publishProgress(taskHandler.getString(R.string.checking));
		return CheckProfile.checkProfile(mProfile, taskHandler.getProfileCheckContext());
	}

	@Override
	protected void onProgressUpdate(String... progress) {
		CheckProfileTaskHandler taskHandler = (CheckProfileTaskHandler) mTaskHandler.get();
		if (taskHandler != null)
			taskHandler.onProfileCheckProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(ExtendedHashMap result) {
		CheckProfileTaskHandler taskHandler = (CheckProfileTaskHandler) mTaskHandler.get();
		if (!isCancelled() && taskHandler != null)
			taskHandler.onProfileChecked(result);
	}

	public interface CheckProfileTaskHandler extends AsyncHttpTaskBaseHandler {
		void onProfileChecked(ExtendedHashMap result);

		void onProfileCheckProgress(String state);

		Context getProfileCheckContext();
	}
}