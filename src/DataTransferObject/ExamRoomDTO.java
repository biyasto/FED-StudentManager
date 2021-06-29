package DataTransferObject;

public class ExamRoomDTO {
    private int examId;
    private String room;
    private String classId;

    public ExamRoomDTO() {
    }

    public ExamRoomDTO(int examId, String room, String classId) {
        this.examId = examId;
        this.room = room;
        this.classId = classId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
