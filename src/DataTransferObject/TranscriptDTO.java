package DataTransferObject;

public class TranscriptDTO {
    private int TranscriptId;
    private double Mark1;
    private double Mark2;
    private double Mark3;
    private double Mark4;

    public TranscriptDTO() {
    }

    public TranscriptDTO(int transcriptId, double mark1, double mark2, double mark3, double mark4) {
        TranscriptId = transcriptId;
        Mark1 = mark1;
        Mark2 = mark2;
        Mark3 = mark3;
        Mark4 = mark4;
    }

    public Double getTotalScore(){
        return this.Mark1 + this.Mark2 + this.Mark3 + this.Mark4;
    }

    public int getTranscriptId() {
        return TranscriptId;
    }

    public void setTranscriptId(int transcriptId) {
        TranscriptId = transcriptId;
    }

    public double getMark1() {
        return Mark1;
    }

    public void setMark1(double mark1) {
        Mark1 = mark1;
    }

    public double getMark2() {
        return Mark2;
    }

    public void setMark2(double mark2) {
        Mark2 = mark2;
    }

    public double getMark3() {
        return Mark3;
    }

    public void setMark3(double mark3) {
        Mark3 = mark3;
    }

    public double getMark4() {
        return Mark4;
    }

    public void setMark4(double mark4) {
        Mark4 = mark4;
    }
}
