package com.HomePropertiesControll.HttpRequest;

public class HttpConfig {
    private static String url_prod_server = "http://192.168.1.7/api/";
    private static String url_local_test_server = "http://10.0.2.2:8080/api/";

    public static String get_url_prod_server() {
        return url_prod_server;
    }

    public static void set_url_prod_server(String url_prod_server) {
        HttpConfig.url_prod_server = url_prod_server;
    }
}
