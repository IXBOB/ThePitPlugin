package com.ixbob.thepit.auth;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Auth {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            11,
            11,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>()
    );
    public static void authIp(String ip){
        long allStartTime = System.currentTimeMillis();
        System.out.println("开始验证IP");
        System.out.println(ip);

        //20240523, https://github.com/ihmily/ip-info-api#address-1.9
        ArrayList<String> urls = new ArrayList<>(Arrays.asList(
                "https://webapi-pc.meitu.com/common/ip_location?ip=" + ip,
                "https://www.ip.cn/api/index?ip=" + ip + "&type=1",
                "https://api.vore.top/api/IPdata?ip=" + ip,
                "https://api.ip.sb/geoip/" + ip,
//                "https://whois.pconline.com.cn/ipJson.jsp?ip=" + ip + "&json=true", 不会用，中文返回乱码
                "https://api.ip2location.io/?ip=" + ip,
                "https://realip.cc/?ip=" + ip,
                "https://ip-api.io/json?ip=" + ip,
                "https://ipapi.co/" + ip + "/json/",
                "https://api.ipapi.is/?ip=" + ip,
                "https://api.ip.sb/geoip/" + ip,
                "https://api.qjqq.cn/api/district?ip=" + ip
        ));

        List<Future<String>> futures = new ArrayList<>();
        for (String url : urls){
            Future<String> future = executor.submit(() -> getIPContent(url));
            futures.add(future);
        }

        int passAmount = 0;
        int failAmount = 0;
        for (Future<String> future : futures){
            try {
                String result = future.get();
                if (result != null) {
                    if (result.contains("China") || result.contains("china") || result.contains("CHINA") || result.contains("中国")) {
                        System.out.println("通过: " + result);
                        passAmount++;
                    } else {
                        System.out.println("拦截: " + result);
                        failAmount++;
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("pass: " + passAmount);
        System.out.println("fail: " + failAmount);
        System.out.println("IP验证结束！花费时间: " + (System.currentTimeMillis() - allStartTime) + " ms");
        if (failAmount > passAmount) {
            if (!ip.contains("127.0.0.1")) {
                Bukkit.banIP(ip);
            }
        }
    }

    public static String getIPContent(String url) {
        long startTime = 0;
        String content = null;

        try {
            startTime = System.currentTimeMillis();
            content = null;
            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
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
        } catch (Exception e) {
            System.out.println("无法连接到 " + url);
            return null;
        }

        System.out.println("成功连接！花费时间: " + (System.currentTimeMillis() - startTime) + " ms " + content);
        return content;
    }
}
