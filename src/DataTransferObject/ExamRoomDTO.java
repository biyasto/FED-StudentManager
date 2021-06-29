package DataTransferObject;

public class ExamRoomDTO {
    private int examId;
    private String room;

    public ExamRoomDTO() {
    }

    public ExamRoomDTO(int examId, String room) {
        this.examId = examId;
        this.room = room;
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
}
