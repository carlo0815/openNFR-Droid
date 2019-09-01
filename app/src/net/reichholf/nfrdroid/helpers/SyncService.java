package net.reichholf.nfrdroid.helpers;

import android.content.Intent;
import androidx.annotation.NonNull;

import net.reichholf.nfrdroid.nfrdroid;
import net.reichholf.nfrdroid.helpers.enigma2.Event;
import net.reichholf.nfrdroid.helpers.enigma2.epgsync.EpgDatabase;
import net.reichholf.nfrdroid.service.HttpIntentService;

/**
 * Created by Stephan on 04.06.2014.
 */
public class SyncService extends HttpIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        setupSSL();
        EpgDatabase epgDatabase = new EpgDatabase();
        nfrdroid.loadCurrentProfile(getApplicationContext());
        String bouquet = intent.getStringExtra(Event.KEY_SERVICE_REFERENCE);
        if(bouquet != null)
            epgDatabase.syncBouquet(getApplicationContext(), bouquet);
    }
}
