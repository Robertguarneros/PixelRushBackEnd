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
        stringBuffer.append(") VALUES (");
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].equalsIgnoreCase(primaryKey)) {
                stringBuffer.append("?");
                if (i < fields.length - 1) {
                    stringBuffer.append(", ");
                }
            }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }
    public static String createQuerySELECT(Class theClass, String pk) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName().toLowerCase());
        sb.append(" WHERE "+pk+"= ?");

        return sb.toString();
    }

    public static String createQuerySELECTAll(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName().toLowerCase());
        return sb.toString();
    }

    public static String createQuerySELECTMatch(Class theClass){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName().toLowerCase());
        sb.append(" WHERE username = ? ORDER BY id DESC LIMIT 1");
        return sb.toString();
    }

    public static String createQueryUPDATE(Class theClass, String pk, String parameterToUpdate){
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(theClass.getSimpleName().toLowerCase());
        sb.append(" SET " + parameterToUpdate + " = ?");
        sb.append(" WHERE " + pk + " = ?");
        return sb.toString();
    }


}
