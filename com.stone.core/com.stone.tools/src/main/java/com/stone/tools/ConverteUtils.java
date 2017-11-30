/**
 * JsonConverter.java
 * @author lixinpeng
 * @DATE: 2015年11月20日 @TIME: 下午4:54:26
 * Copyright (C) 2015 西安上达信息科技有限公司
 */
package com.stone.tools;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 功能说明：JSON字符串转换处理工具类
 *
 * @author lixinpeng
 * @DATE: 2015年11月20日 @TIME: 下午4:54:26
 */
public class ConverteUtils {
	private static ObjectMapper mapper;
	private static XmlMapper xmlMapper;
	private static Logger logger;

	static {
		mapper = new ObjectMapper();
		xmlMapper = new XmlMapper();
		logger = Logger.getLogger(ConverteUtils.class);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 功能说明：Jackson转换为MAP JsonConverter.parseToMap();
	 * 
	 * @author: lixinpeng
	 * @DATE: 2015年11月20日 @TIME: 下午5:00:30
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMap(String json) {
		Map<String, Object> tmpMap = new HashMap<String, Object>();

		if (json == null || json.trim().equals("")) {
			return tmpMap;
		}

		try {
			JsonNode root = mapper.readValue(json, JsonNode.class);
			tmpMap = parseJson(root);
		} catch (Exception e) {
			logger.error("JSON转换异常", e);
		}

		return tmpMap;
	}

	/**
	 * 功能说明：JSON遍历 JsonConverter.parseJson();
	 * 
	 * @author: lixinpeng
	 * @DATE: 2015年11月20日 @TIME: 下午5:00:05
	 * @param json
	 * @return
	 */
	private static Map<String, Object> parseJson(JsonNode json) {
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		if (json != null) {
			Iterator<Entry<String, JsonNode>> itr = json.fields();
			Entry<String, JsonNode> entry = null;
			while (itr.hasNext()) {
				entry = itr.next();
				if (entry.getValue().isArray()) {
					List<Map<String, Object>> tmpNodeList = new ArrayList<Map<String, Object>>();
					Iterator<JsonNode> tmpNodeElement = entry.getValue().elements();

					while (tmpNodeElement.hasNext()) {
						tmpNodeList.add(parseJson(tmpNodeElement.next()));
					}

					tmpMap.put(entry.getKey(), tmpNodeList);
				} else if (entry.getValue().isObject()) {
					tmpMap.put(entry.getKey(), parseJson(entry.getValue()));
				} else if (entry.getValue().isNumber()) {
					tmpMap.put(entry.getKey(), entry.getValue().numberValue());
				} else {
					tmpMap.put(entry.getKey(), entry.getValue().textValue());
				}
			}
		}

		return tmpMap;
	}
	/**
	 * 功能说明：对象转JSON字符串
	 * JsonConverter.toJson();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:56:22
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("JSON序列化异常", e);
		}
		
		return jsonString;
	}
	/**
	 * 功能说明：XML字符串转MAP对象
	 * JsonConverter.xmlToMap();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:56:47
	 * @param xmlString
	 * @return
	 */
	public static Map<String, Object> xmlToMap(String xmlString) {
		StringWriter writer = new StringWriter();
		try {
			JsonParser jsonParser = xmlMapper.getFactory().createParser(xmlString);
			JsonGenerator generator = mapper.getFactory().createGenerator(writer);
			while (jsonParser.nextToken() != null) {
				generator.copyCurrentEvent(jsonParser);
			}

			jsonParser.close();
			generator.close();
		} catch (Exception e) {
			logger.error("XML转JSON失败--->" + e.getMessage(), e);
		}

		return toMap(writer.toString());
	}
	/**
	 * 功能说明：对象转XML字符串
	 * JsonConverter.toXml();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:57:51
	 * @param aObject
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String toXml(Object aObject) {
		StringBuilder builder = new StringBuilder();

		builder.append("<xml>");
		if (aObject instanceof Map) {
			Map<String, Object> tmpInfo = (Map<String, Object>) aObject;
			Object val = null;
			for (String key : tmpInfo.keySet()) {
				val = tmpInfo.get(key);
				if (val == null) {
					continue;
				}
				builder.append("<").append(key).append(">");
				if (val instanceof Number) {
					builder.append(val);
				} else {
					builder/** .append("<![CDATA[") */
							.append(val)/** .append("]]>") */
					;
				}
				builder.append("</").append(key).append(">");
			}
		}
		if (aObject instanceof List) {

		} else {

		}
		builder.append("</xml>");

		return builder.toString();
	}
	/**
	 * 功能说明：JSON字符串转Java对象
	 * JsonConverter.toBean();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午5:58:36
	 * @param c
	 * @param json
	 * @return
	 */
	public static <T> T toBean(Class<T> c, String json) {
		T result = null;
		try {
			result = mapper.readValue(json, c);
		} catch (Exception e) {
			logger.error("JSON转换JAVA对象异常--->" + e.getMessage());
			throw new RuntimeException(e);
		}

		return result;
	}
	/**XML转换常量*/
	private final static String qs = "<", qe = ">", qgs = "</", ts = " type=\"", te = "\"";
	
	/**
	 * 功能说明：对象转XML字符串
	 * JsonConverter.toXmlString();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午6:00:37
	 * @param name
	 * @param obj
	 * @return
	 */
	public static String toXmlString(String name, Object obj) {
		String root = name;
		if (!StringUtils.hasText(name)) {
			root = "root";
		}
		String xmlString = "";
		if (obj == null) {
			xmlString += qs + root + qe + qgs + root + qe;
		} else if (obj instanceof Map) {
			xmlString += xmlMapWrite(root, obj);
		} else if (obj instanceof List) {
			xmlString += xmlListWrite(root, obj);
		} else if (obj instanceof Set) {
			xmlString += xmlSetWrite(root, obj);
		} else if (obj instanceof CharSequence) {
			xmlString += xmlJavaPrimaryWrite(root, obj);
		} else if (obj instanceof Number) {
			xmlString += xmlJavaPrimaryWrite(root, obj);
		} else if (obj instanceof Date) {
			xmlString += xmlJavaPrimaryWrite(root, obj);
		} else if (obj instanceof Boolean) {
			xmlString += xmlJavaPrimaryWrite(root, obj);
		} else {
			xmlString += xmlUserBeanWrite(root, obj);
		}

		return xmlString;
	}
	/**
	 * 功能说明：MAP对象转XML字符串
	 * JsonConverter.xmlMapWrite();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午6:01:06
	 * @param name
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String xmlMapWrite(String name, Object map) {
		String xmlString = qs + name + ts + map.getClass().getSimpleName().toLowerCase() + te + qe;
		if (map != null) {
			Map<String, Object> om = (Map<String, Object>) map;
			for (Entry<String, Object> entry : om.entrySet()) {
				xmlString += toXmlString(entry.getKey(), entry.getValue());
			}
		}
		xmlString += qgs + name + qe;

		return xmlString;
	}
	/**
	 * 功能说明：LIST对象转XML字符串
	 * JsonConverter.xmlListWrite();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午6:01:37
	 * @param name
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String xmlListWrite(String name, Object list) {
		String xmlString = qs + name + ts + list.getClass().getSimpleName().toLowerCase() + te + qe;
		if (list != null) {
			List<Object> om = (List<Object>) list;
			for (Object o : om) {
				xmlString += toXmlString("item", o);
			}
		}
		xmlString += qgs + name + qe;

		return xmlString;
	}
	/**
	 * 功能说明：SET对象转XML字符串
	 * JsonConverter.xmlSetWrite();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午6:02:04
	 * @param name
	 * @param set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String xmlSetWrite(String name, Object set) {
		String xmlString = qs + name + ts + set.getClass().getSimpleName().toLowerCase() + te + qe;
		if (set != null) {
			Set<Object> om = (Set<Object>) set;
			for (Object o : om) {
				xmlString += toXmlString("item", o);
			}
		}
		xmlString += qgs + name + qe;

		return xmlString;
	}
	/**
	 * 功能说明：Java基础类型转XML字符串
	 * JsonConverter.xmlJavaPrimaryWrite();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午6:02:47
	 * @param name
	 * @param value
	 * @return
	 */
	private static String xmlJavaPrimaryWrite(String name, Object value) {
		return qs + name + ts + value.getClass().getSimpleName().toLowerCase() + te + qe + value + qgs + name + qe;
	}
	/**
	 * 功能说明：Java对象转XML字符串
	 * JsonConverter.xmlUserBeanWrite();
	 * @author: lixinpeng
	 * @DATE: 2017年11月9日  @TIME: 下午6:03:19
	 * @param name
	 * @param value
	 * @return
	 */
	private static String xmlUserBeanWrite(String name, Object value) {
		String xmlString = qs + name + ts + value.getClass().getSimpleName().toLowerCase() + te + qe;
		Method[] methods = value.getClass().getDeclaredMethods();
		String mname = "";
		for (Method method : methods) {
			mname = method.getName();
			if (mname.startsWith("get")) {
				try {
					xmlString += toXmlString(mname.substring(3).toLowerCase(), method.invoke(value, new Object[] {}));
				} catch (Exception e) {
					logger.error("用户自定义对象转化XML异常！", e);
				}
			}
		}
		xmlString += qgs + name + qe;

		return xmlString;
	}

	public static String xmlReturn(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
}
