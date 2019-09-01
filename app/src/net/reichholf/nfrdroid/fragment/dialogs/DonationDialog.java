package net.reichholf.nfrdroid.fragment.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import net.reichholf.nfrdroid.nfrdroid;
import net.reichholf.nfrdroid.R;
import net.reichholf.nfrdroid.activities.abs.BaseActivity;
import net.reichholf.nfrdroid.helpers.ExtendedHashMap;
import net.reichholf.nfrdroid.helpers.Statics;

/**
 * Created by Stephan on 29.01.2015.
 */
public class DonationDialog extends ActionDialog {
	private ExtendedHashMap mItems;
	private CharSequence[] mActions;

	private static String KEY_ITEMS = "items";

	public static DonationDialog newInstance(ExtendedHashMap items) {
		DonationDialog d = new DonationDialog();
		Bundle args = new Bundle();
		args.putSerializable(KEY_ITEMS, items);
		d.setArguments(args);
		return d;
	}

	protected void init() {
		mItems = (ExtendedHashMap) getArguments().getSerializable(KEY_ITEMS);
		int i = 0;
		mActions = new CharSequence[mItems.size()];

		for (String sku : nfrdroid.SKU_LIST) {
			String price = mItems.getString(sku);
			if (price == null)
				continue;
			mActions[i] = getString(R.string.donate_sum, price);
			i++;
		}
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setRetainInstance(true);
		init();
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle(R.string.donate)
				.setItems(mActions, (dialog, which) -> {
					BaseActivity ba = (BaseActivity) getActivity();
					ba.purchase(nfrdroid.SKU_LIST[which]);
					finishDialog(Statics.ACTION_NONE, null);
				});
		return builder.create();
	}
}
