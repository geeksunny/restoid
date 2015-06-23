package com.radicalninja.restoid.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.client.Response;

public class ResponseUtils {

    public static String getResponseText(Response response) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String encodeIndentedJson(String json, int indentSize) {
        char space = 0x00A0;
        StringBuffer search = new StringBuffer(indentSize);
        StringBuffer replace = new StringBuffer(indentSize);
        for (int i = 0; i < indentSize; i++) {
            search.append(" ");
            replace.append(space);
        }
        String str = json.replace(search, replace);
        return str.replace("\n", "\r\n");
        //return str;
    }

}
