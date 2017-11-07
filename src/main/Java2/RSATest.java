import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSATest {
    static final String ALGORITHM_RSA = "RSA";
    static final String ALGORITHM_SIGN = "MD5withRSA";
    static String publicKey = null;
    static String privateKey = null;
  /*  public static void main(String[] args) throws Exception {
        // 生成公钥和私钥  
        //generatorKeyPair();
        String source = "张三，安徽理工大学";
        System.out.println("加密前的数据：\r\n" + source);
        System.out.println("--------------------------公钥加密，私钥解密------------------------------");
       publicKey=" MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx+of7gH560Wi8jR215atIdeAiNs8IySYI1IVkHIpxklzizsREz0lbp+61u405/wOc7igVmJGHFEQDrB/9K4DRIaxmcpYCBhq5rwEi9/VKLq5Us1Ob//hNaQaFteKlBhhBBX5+6A7Nm9sSDKrGyUGpZgQlv5dI9qK9mXprxbjmAwIDAQAB";
        privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALH6h/uAfnrRaLyNHbXlq0h14CI2zwjJJgjUhWQcinGSXOLOxETPSVun7rW7jTn/A5zuKBWYkYcURAOsH/0rgNEhrGZylgIGGrmvASL39UourlSzU5v/+E1pBoW14qUGGEEFfn7oDs2b2xIMqsbJQalmBCW/l0j2or2ZemvFuOYDAgMBAAECgYBbh6R3S1XAhAGBNnGZIkw8L6Lc9aLzub69u8399k+cqNDsM7nsSU5IvMye2Z1/vg51nmUu9g+hkGKKIPMymDTeesbK0PVKR4sTk7pM6rvbfQens5k+tCggn/SMyoVILXljaSK5PcMOMjLDD1vzeP5CXyLU9Vr7HH79naRRoRHtQQJBAPZ912MOpfqXmShPy/Sux+QlG2NiNzG55KsSTL0o7XU4IXs11K8q2WBVPNbLmv6kooJc4vx+bJ7b23I+q649gP0CQQC42BwJJc7p3m6eCtc/fKnXi80BbaOn2YEMDr2Q/Cw2YrLvaOmoDnV/wPozJRbZ2iFXbjuXKjxavQYHnwt1lDL/AkBk+HuoijGXi04j4zwrCQW9AS5M8cimR/3Rzfc9bWyIHewpKo3rC2RsP75iRplBQnOHb0FUKP2ZvXoRPHbbMzqJAkB7UCj77U4pdSukoaTRhNAlrO05+7PHQXI24gqTE7hHG/c1gm8Gn4bBkLMZta+V7FB77F0yzolvG1VPdkoFybVvAkBIS+WbvfXWaAGbv/v1XGWrKeKCeHk89XvWV69qbPFZqw9Q6kBtFRdRbOLFUknzir86CN2VPUgbRnpOuhjpoLGf";
        // 公钥加密  
        String target = encryptionByPublicKey(source);
       // PublicKey key=getPublicKey();
        // 私钥解密  
        decryptionByPrivateKey(target);
         String jia_Result=jia(publicKey,"张三，安徽理工大学");
              jie(jia_Result);

    }*/
    public static String jia(String pk,String data) throws Exception {
         publicKey=pk;
         String result=encryptionByPublicKey(data);
         return result;
    }
    public static String  jie(String sk,String jia_result) throws Exception {
          privateKey=sk;
          String result=decryptionByPrivateKey(jia_result);
          return result;
    }
    /**
     * 生成密钥对 
     * @throws Exception
     */
    static void generatorKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        byte[] keyBs = rsaPublicKey.getEncoded();
        publicKey = encodeBase64(keyBs);
        System.out.println("生成的公钥：\r\n" + publicKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        keyBs = rsaPrivateKey.getEncoded();
        privateKey = encodeBase64(keyBs);
        System.out.println("生成的私钥：\r\n" + privateKey);
    }

    /**
     * 获取公钥 
     * @return
     * @throws Exception
     */
    static PublicKey getPublicKey() throws Exception {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 获取私钥 
     * @return
     * @throws Exception
     */
    static PrivateKey getPrivateKey() throws Exception {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * 公钥加密
     */
    static String encryptionByPublicKey(String source) throws Exception{
        PublicKey publicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(source.getBytes("UTF-8"));
        String target = encodeBase64(cipher.doFinal());
       // System.out.println("公钥加密后的数据：\r\n" + target);
        return target;
    }

    /**
     * 私钥解密 
     * @param target
     * @throws Exception
     */
    static String decryptionByPrivateKey(String target) throws Exception {
        PrivateKey privateKey = getPrivateKey();
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipher.update(decodeBase64(target));
        String source = new String(cipher.doFinal(), "UTF-8");
       // System.out.println("私钥解密后的数据：\r\n" + source);
        return source;
    }
    /**
     * base64编码 
     * @param source
     * @return
     * @throws Exception
     */
    static String encodeBase64(byte[] source) throws Exception{
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

    /**
     * Base64解码 
     * @param target
     * @return
     * @throws Exception
     */
    static byte[] decodeBase64(String target) throws Exception{
        return Base64.decodeBase64(target.getBytes("UTF-8"));
    }
}  