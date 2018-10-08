package com.httpprotocol;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 类名称: HttpClient
 * 功能描述:
 * 日期:  2018/10/8 11:02
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class HttpClient {

    private static String addr = "127.0.0.1";

    private static final int port = 8688;

    private static String requestHeaderTemplete = "GET %s HTTP/1.1\r\n" + "Host: 127.0.0.1:8088\r\n\r\n";

    static class Writer extends Thread {
        Socket sock;
        DataOutputStream dataOutStream;

        public Writer(Socket socket,DataOutputStream dataOutputStream) {
            this.sock = socket;
            this.dataOutStream = dataOutputStream;
        }

        @Override
        public void run() {
            //写消息
            try {
                String header = String.format (requestHeaderTemplete, "/test");
                dataOutStream.write (header.getBytes ());
                dataOutStream.flush ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }


    private void send() {
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            socket = new Socket (addr, port);
            out = socket.getOutputStream ();
            in = socket.getInputStream ();
            BufferedReader reader = new BufferedReader (new InputStreamReader (in));
            DataOutputStream dataOutputStream = new DataOutputStream (out);
            //写消息
            new Writer (socket,dataOutputStream).start ();
            //读消息
            String aline;
            while ((aline = reader.readLine ()) != null) {
                System.out.println (aline);
            }
            reader.close ();
            socket.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public static void main(String[] args) {
        HttpClient client = new HttpClient ();
        client.send ();
    }
}
