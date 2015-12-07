package SearchWorm.PDFParser;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class MetaData {
    private PDDocument document;
    private Set<String> metaKeySet;
    private PDDocumentInformation info;
    private Dictionary<String, String> metaData;

    public MetaData(PDDocument doc) {
        // TODO Auto-generated constructor stub
        document = doc;
        info = document.getDocumentInformation();
        metaKeySet = info.getMetadataKeys();
    }

    public Dictionary<String, String> getMetaData() {
        metaData = new Hashtable<String, String>();

        for (String key : metaKeySet) {
            metaData.put(key, String.valueOf(info.getPropertyStringValue(key)));
        }
        metaData.put("PageCount", String.valueOf(document.getNumberOfPages()));

        return metaData;
    }
}