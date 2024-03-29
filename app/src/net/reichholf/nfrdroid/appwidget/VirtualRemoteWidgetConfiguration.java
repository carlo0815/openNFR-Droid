package net.reichholf.nfrdroid.appwidget;

import android.app.ListActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import net.reichholf.nfrdroid.DatabaseHelper;
import net.reichholf.nfrdroid.Profile;
import net.reichholf.nfrdroid.R;
import net.reichholf.nfrdroid.adapter.recyclerview.SimpleExtendedHashMapAdapter;
import net.reichholf.nfrdroid.helpers.ExtendedHashMap;

import java.util.ArrayList;

/**
 * Created by Stephan on 07.12.13.
 */
public class VirtualRemoteWidgetConfiguration extends ListActivity {
	private ArrayList<ExtendedHashMap> mProfileMapList;
	private ArrayList<Profile> mProfiles;
	private SimpleExtendedHashMapAdapter mAdapter;
	private int mAppWidgetId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.virtual_remote_widget_config);
		setResult(RESULT_CANCELED);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			mAppWidgetId = extras.getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		load();
	}

	public void load() {
		DatabaseHelper dbh = DatabaseHelper.getInstance(this);
		mProfileMapList = new ArrayList<>();
		mProfileMapList.clear();
		mProfiles = dbh.getProfiles();
		if (mProfiles.size() > 0) {
			for (Profile m : mProfiles) {
				ExtendedHashMap map = new ExtendedHashMap();
				map.put(DatabaseHelper.KEY_PROFILE_PROFILE, m.getName());
				map.put(DatabaseHelper.KEY_PROFILE_HOST, m.getHost());
				mProfileMapList.add(map);
			}

			mAdapter = new SimpleExtendedHashMapAdapter(this, mProfileMapList, android.R.layout.two_line_list_item, new String[]{
					DatabaseHelper.KEY_PROFILE_PROFILE, DatabaseHelper.KEY_PROFILE_HOST}, new int[]{android.R.id.text1,
					android.R.id.text2});
			setListAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
		} else {
			showToast(getString(R.string.no_profile_available));
			finish();
		}
	}

	private boolean isQuickZapChecked(){
		return ((CheckBox) findViewById(R.id.checkbox_quickzap)).isChecked();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		finish(mProfiles.get(position).getId(), isQuickZapChecked());
	}

	public void finish(int profileId, boolean isQuickZap) {
		saveWidgetConfiguration(profileId, !isQuickZap);
		Context context = getApplicationContext();
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		Profile profile = getWidgetProfile(context, mAppWidgetId);
		VirtualRemoteWidgetProvider.updateWidget(context, appWidgetManager, mAppWidgetId, profile);

		Intent data = new Intent();
		data.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, data);
		finish();
	}

	public void saveWidgetConfiguration(int profileId, boolean isFull) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(getProfileIdKey(mAppWidgetId), profileId);
		editor.putBoolean(getIsFullKey(mAppWidgetId), isFull);
		editor.apply();
	}

	public void showToast(String text) {
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
		toast.show();
	}

	public static Profile getWidgetProfile(Context context, int appWidgetId) {
		int profileId = PreferenceManager.getDefaultSharedPreferences(context).getInt(getProfileIdKey(appWidgetId), -1);
		DatabaseHelper dbh = DatabaseHelper.getInstance(context);
		return dbh.getProfile(profileId);
	}

	public static String getProfileIdKey(int appWidgetId) {
		return VirtualRemoteWidgetProvider.WIDGET_PREFERENCE_PREFIX + Integer.toString(appWidgetId);
	}

	public static String getIsFullKey(int appWidgetId) {
		return VirtualRemoteWidgetProvider.WIDGET_PREFERENCE_PREFIX + Integer.toString(appWidgetId) + "isFull";
	}

	public static boolean isFull(Context context, int appWidgetId) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(VirtualRemoteWidgetProvider.WIDGET_PREFERENCE_PREFIX + Integer.toString(appWidgetId) + "isFull", false);
	}

	public static void deleteWidgetConfiguration(Context context, int appWidgetId) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		if (prefs.contains(getProfileIdKey(appWidgetId))) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.remove(getProfileIdKey(appWidgetId));
			editor.apply();
		}
	}
}
