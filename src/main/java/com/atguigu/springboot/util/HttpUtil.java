package com.atguigu.springboot.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtil {
    public static final String POST = "POST";
    public static final String GET = "GET";
    protected static final int SOCKET_TIMEOUT = 10000; // 10S 百度翻译

    public static String post(String uriString, Map<String, String> params)
            throws Exception
    {
        URL url = new URL(uriString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod(RequstMethod.POST.value());
        con.setRequestProperty(" Content-Type ",
                " application/x-www-form-urlencoded ");
        con.setUseCaches(false);
        con.setInstanceFollowRedirects(true);

        if (params != null) {
            StringBuffer sb = new StringBuffer();
            String data = null;
            byte[] b = null;

            for (String key : params.keySet()) {
                sb.append(key).append('=').append((String)params.get(key)).append('&');
            }
            data = sb.substring(0, sb.length() - 1);
            b = data.getBytes("UTF-8");

            OutputStream out = con.getOutputStream();
            out.write(b);
            out.flush();
            out.close();
        }

        InputStream in = con.getInputStream();
        String responseBody = stream2String(in);

        return responseBody;
    }

    public static String post(String uriString, Map<String, String> params, String authorization)
            throws Exception
    {
        URL url = new URL(uriString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod(RequstMethod.POST.value());
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", authorization);
        con.setUseCaches(false);
        con.setInstanceFollowRedirects(true);

        if (params != null) {
            StringBuffer sb = new StringBuffer();
            String data = null;
            byte[] b = null;

            for (String key : params.keySet()) {
                sb.append(key).append('=').append((String)params.get(key)).append('&');
            }
            data = sb.substring(0, sb.length() - 1);
            b = data.getBytes("UTF-8");

            OutputStream out = con.getOutputStream();
            out.write(b);
            out.flush();
            out.close();
        }

        InputStream in = con.getInputStream();
        String responseBody = stream2String(in);

        return responseBody;
    }

    public static String jpost(String uriString, Map<String, String> params)
            throws Exception
    {
        URL url = new URL(uriString);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod(RequstMethod.POST.value());
        con.setRequestProperty("Content-Type", "application/json");
        con.setUseCaches(false);
        con.setInstanceFollowRedirects(true);

        if (params != null) {
            StringBuffer sb = new StringBuffer();
            String data = null;
            byte[] b = null;

            for (String key : params.keySet()) {
                sb.append(key).append('=').append((String)params.get(key)).append('&');
            }
            data = sb.substring(0, sb.length() - 1);
            b = data.getBytes("UTF-8");

            OutputStream out = con.getOutputStream();
            out.write(b);
            out.flush();
            out.close();
        }

        InputStream in = con.getInputStream();
        String responseBody = stream2String(in);
        //FileUtil.readAll(in);

        return responseBody;
    }

    public static String sendGet(String url, String param)
    {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);

            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic dGVzdDpJbWFnaW5lIw==");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.connect();

            Map map = connection.getHeaderFields();

//      for (String key : map.keySet()) {
//        System.out.println(key + "--->" + map.get(key));
//      }

            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null)
            {
                //String line;
                result = result + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try
            {
                if (in != null)
                    in.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (in != null)
                    in.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            URLConnection conn = realUrl.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                //String line;
                result = result + line;
            }
        } catch (Exception e) {
            System.out.println("POST请求失败" + e);
            e.printStackTrace();
            try
            {
                if (out != null) {
                    out.close();
                }
                if (in != null)
                    in.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        finally
        {
            try
            {
                if (out != null) {
                    out.close();
                }
                if (in != null)
                    in.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static enum RequstMethod
    {
        GET("GET"), POST("POST"), PUT("PUT");

        private String value;

        private RequstMethod(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    public static String stream2String(InputStream inputStream){
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            return result.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * 网易云IM请求方式
     * @param
     * @param params 参数
     * @return
     * @throws Exception
     */
//    public static String IMPost(String urlStr, Map<String, String> params)
//            throws Exception
//    {
//        String appKey = AppConstants.IMAPPKEY;
//        String nonce =  Utils.getRandomString(18).toUpperCase();
//        String curTime = String.valueOf((new Date()).getTime() / 1000L);
//        String checkSum = CheckSumBuilder.getCheckSum(AppConstants.IMAPPSECRET, nonce ,curTime);//参考 计算CheckSum的java代码
//
//        URL url = new URL(urlStr);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        //设置请求头，IM每个接口都必须如此设置
//        con.addRequestProperty("AppKey", appKey);
//        con.addRequestProperty("Nonce", nonce);
//        con.addRequestProperty("CurTime", curTime);
//        con.addRequestProperty("CheckSum", checkSum);
//        con.setDoOutput(true);
//        con.setDoInput(true);
//        con.setRequestMethod(RequstMethod.POST.value());
//        con.setRequestProperty(" Content-Type ", " application/x-www-form-urlencoded;charset=utf-8 ");
//        con.setUseCaches(false);
//        con.setInstanceFollowRedirects(true);
//
//        if (params != null) {
//            StringBuffer sb = new StringBuffer();
//            String data = null;
//            byte[] b = null;
//
//            for (String key : params.keySet()) {
//                sb.append(key).append('=').append((String)params.get(key)).append('&');
//            }
//            data = sb.substring(0, sb.length() - 1);
//            b = data.getBytes("UTF-8");
//
//            OutputStream out = con.getOutputStream();
//            out.write(b);
//            out.flush();
//            out.close();
//        }
//
//        InputStream in = con.getInputStream();
//        String responseBody = stream2String(in);
//
//        return responseBody;
//    }
//
//    /**
//     * 融云IM请求方式
//     * @param urlStr 接口路径
//     * @param params 参数
//     * @return
//     * @throws Exception
//     */
//    public static String RongyunPost(String urlStr, Map<String, String> params)
//            throws Exception
//    {
//
//        String nonce = Utils.getRandomString(18);
//        System.out.println(nonce);
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        System.out.println(timestamp);
//        //SHA1生成签名
//        String sign = CodeUtil.hexSHA1(AppConstants.RY_APP_KEY+nonce+timestamp);
//        System.out.println(sign);
//        URL url = new URL(urlStr);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        //设置请求头，IM每个接口都必须如此设置
//        con.addRequestProperty("App-Key", AppConstants.RY_APP_KEY);
//        con.addRequestProperty("Nonce", nonce);
//        con.addRequestProperty("Timestamp", timestamp);
//        con.addRequestProperty("Signature", sign);
//        con.setDoOutput(true);
//        con.setDoInput(true);
//        con.setRequestMethod(RequstMethod.POST.value());
//        con.setRequestProperty(" Content-Type ", " application/x-www-form-urlencoded;charset=utf-8 ");
//        con.setUseCaches(false);
//        con.setInstanceFollowRedirects(true);
//
//        if (params != null) {
//            StringBuffer sb = new StringBuffer();
//            String data = null;
//            byte[] b = null;
//
//            for (String key : params.keySet()) {
//                sb.append(key).append('=').append((String)params.get(key)).append('&');
//            }
//            data = sb.substring(0, sb.length() - 1);
//            b = data.getBytes("UTF-8");
//
//            OutputStream out = con.getOutputStream();
//            out.write(b);
//            out.flush();
//            out.close();
//        }
//
//        InputStream in = con.getInputStream();
//        String responseBody = stream2String(in);
//
//        return responseBody;
//    }

    //百度翻译 请求方式
    public static String get(String host, Map<String, String> params) {
        try {
            // 设置SSLContext
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

            String sendUrl = getUrlWithQueryString(host, params);

            // System.out.println("URL:" + sendUrl);

            URL uri = new URL(sendUrl); // 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
            }

            conn.setConnectTimeout(SOCKET_TIMEOUT); // 设置相应超时
            conn.setRequestMethod(GET);
            int statusCode = conn.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Http错误码：" + statusCode);
            }

            // 读取服务器的数据
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            String text = builder.toString();

            close(br); // 关闭数据流
            close(is); // 关闭数据流
            conn.disconnect(); // 断开连接

            return text;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

    protected static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     *
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };
}
