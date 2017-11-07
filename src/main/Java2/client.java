import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by fqlive on 2017/10/23.
 */
public class client {
    public static final String IP = "localhost";//服务器地址
    public static final int PORT = 10000;//服务器端口号

    public static void main(String[] args) {

        handler();
    }

    private static void handler(){
        try {
            //实例化一个Socket，并指定服务器地址和端口
            Socket client = new Socket(IP, PORT);
            System.out.println("I am a client");
            //开启两个线程，一个负责读，一个负责写
            new Thread(new ReadHandlerThread(client)).start();
            new Thread(new WriteHandlerThread(client)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 *处理读操作的线程
 */
class ReadHandlerThread implements Runnable{
    private Socket client;

    public ReadHandlerThread(Socket client) {
        this.client = client;
    }
    public void run() {
        BufferedReader in = null;
        try {
            System.out.println("hello");
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            while(true){
                System.out.println("服务器："+in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(client != null){
                client = null;
            }
        }
    }
    public void run1(String s){

    }
}

/*
 * 处理写操作的线程
 */
class WriteHandlerThread implements Runnable{
    private Socket client;

    public WriteHandlerThread(Socket client) {
        this.client = client;
    }

    public void run() {
        PrintWriter out=null;
        try {
            out = new PrintWriter(client.getOutputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));//从控制台获取输入的内容
        try {
            while(true){
                out.println(reader.readLine());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            client.close();
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.close();


    }
}
