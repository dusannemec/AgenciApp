import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrandeAreaConhecimento {

    private int codigoGAC;
    private String nome;

    public GrandeAreaConhecimento() {}

    public GrandeAreaConhecimento(int codigoGAC) {
        this.setCodigoGAC(codigoGAC);
    }

    public int getCodigoGAC() {
        return this.codigoGAC;
    }

    public void setCodigoGAC(int codigoGAC) {
        this.codigoGAC = codigoGAC;
    }

    public String getNome() {
        return this.nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public void carregar(Connection conn) {
        String sqlUpdate = "SELECT * FROM GrandeAreaConhecimento WHERE CodigoGAC = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sqlUpdate);
            stm.setInt(1, this.codigoGAC);
            rs = stm.executeQuery();

            if (rs.next()) {
                this.setNome(rs.getString(1));
            } 
        } catch (Exception e) {
            if (rs != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            } 
        }
    }

}