package com.httpprotocol;

import java.io.*;

/**
 * 类名称: Response
 * 功能描述:
 * 日期:  2018/10/6 23:38
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class Response {

    private static String headerTemplete =
            "HTTP/1.1 200 OK" + "\r\n" +
                    "Content-Length: " + "%s" + "\r\n" +
                    "Connection: " + "%s" + "\r\n" +
                    "Content-Type :" + "%s" + "\r\n\r\n";
    private OutputStream out;
    private String uri;

    public Response(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void send() {

        //输出文本
        //        String body = "<title>test ok</title>";
        //        String head = String.format (headerTemplete, body.length (), "close", "text/html");

        //输出图片
        File bodyFile = new File ("C:\\Users\\ryan\\Pictures\\blogPic\\20170227131616348.png");
        Long fileLength = bodyFile.length ();
        byte[] contentBuffer = new byte[fileLength.intValue ()];
        try {
            FileInputStream fis = new FileInputStream (bodyFile);
            String head = String.format (headerTemplete, fileLength, "close", "image/png");
            out.write (head.getBytes ());

            byte[] bytes = new byte[1024];
            int ch = fis.read (bytes, 0, 1024);
            while (ch != -1) { //ch==-1 means in the end
                out.write (bytes, 0, ch); // write to the client
                // The second read will follow the last read, and if it reaches the end it will return -1
                ch = fis.read (bytes, 0, 1024);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            try {
                out.flush ();
                out.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
