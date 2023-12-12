package session.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectHelper {


    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();
        Field[] fields = theClass.getDeclaredFields();
        String[] sFields = new String[fields.length];
        int i=0;
        for (Field f: fields) sFields[i++]=f.getName();
        return sFields;
    }
    public String getMethodsName(String name){
        return name;
    }

    public static Object getter (Object object, String name){
        String nameUppercase = name.substring(0,1).toUpperCase()+name.substring(1);
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




}
