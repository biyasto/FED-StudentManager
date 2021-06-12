package DataTransferObject;

import java.util.Date;

public class ExamScheduleDTO {
    private String ClassID;
    private Date ExamDate;
    private int Flag;

    public ExamScheduleDTO(String classID, Date examDate, int flag) {
        ClassID = classID;
        ExamDate = examDate;
        Flag = flag;
    }

    public ExamScheduleDTO() {
    }

    public String getClassID() {
        return ClassID;
    }

    public void setClassID(String classID) {
        ClassID = classID;
    }

    public Date getExamDate() {
        return ExamDate;
    }

    public void setExamDate(Date examDate) {
        ExamDate = examDate;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
    }
}
