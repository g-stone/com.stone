package com.stone.fileserver.comparator;

import java.util.Comparator;
import java.util.Hashtable;

public class FilenameComparator implements Comparator<Hashtable<String, Object>> {
	@Override
	public int compare(Hashtable<String, Object> a, Hashtable<String, Object> b) {
		if (((Boolean)a.get("is_dir")) && !((Boolean)b.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)a.get("is_dir")) && ((Boolean)b.get("is_dir"))) {
			return 1;
		} else {
			return ((String)a.get("filename")).compareTo((String)b.get("filename"));
		}
	}
}
