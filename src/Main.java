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
            URL link = new URL("http://web-as.tamu.edu/gradereport/PDFReports/20151/grd20151EN.pdf");
            DataGetter getter = new DataGetter(link);
            String data = getter.getData();
            //String data = readFile("sample.txt", StandardCharsets.UTF_8);
            GradeParser parser = new GradeParser(data);
            List<Course> courseList = parser.getCourse("ENGR","112");
            for (Course c: courseList){
                System.out.println(c);
            }
        } catch (Exception e){
            System.out.println("Exception" + e);
        }
    }
}
