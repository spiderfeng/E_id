import java.util.Scanner;

/**
 * Created by fqlive on 2017/10/23.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("请选择:");
        System.out.println("1.生成电子身份证信息");
        System.out.println("2.认证电子身份证信息");
        Scanner scanner=new Scanner(System.in);
        int select=scanner.nextInt();
        if (select==1)new do_eid().GenEid();
        else new confirm_eid().confirm();

    }
}
