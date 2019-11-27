package br.com.hbsis.fornecedor;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seg_fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "razao", unique = true, nullable = false, length = 200)
    private String razao;
    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;
    @OneToMany(mappedBy = "fornecedor", targetEntity = Categoria.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Categoria> categorias;
    @Column(name = "nome", unique = true, updatable = false, length = 200)
    private String nome;
    @Column(name = "endereco", nullable = false, length = 150)
    private String endereco;
    @Column(name = "telefone", nullable = false, length = 14)
    private String telefone;
    @Column(name = "email", unique = true, updatable = false, length = 100)
    private String email;

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Long getId() {
        return id;
    }

    public String getRazao() { return razao; }

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
        return "Fornecedor{" +
                "id=" + id +
                ", razao='" + razao + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                '}';

    }
}
