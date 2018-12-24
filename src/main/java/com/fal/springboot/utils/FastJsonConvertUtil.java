package com.fal.springboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fal.springboot.entity.Order;

public class FastJsonConvertUtil {

	/**
	 * <B>方法名称：</B>将对象转为JSON字符串<BR>
	 * <B>概要说明：</B>将对象转为JSON字符串<BR>
	 * @param obj 任意对象
	 * @return JSON字符串
	 */
	public static String convertObjectToJSON(Object obj) {
		try {
			String text = JSON.toJSONString(obj);
			return text;		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Order convertJSONToObject(String message,Object obj){
        Order order = JSON.parseObject(message, new TypeReference<Order>() {});
        return order;
    }
}

