package DataTransferObject;

public class TeacherDTO {
    private String Id;
    private String Name;
    private boolean Gender;
    private String BirthDay;
    private String Email;
    private String Pass;
    private int Type;
    private String Faculty;

    public TeacherDTO(String id, String name, boolean gender, String birthDay, String email,String pass, int type, String faculty) {
        Id = id;
        Name = name;
        Gender = gender;
        BirthDay = birthDay;
        Email = email;
        Pass = pass;
        Type = type;
        Faculty = faculty;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public TeacherDTO() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }
}
