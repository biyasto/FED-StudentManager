package DataAccessLayer;

import DataTransferObject.ExamRoomDTO;
import DataTransferObject.ExamScheduleDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamRoomDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;
    private int numberOfStudentInOneRoom = 20;
    private final List<String> rooms = Arrays.asList(
            "A1.1", "A1.2", "A1.3", "A1.4", "A2.1", "A2.2", "A2.3", "A2.4",
            "B1.1", "B1.2", "B1.3", "B1.4", "B2.1", "B2.2", "B2.3", "B2.4",
            "C1.1", "C1.2", "C1.3", "C1.4", "C2.1", "C2.2", "C2.3", "C2.4",
            "E1.1", "E1.2", "E1.3", "E1.4", "E2.1", "E2.2", "E2.3", "E2.4"
    );

    public int addExamRoom(int examId, String room){
        String sql = "insert into examRoom values (?, ?)";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setInt(1, examId);
            pres.setString(2, room);
            result = pres.executeUpdate();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                pres.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    public List<String> getEmptyRoomForExam(ExamScheduleDTO examScheduleDTO){
        List<String> list = new ArrayList<>();
        String sql = " select e2.* from examschedule e1, examroom e2 where e1.id = e2.examId and e1.examDate = ? and shift = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setDate(1, examScheduleDTO.getExamDate());
            pres.setInt(2, examScheduleDTO.getShift());
            rs = pres.executeQuery();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                String s = rs.getString("room");
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                pres.close();
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return removeNotEmptyRooms(list);
    }

    private List<String> removeNotEmptyRooms(List<String> notEmptyRooms){
        List<String> newList = new ArrayList<>();
        for (String room : rooms) {
            if (!notEmptyRooms.contains(room)){
                newList.add(room);
            }
        }
        return newList;
    }

}
