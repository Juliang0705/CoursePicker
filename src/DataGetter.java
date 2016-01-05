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

    /**
     *
     * @param url a string specifies where the data comes from
     * @throws Exception if the documents doesn't end with .pdf or connection cannot be made
     */
    public DataGetter(String url) throws Exception{
        this(new URL(url));
    }

    /**
     *
     * @param url an URl object specifies where the data comes from
     * @throws Exception if the documents doesn't end with .pdf or connection cannot be made
     */
    public DataGetter(URL url) throws Exception{
        if (!url.getFile().endsWith(".pdf"))
            throw new Exception("DataGetter Error: url must end with .pdf");
        URLConnection connection = url.openConnection();
        this.pd = PDDocument.load(connection.getInputStream());
        this.stripper =  new PDFTextStripper();
        this.data = stripper.getText(pd);
        pd.close();
    }

    /**
     *
     * @return the data as a string
     */
    String getData(){
        return this.data;
    }
}
