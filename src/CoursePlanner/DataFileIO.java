package CoursePlanner;

/**
 * Created by Jeffrey on 12/19/2016.
 * Allows for saving and loading course lists
 */

import CoursePlanner.ScheduleMaker.FutureCourse;
import CoursePlanner.ScheduleMaker.User;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class DataFileIO {
    private File file;
    private String content;                     //Text of save files
    private User user;                          //User object
    private List<FutureCourse> selectedCourses; //User current course list

    /**
     * @throws IOException
     */
    public void saveFile() throws IOException{
        //Generate content output string from course list
        selectedCourses = user.getSelectedCourses();
        content = "CoursePicker course list:\n";
        for(FutureCourse course : selectedCourses)
            content += course.toString() + "\n";
        //Write content to file
        FileWriter fw = new FileWriter(file.getPath());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);

        if (bw != null) bw.close();
        if (fw != null) fw.close();
    }

    /**
     * @throws IOException
     */
    public void openFile() throws IOException{
        // Open the file
        FileInputStream fs = new FileInputStream(file.getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        //Read File Line By Line
        String strLine;
        while (((strLine = br.readLine()) != null)) {
            if(Objects.equals("CoursePicker course list:", strLine)) continue;
            // Print the content on the console
            System.out.println (strLine);
        }

        //Close the input stream
        br.close();
    }

    /**
     * @param file a File object for path access
     */
    public DataFileIO (File file){
        this.file = file;
        user = User.currentUser();
    }
}
