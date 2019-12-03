package br.com.hbsis.linhaCategoria;

public class LinhaDTO {
    private long codigo_linha;
    private long categoria_linha;
    private String nome_linha;

public LinhaDTO(){

    }

public LinhaDTO(long codigo_linha, long categoria_linha, String nome_linha){
    this.codigo_linha = codigo_linha;
    this.categoria_linha = categoria_linha;
    this.nome_linha = nome_linha;
}

public static LinhaDTO of(Linha linha){
    return new LinhaDTO(
            linha.getCodigo_linha(),
            linha.getCategoria_linha(),
            linha.getNome_linha()
    );
}

    public Long getCodigo_linha() {
        return codigo_linha;
    }

    public void setCodigo_linha(Long codigo_linha) {
        this.codigo_linha = codigo_linha;
    }

    public Long getCategoria_linha() {
        return categoria_linha;
    }

    public void setCategoria_linha(Long categoria_linha) {
        this.categoria_linha = categoria_linha;
    }

    public String getNome_linha() {
        return nome_linha;
    }

    public void setNome_linha(String nome_linha) {
        this.nome_linha = nome_linha;
    }

    @Override
    public String toString() {
        return "LinhaDTO{" +
                ", codigo_linha=" + codigo_linha +
                ", categoria_linha=" + categoria_linha +
                ", nome_linha='" + nome_linha + '\'' +
                '}';
    }
}
