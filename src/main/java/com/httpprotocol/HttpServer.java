package com.httpprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 类名称: HttpServer
 * 功能描述:
 * 日期:  2018/10/6 16:26
 *
 * @author: renpengfei
 * @since: JDK1.8
 */
public class HttpServer extends Thread {

    private final static boolean controlSwitch = true;

    private Socket socket;

    private HttpServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            InputStream in = socket.getInputStream ();
            OutputStream out = socket.getOutputStream ();
            Request request = new Request (in);
            request.parse ();
            Response response = new Response (out);
            response.setUri (request.getUri ());
            response.send ();

        } catch (IOException e) {
            e.printStackTrace ();
        } finally {
            try {
                socket.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        int q_len = 6;
        int port = 8088;
        ServerSocket serverSocket = new ServerSocket(port, q_len);
        while (controlSwitch) {
            // wait for the next client connection:
            new HttpServer(serverSocket.accept()).start();
        }
    }
}
