package mainCode;

public class Users {

    private String id;

    private String FName;

    private String LName;

    private String userName;

    private String password;

    private String title;


    public Users(String id, String fName, String lName, String userName, String password, String title) {
        this.id = id;
        this.FName = fName;
        this.LName = lName;
        this.userName = userName;
        this.password = password;
        this.title = title;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String fName) {
        this.FName = fName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String lName) {
        this.LName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", fName='" + FName + '\'' +
                ", lName='" + LName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
