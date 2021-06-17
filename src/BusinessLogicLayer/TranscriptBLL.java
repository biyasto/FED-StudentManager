package BusinessLogicLayer;

import DataAccessLayer.TranscriptDAL;
import DataTransferObject.TranscriptDTO;

import java.util.List;

public class TranscriptBLL {
    private TranscriptDAL dal = new TranscriptDAL();

    public List<TranscriptDTO> GetTranscriptOfClass(String classID, String studentID) {
        return dal.GetTranscriptOfClass(classID, studentID);
    }
}
