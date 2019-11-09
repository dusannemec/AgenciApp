import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Teste {
    public static void main(String [] args) {
        Connection conn = null;
        Projeto projeto;
        Pesquisador pesquisador;

        ArrayList<String> pesquisadoresProjeto;
        ArrayList<Integer> projetosPesquisador;

        try {
            ConexaoBD bd = new ConexaoBD();
            conn = bd.conectar();

            int menu;
            do {
                menu = Integer.parseInt(JOptionPane.showInputDialog(
                    "1. Projetos\n" +
                    "2. Pesquisadores\n" +
                    "0. Sair"
                ));
                if (menu == 0) {}
                else if (menu == 1) {
                    int subMenu;
                    do {
                        subMenu = Integer.parseInt(JOptionPane.showInputDialog(
                            "1. Cadastrar Projeto\n" +
                            "2. Excluir Projeto\n" +
                            "3. Mostrar detalhes de um projeto\n" +
                            "4. Alterar um projeto\n" +
                            "5. Mostrar Pesquisadores envolvidos em um projeto\n" +
                            "0. Sair"
                        ));
                        if (subMenu == 0) {}
                        else if(subMenu == 1) {
                            projeto = new Projeto(
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código do projeto")), //codigoInterno 
                                JOptionPane.showInputDialog("Insira o título do projeto"), // título 
                                Integer.parseInt(JOptionPane.showInputDialog("Insira a duração do projeto (em meses)")), // duração
                                Double.parseDouble(JOptionPane.showInputDialog("Insira o orçamento do projeto")), // orçamento 
                                JOptionPane.showInputDialog("Insira a instituição onde será realizado o projeto"), 
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código da área de conhecimento do projeto")), 
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código da grande área de conhecimento do projeto"))
                            );

                            projeto.inserir(conn);
                        }
                        else if (subMenu == 2) {
                            projeto = new Projeto(
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código do projeto para excluir")
                            ));

                            projeto.excluir(conn);
                        }
                        else if (subMenu == 3) {
                            projeto = new Projeto(
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código do projeto para visualizar detalhes")
                            ));

                            projeto.carregarProjeto(conn);

                            JOptionPane.showMessageDialog(null, projeto);
                        }
                        else if (subMenu == 4) {
                            projeto = new Projeto(
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código do projeto")), //codigoInterno 
                                JOptionPane.showInputDialog("Insira o título do projeto"), // título 
                                Integer.parseInt(JOptionPane.showInputDialog("Insira a duração do projeto (em meses)")), // duração
                                Double.parseDouble(JOptionPane.showInputDialog("Insira o orçamento do projeto")), // orçamento 
                                JOptionPane.showInputDialog("Insira a instituição onde será realizado o projeto"), 
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código da área de conhecimento do projeto")), 
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código da grande área de conhecimento do projeto"))
                            );

                            projeto.alterar(conn);
                        }
                        else if (subMenu == 5) {
                            projeto = new Projeto(
                                Integer.parseInt(JOptionPane.showInputDialog("Insira o código do projeto para visualizar quais pesquisadores estão trabalhando nele")
                            ));
                            
                            pesquisadoresProjeto = projeto.carregarPesquisadoresDoProjeto(conn);

                            System.out.println(pesquisadoresProjeto.size());

                            String concatenador = "Pesquisadores envoldidos no projeto:\n";
                            for(String cpf: pesquisadoresProjeto) {
                                pesquisador = new Pesquisador(cpf);
                                pesquisador.carregarPesquisador(conn);
                                concatenador += pesquisador + "\n";
                            }

                            JOptionPane.showMessageDialog(null, concatenador);
                        }
                    } while (subMenu != 0);
                }
                else if (menu == 2) {
                    int subMenu;
                    do {
                        subMenu = Integer.parseInt(JOptionPane.showInputDialog(
                            "1. Cadastrar Pesquisador\n" +
                            "2. Excluir Pesquisador\n" +
                            "3. Mostrar dados de um pesquisador\n" +
                            "4. Alterar dados de um pesquisador\n" +
                            "5. Mostrar os projetos em que o pesquisador está trabalhando\n" +
                            "0. Sair"
                        ));
                        if (subMenu == 0) {}
                        else if(subMenu == 1) {
                            pesquisador = new Pesquisador(
                                JOptionPane.showInputDialog("Insira o CPF do Pesquisador"), // CPF 
                                JOptionPane.showInputDialog("Insira o RG do Pesquisador"), // RG 
                                JOptionPane.showInputDialog("Insira o Nome do Pesquisador"), // Nome
                                (JOptionPane.showInputDialog("Insira o Gênero do Pesquisador")).charAt(0), // Gênero 
                                JOptionPane.showInputDialog("Insira o Grau Acadêmico"), // Grau Acadêmico 
                                JOptionPane.showInputDialog("Insira a Instituição onde o pesquisador estudou") // Instituição
                            );

                            pesquisador.inserir(conn);
                        }
                        else if (subMenu == 2) {
                            pesquisador = new Pesquisador(
                                JOptionPane.showInputDialog("Insira o CPF do Pesquisador para excluir")
                            );

                            pesquisador.excluir(conn);
                        }
                        else if (subMenu == 3) {
                            pesquisador = new Pesquisador(
                                JOptionPane.showInputDialog("Insira o CPF do Pesquisador para visualizar dados")
                            );

                            pesquisador.carregarPesquisador(conn);

                            JOptionPane.showMessageDialog(null, pesquisador);
                        }
                        else if (subMenu == 4) {
                            pesquisador = new Pesquisador(
                                JOptionPane.showInputDialog("Insira o CPF do Pesquisador"), // CPF 
                                JOptionPane.showInputDialog("Insira o RG do Pesquisador"), // RG 
                                JOptionPane.showInputDialog("Insira o Nome do Pesquisador"), // Nome
                                (JOptionPane.showInputDialog("Insira o Gênero do Pesquisador")).charAt(0), // Gênero 
                                JOptionPane.showInputDialog("Insira o Grau Acadêmico"), // Grau Acadêmico 
                                JOptionPane.showInputDialog("Insira a Instituição onde o pesquisador estudou") // Instituição
                            );

                            pesquisador.alterar(conn);
                        }
                        else if (subMenu == 5) {
                            pesquisador = new Pesquisador(
                                JOptionPane.showInputDialog("Insira o CPF do Pesquisador para ver em quais projetos ele está trabalhando")
                            );

                            
                            projetosPesquisador = pesquisador.carregarProjetosDoPesquisador(conn);

                            System.out.println(projetosPesquisador.size());

                            String concatenador = "Pesquisadores envoldidos no projeto:\n";
                            for(int codigoInterno: projetosPesquisador) {
                                projeto = new Projeto(codigoInterno);
                                projeto.carregarProjeto(conn);
                                concatenador += projeto + "\n";
                            }

                            JOptionPane.showMessageDialog(null, concatenador);
                        }
                    } while (subMenu != 0);
                }
            } while (menu != 0);
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                }
                catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            } 
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException e1) {
                    System.out.println(e1.getStackTrace());
                }
            }
        }
    }
}
