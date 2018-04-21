package hyperledger.hku.hk.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Map;

public class HttpUtils {
    public static HttpResponse get(String url) throws Exception {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(url);
        return client.execute(httpGet);
    }

    public static HttpResponse post(String url, Map<String, String> params, String encoding) throws Exception {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient client = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json");

        //parameters
        JSONObject postData = new JSONObject();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postData.put(entry.getKey(), entry.getValue());
            }
        }

        httpPost.setEntity(new StringEntity(postData.toString(), encoding));
        CloseableHttpResponse response = client.execute(httpPost);
        return response;
    }
}
