package com.example.fatih.wirelesscomchat;

/**
 * Created by vmanohar on 3/19/18.
 */

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://45.76.144.238:8080/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
