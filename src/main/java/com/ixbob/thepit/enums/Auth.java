package com.ixbob.thepit.enums;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Auth {
    public static void authIp(String ip) throws Exception {
        String result1 = get("https://webapi-pc.meitu.com/common/ip_location?ip=" + ip);
        String result2 = get("https://www.ip.cn/api/index?ip=" + ip + "&type=1");
//        String result3 = get("https://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true"); 不可用
//        String result4 = get("https://api.vore.top/api/IPdata?ip=" + ip); 不可用
    }

    public static String get(String url) throws Exception { //https://blog.csdn.net/csdn_ljl/article/details/96425254#:~:text=%E5%9C%A8%20Java%20%E4%B8%AD%EF%BC%8C%E5%8F%AF%E4%BB%A5%E4%BD%BF%E7%94%A8HTTPClient%E6%88%96HttpURLConnection%E6%9D%A5%20%E5%8F%91%E8%B5%B7GET%E8%AF%B7%E6%B1%82,%E3%80%82%20HTTPClient%E6%98%AF%E4%B8%80%E4%B8%AA%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BC%80%E6%BA%90%E6%A1%86%E6%9E%B6%EF%BC%8C%E5%AF%B9HTTP%E8%AF%B7%E6%B1%82%E8%BF%9B%E8%A1%8C%E4%BA%86%E5%BE%88%E5%A5%BD%E7%9A%84%E5%B0%81%E8%A3%85%EF%BC%8C%E8%80%8CHttpURLConnection%E6%98%AF%20Java%20%E7%9A%84%E6%A0%87%E5%87%86%E8%AF%B7%E6%B1%82%E6%96%B9%E5%BC%8F%E3%80%82
        long startTime = System.currentTimeMillis();
        String content = null;
        URLConnection urlConnection = new URL(url).openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        connection.setRequestMethod("GET");
        //连接
        connection.connect();
        //得到响应码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8
            ));
            StringBuilder bs = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bs.append(line);
            }
            content = bs.toString();
        }
        System.out.println(content);
        System.out.println("cost time: " + (System.currentTimeMillis() - startTime) + " ms");
        return content;
    }
}
