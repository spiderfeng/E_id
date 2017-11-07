import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by fqlive on 2017/10/23.
 */
public class client2  {
    public static void main(String[] args) {
        try {
            //创建一个客户端socket
            Socket socket = new Socket("localhost",1000);
            //向服务器端传递信息
            OutputStream ots = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(ots);
            pw.write("用户名：admin;密码：123");
            pw.flush();
            //关闭输出流
            socket.shutdownOutput();
            //获取服务器端传递的数据
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while((info=br.readLine())!=null){
                System.out.println("我是客户端，服务器说："+info);
            }
            //关闭资源
            br.close();
            isr.close();
            is.close();
            pw.close();
            ots.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
