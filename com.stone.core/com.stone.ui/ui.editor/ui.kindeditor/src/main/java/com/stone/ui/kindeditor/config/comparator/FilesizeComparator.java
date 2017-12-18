package com.stone.ui.kindeditor.config.comparator;

import java.util.Comparator;
import java.util.Hashtable;

public class FilesizeComparator implements Comparator<Hashtable<String, Object>> {
	@Override
	public int compare(Hashtable<String, Object> a, Hashtable<String, Object> b) {
		if (((Boolean)a.get("is_dir")) && !((Boolean)b.get("is_dir"))) {
			return -1;
		} else if (!((Boolean)a.get("is_dir")) && ((Boolean)b.get("is_dir"))) {
			return 1;
		} else {
			if (((Long)a.get("filesize")) > ((Long)b.get("filesize"))) {
				return 1;
			} else if (((Long)a.get("filesize")) < ((Long)b.get("filesize"))) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
