/**
 * Created by fqlive on 2017/10/23.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;



/*
 * 一个简单的Socket服务器，能接收客户端请求，并将请求返回给客户端
 *
 */

public class server2 {
    public static void main(String[] args) throws IOException {
        //创建一个服务器端socket，指定绑定的端口号，并监听此端口
        ServerSocket serverSocket = new ServerSocket(1000);
        //调用accept()方法开始监听，等待客户端的连接
        System.out.println("**********服务器即将启动，等待客户端的连接*************");
        Socket socket = serverSocket.accept();
        //获取输入流，并读取客户端信息
        InputStream inp = (InputStream) socket.getInputStream();
        //把字节流转换成字符流
        InputStreamReader isr = new InputStreamReader(inp);
        //为字符流增加缓冲区
        BufferedReader bfr = new BufferedReader(isr);
        String info = null;
        while((info=bfr.readLine())!=null){//循环读取数据
            System.out.println("我是服务器，客户端说："+info);
        }
        socket.shutdownInput();//关闭输入流
        //向客户端传递的信息
        OutputStream ots = (OutputStream) socket.getOutputStream();
        PrintWriter pw = new PrintWriter(ots);
        pw.write("欢迎登陆");
        pw.flush();

        //关闭资源
        pw.close();
        ots.close();
        bfr.close();
        isr.close();
        inp.close();
        socket.close();
        serverSocket.close();

    }

}
