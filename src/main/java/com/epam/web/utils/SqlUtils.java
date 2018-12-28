package com.epam.web.utils;

import java.util.List;

public class SqlUtils {

    public static String getInsertOrUpdateStatement(String table, List<String> fields) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getUpdatePart(fields));
        stringBuffer.append(" ");
        stringBuffer.append(getInsertIntoPart(table, fields));
        stringBuffer.append(" ");
        stringBuffer.append(getGlobalValuesPart(fields));
        stringBuffer.append(" ");
        stringBuffer.append(getDuplicateKeyUpdatePart(fields));
        return stringBuffer.toString();
    }

    public static String getDeleteStatement(String table) {
        return "DELETE from " + table + " WHERE id=?";
    }

    private static String getGlobalValuesPart(List<String> fields) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("VALUES").append(" (");
        for (String field : fields) {
            stringBuffer.append("@");
            stringBuffer.append(field);
            stringBuffer.append(",");
        }
        return deleteLastAndReplaceWith(")", stringBuffer);
    }

    private static String deleteLastAndReplaceWith(String replaceTo, StringBuffer stringBuffer) {
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append(replaceTo);
        return stringBuffer.toString();
    }

    private static String getInsertIntoPart(String table, List<String> fields) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO").append(" ").append(table);
        stringBuffer.append(" (");
        for (String field : fields) {
            stringBuffer.append(field);
            stringBuffer.append(",");
        }
        return deleteLastAndReplaceWith(")", stringBuffer);
    }

    private static String getUpdatePart(List<String> fields) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SET").append(" ");
        for (String field : fields) {
            stringBuffer.append("@");
            stringBuffer.append(field);
            stringBuffer.append("=");
            stringBuffer.append("?");
            stringBuffer.append(",");
        }

        return deleteLastAndReplaceWith(";", stringBuffer);
    }

    private static String getDuplicateKeyUpdatePart(List<String> fields) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ON DUPLICATE KEY UPDATE").append(" ");
        fields.remove("id");
        for (String field : fields) {
            stringBuffer.append(field);
            stringBuffer.append("=");
            stringBuffer.append("@");
            stringBuffer.append(field);
            stringBuffer.append(",");
        }
        return deleteLastAndReplaceWith(";", stringBuffer);
    }

}
