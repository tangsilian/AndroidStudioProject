package com.example.uitl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetUtil {
    public static String sendGet(String url)
    {
        String result="";
        BufferedReader in=null;
        try{
            String urlName=url;
            URL realUrl=new URL(urlName);
            //打开和URL之间的连接
            URLConnection conn=realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
            //建立实际连接
            conn.connect();
            //定义BufferedReader输入流来读取URL响应
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            //逐行读取数据，如果数据不为空，则放入result中
            while((line=in.readLine())!=null)
            {
                result+="\n"+line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //如果网络异常，获取数据失败，返回空
            result="";
            return result;
            //e.printStackTrace();
        }
        finally {
            //释放资源
            try{
                if(in!=null)
                {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
