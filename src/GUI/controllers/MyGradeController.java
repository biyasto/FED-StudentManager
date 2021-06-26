package GUI.controllers;

import BusinessLogicLayer.StudentClassBLL;
import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.items.StudentGradeItemController;
import Utils.DatabaseUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyGradeController implements Initializable {

    @FXML
    private AnchorPane inforPane;

    @FXML
    private AnchorPane panelGradeStatistic;

    @FXML
    private VBox gradeScrollPane;

    @FXML
    private ChoiceBox<String> SemesterChoiceBox;

    @FXML
    private ChoiceBox<String> SchoolYearChoiceBox;

    @FXML
    private Label lblSemesterGPA;

    @FXML
    private AnchorPane InfoBox;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private ImageView UserAvatar;

    @FXML
    private Label IDLabel;

    @FXML
    private Label lblCredit;

    @FXML
    private Label lblGPA;

    @FXML
    private Button RefeshButton;

    @FXML
    private Button btnPrintPDF;

    private StudentDTO studentUser = NavigationController.studentUser;
    private final static PdfPTable markTable = new PdfPTable(7);
    private List<StudentCLassDTO> studentCLassList = new ArrayList<>();
    private final List<String> schoolYearList = new ArrayList<>();
    private final List<String> semesterList = new ArrayList<>();

    private String yearFilter = "All";
    private String semesterFilter = "All";
    private final DecimalFormat df = new DecimalFormat("##.#");
    private int credit = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //load data for label
        NameLabel.setText(studentUser.getName());
        IDLabel.setText(studentUser.getId());
        FacultyLabel.setText(studentUser.getFaculty());
        EmailLabel.setText(studentUser.getEmail());
        PositionLabel.setText("Student");

        //load grade statistic
        loadGradeFirstTime(studentUser);

        //init combobox's data
        ObservableList<String> dataSchoolYear = FXCollections.observableArrayList();
        dataSchoolYear.setAll(schoolYearList);
        SchoolYearChoiceBox.setItems(dataSchoolYear);
        SchoolYearChoiceBox.setValue("All");          //set default value
        SchoolYearChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            yearFilter = newValue;
            loadGradeFilter();
        });

        ObservableList<String> dataSemester = FXCollections.observableArrayList();
        dataSemester.setAll(semesterList);
        SemesterChoiceBox.setItems(dataSemester);
        SemesterChoiceBox.setValue("All");          //set default value
        SemesterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            semesterFilter = newValue;
            loadGradeFilter();
        });
    }

    void loadGradeFirstTime(StudentDTO student) {
        schoolYearList.add("All");
        semesterList.add("All");

        double sumGPA = 0.0;
        int num = 0;
        credit = 0;

        StudentClassBLL studentClassBLL = new StudentClassBLL();
        studentCLassList = studentClassBLL.getAllClassOfStudent(student.getId());

        if (!studentCLassList.isEmpty()) {
            for (StudentCLassDTO studentCLass : studentCLassList) {
                try {
                    SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
                    SubjectBLL subjectBLL = new SubjectBLL();
                    TranscriptBLL transcriptBLL = new TranscriptBLL();

                    //get classId and className to set data for grade item
                    SubjectClassDTO subjectClass = subjectClassBLL.getClassById(studentCLass.getClassId());
                    SubjectDTO subject = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
                    TranscriptDTO transcriptOfStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

                    if (!schoolYearList.contains(String.valueOf(subjectClass.getSchoolYear())))
                        schoolYearList.add(String.valueOf(subjectClass.getSchoolYear()));

                    if (!semesterList.contains(String.valueOf(subjectClass.getSemester())))
                        semesterList.add(String.valueOf(subjectClass.getSemester()));

                    sumGPA += calculateAvg(transcriptOfStudent);
                    num++;
                    credit += subject.getCredits();

                    bindData(subjectClass, subject, transcriptOfStudent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            lblCredit.setText("Accumulated credits: " + credit);
            if (num != 0) {
                double semesterGPA = sumGPA / num;
                lblGPA.setText("GPA: " + df.format(semesterGPA));
            }
        }
    }

    void loadGradeFilter() {
        double sumGPA = 0.0;
        int num = 0;
        credit = 0;
        gradeScrollPane.getChildren().clear();
        if (!studentCLassList.isEmpty()) {
            for (StudentCLassDTO studentCLass : studentCLassList) {
                try {
                    SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
                    SubjectBLL subjectBLL = new SubjectBLL();
                    TranscriptBLL transcriptBLL = new TranscriptBLL();

                    //get classId and className to set data for grade item
                    SubjectClassDTO subjectClass = subjectClassBLL.getClassById(studentCLass.getClassId());
                    SubjectDTO subject = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
                    TranscriptDTO transcriptOfStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), studentUser.getId());

                    if (yearFilter.equals("All") && semesterFilter.equals("All")) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                        credit += subject.getCredits();
                    } else if (yearFilter.equals("All") && String.valueOf(subjectClass.getSemester()).equals(semesterFilter)) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                        sumGPA += calculateAvg(transcriptOfStudent);
                        credit += subject.getCredits();
                        num++;
                    } else if (semesterFilter.equals("All") && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                        sumGPA += calculateAvg(transcriptOfStudent);
                        credit += subject.getCredits();
                        num++;
                    } else {
                        if (String.valueOf(subjectClass.getSemester()).equals(semesterFilter)
                                && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                            bindData(subjectClass, subject, transcriptOfStudent);
                            sumGPA += calculateAvg(transcriptOfStudent);
                            credit += subject.getCredits();
                            num++;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (num != 0) {
                double semesterGPA = sumGPA / num;
                lblSemesterGPA.setText("semester GPA: " + df.format(semesterGPA));
                lblCredit.setText("Accumulated credits: " + credit);
                lblGPA.setText("GPA: " + df.format(semesterGPA));
            }

        }
    }

    double calculateAvg(TranscriptDTO transcript) {
        return transcript.getMark1() * 0.1
                + transcript.getMark2() * 0.2
                + transcript.getMark3() * 0.2
                + transcript.getMark4() * 0.5;
    }

    void bindData(SubjectClassDTO subjectClass, SubjectDTO subject, TranscriptDTO transcriptOfStudent) throws IOException {
        URL urlLayout = new File("src/GUI/resources/items/StudentGradeItem.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        StudentGradeItemController studentGradeItemController = fxmlLoader.getController();
        studentGradeItemController.setData(subjectClass, subject, transcriptOfStudent);

        gradeScrollPane.getChildren().addAll(item);
    }

    public String selectSaveFilePath() {
        Stage stage = (Stage) btnPrintPDF.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("PersonalScoreBroad_" + studentUser.getId());
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

        float[] table_colW = {100, 200, 70, 50, 50, 50, 50};
        markTable.setWidths(table_colW);

        PdfPCell cellClassId = new PdfPCell(new Paragraph("Class id", listFont.get(3)));
        cellClassId.setBorderColor(BaseColor.BLACK);
        cellClassId.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellClassId.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cellClassId.setMinimumHeight(30);
        markTable.addCell(cellClassId);

        PdfPCell cellSubjectName = new PdfPCell(new Paragraph("Subject name", listFont.get(3)));
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

        Paragraph prgAddress = new Paragraph("THIÊN HÀ TRONG VŨ TRỤ NÀY HOÀI XA XÔI", listFont.get(2));
        prgAddress.setIndentationLeft(100);
        document.add(prgAddress);

        Paragraph prgSoDT = new Paragraph("CONTACT: 99999999999", listFont.get(2));
        prgSoDT.setIndentationLeft(100);
        document.add(prgSoDT);

        Paragraph prgTitle = new Paragraph("PERSONAL SCOREBOARD", listFont.get(0));
        prgTitle.setAlignment(Element.ALIGN_CENTER);
        prgTitle.setSpacingBefore(10);
        prgTitle.setSpacingAfter(10);
        document.add(prgTitle);
    }

    public void addRow(String classId, String subjectName, double m1, double m2, double m3, double m4, double avg) throws DocumentException, IOException {
        List<Font> listFont = createFonts();
        PdfPCell cellClassIdI = new PdfPCell(new Paragraph(classId, listFont.get(5)));
        cellClassIdI.setBorderColor(BaseColor.BLACK);
        cellClassIdI.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellClassIdI.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellClassIdI);

        PdfPCell cellSubjectNameI = new PdfPCell(new Paragraph(subjectName, listFont.get(5)));
        cellSubjectNameI.setBorderColor(BaseColor.BLACK);
        cellSubjectNameI.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellSubjectNameI.setVerticalAlignment(Element.ALIGN_MIDDLE);
        markTable.addCell(cellSubjectNameI);

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
    void printPDF(ActionEvent event) throws IOException, DocumentException, SQLException {
        //get save file path
        String filePath = selectSaveFilePath();
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        List<Font> listFont = createFonts();

        //Header file PDF
        createHeaderPDF(document);

        //Student information
        Paragraph prgName = new Paragraph("Name: " + studentUser.getName(), listFont.get(4));
        document.add(prgName);
        Paragraph prgId = new Paragraph("Id: " + studentUser.getId(), listFont.get(4));

        document.add(prgId);
        if (!yearFilter.equals("All")){
            int year = Integer.parseInt(yearFilter) + 1;
            Paragraph prgSchoolYear = new Paragraph("School year: " + yearFilter + "-" + String.valueOf(year), listFont.get(4));
            document.add(prgSchoolYear);
        }


        Paragraph prgCredit = new Paragraph("Accumulated credits: " + credit, listFont.get(4));
        document.add(prgCredit);

        Paragraph prgFaculty = new Paragraph("Faculty: " + studentUser.getFaculty(), listFont.get(4));
        prgFaculty.setSpacingAfter(10);
        document.add(prgFaculty);

        //Header table
        createHeaderTable();


        //add row data to table
        if (!studentCLassList.isEmpty()) {
            for (StudentCLassDTO studentCLass : studentCLassList) {
                try {
                    SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
                    SubjectBLL subjectBLL = new SubjectBLL();
                    TranscriptBLL transcriptBLL = new TranscriptBLL();

                    //get data
                    SubjectClassDTO subjectClass = subjectClassBLL.getClassById(studentCLass.getClassId());
                    SubjectDTO subject = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
                    TranscriptDTO transcriptOfStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), studentUser.getId());

                    if (yearFilter.equals("All") && semesterFilter.equals("All")) {
                        addRow(studentCLass.getClassId(),
                                subject.getSubjectName(),
                                transcriptOfStudent.getMark1(),
                                transcriptOfStudent.getMark2(),
                                transcriptOfStudent.getMark3(),
                                transcriptOfStudent.getMark4(),
                                10 // Nữa tạo thêm thuộc tính điểm trung bình
                        );
                    } else if (yearFilter.equals("All") && String.valueOf(subjectClass.getSemester()).equals(semesterFilter)) {
                        addRow(studentCLass.getClassId(),
                                subject.getSubjectName(),
                                transcriptOfStudent.getMark1(),
                                transcriptOfStudent.getMark2(),
                                transcriptOfStudent.getMark3(),
                                transcriptOfStudent.getMark4(),
                                10 // Nữa tạo thêm thuộc tính điểm trung bình
                        );

                    } else if (semesterFilter.equals("All") && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                        addRow(studentCLass.getClassId(),
                                subject.getSubjectName(),
                                transcriptOfStudent.getMark1(),
                                transcriptOfStudent.getMark2(),
                                transcriptOfStudent.getMark3(),
                                transcriptOfStudent.getMark4(),
                                10 // Nữa tạo thêm thuộc tính điểm trung bình
                        );

                    } else {
                        if (String.valueOf(subjectClass.getSemester()).equals(semesterFilter)
                                && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                            addRow(studentCLass.getClassId(),
                                    subject.getSubjectName(),
                                    transcriptOfStudent.getMark1(),
                                    transcriptOfStudent.getMark2(),
                                    transcriptOfStudent.getMark3(),
                                    transcriptOfStudent.getMark4(),
                                    10 // Nữa tạo thêm thuộc tính điểm trung bình
                            );
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        document.add(markTable);
        document.close();
        writer.close();
    }

}
