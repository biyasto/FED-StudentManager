package DataTransferObject;

public class StudentCLassDTO {
    private String StudentId;
    private String ClassId;
    private String TranScriptId;

    public StudentCLassDTO(String studentId, String classId, String tranScriptId) {
        StudentId = studentId;
        ClassId = classId;
        TranScriptId = tranScriptId;
    }

    public StudentCLassDTO() {
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getTranScriptId() {
        return TranScriptId;
    }

    public void setTranScriptId(String tranScriptId) {
        TranScriptId = tranScriptId;
    }
}
