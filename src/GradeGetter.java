/**
 * This singleton class is mainly used as an integration of other classes
 * Created by Juliang on 1/5/16.
 */
import java.util.*;
import java.util.regex.*;
public class GradeGetter {
    private static GradeGetter ourInstance = new GradeGetter();
    private HashMap<String,String> courseMap;
    private HashMap<String,GradeParser> parserMap;
    public static GradeGetter getInstance() {
        return ourInstance;
    }

    /**
     * intialize the instance with a course map
     */
    private GradeGetter() {
        this.courseMap = new HashMap<>();
        //for engineering: EN
        String[] ENCourses = {"AERO","MEMA","BMEN","BIOT","CHEN","SENG","CVEN","OCEN","ENGR","CSCE","ECEN",
                                "ENDG","ENTC","IDIS","ISEN","MSEN","MEEN","NUEN","PETE"};
        for (String abbr: ENCourses){
            this.courseMap.put(abbr,"EN");
        }
        //for bussiness: BA
        String[] BACourses = {"ACCT","BUAD","BUSN","IBUS","FINC","ISYS","SCMT","MGMT","MKTG"};
        for (String abbr: BACourses){
            this.courseMap.put(abbr,"BA");
        }
        //for liberal arts: LA
        String[] LACourses = {"ANTH","AFST","FILM","JOUR","LBAR","RELS","WGST","COMM","ECMT","ECON","ENGL",
                                "LING","HISP","SPAN","HIST","ARAB","CLAS","EURO","FREN","GREM","INTS","ITAL",
                                "JAPN","RUSS","MUSC","THAR","HUMA","PHIL","POLS","PSYC","SOCI"};

        for (String abbr: LACourses){
            this.courseMap.put(abbr,"LA");
        }
        //For geosciences: GE
        String[] GECourses = {"ATMO","GEOS","GEOG","GEOL","GEOP","OCNG"};
        for (String abbr: GECourses){
            this.courseMap.put(abbr,"GE");
        }
        //For sciences: SC
        String[] SCCourses = {"BIOL","CHEM","NRSC","MATH","ASTR","PHYS","STAT"};
        for (String abbr: SCCourses){
            this.courseMap.put(abbr,"SC");
        }
        //For Agriculture and life sciences: AG
        String[] AGCourses = {"AGCJ","AGSC","ALEC","ALED","AGEC","ANSC","BICH","GENE","AGSM","BAEN","AGLS","ESSM",
                                "RENR","ENTO","FIVS","HORT","FSTC","NUTR","BESC","PLPA","POSC","RENR","RPTS","MEPS",
                                "SCSC","WFSC"};
        for (String abbr: AGCourses){
            this.courseMap.put(abbr,"AG");
        }
        //For Architecture: AR
        String[] ARCourses = {"ARCH","ENDS","COSC","LAND","PLAN","URPN","ARTS","VIST","VIZA"};
        for (String abbr: ARCourses){
            this.courseMap.put(abbr,"AR");
        }
        //For Education : ED
        String[] EDCourses = {"CEHD"};
        for (String abbr: EDCourses){
            this.courseMap.put(abbr,"ED");
        }
        //For English language institution: EL
        String[] ELCourses = {"ELIC","ELID","ELIG","ELIL","ELIO","ELIR","ELIV"};
        for (String abbr: ELCourses){
            this.courseMap.put(abbr,"EL");
        }
        //For government: GB
        String[] GBCourses = {"BUSH","INTA","PSAA"};
        for (String abbr: GBCourses){
            this.courseMap.put(abbr,"GB");
        }
        //For Medicine : MD
        String[] MDCourses = {"EDHP"};
        for (String abbr: MDCourses){
            this.courseMap.put(abbr,"MD");
        }
        //For military science: MS
        String[] MSCourses = {"AERS","SOMS","MLSC","NVSC"};
        for (String abbr: MSCourses){
            this.courseMap.put(abbr,"MS");
        }
        //For veterinary medicine: VM
        String[] VMCourses = {"BIMS","VMID","VIBS","VLCS","VTPP","VSCS","VPAR","VTMI","VTPB"};
        for (String abbr: VMCourses){
            this.courseMap.put(abbr,"VM");
        }
        this.parserMap = new HashMap<>();
    }

    /**
     *
     * @param course course string such as "CSCE-121", "ENGL104"
     * @param n Number of years
     * @return a List of URL as String
     * @throws Exception if input is not correctly formatted or Number of years is not correct
     * or input is not supported
     */
    public List<String> makeURL(String course,int n) throws Exception{
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (n <=0 || n > year - 2010)
            throw new Exception("Bad number of years: " + n + ". Years must be within (0,currentYear - 2010]");
        course = course.toUpperCase();
        course = course.replace(' ', '-');
        Pattern p = Pattern.compile("([A-Z]{4})(-?)(\\d{3})");
        Matcher m = p.matcher(course);
        if (!m.matches())
            throw new Exception("Bad input: "+ course);
        String abbr = this.courseMap.get(course.substring(0,4));
        if (abbr == null)
            throw new Exception(course + " is not supported");
        List<String> urlList = new ArrayList<>();
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int[] semesters = {1,3}; // 1 is spring 3 is fall 2 is summer
        //http://web-as.tamu.edu/gradereport/PDFReports/20143/grd20143EN.pdf;
        if (month < 3){ // last spring record is available
            urlList.add("http://web-as.tamu.edu/gradereport/PDFReports/" + (year-1) + 1 +"/grd" + (year-1)
                    + 1 + abbr + ".pdf");
        }
        else if (month >= 3 && month < 7){ // last year record available
            for (int j : semesters)
                urlList.add("http://web-as.tamu.edu/gradereport/PDFReports/" + (year-1) +j +"/grd" + (year-1)
                    + j + abbr + ".pdf");
        }else if (month >= 7 ){ // this spring and last year record available
            urlList.add("http://web-as.tamu.edu/gradereport/PDFReports/" + year + 1 +"/grd" + year
                    + 1 + abbr + ".pdf");
            for (int j : semesters)
                urlList.add("http://web-as.tamu.edu/gradereport/PDFReports/" + (year-1) +j +"/grd" + (year-1)
                        + j + abbr + ".pdf");
        }
        for (int i = 2; i < n+1; ++i){    // get data from last two years
            for (int j : semesters)
                urlList.add("http://web-as.tamu.edu/gradereport/PDFReports/" + (year-i) +j +"/grd" + (year-i)
                            + j + abbr + ".pdf");
        }
        return urlList;
    }

    /**
     *
     * @param course course string such as "CSCE-121", "ENGR111"
     * @param years number of year in integer, must be within (0,currentYear - 2010]
     * @param compress reduce duplicate course with the same professor
     * @return  a List of courses
     * @throws Exception if inputs are not correctly formatted
     */
    public List<Course> getCourse(String course, int years,boolean compress) throws Exception{
        String courseUpper = course.toUpperCase();
        List<String> urlList = this.makeURL(courseUpper,years);
        List<Course> courseList = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();
        boolean exceptionOccurred = false;
        for (String url: urlList){
            threadList.add(new Thread(() -> {
                    try {
                        String filename = url.substring(url.lastIndexOf("/") + 1);
                        if(parserMap.containsKey(filename))

                        { // get from the cache
                            GradeParser parser = parserMap.get(filename);
                            courseList.addAll(parser.getCourse(courseUpper));
                        }

                        else

                        {// get from url or local files
                            DataGetter getter = new DataGetter(url);
                            GradeParser parser = new GradeParser(getter.getData());
                            courseList.addAll(parser.getCourse(courseUpper));
                            parserMap.put(filename, parser);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
            }));
        }
        for (Thread t: threadList)
            t.start();
        for (Thread t: threadList)
            t.join();
        if (compress)
            return GradeAnalyzer.compress(courseList);
        else
            return courseList;
    }


}
