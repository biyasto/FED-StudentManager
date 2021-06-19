package DataTransferObject;

import java.sql.Date;
import java.sql.Time;

public class ExamScheduleDTO {
    private String classId;
    private java.sql.Date examDate;
    private int flag;
    private Time timeStart;
    private Time timeEnd;

    public ExamScheduleDTO() {
    }

    public ExamScheduleDTO(String classId, java.sql.Date examDate, int flag, Time timeStart, Time timeEnd) {
        this.classId = classId;
        this.examDate = examDate;
        this.flag = flag;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(java.sql.Date examDate) {
        this.examDate = examDate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }
}
