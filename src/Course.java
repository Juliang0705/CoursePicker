/**
 * Created by Juliang on 1/5/16.
 * A class represents courses
 */
public class Course {
    private String instructor, course, section;
    private int A,B,C,D,F,Q;

    /**
     *
     * @param courseName a string such as "CSCE", "ENGR"
     * @param section 3 digits number such as 121, 221
     * @param instructor a string for the name
     * @param A letter grade
     * @param B letter grade
     * @param C letter grade
     * @param D letter grade
     * @param F letter grade
     * @param Q letter grade
     */
    public Course(String courseName, String section, String instructor,int A, int B, int C, int D, int F, int Q){
        this.instructor = instructor;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.F = F;
        this.Q = Q;
        this.course = courseName;
        this.section = section;
    }

    /**
     *
     * @return the total number of student enrolled in this section
     */
    public int total(){
        return A + B + C + D + F + Q;
    }

    /**
     *
     * @return instructor name
     */
    public String getInstructor(){
        return this.instructor;
    }

    /**
     *
     * @return course name
     */
    public String getCourse(){
        return this.course;
    }

    /**
     *
     * @return section number as a string
     */
    public String getSection(){
        return this.section;
    }
    public int getA(){
        return A;
    }
    public int getB(){
        return B;
    }
    public int getC(){
        return C;
    }
    public int getD(){
        return D;
    }
    public int getF(){
        return F;
    }
    public int getQ(){
        return Q;
    }
    @Override
    public String toString(){
        return course + "-" + section + ": " + instructor + "\n" +
                "A: " + A + " B: " + B + " C: " + C + " D: " + D +
                " F: " + F + " Q: " + Q;
    }

    /**
     *
     * @param other another Course object
     * @throws Exception if instructor or and course name are not the same
     */
    public void add(Course other) throws Exception{
        if (! this.course.equals(other.course))
            throw new Exception(this.course + " is not the same as " + other.course);
        else if (! this.instructor.equals(other.instructor))
            throw new Exception(this.instructor + " is not the same as " + other.instructor);
        this.section = "Multiple";
        this.A += other.A;
        this.B += other.B;
        this.C += other.C;
        this.D += other.D;
        this.F += other.F;
        this.Q += other.Q;
    }
}
