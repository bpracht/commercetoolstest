package com.pracht.commercetools.etsy.model.service;

import com.pracht.commercetools.etsy.model.model.EtsyShopListing;
import com.pracht.commercetools.etsy.model.model.EtsyShop;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class EtsyService {
    private final String ENDPOINT = "https://openapi.etsy.com/v2";
    private static Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String getApiKeyString() throws IOException {
        if (apiKeyString == null) {
            File commerceToolsDirectory = new File(System.getProperty("user.home"), "/.commercetools");
            if (!commerceToolsDirectory.exists() || !commerceToolsDirectory.isDirectory()) {
                log.error("Missing directory " + commerceToolsDirectory.getAbsolutePath());
                throw new IllegalArgumentException("Missing configuration directory");
            }
            File apiKeyFile = new File(commerceToolsDirectory, "apikey");
            if (!apiKeyFile.exists()) {
                log.error("Missing api key file " + apiKeyFile.getAbsolutePath());
                throw new IllegalArgumentException("Missing api key file ");
            }
            BufferedReader apiKeyReader = new BufferedReader(new FileReader(apiKeyFile));
            apiKeyString = apiKeyReader.readLine();
        }
        return apiKeyString;
    }

    private String apiKeyString;

    private String getApiKeyParm() throws IOException {
        return "?api_key=" + getApiKeyString();
    }


    public Map<Long, EtsyShop> getEtsyShops(List<Long> shopIds) throws InterruptedException, IOException {
        Map<Long, EtsyShop> result = new HashMap<Long, EtsyShop>();
        ArrayList<Future<HttpResponse>> responseFutures = new ArrayList<>(shopIds.size());
        CountDownLatch countDownLatch = new CountDownLatch(shopIds.size());
        int counter = 0;
        for (Long shopId : shopIds) {
            FutureCallback<HttpResponse> callback = new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse httpResponse) {
                    EtsyShop etsyShop = new EtsyShop(getJsonString(httpResponse));
                    countDownLatch.countDown();
                    synchronized (result) {
                        result.put(shopId, etsyShop);
                    }
                }

                @Override
                public void failed(Exception e) {
                    countDownLatch.countDown();
                }

                @Override
                public void cancelled() {
                    countDownLatch.countDown();
                }
            };
            responseFutures.add(counter++, getAsyncEtsyShop(shopId, callback));
        }
        countDownLatch.await(3, TimeUnit.SECONDS);
        return result;
    }

    private String getJsonString(HttpResponse response) {
        String result = null;
        try {
            BufferedReader rd = null;
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            StringBuilder resultBuffer = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                resultBuffer.append(line);
            }
            result = resultBuffer.toString();
        } catch (IOException e) {
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());
            e.printStackTrace();
        }
        return result;

    }


    public Future<HttpResponse> getAsyncEtsyShop(long shopId, FutureCallback<HttpResponse> callback) throws IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        final HttpGet request1 = new HttpGet(ENDPOINT + "/shops/" + shopId + getApiKeyParm());
        return httpclient.execute(request1, callback);
    }

    public Map<Long, EtsyShopListing> getListingsFromSpecificShops(List<Long> shopIds) throws InterruptedException, IOException {
        Map<Long, EtsyShopListing> result = new HashMap<Long, EtsyShopListing>();
        ArrayList<Future<HttpResponse>> responseFutures = new ArrayList<>(shopIds.size());
        CountDownLatch countDownLatch = new CountDownLatch(shopIds.size());
        int counter = 0;
        for (Long shopId : shopIds) {
            FutureCallback<HttpResponse> callback = new FutureCallback<HttpResponse>() {

                @Override
                public void completed(HttpResponse httpResponse) {
                    EtsyShopListing etsyShopListing = new EtsyShopListing(getJsonString(httpResponse));
                    countDownLatch.countDown();
                    synchronized (result) {
                        result.put(shopId, etsyShopListing);
                    }
                }

                @Override
                public void failed(Exception e) {
                    countDownLatch.countDown();
                }

                @Override
                public void cancelled() {
                    countDownLatch.countDown();
                }
            };
            responseFutures.add(counter++, getAsyncEtsyShop(shopId, callback));
            getAsyncListingsFromSpecificShops(shopId, callback);
        }
        countDownLatch.await(3, TimeUnit.SECONDS);
        return result;
    }

    public Future<HttpResponse> getAsyncListingsFromSpecificShops(long shopId, FutureCallback<HttpResponse> callback) throws IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        httpclient.start();
        final HttpGet request1 = new HttpGet(ENDPOINT + "/shops/" + shopId + "/listings/active" + getApiKeyParm());
        return httpclient.execute(request1, callback);
    }
}
