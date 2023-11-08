package Database.Connector;

public class User {
    private String Nickname;
    private String Password;

    public User()
    {
        Nickname = "";
        Password = "";
    }

    public User (String Nickname, String Password){
        this.Nickname = Nickname;
        this.Password = Password;
    }


    public String getNickname() {
        return Nickname;
    }

    public String getPassword() {
        return Password;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
