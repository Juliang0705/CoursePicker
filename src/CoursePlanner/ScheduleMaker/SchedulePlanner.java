package CoursePlanner.ScheduleMaker;

/**
 * Created by Juliang on 4/7/16.
 */
import java.time.Year;
import java.util.*;
import java.util.concurrent.Future;

/**
 * This singleton class is an integration of other classes and makes it easier to use
 * also provides caching service to speed up the program
 */
public class SchedulePlanner {
    private static SchedulePlanner ourInstance = new SchedulePlanner();

    public static SchedulePlanner getInstance() {
        return ourInstance;
    }
    final private List<String> allSubjects = new ArrayList<>();
    /**
     * first key should be subject + year + semester
     * i.e. CSCE20163(CSCE 2016 Spring)
     * second key is the course number such as 411, 222
     */
    private HashMap<String,HashMap<String,List<FutureCourse>>> courseCatalog;
    private SchedulePlanner() {
        allSubjects.addAll(Arrays.asList("AERO","MEMA","BMEN","BIOT","CHEN","SENG","CVEN","OCEN","ENGR","CSCE","ECEN",
                "ENDG","ENTC","IDIS","ISEN","MSEN","MEEN","NUEN","PETE","ACCT","BUAD","BUSN","IBUS","FINC","ISYS","SCMT","MGMT","MKTG",
                "ANTH","AFST","FILM","JOUR","LBAR","RELS","WGST","COMM","ECMT","ECON","ENGL",
                "LING","HISP","SPAN","HIST","ARAB","CLAS","EURO","FREN","GREM","INTS","ITAL",
                "JAPN","RUSS","MUSC","THAR","HUMA","PHIL","POLS","PSYC","SOCI","ATMO","GEOS","GEOG","GEOL","GEOP","OCNG",
                "BIOL","CHEM","NRSC","MATH","ASTR","PHYS","STAT","AGCJ","AGSC","ALEC","ALED","AGEC","ANSC","BICH","GENE","AGSM","BAEN","AGLS","ESSM",
                "RENR","ENTO","FIVS","HORT","FSTC","NUTR","BESC","PLPA","POSC","RENR","RPTS","MEPS",
                "SCSC","WFSC","ARCH","ENDS","COSC","LAND","PLAN","URPN","ARTS","VIST","VIZA",
                "CEHD","EDAD","TAMG","EHRD","INST","MASC","BEFB","BIED","CPSY","EDTC","EPFB","EPSY","SEFB",
                "SPED","SPSY","ATTR","DCED","HEFB","HLTH","KINE","SPMT","EDCI","MEFB","RDNG","TEFB",
                "ELIC","ELID","ELIG","ELIL","ELIO","ELIR","ELIV","BUSH","INTA","PSAA","EDHP","AERS","SOMS","MLSC","NVSC",
                "BIMS","VMID","VIBS","VLCS","VTPP","VSCS","VPAR","VTMI","VTPB"
                ));
    }
    private HashMap<String,List<FutureCourse>> separateCourseByNumber(List<FutureCourse> courses){
        HashMap<String,List<FutureCourse>> separatedCourseList = new HashMap<>();
        for (FutureCourse course : courses){
            if (separatedCourseList.containsKey(course.getNumber())){
                separatedCourseList.get(course.getNumber()).add(course);
            }else{
                List<FutureCourse> newList = new ArrayList<>();
                newList.add(course);
                separatedCourseList.put(course.getNumber(),newList);
            }
        }
        return separatedCourseList;
    }

    public List<FutureCourse> getCourse(String year, Semester semester, String subject,String number) throws Exception{
        String key = subject + year;
        if (semester == Semester.SPRING){
            key += 1;
        }else if (semester == Semester.SUMMER){
            key += 2;
        }else if (semester == Semester.FALL){
            key += 3;
        }
        // get it from the cache
        if (this.courseCatalog.containsKey(key)){
            if (this.courseCatalog.get(key).containsKey(number)){
                return this.courseCatalog.get(key).get(number);
            }else
                throw new Exception("Course number is not found");
        }
        //get it from the server(slower)
        int currentYear = Year.now().getValue();
        if (Integer.parseInt(year) != currentYear || Integer.parseInt(year) != currentYear+1)
            throw new Exception("It is pointless to look up past course for scheduling purpose");
        if (!allSubjects.contains(subject))
            throw new Exception("Subject "+subject+ " not found");
        ScheduleDataParser parser = new ScheduleDataParser(new ScheduleDataGetter(year,semester,subject));
        // this get all the courses
        List<FutureCourse> courses = parser.getCourseList();
        //save it in the cache
        this.courseCatalog.put(key,this.separateCourseByNumber(courses));
        if (this.courseCatalog.get(key).containsKey(number)){
            return this.courseCatalog.get(key).get(number);
        }else{
            throw new Exception("Course number is not found");
        }
    }
}
