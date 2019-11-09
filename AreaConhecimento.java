import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AreaConhecimento {

    private int codigoAC;
    private String nome;
    private int codigoGAC;

    public AreaConhecimento() {}

    public AreaConhecimento(int codigoAC) {
        this.setCodigoAC(codigoAC);
    }

    public int getCodigoAC() {
        return this.codigoAC;
    }

    public void setCodigoAC(int codigoAC) {
        this.codigoAC = codigoAC;
    }

    public String getNome() {
        return this.nome;
    }

    private void setNome(String  nome) {
        this.nome = nome;
    }

    public int getCodigoGAC() {
        return this.codigoGAC;
    }

    private void setCodigoGAC(int codigoGAC) {
        this.codigoGAC = codigoGAC;
    }

    public void carregar(Connection conn) {
        String sqlSelect = "SELECT * FROM AreaConhecimento WHERE CodigoAC = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sqlSelect);
            stm.setInt(1, this.codigoAC);
            rs = stm.executeQuery();

            if (rs.next()) {
                this.setNome(rs.getString(1));
                this.setCodigoGAC(rs.getInt(2));
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