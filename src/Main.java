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
            List<Course> courseList = GradeGetter.getInstance().getCourse("STAT 211",5,true);
            for (Course c: courseList){
                System.out.println(c);
            }
        } catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }
}
