import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Submete {

    private String pesquisadorCPF;
    private int codigoInternoProjeto;
    private String dataEnvio;

    private ArrayList<Integer> projetosPesquisador;
    private ArrayList<String> pesquisadoresProjeto;

    public Submete() {
        this.setPesquisadoresProjeto();
        this.setProjetosPesquisador();
    }

    public Submete(String pesquisadorCPF) {
        this.setPesquisadorCPF(pesquisadorCPF);
        this.setProjetosPesquisador();
    }

    public Submete(int codigoInternoProjeto) {
        this.setCodigoInternoProjeto(codigoInternoProjeto);
        this.setPesquisadoresProjeto();
    }

    public Submete(String pesquisadorCPF, int codigoInternoProjeto, String dataEnvio) {
        this.setPesquisadorCPF(pesquisadorCPF);
        this.setCodigoInternoProjeto(codigoInternoProjeto);
        this.setDataEnvio(dataEnvio);
        this.setProjetosPesquisador();
        this.setPesquisadoresProjeto();
    }

    public String getPesquisadorCPF() {
        return this.pesquisadorCPF;
    }

    public void setPesquisadorCPF(String pesquisadorCPF) {
        this.pesquisadorCPF = pesquisadorCPF;
    }

    public int getCodigoInternoProjeto() {
        return this.codigoInternoProjeto;
    }

    public void setCodigoInternoProjeto(int codigoInternoProjeto) {
        this.codigoInternoProjeto = codigoInternoProjeto;
    }

    public String getDataEnvio() {
        return this.dataEnvio;
    }
 
    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public ArrayList<Integer> getProjetosPesquisador() {
        return this.projetosPesquisador;
    }

    public void setProjetosPesquisador() {
        this.projetosPesquisador = new ArrayList<>();
    }

    public ArrayList<String> getPesquisadoresProjeto() {
        return this.pesquisadoresProjeto;
    }

    public void setPesquisadoresProjeto() {
        this.pesquisadoresProjeto = new ArrayList<>();
    }
 
    public void incluir(Connection conn) {
        String sqlInsert = "INSERT INTO Submete(PesquisadorCPF, CodigoInternoProjeto, DataEnvio) VALUES (?,?,?)";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlInsert);
            stm.setString(1, this.getPesquisadorCPF());
            stm.setInt(2, this.getCodigoInternoProjeto());
            stm.setString(3, this.getDataEnvio());

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

    public void excluirProjetosAssociadosPesquisador(Connection conn) {
        /*
            Esse método exclui projetos associados a um único pesquisador.
            Por exemplo:
            PesquisadorCPF | CodigoInternoProjeto
            90               1
            90               2

            O pesquisador de CPF 90 está envolvido nos projetos 1 e 2, e ambos os registros serão excluidos
            se esse for o CPF informado no WHERE.
        */ 
        String sqlDelete = "DELETE FROM Submete WHERE PesquisadorCPF = ?";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlDelete);
            stm.setString(1, this.getPesquisadorCPF());
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

    public void excluirPesquisadoresAssociadosProjeto(Connection conn) {
        /*
            Esse método exclui pesquisadores associados a um único projeto.
            Por exemplo:
            PesquisadorCPF | CodigoInternoProjeto
            90               1
            70               1

            O projeto de Código Interno 1 tem os pesquisadores de CPF 90 e 70, ambos os registros serão excluidos
            se esse for o Codigo Interno informado no WHERE.
        */

        String sqlDelete = "DELETE FROM Submete WHERE CodigoInternoProjeto = ?";

        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sqlDelete);
            stm.setInt(1, this.getCodigoInternoProjeto());
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

    public ArrayList<Integer> carregarProjetosPesquisador(Connection conn) {
        String sqlSelect = "SELECT * FROM Submete WHERE PesquisadorCPF = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sqlSelect);
            stm.setString(1, this.getPesquisadorCPF());
            rs = stm.executeQuery();
            
            while (rs.next()) {
                this.getProjetosPesquisador().add(rs.getInt("CodigoInternoProjeto"));
            }
            return this.getProjetosPesquisador();
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
        return new ArrayList<>();
    }

    public ArrayList<String> carregarPesquisadoresProjeto(Connection conn) {
        String sqlSelect = "SELECT * FROM Submete WHERE CodigoInternoProjeto = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sqlSelect);
            stm.setInt(1, this.getCodigoInternoProjeto());
            rs = stm.executeQuery();
            
            while (rs.next()) {
                this.getPesquisadoresProjeto().add(rs.getString(1));
            }
            return this.getPesquisadoresProjeto();
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
        return new ArrayList<>();
    }
}