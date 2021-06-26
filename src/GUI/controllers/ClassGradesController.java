package GUI.controllers;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.items.ClassGradeItemController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassGradesController {

    @FXML
    private AnchorPane classPane;

    @FXML
    private Label SubjectName;

    @FXML
    private Label ClassID;

    @FXML
    private Label teacherName;

    @FXML
    private Label attendantNum;

    @FXML
    private Label facultyName;

    @FXML
    private Button btnAddStudent;

    @FXML
    private Button btnExport;

    @FXML
    private VBox studentGrades;

    private List<StudentDTO> studentList = null;
    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;
    private final static PdfPTable markTable = new PdfPTable(7);

    private final StackPane container = NavigationController.containerNav;

    public void setData(List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        this.studentList = studentList;
        this.teacher = teacher;
        this.subject = subject;
        this.subjectClass = subjectClass;

        ClassID.setText(subjectClass.getClassId());
        SubjectName.setText(subject.getSubjectName());
        facultyName.setText("Faculty: " + subject.getFaculty());
        teacherName.setText("Teacher: " + teacher.getName());
        attendantNum.setText("Attendants: " + studentList.size());

        showClass();
    }

    void showClass() {
        for (StudentDTO student : studentList) {
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

            try {
                URL urlLayout = new File("src/GUI/resources/items/ClassGradeItem.fxml").toURI().toURL();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(urlLayout);
                Node item = fxmlLoader.load();

                ClassGradeItemController classGradeItemController = fxmlLoader.getController();
                classGradeItemController.setData(student, transcriptOfOneStudent);

                studentGrades.getChildren().addAll(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void inputGrades(MouseEvent event) {
        try {
            URL urlLayout = new File("src/GUI/resources/InputGrades.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            InputGradesController inputGradesController = fxmlLoader.getController();
            inputGradesController.setData(studentList, teacher, subject, subjectClass);

            container.getChildren().addAll(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addStudents(MouseEvent event) throws IOException {
        //open add students form
        URL urlLayout = new File("src/GUI/resources/AddStudent.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        AddStudentController addStudentController = fxmlLoader.getController();
        addStudentController.setData(studentList, teacher, subject, subjectClass);

        container.getChildren().add(item);
    }

    public String selectSaveFilePath() {
        Stage stage = (Stage) btnExport.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("ClassScoreBroad");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf", "*.pdf"));
        File filePath = fileChooser.showSaveDialog(stage);
        return filePath.getAbsolutePath();
    }

    public List<Font> createFonts() throws DocumentException, IOException {
        List<Font> fontList = new ArrayList<>();
        File fileFonTitle = new File("src/Fonts/vuArialBold.ttf");
        BaseFont bfTitle = BaseFont.createFont(fileFonTitle.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontTitle1 = new Font(bfTitle, 16);
        fontTitle1.setColor(BaseColor.BLUE);
        fontList.add(fontTitle1);

        Font fontTitle2 = new Font(bfTitle, 13);
        fontTitle2.setColor(BaseColor.BLUE);
        fontList.add(fontTitle2);

        Font fontTitle3 = new Font(bfTitle, 13);
        fontList.add(fontTitle3);
        Font fontTitle4 = new Font(bfTitle, 10);
        fontList.add(fontTitle4);

        File fileFontContent = new File("src/Fonts/vuArial.ttf");
        BaseFont bfContent = BaseFont.createFont(fileFontContent.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontContent1 = new Font(bfContent, 13);
        fontList.add(fontContent1);

        Font fontContent2 = new Font(bfContent, 10);
        fontList.add(fontContent2);

        return fontList;
    }

    public void createHeaderTable() throws DocumentException, IOException {
        List<Font> listFont = createFonts();
        markTable.setWidthPercentage(100);
        markTable.setSpacingAfter(10);
        markTable.setSpacingBefore(10);

        float[] table_colW = {100, 180, 75, 70, 70, 70, 70};
        markTable.setWidths(table_colW);

        PdfPCell cellClassId = new PdfPCell(new Paragraph("Student id", listFont.get(3)));
        cellClassId.setBorderColor(BaseColor.BLACK);
        cellClassId.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellClassId.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellClassId.setMinimumHeight(30);
        markTable.addCell(cellClassId);

        PdfPCell cellSubjectName = new PdfPCell(new Paragraph("Student name", listFont.get(3)));
        cellSubjectName.setBorderColor(BaseColor.BLACK);
        cellSubjectName.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSubjectName.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellSubjectName.setMinimumHeight(30);
        markTable.addCell(cellSubjectName);

        PdfPCell cellAttendance = new PdfPCell(new Paragraph("Attendance", listFont.get(3)));
        cellAttendance.setBorderColor(BaseColor.BLACK);
        cellAttendance.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellAttendance.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellAttendance.setMinimumHeight(30);
        markTable.addCell(cellAttendance);

        PdfPCell cellPractice = new PdfPCell(new Paragraph("Practice", listFont.get(3)));
        cellPractice.setBorderColor(BaseColor.BLACK);
        cellPractice.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellPractice.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellPractice.setMinimumHeight(30);
        markTable.addCell(cellPractice);

        PdfPCell cellMidterm = new PdfPCell(new Paragraph("Midterm", listFont.get(3)));
        cellMidterm.setBorderColor(BaseColor.BLACK);
        cellMidterm.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellMidterm.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellMidterm.setMinimumHeight(30);
        markTable.addCell(cellMidterm);

        PdfPCell cellFinal = new PdfPCell(new Paragraph("Final", listFont.get(3)));
        cellFinal.setBorderColor(BaseColor.BLACK);
        cellFinal.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellFinal.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellFinal.setMinimumHeight(30);
        markTable.addCell(cellFinal);

        PdfPCell cellAverage = new PdfPCell(new Paragraph("Average", listFont.get(3)));
        cellAverage.setBorderColor(BaseColor.BLACK);
        cellAverage.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellAverage.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellAverage.setMinimumHeight(30);
        markTable.addCell(cellAverage);

    }

    public void createHeaderPDF(Document document) throws DocumentException, IOException {

        //set logo icon
        Image logo = Image.getInstance("src/Icon/icon.png");
        logo.setAbsolutePosition(80, 750);
        logo.scaleAbsolute(50, 50);
        document.add(logo);

        //Title header
        List<Font> listFont = createFonts();
        Paragraph prgSchoolName = new Paragraph("UNIVERSITY OF INFORMATION TECHNOLOGY", listFont.get(1));
        prgSchoolName.setIndentationLeft(100);
        document.add(prgSchoolName);

        Paragraph prgAddress = new Paragraph("Thiên Hà Trong Vũ Trụ Này Hoài Xa Xôi", listFont.get(2));
        prgAddress.setIndentationLeft(100);
        document.add(prgAddress);

        Paragraph prgSoDT = new Paragraph("CONTACT: 99999999999", listFont.get(2));
        prgSoDT.setIndentationLeft(100);
        document.add(prgSoDT);

        Paragraph prgTitle = new Paragraph("CLASS SCOREBOARD", listFont.get(0));
        prgTitle.setAlignment(Element.ALIGN_CENTER);
        prgTitle.setSpacingBefore(10);
        prgTitle.setSpacingAfter(10);
        document.add(prgTitle);
    }

    public void addRow(String studentId, String studentName, double m1, double m2, double m3, double m4, double avg) throws DocumentException, IOException {
        List<Font> listFont = createFonts();
        PdfPCell cellStudentIdI = new PdfPCell(new Paragraph(studentId, listFont.get(5)));
        cellStudentIdI.setBorderColor(BaseColor.BLACK);
        cellStudentIdI.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellStudentIdI.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellStudentIdI);

        PdfPCell cellStudentNameI = new PdfPCell(new Paragraph(studentName, listFont.get(5)));
        cellStudentNameI.setBorderColor(BaseColor.BLACK);
        cellStudentNameI.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellStudentNameI.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellStudentNameI);

        PdfPCell cellMark1 = new PdfPCell(new Paragraph(String.valueOf(m1), listFont.get(5)));
        cellMark1.setBorderColor(BaseColor.BLACK);
        cellMark1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellMark1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellMark1);

        PdfPCell cellMark2 = new PdfPCell(new Paragraph(String.valueOf(m2), listFont.get(5)));
        cellMark2.setBorderColor(BaseColor.BLACK);
        cellMark2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellMark2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellMark2);

        PdfPCell cellMark3 = new PdfPCell(new Paragraph(String.valueOf(m3), listFont.get(5)));
        cellMark3.setBorderColor(BaseColor.BLACK);
        cellMark3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellMark3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellMark3);

        PdfPCell cellMark4 = new PdfPCell(new Paragraph(String.valueOf(m4), listFont.get(5)));
        cellMark4.setBorderColor(BaseColor.BLACK);
        cellMark4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellMark4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellMark4);

        PdfPCell cellAverageI = new PdfPCell(new Paragraph("10", listFont.get(5)));
        cellAverageI.setBorderColor(BaseColor.BLACK);
        cellAverageI.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellAverageI.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellAverageI);
    }
    @FXML
    void export(ActionEvent event) throws DocumentException, IOException {
        String filePath = selectSaveFilePath();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        List<Font> listFont = createFonts();

        //Head file pdf
        createHeaderPDF(document);


        //Information class
        Paragraph prgID = new Paragraph("ClassId: " + subjectClass.getClassId(), listFont.get(4));
        document.add(prgID);
        Paragraph prgTeacherName = new Paragraph("Teacher: " + teacher.getName(), listFont.get(4));
        document.add(prgTeacherName);
        int year = subjectClass.getSchoolYear() + 1;
        Paragraph prgSchoolYear = new Paragraph("School year: " +subjectClass.getSchoolYear() +"-"+String.valueOf(year) , listFont.get(4));
        document.add(prgSchoolYear);
        Paragraph prgSemester = new Paragraph("Semester: " +subjectClass.getSemester() , listFont.get(4));
        prgSemester.setSpacingAfter(10);
        document.add(prgSemester);

        //Header table
        createHeaderTable();

        studentList.forEach(student ->{
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());
            try {
                addRow(student.getId(),
                        student.getName(),
                        transcriptOfOneStudent.getMark1(),
                        transcriptOfOneStudent.getMark2(),
                        transcriptOfOneStudent.getMark3(),
                        transcriptOfOneStudent.getMark4(),
                        10
                        );
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        document.add(markTable);
        document.close();
        writer.close();

    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(classPane);
    }
}
