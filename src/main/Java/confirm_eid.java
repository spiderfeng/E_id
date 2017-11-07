import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;
import java.util.Scanner;

/**
 * Created by fqlive on 2017/10/23.
 */
public class confirm_eid {
    public void confirm() throws Exception {
        Scanner cin=new Scanner(System.in);
        System.out.println("请输入您的ID和hash(ID):");
        String id1=cin.nextLine();
        String hash_id1=cin.nextLine();
          int index=getIndex(id1);
          if(index==-1){
              System.out.println("您输的ID号有误,退出");
              System.exit(0);
          }
          int yes_no= con_self(index,id1,hash_id1);
           if(yes_no==0) System.out.println("您的身份已认证合法！");
            else {
            System.out.println("您的身份未被认证成功，退出!");
            System.exit(0);
            }
        System.out.println("请提供您要认证的人的id号和hash(id):");
        String id=cin.nextLine();
        String hash_id=cin.nextLine();
        index=getIndex(id);
        yes_no=con_self(index,id,hash_id);
        if(yes_no==0) System.out.println("您申请认证的人认证成功！");
        else{
            System.out.println("您申请认证的人身份不合法,退出!");
            System.exit(0);
        }
    }


    private int con_self(int index, String id1, String hash_id1) throws Exception {  //客户端，用自己的私钥加密数据后添加秘密口令传给CA端
        String sk=new do_eid().getPK_SK(index,"security_key","src/1.xml");
        String data=id1+","+hash_id1;
        String enc_data=new RSATest().jia(sk,data);  //RSA加密id和hash_id
        enc_data=enc_data+","+getRandom(index);
        int yes_no=confirm(enc_data,index);
        return yes_no;


    }
    private int confirm(String enc_data, int index) throws Exception {
        String info[]=enc_data.split(",");
         String pk=new do_eid().getPK_SK(index,"public_key","src/2.xml");
         String jie_data=new RSATest().jie(pk,info[0]);
         String id_hash[]=jie_data.split(",");
         if(info[1].equals(getRandom(index))){  //B是真的B
             Document document=Doc_xml.getDocument("src/2.xml");
             Element element= document.getRootElement();
             List <Element> p1s=element.elements("p1");
             Element p1=p1s.get(index);
            Element id=p1.element("id");
            Element hash_id=p1.element("hash_id");
       /*      System.out.println(id.getText()+" "+hash_id.getText());
             System.out.println(id_hash[0]+" "+id_hash[1]);*/
            if(id_hash[0].equals(id.getText())&&id_hash[1].equals(hash_id.getText()))return 0;
         }
         return -1;
    }

    private String getRandom(int index) throws Exception {
        Document document=Doc_xml.getDocument("src/1.xml");
        Element element= document.getRootElement();
        List <Element> p1s=element.elements("p1");
        Element p1=p1s.get(index);
        Element Ran_Ling = p1.element("Ran_Ling");
        return Ran_Ling.getText();
    }

    private int getIndex(String id) throws Exception {
        Document document=Doc_xml.getDocument("src/1.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        for (int i = 0; i <p1s.size() ; i++) {
            Element p1=p1s.get(i);
            Element id1=p1.element("id");
            if(id1.getText().equals(id)) {
                return i;
            }
        }
        return -1;
    }

}
