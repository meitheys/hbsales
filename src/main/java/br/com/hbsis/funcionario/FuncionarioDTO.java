package br.com.hbsis.funcionario;

public class FuncionarioDTO {
    private long id;
    private String nome;
    private String email;
    private String uuid;

    public FuncionarioDTO(long id, String nome, String email, String uuid){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.uuid = uuid;
    }

    public static FuncionarioDTO of(Funcionario funcionario) {
        return new FuncionarioDTO(
                funcionario.getId(),
                funcionario.getNomeFuncionario(),
                funcionario.getEmail(),
                funcionario.getUuid()
        );
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FuncionarioDTO{" +
                "id=" + id +
                ", nomeFuncionario='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}


