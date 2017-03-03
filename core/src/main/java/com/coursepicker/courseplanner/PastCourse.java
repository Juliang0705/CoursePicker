package com.coursepicker.courseplanner;

/**
 * Created by Juliang on 1/5/16.
 * A class represents courses
 */
public class PastCourse {
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
    public PastCourse(String courseName, String section, String instructor, int A, int B, int C, int D, int F, int Q){
        this.instructor = instructor;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.F = F;
        this.Q = Q;
        this.course = courseName;
        this.section = section;
        if (isHonor()) {
            this.section += "(Honor)";
            this.instructor += "(Honor)";
        }
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
    public float getAPercentage() { return (float)A/total();}

    public int getB(){
        return B;
    }
    public float getBPercentage() { return (float)B/total();}

    public int getC(){
        return C;
    }
    public float getCPercentage() { return (float)C/total();}

    public int getD(){
        return D;
    }
    public float getDPercentage() { return (float)D/total();}

    public int getF(){
        return F;
    }
    public float getFPercentage() { return (float)F/total();}

    public int getQ(){
        return Q;
    }
    public float getQPercentage() { return (float)Q/total();}

    @Override
    public String toString(){
        return course + "-" + section + ": " + instructor + "\n" +
                "A:" + A + "  B:" + B + "  C:" + C + "  D:" + D +
                "  F:" + F + "  Q:" + Q + "  Average: " + getAverage() + "  With Q-Drop: " + getAverageWithQdrop();
    }

    /**
     *
     * @param other another CoursePicker.PastCourse object
     * @throws Exception if instructor or and course name are not the same
     */
    public void add(PastCourse other) throws Exception{
        if (! this.course.equals(other.course))
            throw new Exception(this.course + " is not the same as " + other.course);
        else if (! this.instructor.equals(other.instructor))
            throw new Exception(this.instructor + " is not the same as " + other.instructor);
        else if (this.isHonor() != other.isHonor())
            throw new Exception(this.section + " is not the same kind as " + other.section);
        if (this.isHonor())
            this.section = "Multiple(Honor)";
        else
            this.section = "Multiple";
        this.A += other.A;
        this.B += other.B;
        this.C += other.C;
        this.D += other.D;
        this.F += other.F;
        this.Q += other.Q;
    }

    /**
     * calculate the average GPA in this course excluding Q-drop
     * A = 4.0 B = 3.0 C = 2.0 D = 1.0 F = 0.0
     * @return the average GPA excluding Q-drop
     */
    public float getAverage(){
        return (A * 4 + B * 3 + C * 2 + D * 1 + F * 0) / (float)(total() - Q);
    }

    /**
     * calculate the average GPA in this course including Q-drop
     * A = 4.0 B = 3.0 C = 2.0 D = 1.0 F = 0.0 Q = 1.5
     * @return the average GPA including Q-drop
     */
    public float getAverageWithQdrop(){
        return (float)(A * 4 + B * 3 + C * 2 + D * 1 + F * 0 + Q * 1.5) / total();
    }

    /**
     *
     * @return true if this course is honor class
     */
    public boolean isHonor(){
        return this.section.charAt(0) == '2' || this.section.endsWith("(Honor)");
    }
}
