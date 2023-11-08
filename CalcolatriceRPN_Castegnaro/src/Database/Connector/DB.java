package Database.Connector;

import com.sun.jdi.connect.Connector;

import java.sql.*;

public class DB {

    public static User activeUser;
    private Connection connect(){

        final String DATABASE_URL = "jdbc:mysql://localhost:3306/utenti";
        final String USERNAME = "root";
        final String PASSWORD = "";
        Connection conn;

        try{
            conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public boolean userExists(String Nickname){
        boolean ris = false;
        try{
            Connection conn = connect();
            String sql = "SELECT Count(*) FROM utenti WHERE Nickname = ?";
            Statement stat = conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,Nickname);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.getInt(1)==1)
                ris = true;
        }catch(SQLException e){
            System.out.println("Connessione fallita!");
            e.printStackTrace();
        }
        return ris;
    }

    public int register(String Nome, String Cognome, String Nickname, String Password, int Age) throws SQLException {
        if(Nome.isEmpty() || Cognome.isEmpty() || Nickname.isEmpty() || Password.isEmpty() || Age <= 0 )
            return -1;

        if(userExists(Nickname))
            return -2;

        Connection conn  = connect();
        Statement stat = conn.createStatement();
        String sql = "INSERT INTO utenti (Nome, Cognome, Nickname, Password, Age) VALUES" +
                "(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, Nome);
        preparedStatement.setString(2, Cognome);
        preparedStatement.setString(3, Nickname);
        preparedStatement.setString(4, Password);
        preparedStatement.setInt(5, Age);

        preparedStatement.executeUpdate();
        activeUser = new User(Nickname, Password);

        stat.close();
        conn.close();

        return 1;

    }

    public int login(String Nickname, String Password){
        if(Nickname.isEmpty() || Password.isEmpty())
            return -1;

        Connection conn = connect();

        try {
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM utenti WHERE Nickname =? AND Password =?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, Nickname);
            preparedStatement.setString(2, Password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                activeUser = new User(resultSet.getString("Nickname"), resultSet.getString("Password"));
                System.out.println("Login avvenuto con successo!");
                stat.close();
                conn.close();
                return 1;
            } else {
                stat.close();
                conn.close();
                System.out.println("Login fallito!");
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Login fallito!");
            return -1;


        }
    }

    public int registraCronologia(String operazione){
        if(operazione.isEmpty() || activeUser.getNickname().isEmpty())
            return -1;

        Connection conn = connect();
        try{
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO cronologia(NicknameUtente, operazione) VALUES" +
                    "(?, ?)";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1, activeUser.getNickname());
            preparedStatement.setString(2, operazione);
            preparedStatement.executeUpdate();
            statement.close();
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 1;
    }

    public String stampaInformazione(){
        Connection conn = connect();
        String ris = "";
        try{
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM cronologia WHERE NicknameUtente = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, activeUser.getNickname());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ris += "NicknameUtente: " + resultSet.getString("NicknameUtente") + "\n" +
                        "operazione: " + resultSet.getString("operazione") + "\n";
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ris;
    }
}
