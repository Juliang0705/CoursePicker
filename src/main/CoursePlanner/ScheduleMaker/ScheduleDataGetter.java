package CoursePlanner.ScheduleMaker;

/**
 * Created by Juliang on 4/1/16.
 */
import java.io.*;
import java.net.*;
import java.util.regex.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * this class fetches raw HTML data associated with a particular department in given year and semester
 */
public class ScheduleDataGetter {
    public enum Semester{
        SPRING,SUMMER,FALL
    }
    private String data;

    private String year;

    private int semester;

    private String subject;

    static private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * get all raw HTML data. It is not guaranteed that html data will be valid. It is parser's responsibility to check.
     * @param year  String for example 2016, 2015
     * @param semester int 1 for spring, 2 for summer, 3 for fall
     * @param subject String 4 capitalized abbreviation of a department such as MATH, CSCE
     * @throws Exception if inputs are not valid.
     */
    public ScheduleDataGetter(String year, Semester semester, String subject) throws Exception{
        //checking if inputs are sort of valid.
        Pattern yearPattern = Pattern.compile("\\d{4}");
        Matcher yearMatcher = yearPattern.matcher(year);
        Pattern subjectPattern = Pattern.compile("[A-Z]{4}");
        Matcher subjectMatcher = subjectPattern.matcher(subject);
        if (!yearMatcher.matches() ||!subjectMatcher.matches())
            throw new Exception("Bad input: "+ year + semester + " " + subject);
        this.year = year;
        if (semester == Semester.SPRING){
            this.semester = 1;
        }else if (semester == Semester.SUMMER){
            this.semester = 2;
        }else if (semester == Semester.FALL){
            this.semester = 3;
        }
        this.subject = subject;
        String term = year + this.semester + "1"; // 1 for college station campus
        //making post request to the server
        //pulling all the data for a single particular course
        OutputStreamWriter writer = null;
        try {
            String urlParameters = "term_in="+ term +"&sel_subj=dummy&sel_day=dummy&sel_schd=dummy&sel_insm=dummy&sel_camp=dummy&sel_levl=dummy&sel_sess=dummy&sel_instr=dummy&sel_ptrm=dummy&sel_attr=dummy&sel_subj="+subject+"&sel_crse=&sel_title=&sel_schd=%25&sel_insm=%25&sel_from_cred=&sel_to_cred=&sel_camp=%25&sel_levl=%25&sel_ptrm=%25&sel_instr=%25&sel_attr=%25&begin_hh=0&begin_mi=0&begin_ap=a&end_hh=0&end_mi=0&end_ap=a";
            URL url = new URL("https://compass-ssb.tamu.edu/pls/PROD/bwckschd.p_get_crse_unsec");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(urlParameters);
            writer.flush();
            data = convertStreamToString(conn.getInputStream());
        }finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     *
     * @return the raw HTML code
     */
    public String getRawData(){
        return this.data;
    }

    /**
     *
     * @return plain text without the html tags
     */
    public String getCleanData(){
        Document document = Jsoup.parse(this.data);
        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        return Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
    }

    public String getSubject() {
        return subject;
    }

    public int getSemester() {
        return semester;
    }

    public String getYear() {
        return year;
    }

    public String getTerm(){
        String term = "";
        if (semester == 1){
            term = "Spring ";
        }else if (semester == 2){
            term = "Summer ";
        }else if (semester == 3){
            term = "Fall ";
        }
        return term + year;
    }
}
