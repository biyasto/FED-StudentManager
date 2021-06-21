package Model;

public class StudentGrade {
    String studentId;
    String name;
    int transcriptId;
    Double mark1;
    Double mark2;
    Double mark3;
    Double mark4;

    public StudentGrade() {
    }

    public StudentGrade(String studentId, String name, int transcriptId, Double mark1, Double mark2, Double mark3, Double mark4) {
        this.studentId = studentId;
        this.name = name;
        this.transcriptId = transcriptId;
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.mark4 = mark4;
    }

    public int getTranscriptId() {
        return transcriptId;
    }

    public void setTranscriptId(int transcriptId) {
        this.transcriptId = transcriptId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMark1() {
        return mark1;
    }

    public void setMark1(Double mark1) {
        this.mark1 = mark1;
    }

    public Double getMark2() {
        return mark2;
    }

    public void setMark2(Double mark2) {
        this.mark2 = mark2;
    }

    public Double getMark3() {
        return mark3;
    }

    public void setMark3(Double mark3) {
        this.mark3 = mark3;
    }

    public Double getMark4() {
        return mark4;
    }

    public void setMark4(Double mark4) {
        this.mark4 = mark4;
    }
}
