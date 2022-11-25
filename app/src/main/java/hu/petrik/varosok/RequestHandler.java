package hu.petrik.varosok;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public final class RequestHandler {
    private RequestHandler(){}
    public static Response get(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Accept","application/json");
        return getResponse(connection);
    }

    public static Response put(String url, String data) throws IOException {
        HttpURLConnection connection = setupConnection(url);
        connection.setRequestMethod("PUT");
        addRequestBody(data, connection);
        return getResponse(connection);
    }

    public static Response delete(String url) throws IOException {
        HttpURLConnection connection = setupConnection(url);
        connection.setRequestMethod("DELETE");
        return getResponse(connection);
    }

    public static Response post(String url, String data) throws IOException {
        HttpURLConnection connection = setupConnection(url);
        connection.setRequestMethod("POST");
        addRequestBody(data, connection);
        return getResponse(connection);
    }

    private static HttpURLConnection setupConnection(String url) throws IOException {
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Accept","application/json");
        return connection;
    }

    private static void addRequestBody(String data, HttpURLConnection connection) throws IOException {
        connection.setRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        writer.write(data);
        writer.flush();
        os.close();
    }

    private static Response getResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        InputStream is = null;
        if (responseCode >= 400){
            is = connection.getErrorStream();
        }else{
            is = connection.getInputStream();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        StringBuilder stringbuilder = new StringBuilder();
        while(line != null){
            stringbuilder.append(line).append(System.lineSeparator());
            line = br.readLine();
        }
        br.close();
        is.close();
        String content = stringbuilder.toString().trim();
        return new Response(responseCode, content);
    }
}
