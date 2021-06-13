package DataTransferObject;

public class SubjectDTO {
    private String SubjectID;
    private String SubjectName;
    private int Credits;
    private String Faculty;

    public SubjectDTO(String subjectID, String subjectName, int credits, String faculty) {
        SubjectID = subjectID;
        SubjectName = subjectName;
        Credits = credits;
        Faculty = faculty;
    }

    public SubjectDTO() {
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public int getCredits() {
        return Credits;
    }

    public void setCredits(int credits) {
        Credits = credits;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }
}
