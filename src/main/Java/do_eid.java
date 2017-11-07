import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;
import java.util.Scanner;
/**
 * Created by fqlive on 2017/10/23.
 */
public class do_eid {

    public void GenEid( ) throws Exception {
        System.out.println("客户端发送消息：get my e_id");
        System.out.println("请输入您的ID:");
        Scanner cin=new Scanner(System.in);
        String id=cin.nextLine();
        int  had_Gen=Had_Gen(id);
        if(had_Gen==-2) {
            System.out.println("您的电子身份证已经生成过了，退出程序!");
            System.exit(0);
        }else if(had_Gen==-1){
            System.out.println("您输入的身份证信息不正确，退出程序!");
            System.exit(0);
        }else do_eid1(had_Gen);
    }
      public   void do_eid1(int index) throws Exception {
        Scanner cin=new Scanner(System.in);
          System.out.println("向CA端发送公钥");
          String pk=getPK_SK(index,"public_key","src/1.xml");
             addPK(index,pk);
          System.out.println("CA已接收公钥并保存");
          System.out.println("向CA端发送用私钥加密后的信息和64位口令，该口令将存储在CA端，以便后续对客户端身份认证");
          String sk=getPK_SK(index,"security_key","src/1.xml");
          String random=getRandm();
          String data=getEncty(index,sk);
          System.out.println(data+random);
          addRandom(index,random);  //向2.xml中加入随机口令
          addRandom2(index,random);
          String jie_data=getDecry(index,data);
          System.out.println("解密后信息："+jie_data);
          String info[]=jie_data.split(",");
          System.out.println("请输入要加密的密码(密码要足够长，可以是汉字，字母及数字):");
          String key=cin.nextLine();
          String jia_key=new RSATest().jia(sk,key);
          System.out.println("将用私钥加密后的密码和随机口令发送给CA："+jia_key+random);
          String real_key=getReal_key(index,jia_key+","+random);
         // System.out.println(real_key);
          String des_data=new DES().getResult(jie_data,real_key);
          String getSHA1Re=new SHA1().getSHA1(info[0]); //获取hash(Id);
          addSHA_ID_DESDATA(index,getSHA1Re,des_data,info[0]);//向2.xml中写入id,hash（id），des_data
          addHashId(index,getSHA1Re);  //向1.xml中写入hash(id);
          System.out.println("CA用客户端的公钥解密后将用户信息存储，并将得到的hash(id)返回给客户端："+getSHA1Re);
    }

    private void addRandom2(int index, String random) throws Exception {
       Document document=Doc_xml.getDocument("src/1.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        org.dom4j.Element Ran_Ling = p1.addElement("Ran_Ling");
        Ran_Ling.setText(random);
        Doc_xml.xmlWriters("src/1.xml", document);
    }

    private void addHashId(int index, String getSHA1Re) throws Exception {
        Document document=Doc_xml.getDocument("src/1.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1= p1s.get(index);
        Element hash_id=p1.addElement("hash_id");
        hash_id.setText(getSHA1Re);
        Doc_xml.xmlWriters("src/1.xml", document);
    }

    private void addSHA_ID_DESDATA(int index, String getSHA1Re, String des_data, String s) throws Exception {
       Document document=Doc_xml.getDocument("src/2.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        Element id=p1.addElement("id");
        Element hash_id=p1.addElement("hash_id");
        Element desData=p1.addElement("desData");
        id.setText(s);
        hash_id.setText(getSHA1Re);
        desData.setText(des_data);
        Doc_xml.xmlWriters("src/2.xml", document);
    }
    private String getReal_key(int index, String s) throws Exception {
        String info[]=s.split(",");
        String real_key="";
        Document document=Doc_xml.getDocument("src/2.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        org.dom4j.Element Ran_Ling = p1.element("Ran_Ling");
        Element public_key=p1.element("public_key");
        if(Ran_Ling.getText().equals(info[1])){   //说明用户身份真实，防止时差攻击
           real_key=new RSATest().jie(public_key.getText(),info[0]);
        }
         return  real_key;
    }

    private void addRandom(int index, String random) throws Exception {
        Document document=Doc_xml.getDocument("src/2.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        org.dom4j.Element Ran_Ling = p1.addElement("Ran_Ling");
        Ran_Ling.setText(random);
        Doc_xml.xmlWriters("src/2.xml",document);
    }

    private String getRandm() {
        String s="";
        for(int i = 0; i < 64; i++){
            int a= (int) (Math.random() * 10);
            s=s+String.valueOf(a);
        }
        return s;
    }

    private void addPK(int index, String pk) throws Exception {
        Document document=Doc_xml.getDocument("src/2.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        org.dom4j.Element public_key = p1.addElement("public_key");
         public_key.setText(pk);
        Doc_xml.xmlWriters("src/2.xml", document);
    }
    public  int Had_Gen(String id) throws Exception {
        Document document=Doc_xml.getDocument("src/1.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        for (int i = 0; i <p1s.size() ; i++) {
            org.dom4j.Element p1=p1s.get(i);
            org.dom4j.Element id1=p1.element("id");
            org.dom4j.Element hash_id=p1.element("hash_id");
            if(id1.getText().equals(id)) {
                if (hash_id == null)
                    return i;
                else return -2;
            }
        }
        return -1;
    }

    private String getDecry(int index, String data) throws Exception {
        Document document=Doc_xml.getDocument("src/2.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        org.dom4j.Element pk=p1.element("public_key");
           String pub_key=pk.getText();
        String redult=new RSATest().jie(pub_key,data);
        return redult;
    }
    public String getEncty(int index,String sk) throws Exception {
        Document document=Doc_xml.getDocument("src/1.xml");
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        org.dom4j.Element id=p1.element("id");
        org.dom4j.Element name=p1.element("name");
        org.dom4j.Element address=p1.element("address");
        String ming=id.getText()+","+name.getText()+","+address.getText()+",";
        String result=new RSATest().jia(sk,ming);   //私钥加密后结果
        return result;
    }
   public  String getPK_SK(int index,String ps,String path) throws Exception {
        Element  p1=getElement(index,path);
        Element ps_key=p1.element(ps);
        return ps_key.getText();
   }

    private Element getElement(int index, String s) throws Exception {
        Document document=Doc_xml.getDocument(s);
        org.dom4j.Element element= document.getRootElement();
        List <org.dom4j.Element> p1s=element.elements("p1");
        org.dom4j.Element p1=p1s.get(index);
        return p1;
    }

}
