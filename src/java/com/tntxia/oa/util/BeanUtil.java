package com.tntxia.oa.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.oa.system.entity.User;

public class BeanUtil {

	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		BeanInfo beanInfo = null;

		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String mapKey = entry.getKey();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (StringUtils.equalsIgnoreCase(mapKey, key)) {
					Object value = map.get(mapKey);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					try {
						if(value!=null){
							setter.invoke(obj, value);
						}
						
					} catch (Exception e) {

						System.out.println("transMap2Bean Error " + e + " "
								+ key);
					}
				}

			}
		}

		return;

	}

	public static void main(String argsp[]) {
		User user = new User();
		Map map = new HashMap();
		map.put("ID", 1);
		BeanUtil.transMap2Bean(map, user);
		System.out.println(user.getId());
	}
}
