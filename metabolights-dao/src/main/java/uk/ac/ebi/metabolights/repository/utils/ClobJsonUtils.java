package uk.ac.ebi.metabolights.repository.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * Created by kalai on 17/11/2015.
 */
public class ClobJsonUtils {

    public static String convertToString(Clob clobObject) {
        if (clobObject == null) {
            return "";
        }
        String clobAsString = "";
        try {
            InputStream in = clobObject.getAsciiStream();
            StringWriter w = new StringWriter();
            IOUtils.copy(in, w);
            in.close();
            w.close();
            clobAsString = w.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return clobAsString;

    }

    public static Clob convertToClob(String jsontext) {
        SessionWrapper sessionWrapper = HibernateUtil.getSession();
        Session session = sessionWrapper.needSession();
        return session.getLobHelper().createClob(jsontext);
    }

    public static <T> T parseJson(String jsonString, Class<T> valueType) {
        if(jsonString==null)return null;

       ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonString, valueType);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T parseJsonFromCharArray(Character[] jsonString, Class<T> valueType) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonString.toString(), valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String parseToJSONString(Object toConvert) {
        if(toConvert == null) return "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(toConvert);
        } catch (IOException e) {
            return "";
        }

    }

    public static Character[] parseToJSONCharArray(Object toConvert) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            char[] charArray  = mapper.writeValueAsString(toConvert).toCharArray();
            return ArrayUtils.toObject(charArray);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
