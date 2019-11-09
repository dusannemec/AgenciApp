import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/AgenciApp", "aluno", "Usjt@@192");
    } 
}