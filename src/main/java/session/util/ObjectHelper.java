package session.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


public class ObjectHelper {


    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();
        Field[] fields = theClass.getDeclaredFields();
        String[] sFields = new String[fields.length];
        int i=0;
        for (Field f: fields) sFields[i++]=f.getName();
        return sFields;
    }

    public String getMethodsName(String property){
        return property.substring(0,1).toUpperCase()+property.substring(1);
    }

    public static Object getter (Object object, String property){
        String nameUppercase = property.substring(0,1).toUpperCase()+property.substring(1);
        String getName= "get"+nameUppercase;
        try {
            Method method = object.getClass().getDeclaredMethod(getName);
            Object object1 = method.invoke(object);
            return  object1;
        }catch (NoSuchMethodException e){
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void setter(Object object, String property, Object value) {
        try {
            String setterName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
            Method setterMethod = findSetterMethod(object.getClass(), setterName, value);
            setterMethod.invoke(object, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Method findSetterMethod(Class<?> clazz, String methodName, Object value) throws NoSuchMethodException {
        try {
            return clazz.getMethod(methodName, value.getClass());
        } catch (NoSuchMethodException e) {
            // If the method with value.getClass() parameter is not found,
            // try to find the method with the corresponding primitive type.
            if (value.getClass() == Integer.class) {
                return clazz.getMethod(methodName, int.class);
            } else {
                throw e;
            }
        }
    }

}
