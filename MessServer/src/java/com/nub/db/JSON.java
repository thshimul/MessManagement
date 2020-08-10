package com.nub.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

    String jsonString = "";
    JSONObject jsonobject = null;
    JSONArray jsonArray = new JSONArray();

    

    public String JSONCON(ResultSet resultSet) throws SQLException, JSONException {
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            jsonobject = new JSONObject();

            for (int i = 0; i < metaData.getColumnCount(); i++) {

                jsonobject.put(metaData.getColumnLabel(i + 1), resultSet.getObject(i + 1));

            }

            jsonArray.put(jsonobject);
        }

        if (jsonArray.length() > 0) {
            jsonString = jsonArray.toString();
        }

        System.out.println(jsonString);
        return jsonString;
    }
}
