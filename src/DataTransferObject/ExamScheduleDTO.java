package DataTransferObject;

import java.sql.Date;
import java.sql.Time;

public class ExamScheduleDTO {
    private String classId;
    private java.sql.Date examDate;
    private int flag;
    private int shift;
    private Time totalTime;

    public ExamScheduleDTO() {
    }

    public ExamScheduleDTO(String classId, Date examDate, int flag, int shift, Time totalTime) {
        this.classId = classId;
        this.examDate = examDate;
        this.flag = flag;
        this.shift = shift;
        this.totalTime = totalTime;
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

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
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

    public Time getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Time totalTime) {
        this.totalTime = totalTime;
    }
}
