package DataTransferObject;

import java.sql.Date;
import java.sql.Time;

public class ExamScheduleDTO {
    private int id;
    private String subjectId;
    private int schoolYear;
    private int semester;
    private int flag;
    private int shift;
    private java.sql.Date examDate;
    private Time totalTime;

    public ExamScheduleDTO() {
        this.id = -1;
    }

    public ExamScheduleDTO(String subjectId, int schoolYear, int semester, int flag, int shift, Date examDate, Time totalTime) {
        this.id = -1;
        this.subjectId = subjectId;
        this.schoolYear = schoolYear;
        this.semester = semester;
        this.flag = flag;
        this.shift = shift;
        this.examDate = examDate;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Time getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Time totalTime) {
        this.totalTime = totalTime;
    }
}
