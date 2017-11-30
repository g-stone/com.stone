/**
 * DateUtils.java
 * @author lixinpeng
 * @DATE: 2017年11月15日 @TIME: 下午7:39:02
 * Copyright (C) 2017 西安上达信息科技有限公司
 */
package com.stone.tools;

import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.util.Args;

/**
 * 功能说明
 *
 * @author lixinpeng
 * @DATE: 2017年11月15日 @TIME: 下午7:39:02
 */
public class DateUtils {
	public final static String CN_DATE_FULL = "yyyy-MM-dd HH:mm:ss";
	public final static String CN_DATE_MINUTE = "yyyy-MM-dd HH:mm";
	public final static String CN_DATE_DAY = "yyyy-MM-dd";
	public final static String UN_DATE_DIG_FULL = "yyyyMMddHHmmss";
	public final static String CN_DATE_DIG_MINUTE = "yyyyMMddHHmm";
	public final static String UN_DATE_DIG_SHORT = "yyyyMMdd";
	
	private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> 
		THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() {
			@Override
			protected SoftReference<Map<String, SimpleDateFormat>> initialValue() {
				return new SoftReference<Map<String, SimpleDateFormat>>(
						new HashMap<String, SimpleDateFormat>());
			}
		};
	
	public final static String[] DEFAULT_PATTERN = new String[]{
		CN_DATE_FULL, CN_DATE_MINUTE, CN_DATE_DAY, 
		UN_DATE_DIG_FULL, CN_DATE_DIG_MINUTE, UN_DATE_DIG_SHORT
	};
	
	private static final Date DEFAULT_TWO_DIGIT_YEAR_START;

	static {
		final Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
	}
	
	public static Date parseDate(String dateValue, String ... patterns){
		Args.notNull(dateValue, "Date value");
		final String[] localDateFormats = patterns != null ? patterns : DEFAULT_PATTERN;
		String v = dateValue;
		// trim single quotes around date if present
		// see issue #5279
		if (v.length() > 1 && v.startsWith("'") && v.endsWith("'")) {
			v = v.substring (1, v.length() - 1);
		}
		
		for (final String dateFormat : localDateFormats) {
			final SimpleDateFormat dateParser = get(dateFormat);
			dateParser.set2DigitYearStart(DEFAULT_TWO_DIGIT_YEAR_START);
			final ParsePosition pos = new ParsePosition(0);
			final Date result = dateParser.parse(v, pos);
			if (pos.getIndex() != 0) {
				return result;
			}
		}
		return null;
	}
	
	public static String formatDate(final Date date, final String pattern){
		Args.notNull(date, "Date");
		Args.notNull(pattern, "Pattern");
		final SimpleDateFormat formatter = get(pattern);
		return formatter.format(date);
	}
	
	private static SimpleDateFormat get(String pattern){
		final SoftReference<Map<String, SimpleDateFormat>> ref = THREADLOCAL_FORMATS.get();
		Map<String, SimpleDateFormat> formats = ref.get();
		if (formats == null) {
			formats = new HashMap<String, SimpleDateFormat>();
			THREADLOCAL_FORMATS.set(new SoftReference<Map<String, SimpleDateFormat>>(formats));
		}
		
		SimpleDateFormat format = formats.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern, Locale.CHINA);
			format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			formats.put(pattern, format);
		}
		
		return format;
	}
}
