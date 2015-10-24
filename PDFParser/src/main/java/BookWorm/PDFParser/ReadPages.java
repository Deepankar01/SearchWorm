package BookWorm.PDFParser;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class ReadPages {

	private PDFTextStripper stripper;
	private PDDocument document;
	
	public ReadPages(PDDocument doc) throws IOException
	{
		 document = doc;
		 stripper = new PDFTextStripper();
	}
	
	public String getPages(int StartPage,int EndPage) throws IOException
	{
		stripper.setStartPage(StartPage);
		stripper.setEndPage(EndPage);
		return (stripper.getText(document));
	}
}
