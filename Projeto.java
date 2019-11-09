import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Projeto extends Submete{

    private int codigoInterno;
    private String titulo;
    private int duracao;
    private double orcamento;
    private String instituicao;
    private int codigoAC;
    private int codigoGAC;

    public Projeto() {
        super();
    }

    public Projeto(int codigoInterno) {
        super();
        this.setCodigoInterno(codigoInterno);
    }

    public Projeto(
        int codigoInterno,
        String titulo,
        int duracao,
        double orcamento,
        String instituicao,
        int codigoAC,
        int codigoGAC
    ) {
        super();
        this.setCodigoInterno(codigoInterno);
        this.setTitulo(titulo);
        this.setDuracao(duracao);
        this.setOrcamento(orcamento);
        this.setInstituicao(instituicao);
        this.setCodigoAC(codigoAC);
        this.setCodigoGAC(codigoGAC); 
    }

    public int getCodigoInterno() {
        return this.codigoInterno;
    }

    public void setCodigoInterno(int codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public double getOrcamento() {
        return this.orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public String getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public int getCodigoAC() {
        return this.codigoAC;
    }

    public void setCodigoAC(int codigoAC) {
        this.codigoAC = codigoAC;
    }

    public int getCodigoGAC() {
        return this.codigoGAC;
    }

    public void setCodigoGAC(int codigoGAC) {
        this.codigoGAC = codigoGAC;
    }

    public String toString() {
        return String.format("Projeto[CodigoInterno=%d, Titulo=%s, Duracao=%d, Orcamento=R$%.2f, Instituicao=%s, CodigoAC=%d, CodigoGAC=%d]",
                                this.getCodigoInterno(), this.getTitulo(), this.getDuracao(), this.getOrcamento(), this.getInstituicao(), this.getCodigoAC(), this.getCodigoGAC());
    }
 
    public void inserir(Connection conn) {
        String sqlInsert = "INSERT INTO Projeto(CodigoInterno, Titulo, Duracao, Orcamento, Instituicao, CodigoAC, CodigoGAC) VALUES(?,?,?,?,?,?,?)";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlInsert);
            stm.setInt(1, this.getCodigoInterno());
            stm.setString(2, this.getTitulo());
            stm.setInt(3, this.getDuracao());
            stm.setDouble(4, this.getOrcamento());
            stm.setString(5, this.getInstituicao());
            stm.setInt(6, this.getCodigoAC());
            stm.setInt(7, this.getCodigoGAC());

            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getStackTrace());
            }
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            }
        }
    }

    public void excluir(Connection conn) {
        String sqlDelete = "DELETE FROM Projeto WHERE CodigoInterno = ?";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlDelete);
            stm.setInt(1, this.getCodigoInterno());

            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getStackTrace());
            }
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            }
        }
    }

    public void carregarProjeto(Connection conn) {
        String sqlSelect = "SELECT * FROM Projeto WHERE CodigoInterno = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sqlSelect);
            stm.setInt(1, this.getCodigoInterno());
            rs = stm.executeQuery();

            if(rs.next()) {
                this.setTitulo(rs.getString("Titulo"));
                this.setDuracao(rs.getInt("Duracao"));
                this.setOrcamento(rs.getDouble("Orcamento"));
                this.setInstituicao(rs.getString("Instituicao"));
                this.setCodigoAC(rs.getInt("CodigoAC"));
                this.setCodigoGAC(rs.getInt("CodigoGAC"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (rs != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }   
            }
        } finally {
            if (rs != null ) {
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

    public ArrayList<String> carregarPesquisadoresDoProjeto(Connection conn) {
        this.setCodigoInternoProjeto(this.getCodigoInterno());
        return this.carregarPesquisadoresProjeto(conn);
    }

    public void alterar(Connection conn) {
        String sqlUpdate = "UPDATE Projeto SET Titulo = ?, Duracao = ?, Orcamento = ?, Instituicao = ?, CodigoAC = ?, CodigoGAC = ? WHERE CodigoInterno = ?";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlUpdate);
            stm.setString(1, this.getTitulo());
            stm.setInt(2, this.getDuracao());
            stm.setDouble(3, this.getOrcamento());
            stm.setString(4, this.getInstituicao());
            stm.setInt(5, this.getCodigoAC());
            stm.setInt(6, this.getCodigoGAC());
            stm.setInt(7, this.getCodigoInterno());

            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getStackTrace());
            }
        } finally {
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