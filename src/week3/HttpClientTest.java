package week3;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class HttpClientTest {
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8808";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/html; charset=utf-8");
        httpPost.addHeader("Accept","application/html");
        HttpResponse httpResponse  = httpClient.execute(httpPost);
        if(httpResponse != null){
            if(httpResponse.getStatusLine().getStatusCode()!=200){
                System.out.println("返回状态非200");
            }
            InputStream inputStream = httpResponse.getEntity().getContent();
            String result = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
            System.out.println("netty网关返回:"+result);
        }
    }
}
