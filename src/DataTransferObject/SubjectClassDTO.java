package DataTransferObject;

public class SubjectClassDTO {
    private String ClassId;
    private String HeadMaster;
    private String SubjectId;
    private int SchoolYear;
    private int Semester;
    private int Attendance;
    private int Quiz;
    private int Practice;
    private int Final;


    public SubjectClassDTO(String classId, String headMaster, String subjectId, int schoolYear, int semester, int attendance, int quiz, int practice, int aFinal) {
        ClassId = classId;
        HeadMaster = headMaster;
        SubjectId = subjectId;
        SchoolYear = schoolYear;
        Semester = semester;
        Attendance = attendance;
        Quiz = quiz;
        Practice = practice;
        Final = aFinal;
    }

    public SubjectClassDTO() {
    }

    public int getAttendance() {
        return Attendance;
    }

    public void setAttendance(int attendance) {
        Attendance = attendance;
    }

    public int getQuiz() {
        return Quiz;
    }

    public void setQuiz(int quiz) {
        Quiz = quiz;
    }

    public int getPractice() {
        return Practice;
    }

    public void setPractice(int practice) {
        Practice = practice;
    }

    public int getFinal() {
        return Final;
    }

    public void setFinal(int aFinal) {
        Final = aFinal;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getHeadMaster() {
        return HeadMaster;
    }

    public void setHeadMaster(String headMaster) {
        HeadMaster = headMaster;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public int getSchoolYear() {
        return SchoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        SchoolYear = schoolYear;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int semester) {
        Semester = semester;
    }

    @Override
    public String toString() {
        return this.getClassId();
    }
}
