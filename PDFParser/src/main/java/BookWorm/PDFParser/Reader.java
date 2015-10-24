package BookWorm.PDFParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;


public class Reader {

	public static void main (String[] args) throws Exception {
		// TODO Auto-generated method stub
		PDDocument document = PDDocument.load("Example.pdf");
		int startPage=1;
		int endPage = 2;
		
		//Class to read the pages of the book
		ReadPages readPages = new ReadPages(document);
		readPages.getPages(startPage, endPage);
		
		//Class to get the metaData of the object
		MetaData metaData = new MetaData(document);
		System.out.println(metaData.getMetaData());
		
		
		//To Index the data to the cluster
		Settings settings = ImmutableSettings.settingsBuilder()
		        .put("cluster.name", "bookworm").build();
		BookWormElasticSearch bookWormElasticSearch = new BookWormElasticSearch(settings);
		bookWormElasticSearch.addData("TestBook1", "2", "Book Content");
		//bookWormElasticSearch.removeBook("TestBook1");
		bookWormElasticSearch.searchBooks("Content");
		bookWormElasticSearch.closeClient();
		
	}

}
