import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
public class Main {
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    public static void main(String[] args) {
        try {
           /* URL link = new URL("http://web-as.tamu.edu/gradereport/PDFReports/20153/grd20153EN.pdf");
            DataGetter getter = new DataGetter(link);
            String data = getter.getData();
            GradeParser parser = new GradeParser(data);
            List<Course> courseList = GradeAnalyzer.compress(parser.getCourse("ENGR",112));
            int total = 0;
            for (Course c: courseList){
                System.out.println(c);
                total += c.total();
            }
            System.out.println("Total: " + total);*/
            List<Course> courseList = GradeGetter.getInstance().getCourse("GEOG 203",6,true);
            for (Course c: courseList){
                System.out.println(c);
            }
        } catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
