package DataTransferObject;

import java.util.Date;

public class ExamScheduleDTO {
    private String ClassId;
    private Date ExamDate;
    private int ExamType;

    public ExamScheduleDTO(String classId, Date examDate, int examType) {
        ClassId = classId;
        ExamDate = examDate;
        ExamType = examType;
    }

    public ExamScheduleDTO() {
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public Date getExamDate() {
        return ExamDate;
    }

    public void setExamDate(Date examDate) {
        ExamDate = examDate;
    }

    public int getExamType() {
        return ExamType;
    }

    public void setExamType(int examType) {
        ExamType = examType;
    }
}
