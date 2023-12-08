package session.util;

public class QueryHelper {
    public static String createQueryINSERT(Object entity) {

        StringBuffer stringBuffer = new StringBuffer("INSERT INTO ");
        stringBuffer.append(entity.getClass().getSimpleName()).append(" ");
        stringBuffer.append("(");
        String [] fields = ObjectHelper.getFields(entity);
        for (String field: fields) {
            stringBuffer.append(field).append(", ");
        }
        stringBuffer.append(") VALUES (?");
        for (String field: fields) {
            stringBuffer.append(", ?");
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
    public static String createQuerySELECT(Object entity, String primaryKey){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT * FROM ").append(entity.getClass().getSimpleName());
        stringBuffer.append(" WHERE ").append(primaryKey).append(" =?");
        return stringBuffer.toString();
    }

}
