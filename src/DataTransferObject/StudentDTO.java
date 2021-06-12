package DataTransferObject;

public class StudentDTO {
    private String Id;
    private String Name;
    private boolean Gender;
    private String BirthDay;
    private String Email;
    private int  Type;
    private String Faculty;

    public StudentDTO(String id, String name, boolean gender, String birthDay, String email, int type, String faculty) {
        Id = id;
        Name = name;
        Gender = gender;
        BirthDay = birthDay;
        Email = email;
        Type = type;
        Faculty = faculty;
    }

    public StudentDTO() {
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

    public boolean getGender() {
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
