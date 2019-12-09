package br.com.hbsis.linhaCategoria;

public class LinhaDTO {
    private String codigoLinha;
    private String categoriaLinha;
    private long id;
    private String nomeLinha;

public LinhaDTO(){

    }

public LinhaDTO(long id, String codigoLinha, String categoriaLinha, String nomeLinha){
    this.id = id;
    this.codigoLinha = codigoLinha;
    this.categoriaLinha = categoriaLinha;
    this.nomeLinha = nomeLinha;
}

    public static LinhaDTO of(Linha linha){
    return new LinhaDTO(
            linha.getId(),
            linha.getCodigoLinha(),
            linha.getCategoriaLinha().getCodigoCategoria(),
            linha.getNomeLinha()
    );
}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigoLinha() {
        return codigoLinha;
    }

    public void setCodigoLinha(String codigoLinha) {
        this.codigoLinha = codigoLinha;
    }

    public String getCategoriaLinha() {
        return categoriaLinha;
    }

    public void setCategoriaLinha(String categoriaLinha) {
        this.categoriaLinha = categoriaLinha;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    @Override
    public String toString() {
        return "LinhaDTO{" +
                "codigo_linha='" + codigoLinha + '\'' +
                ", categoria_linha=" + categoriaLinha +
                ", id=" + id +
                ", nome_linha='" + nomeLinha + '\'' +
                '}';
    }
}
