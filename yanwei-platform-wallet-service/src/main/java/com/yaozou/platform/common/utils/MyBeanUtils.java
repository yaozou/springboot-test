package com.yanwei.platform.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 数据处理通用类
 * @author luojianhong
 * @version $Id: MyBeanUtils.java, v 0.1 2017年7月11日 下午6:01:46 luojianhong Exp $
 */
public class MyBeanUtils extends PropertyUtilsBean {

    /**
     * 将实体类转为map<String,String[]>
     * @param obj
     * @author zhaolijin
     * @return
     */
    public static Map<String, String[]> beanToMap(Object obj) throws Exception {
        Map<String, String[]> params = new HashMap<String, String[]>(0);
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if (!"class".equals(name)) {
                Object object = propertyUtilsBean.getNestedProperty(obj, name);
                if (object != null && !"".equals(object.toString())) {
                    String[] strs = { object.toString() };
                    params.put(name, strs);
                }
            }
        }
        return params;
    }
    
    
    /**
     * 将实体类转为map<String,Object>
     * @param obj
     * @author zhaolijin
     * @return
     */
    public static Map<String, Object> beanToMapObject(Object obj) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>(0);
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if (!"class".equals(name)) {
                Object object = propertyUtilsBean.getNestedProperty(obj, name);
                if (object != null && !"".equals(object.toString())) {
                    params.put(name, object);
                }
            }
        }
        return params;
    }

    private static void convert(Object dest, Object orig) throws IllegalAccessException,
                                                          InvocationTargetException {

        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        // Copy the properties, converting as necessary
        if (orig instanceof DynaBean) {
            DynaProperty origDescriptors[] = ((DynaBean) orig).getDynaClass().getDynaProperties();
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if (PropertyUtils.isWriteable(dest, name)) {
                    Object value = ((DynaBean) orig).get(name);
                    try {
                        getInstance().setSimpleProperty(dest, name, value);
                    } catch (Exception e) {
                        ; // Should not happen
                    }

                }
            }
        } else if (orig instanceof Map) {
            Iterator names = ((Map) orig).keySet().iterator();
            while (names.hasNext()) {
                String name = (String) names.next();
                if (PropertyUtils.isWriteable(dest, name)) {
                    Object value = ((Map) orig).get(name);
                    try {
                        getInstance().setSimpleProperty(dest, name, value);
                    } catch (Exception e) {
                        ; // Should not happen
                    }

                }
            }
        } else
        /* if (orig is a standard JavaBean) */
        {
            PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                //              String type = origDescriptors[i].getPropertyType().toString();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }
                if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                    try {
                        Object value = PropertyUtils.getSimpleProperty(orig, name);
                        getInstance().setSimpleProperty(dest, name, value);
                    } catch (java.lang.IllegalArgumentException ie) {
                        ; // Should not happen
                    } catch (Exception e) {
                        ; // Should not happen
                    }

                }
            }
        }

    }

    /**
     * 对象拷贝
     * 数据对象空值不拷贝到目标对象
     * @param dataObject
     * @param toObject
     * @throws NoSuchMethodException
     * copy
     */
    public static void copyBeanNotNull2Bean(Object databean, Object tobean) throws Exception {
        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            //          String type = origDescriptors[i].getPropertyType().toString();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (PropertyUtils.isReadable(databean, name)
                && PropertyUtils.isWriteable(tobean, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(databean, name);
                    if (value != null) {
                        getInstance().setSimpleProperty(tobean, name, value);
                    }
                } catch (java.lang.IllegalArgumentException ie) {
                    ; // Should not happen
                } catch (Exception e) {
                    ; // Should not happen
                }

            }
        }
    }

    /**
     * 把orig和dest相同属性的value复制到dest中
     * @param dest
     * @param orig
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyBean2Bean(Object dest, Object orig) throws Exception {
        convert(dest, orig);
    }

    public static void copyBean2Map(Map map, Object bean) {
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
        for (int i = 0; i < pds.length; i++) {
            PropertyDescriptor pd = pds[i];
            String propname = pd.getName();
            try {
                Object propvalue = PropertyUtils.getSimpleProperty(bean, propname);
                map.put(propname, propvalue);
            } catch (IllegalAccessException e) {
                //e.printStackTrace();
            } catch (InvocationTargetException e) {
                //e.printStackTrace();
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * 将Map内的key与Bean中属性相同的内容复制到BEAN中
     * @param bean Object
     * @param properties Map
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyMap2Bean(Object bean, Map properties) throws IllegalAccessException,
                                                                 InvocationTargetException {
        // Do nothing unless both arguments have been specified
        if ((bean == null) || (properties == null)) {
            return;
        }
        // Loop through the property name/value pairs to be set
        Iterator names = properties.keySet().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            // Identify the property name and value(s) to be assigned
            if (name == null) {
                continue;
            }
            Object value = properties.get(name);
            try {
                Class clazz = PropertyUtils.getPropertyType(bean, name);
                if (null == clazz) {
                    continue;
                }
                String className = clazz.getName();
                if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                    if (value == null || value.equals("")) {
                        continue;
                    }
                }
                getInstance().setSimpleProperty(bean, name, value);
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
    }

    /**
     * 自动转Map key值大写
     * 将Map内的key与Bean中属性相同的内容复制到BEAN中
     * @param bean Object
     * @param properties Map
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyMap2Bean_Nobig(Object bean,
                                          Map properties) throws IllegalAccessException,
                                                          InvocationTargetException {
        // Do nothing unless both arguments have been specified
        if ((bean == null) || (properties == null)) {
            return;
        }
        // Loop through the property name/value pairs to be set
        Iterator names = properties.keySet().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            // Identify the property name and value(s) to be assigned
            if (name == null) {
                continue;
            }
            Object value = properties.get(name);
            // 命名应该大小写应该敏感(否则取不到对象的属性)
            //name = name.toLowerCase();
            try {
                if (value == null) { // 不光Date类型，好多类型在null时会出错
                    continue; // 如果为null不用设 (对象如果有特殊初始值也可以保留？)
                }
                Class clazz = PropertyUtils.getPropertyType(bean, name);
                if (null == clazz) { // 在bean中这个属性不存在
                    continue;
                }
                String className = clazz.getName();
             

                getInstance().setSimpleProperty(bean, name, value);
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
    }

    /**
     * Map内的key与Bean中属性相同的内容复制到BEAN中
     * 对于存在空值的取默认值
     * @param bean Object
     * @param properties Map
     * @param defaultValue String
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyMap2Bean(Object bean, Map properties,
                                    String defaultValue) throws IllegalAccessException,
                                                         InvocationTargetException {
        // Do nothing unless both arguments have been specified
        if ((bean == null) || (properties == null)) {
            return;
        }
        // Loop through the property name/value pairs to be set
        Iterator names = properties.keySet().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            // Identify the property name and value(s) to be assigned
            if (name == null) {
                continue;
            }
            Object value = properties.get(name);
            try {
                Class clazz = PropertyUtils.getPropertyType(bean, name);
                if (null == clazz) {
                    continue;
                }
                String className = clazz.getName();
                if (className.equalsIgnoreCase("java.sql.Timestamp")) {
                    if (value == null || value.equals("")) {
                        continue;
                    }
                }
                if (className.equalsIgnoreCase("java.lang.String")) {
                    if (value == null) {
                        value = defaultValue;
                    }
                }
                getInstance().setSimpleProperty(bean, name, value);
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
    }

    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException(
                "Could not find field [" + fieldName + "] on target [" + object + "]");
        }

        makeAccessible(field);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("never happend exception!", e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     */
    public static void setFieldValue(final Object object, final String fieldName,
                                     final Object value){
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException(
                "Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            //取出字段类型
            Class clazz = PropertyUtils.getPropertyType(object, fieldName);
            if (null == clazz) { // 在bean中这个属性不存在
               return;
            }
            if(clazz==Integer.class) {
                field.set(object,Integer.parseInt(String.valueOf(value)));
            }else if(clazz==Long.class) {
                field.set(object,new Double(Double.parseDouble(String.valueOf(value))).longValue());
            }else if(clazz==Double.class) {
                field.set(object,Double.parseDouble(String.valueOf(value)));
            }
            else {
                field.set(object,String.valueOf(value));
            }
            
            
           
        } catch (Exception e) {
            throw new RuntimeException("never happend exception!", e);
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {

        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    protected static Field getDeclaredField(final Class<?> clazz, final String fieldName) {

        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
            .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers())
            || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static Object getSimplePropertyObj(Object bean,
                                              String propName) throws IllegalArgumentException,
                                                               SecurityException,
                                                               IllegalAccessException,
                                                               InvocationTargetException,
                                                               NoSuchMethodException {
        
        return bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
    }

    private static String getReadMethod(String name) {
        
        return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
        
    }

    public MyBeanUtils() {
        super();
    }
}
