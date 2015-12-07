package SearchWorm.PDFParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

import java.util.Dictionary;

public class ReaderDemo {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        PDDocument document = PDDocument.load("/home/abhinav/Desktop/server_uploads/Example.pdf");

        Dictionary<String, String> metaDict;
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "searchworm").build();
        SearchWormElasticSearch searchWormElasticSearch = new SearchWormElasticSearch(settings);


        //Class to read the pages of the book
        ReadPages readPages = new ReadPages(document);

        //Class to get the metaData of the object
        MetaData metaData = new MetaData(document);
        metaDict = metaData.getMetaData();

        for (int i = 1; i <= Integer.parseInt(metaDict.get("PageCount")); i++) {
            //To Index the data to the cluster
            searchWormElasticSearch.addData(metaDict.get("Title"), String.valueOf(i), readPages.getPages(i, i));
        }

        searchWormElasticSearch.closeClient();
    }
}