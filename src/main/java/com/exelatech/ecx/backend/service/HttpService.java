package com.exelatech.ecx.backend.service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.exelatech.ecx.backend.domain.Setting;

public class HttpService<T> {

    static Logger log = Logger.getLogger(HttpService.class.getName());

    /**
     * 
     * @param Search Splunk query
     * @param sett   Config with the url, usr, pwd
     * @return A dictionary with the job id.
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws KeyStoreException
     */
    public T SplunkPost(MultiValueMap<String, String> Search, Setting sett)
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {

        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(sett.getUser(), sett.getPassword()));

        HttpClientBuilder httpClient = HttpClientBuilder.create();
        // httpClient.setConnectionTimeToLive(5, TimeUnit.MINUTES);
        httpClient.setDefaultCredentialsProvider(provider);

        SSLContext context = SSLContext.getInstance("TLS");
        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };
        context.init(null, byPassTrustManagers,new SecureRandom());
        httpClient.setSSLContext(context);


        //httpClient.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build());
        httpClient.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);

        CloseableHttpClient clo = httpClient.build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000 * 60 * 5);
        requestFactory.setHttpClient(clo);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(Search, headers);

        @SuppressWarnings("unchecked")
        T job = (T) restTemplate.postForEntity(sett.getUrl() + "/?output_mode=json", request, Object.class).getBody();

        // try {
        //     clo.close();
        //     requestFactory.destroy();        
            
        // } catch (Exception e) {
        //    log.warn(e);
        // }

        return job;
    }

    public String SplunkGet(String JobId, Setting sett)
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {

        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(sett.getUser(), sett.getPassword()));

        HttpClientBuilder httpClient = HttpClientBuilder.create();
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };
        context.init(null, byPassTrustManagers,new SecureRandom());
        httpClient.setSSLContext(context);

        httpClient.setDefaultCredentialsProvider(provider);
        httpClient.setSSLContext(context);
        //httpClient.setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build());
        httpClient.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);


       

        CloseableHttpClient clo = httpClient.build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000 * 60 * 5);
        requestFactory.setHttpClient(clo);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseEntity<String> result = restTemplate
                .getForEntity(sett.getUrl() + "/" + JobId + "/results/?output_mode=json&count=0", String.class);
        String rString = result.getBody();
        // try {
        //     clo.close();
        //     requestFactory.destroy();
        // } catch (Exception e) {
        //    log.warn(e);
        // }

        return rString;
    }

    public String POST(String restfulUrl, String user, String password, String rawJson) {
        String httpResult = "";
        HttpPost request = new HttpPost(restfulUrl);

        try {
            ArrayList<BasicNameValuePair> headers = new ArrayList<BasicNameValuePair>();
            headers.add(new BasicNameValuePair("content-type", "application/json"));

            // Add headers to post request
            for (int i = 0; i < headers.size(); i++) {
                request.addHeader(headers.get(i).getName(), headers.get(i).getValue());
            }
            // Set BasicAuth credentials

            // Add the post params into to request
            if (rawJson != null && rawJson.length() > 1) {
                request.setEntity(new StringEntity(rawJson));
            }
            // Build the http post
            HttpClientBuilder httpClient = HttpClientBuilder.create();
            httpClient.setConnectionTimeToLive(5, TimeUnit.MINUTES);

            if (user != "" && password != "") {

                // Authentication block
                CredentialsProvider provider = new BasicCredentialsProvider();
                provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, password));
                // Build the http post
                httpClient.setDefaultCredentialsProvider(provider);
                httpClient.setSSLContext(
                        new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build());
                httpClient.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);

            }

            CloseableHttpClient closeableHttpClient = httpClient.build();
            // Execute
            CloseableHttpResponse response = closeableHttpClient.execute(request);

            org.apache.http.HttpEntity entity = response.getEntity();
            if (entity != null) {
                httpResult = EntityUtils.toString(entity);
                closeableHttpClient.close();
                response.close();
            }
            // Return the result
            return httpResult;
        } catch (Exception ex) {
            log.error("Error to execute a POS request" + ex.getMessage());
            return "";
        }
    }

    public HttpService() {
    }

}