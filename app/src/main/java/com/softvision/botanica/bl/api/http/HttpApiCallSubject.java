package com.softvision.botanica.bl.api.http;

import com.softvision.botanica.bl.api.ApiCallSubject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/30/13
 * Time: 3:48 PM
 */
public class HttpApiCallSubject implements ApiCallSubject {
    private static final String TAG = "HttpApiCallSubject";

    private static final String HTTP_SCHEME_NAME = "http";
    private static final int HTTP_SCHEME_PORT = 3000;

    private static final int MAX_NUM_CONNECTIONS = 10;
    private static final int MAX_NUM_CONNECTIONS_PER_ROUTE = 10;

    private static final int HTTP_TIMEOUT = 20000;

    private HttpClient mHttpClient;

    /**
     * method that creates an HttpClient object used to perform the requests
     * from the application's web services VPN API
     */
    private HttpClient getDefaultHttpClient() {

        HttpParams httpParams = new BasicHttpParams();

        // timeout in milliseconds until a connection is established
        HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);

        // timeout for waiting for pojo
        HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);

        ConnManagerParams.setMaxTotalConnections(httpParams, MAX_NUM_CONNECTIONS);
        ConnPerRoute connPerRoute = new ConnPerRoute() {
            @Override
            public int getMaxForRoute(HttpRoute httpRoute) {
                return MAX_NUM_CONNECTIONS_PER_ROUTE;
            }
        };
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HTTP_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), HTTP_SCHEME_PORT));
        ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        return new DefaultHttpClient(clientConnectionManager, httpParams);
    }

    /**
     * execute a http post request
     *
     * @param url
     * @param body
     * @return
     */
    @Override
    public String getResponse(String url, String body) {
        if (mHttpClient == null) {
            mHttpClient = getDefaultHttpClient();
        }

        try {
            HttpPost httpPostRequest = new HttpPost(url);
            httpPostRequest.setEntity(new StringEntity(body));

            httpPostRequest.addHeader("Content-Type", "application/octet-stream");
            HttpResponse httpGetResponse = mHttpClient.execute(httpPostRequest);
            int httpStatusCode = httpGetResponse.getStatusLine().getStatusCode();

            if (httpStatusCode == HttpStatus.SC_OK) {
                return getStringFromInputStream(httpGetResponse.getEntity().getContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param inputStream - inputStream that is to be converted to a string
     * @return - string representation of inputStream parameter
     * @throws java.io.IOException
     */
    private String getStringFromInputStream(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            char[] buffer = new char[1024];
            int n;
            try {
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        }
        return null;
    }
}
