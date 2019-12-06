package br.com.hbsis.funcionario;

public class FuncionarioDTO {
    private long id;
    private String nomeFuncionario;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FuncionarioDTO(long id, String nomeFuncionario, String email){
        this.id = id;
        this.nomeFuncionario = nomeFuncionario;
        this.email = email;
    }

    public static FuncionarioDTO of(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNomeFuncionario(),
                funcionario.getEmail()
        );
    }

    @Override
    public String toString() {
        return "FuncionarioDTO{" +
                "id=" + id +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


