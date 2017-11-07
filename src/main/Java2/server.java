/**
 * Created by fqlive on 2017/10/23.
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    public static final int PORT = 10000;//监听的端口号

    public static void main(String[] args) {
        System.out.println("sever begins");
        server server = new server();
        server.init();
    }

    public void init() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                //一个客户端连接就开两个线程分别处理读和写
                new Thread(new ReadHandlerThread1(client)).start();
                new Thread(new WriteHandlerThread1(client)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 *处理读操作的线程
 */
class ReadHandlerThread1 implements Runnable{
    private Socket client=null;

    public ReadHandlerThread1(Socket client) {
        this.client = client;
    }

    public void run() {
        BufferedReader in = null;
        try{
            while(true){
                //读取客户端数据
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("客户端:" + in.readLine());
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(client != null){
                client = null;
            }
        }
    }
}

/*
 * 处理写操作的线程
 */
class WriteHandlerThread1 implements Runnable{
    private Socket client;

    public WriteHandlerThread1(Socket client) {
        this.client = client;
    }

    public void run() {
        PrintWriter out=null;
        try {
            System.out.println("ok");
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//从控制台获取输入的内容
        try{
            while(true){
                //向客户端回复信息
                out.println(reader.readLine());
                out.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(client != null){
                client = null;
            }
        }
    }
}
