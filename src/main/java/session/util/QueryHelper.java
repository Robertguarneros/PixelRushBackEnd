package session.util;

public class QueryHelper {
    public static String createQueryINSERT(Object entity,String primaryKey) {

        StringBuffer stringBuffer = new StringBuffer("INSERT INTO ");
        stringBuffer.append(entity.getClass().getSimpleName()).append(" (");
        String [] fields = ObjectHelper.getFields(entity);

        for (String field: fields) {
            if (!field.equalsIgnoreCase(primaryKey)){
                stringBuffer.append(field).append(", ");}
        }
        stringBuffer.setLength(stringBuffer.length()-2);
        stringBuffer.append(") VALUES (?");
        for (String field: fields) {
            if (!field.equalsIgnoreCase(primaryKey))
            stringBuffer.append(", ?");
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
    public static String createQuerySELECT(Object entity, String primaryKey){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT * FROM ").append(entity.getClass().getSimpleName().toLowerCase());
        stringBuffer.append(" WHERE ").append(primaryKey).append(" =?");
        return stringBuffer.toString();
    }

}
