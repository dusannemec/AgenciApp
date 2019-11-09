import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pesquisador extends Submete {
    
    private String cpf;
    private String rg;
    private String nome;
    private char genero;
    private String grauAcademico;
    private String instituicao;

    public Pesquisador() {
        super();
    }

    public Pesquisador(String cpf) {
        super();
        this.setCpf(cpf);
    }

    public Pesquisador(String cpf,
                       String rg,
                       String nome,
                       char genero,
                       String grauAcademico,
                       String instituicao) {
        super();
        this.setCpf(cpf);
        this.setRg(rg);
        this.setNome(nome);
        this.setGenero(genero);
        this.setGrauAcademico(grauAcademico);
        this.setInstituicao(instituicao);
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return this.rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getGenero() {
        return this.genero;
    }

    public void setGenero(char genero) {
        char upperGen = Character.toUpperCase(genero);
        if (upperGen == 'M' || upperGen == 'F') {
            this.genero = upperGen;
        }
    }

    public String getGrauAcademico() {
        return this.grauAcademico;
    }

    public void setGrauAcademico(String grauAcademico) {
        this.grauAcademico = grauAcademico;
    }

    public String getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String toString() {
        return String.format("Pesquisador[CPF=%s, RG=%s, Nome=%s, Genero=%c, Instituicao=%s]",
        this.getCpf(), this.getRg(), this.getNome(), this.getGenero(), this.getInstituicao());
    }

    public void inserir(Connection conn) {
        String sqlInsert = "INSERT INTO Pesquisador(CPF, RG, Nome, Genero, GrauAcademico, Instituicao) VALUES (?,?,?,?,?,?)";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlInsert);
            stm.setString(1, this.getCpf());
            stm.setString(2, this.getRg());
            stm.setString(3, this.getNome());
            stm.setString(4, String.valueOf(this.getGenero()));
            stm.setString(5, this.getGrauAcademico());
            stm.setString(6, this.getInstituicao());

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
        this.setPesquisadorCPF(this.getCpf());
        this.excluirProjetosAssociadosPesquisador(conn);
        
        String sqlDelete = "DELETE FROM Pesquisador WHERE CPF = ?";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlDelete);
            stm.setString(1, this.getCpf());
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

    public void carregarPesquisador(Connection conn) {
        String sqlSelect = "SELECT * FROM Pesquisador WHERE CPF = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sqlSelect);
            stm.setString(1, this.getCpf());
            rs = stm.executeQuery();

            if(rs.next()) {
                this.setRg(rs.getString("RG"));
                this.setNome(rs.getString("Nome"));
                this.setGenero(rs.getString("Genero").charAt(0));
                this.setGrauAcademico(rs.getString("GrauAcademico"));
                this.setInstituicao(rs.getString("Instituicao"));
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

    public ArrayList<Integer> carregarProjetosDoPesquisador(Connection conn) {
        this.setPesquisadorCPF(this.getCpf());
        return this.carregarProjetosPesquisador(conn);
    }

    public void alterar(Connection conn) {
        String sqlUpdate = "UPDATE Pesquisador SET RG = ?, Nome = ?, Genero = ?, GrauAcademico = ? WHERE CPF = ?";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlUpdate);
            stm.setString(1, this.getRg());
            stm.setString(2, this.getNome());
            stm.setString(3, String.valueOf(this.getGenero()));
            stm.setString(4, this.getGrauAcademico());
            stm.setString(5, this.getInstituicao());
            stm.setString(6, this.getCpf());

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