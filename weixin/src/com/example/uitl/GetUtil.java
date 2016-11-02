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
            //�򿪺�URL֮�������
            URLConnection conn=realUrl.openConnection();
            //����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
            //����ʵ������
            conn.connect();
            //����BufferedReader����������ȡURL��Ӧ
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            //���ж�ȡ���ݣ�������ݲ�Ϊ�գ������result��
            while((line=in.readLine())!=null)
            {
                result+="\n"+line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //��������쳣����ȡ����ʧ�ܣ����ؿ�
            result="";
            return result;
            //e.printStackTrace();
        }
        finally {
            //�ͷ���Դ
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
