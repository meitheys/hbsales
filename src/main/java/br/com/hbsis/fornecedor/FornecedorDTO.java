package br.com.hbsis.fornecedor;

public class FornecedorDTO {
    private Long id;
    private String razao;
    private String cnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;

    public FornecedorDTO() {
    }

    public FornecedorDTO(Long id, String razao, String cnpj, String nome, String endereco, String telefone, String email){
        this.id = id;
        this.razao = razao;
        this.cnpj = cnpj;
        this.nome = nome;
        this.endereco = endereco;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;

    }

    public static FornecedorDTO of(Fornecedor fornecedor) {
        return new FornecedorDTO(
                fornecedor.getIdFornecedor(),
                fornecedor.getRazao(),
                fornecedor.getCnpj(),
                fornecedor.getNome(),
                fornecedor.getEndereco(),
                fornecedor.getTelefone(),
                fornecedor.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "FornecedorDTO{" +
                "id=" + id +
                ", razao='" + razao + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\''+
                '}';
    }
}