package com.azorom.chatgame.JSON;

import android.util.Log;

import com.azorom.chatgame.Requests.Constants.RequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

//TODO: ADD BETTER ERROR HANDLING WHILE PARSING JSON STRINGS
public class CustomJsonParser {
    private static final ObjectMapper objMapper = new ObjectMapper();
    public static <ResponseType, ErrorType> Object parseResponse(
            String json,
            Class<ResponseType> responseClass,
            Class<ErrorType> errorClass
    ){
        ResponseType response = null;
        ErrorType error = null;
        try {
            response = objMapper.readValue(json, responseClass);
        } catch (UnrecognizedPropertyException e){
            try {
                error = objMapper.readValue(json, errorClass);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new RequestResponse<>(response, error);
    }

    public static String convertToJson(Object body){
        String jsonString = "";
        try {
            jsonString = objMapper.writer().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            Log.d("DEBUG", e.toString());
            return "";
        }
        return jsonString;
    }

    public static byte[] convertToBytes(Object body){
        byte[] bytes;
        try {
            bytes = objMapper.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            Log.d("DEBUG", e.toString());
            return null;
        }
        return bytes;
    }
}