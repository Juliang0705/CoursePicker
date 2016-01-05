/**
 * Created by Juliang on 1/4/16.
 * this class get raw pdf data from url and parse it to string
 */
import java.net.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;

public class DataGetter {
    private PDDocument pd;
    private PDFTextStripper stripper;
    private String data;

    public DataGetter(String url) throws Exception{
        this(new URL(url));
    }

    public DataGetter(URL url) throws Exception{
        if (!url.getFile().endsWith(".pdf"))
            throw new Exception("DataGetter Error: url must end with .pdf");
        URLConnection connection = url.openConnection();
        this.pd = PDDocument.load(connection.getInputStream());
        this.stripper =  new PDFTextStripper();
        this.data = stripper.getText(pd);
        pd.close();
    }
    String getData(){
        return this.data;
    }
}
