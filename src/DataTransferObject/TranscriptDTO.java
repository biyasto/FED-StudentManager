package DataTransferObject;

public class TranscriptDTO {
    private String TranscriptId;
    private String SubjectId;
    private double Marks;
    private int Flag;

    public TranscriptDTO(String transcriptId, String subjectId, double marks, int flag) {
        TranscriptId = transcriptId;
        SubjectId = subjectId;
        Marks = marks;
        Flag = flag;
    }

    public TranscriptDTO() {
    }

    public String getTranscriptId() {
        return TranscriptId;
    }

    public void setTranscriptId(String transcriptId) {
        TranscriptId = transcriptId;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public double getMarks() {
        return Marks;
    }

    public void setMarks(double marks) {
        Marks = marks;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
    }
}
