package com.httpprotocol;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类名称: Request
 * 功能描述:
 * 日期:  2018/10/6 16:37
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class Request {

    private InputStream inputStream;

    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse(){

        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j=0; j<i; j++)
        {
            request.append((char) buffer[j]);
        }
        System.out.println("************Http-request*****************");
        System.out.print(request.toString());
        uri = parseUri(request.toString().replace('/', '\\'));
        System.out.println (uri);
    }

    public String parseUri(String requestString){
        int index1, index2;
        index1 = requestString.indexOf(' ');
        /*
         * http请求行的结构:方法 统一资源标识符（URI） 协议/版本(它们之间以空格分隔)
         * 例如：POST //examples/default.jsp HTTP/1.1
         */
        if (index1 != -1) {// index1 == -1表示没找到
            index2 = requestString.indexOf(' ', index1 + 1);//从index+1位置开始寻找‘ ’
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
