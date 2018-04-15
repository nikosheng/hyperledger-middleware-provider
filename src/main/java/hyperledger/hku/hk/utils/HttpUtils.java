package hyperledger.hku.hk.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtils {
    public static HttpResponse get(String url) throws Exception{
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);
        return client.execute(httpGet);
    }
}
