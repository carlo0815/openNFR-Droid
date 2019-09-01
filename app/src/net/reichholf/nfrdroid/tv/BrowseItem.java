package net.reichholf.nfrdroid.tv;

import net.reichholf.nfrdroid.helpers.ExtendedHashMap;

import java.io.Serializable;

public class BrowseItem implements Serializable{
	public enum Type {
		Reload,
		Service,
		Movie,
		Profile,
		Preferences
	}

	public Type type;
	public ExtendedHashMap data;

	public BrowseItem(Type itemType, ExtendedHashMap itemData) {
		type = itemType;
		data = itemData;
	}
}
