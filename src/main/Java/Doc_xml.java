import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;

/**
 * Created by fqlive on 2017/10/23.
 */
public class Doc_xml {
    public static void xmlWriters(String path,Document document) throws Exception {
        org.dom4j.io.OutputFormat outputFormat= org.dom4j.io.OutputFormat.createPrettyPrint();//以对齐的方式显示，格式比较好看
        XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(path),outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
    public static Document getDocument(String path) throws Exception {
        SAXReader saxReader=new SAXReader();
        Document document=saxReader.read(path);
        return document;
    }
}
