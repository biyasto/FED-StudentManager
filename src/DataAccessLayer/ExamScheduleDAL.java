package DataAccessLayer;

import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamScheduleDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public List<ExamScheduleDTO> getAllExamSchedule() {
        List<ExamScheduleDTO> list = new ArrayList<>();
        String sql = "select * from ExamSchedule";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                ExamScheduleDTO s = new ExamScheduleDTO();
                s.setSubjectId(rs.getString("subjectId"));
                s.setSchoolYear(rs.getInt("schoolYear"));
                s.setSemester(rs.getInt("semester"));
                s.setFlag(rs.getInt("flag"));
                s.setShift(rs.getInt("shift"));
                s.setExamDate(rs.getDate("examDate"));
                s.setTotalTime(rs.getTime("totalTime"));

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
        return list;
    }

    public List<ExamScheduleDTO> getAllExamScheduleByStudent(StudentDTO student) {
        List<ExamScheduleDTO> list = new ArrayList<>();
        String sql = " select e.* from subjectclass s1, studentclass s2, examschedule e where " +
                "s1.classId = s2.classId " +
                "and s2.studentId = ? " +
                "and s1.subjectId = e.subjectId " +
                "and s1.schoolYear = e.schoolYear " +
                "and s1.semester = e.semester;";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, student.getId());
            rs = pres.executeQuery();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                ExamScheduleDTO s = new ExamScheduleDTO();
                s.setSubjectId(rs.getString("subjectId"));
                s.setSchoolYear(rs.getInt("schoolYear"));
                s.setSemester(rs.getInt("semester"));
                s.setFlag(rs.getInt("flag"));
                s.setShift(rs.getInt("shift"));
                s.setExamDate(rs.getDate("examDate"));
                s.setTotalTime(rs.getTime("totalTime"));

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
        return list;
    }

    public List<ExamScheduleDTO> getAllExamScheduleByTeacher(TeacherDTO teacher) {
        List<ExamScheduleDTO> list = new ArrayList<>();
        String sql = " select e.* from subjectclass s, examschedule e  where s.headmaster = ? and s.schoolYear = e.schoolyear and s.semester = e.semester and s.subjectid = e.subjectid";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, teacher.getId());
            rs = pres.executeQuery();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                ExamScheduleDTO s = new ExamScheduleDTO();
                s.setSubjectId(rs.getString("subjectId"));
                s.setSchoolYear(rs.getInt("schoolYear"));
                s.setSemester(rs.getInt("semester"));
                s.setFlag(rs.getInt("flag"));
                s.setShift(rs.getInt("shift"));
                s.setExamDate(rs.getDate("examDate"));
                s.setTotalTime(rs.getTime("totalTime"));

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
        return list;
    }

    public List<String> getEmptyRoomForSchedule(Date date, int shift) {
        List<String> list = new ArrayList<>();
        String sql = " select e2.room from examschedule e1, examroom e2 where e1.id = e2.examId and e1.examDate = ? and shift = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            pres.setDate(1, date);
            pres.setInt(2, shift);
            rs = pres.executeQuery();

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
        return list;
    }

    public int addNewEvent(ExamScheduleDTO event) {
        String sqlInsert = "insert into examschedule (subjectId, schoolYear, semester, flag, shift, examdate, totalTime) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sqlInsert);

            pres.setString(1, event.getSubjectId());
            pres.setInt(2, event.getSchoolYear());
            pres.setInt(3, event.getSemester());
            pres.setInt(4, event.getFlag());
            pres.setInt(5, event.getShift());
            pres.setDate(6, event.getExamDate());
            pres.setTime(7, event.getTotalTime());

            result = pres.executeUpdate();
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

    public int getExamScheduleId(ExamScheduleDTO event) {
        String sqlInsert = "select id from examschedule where subjectId = ? and schoolYear = ? and semester = ? and flag = ?";
        int id = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sqlInsert);

            pres.setString(1, event.getSubjectId());
            pres.setInt(2, event.getSchoolYear());
            pres.setInt(3, event.getSemester());
            pres.setInt(4, event.getFlag());

            rs = pres.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
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
        return id;
    }

    public int deleteExamSchedule(ExamScheduleDTO event) {
        String sqlInsert = "delete from examschedule where subjectId = ? and schoolYear = ? and semester = ? and flag = ?";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sqlInsert);

            pres.setString(1, event.getSubjectId());
            pres.setInt(2, event.getSchoolYear());
            pres.setInt(3, event.getSemester());
            pres.setInt(4, event.getFlag());

            result = pres.executeUpdate();
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
}
