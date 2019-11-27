package br.com.hbsis.categoria;


public class CategoriaDTO {
    private Long id;
    private Long fornecedor;
    private String nomeCategoria;

    public CategoriaDTO() {

    }

    public CategoriaDTO(Long id, String nomeCategoria, Long fornecedor) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor = fornecedor;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNomeCategoria(),
                categoria.getFornecedor().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Long fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", id_fornecedor" + fornecedor + '\'' +
                ", categoria='" + nomeCategoria + '\'' +
                '}';
    }
}