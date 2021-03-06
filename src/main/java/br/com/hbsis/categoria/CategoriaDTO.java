package br.com.hbsis.categoria;


public class CategoriaDTO {
    private Long idCategoria;
    private Long fornecedor;
    private String nomeCategoria;
    private String codigo_categoria;

    public CategoriaDTO() {

    }

    public CategoriaDTO(Long idCategoria, String nomeCategoria, Long fornecedor, String codigo_categoria) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.fornecedor = fornecedor;
        this.codigo_categoria = codigo_categoria;
    }

    public static CategoriaDTO of(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNomeCategoria(),
                categoria.getFornecedor().getIdFornecedor(),
                categoria.getCodigoCategoria()
        );
    }

    public String getCodigo_categoria() {
        return codigo_categoria;
    }

    public void setCodigo_categoria(String codigo_categoria) {
        this.codigo_categoria = codigo_categoria;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
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
        return "CategoriaDTO{" +
                "idCategoria=" + idCategoria +
                ", fornecedor=" + fornecedor +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", codigo_categoria='" + codigo_categoria + '\'' +
                '}';
    }
}