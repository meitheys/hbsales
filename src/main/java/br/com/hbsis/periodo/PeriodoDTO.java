package br.com.hbsis.periodo;


import java.time.LocalDate;


public class PeriodoDTO {
    private long id;
    private long idFornecedor;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private LocalDate retirada;
    private String descricao;

    public PeriodoDTO() {
    }

    public PeriodoDTO(long id, long idFornecedor, LocalDate dataInicial, LocalDate dataFinal, LocalDate retirada) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.retirada = retirada;
        this.descricao = descricao;
    }

    public static PeriodoDTO of(Periodo periodo) {
        return new PeriodoDTO(
                periodo.getId(),
                periodo.getIdFornecedor().getIdFornecedor(),
                periodo.getDataInicial(),
                periodo.getDataFinal(),
                periodo.getRetirada()
        );
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(long idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public LocalDate getRetirada() {
        return retirada;
    }

    public void setRetirada(LocalDate retirada) {
        this.retirada = retirada;
    }

    @Override
    public String toString() {
        return "PeriodoDTO{" +
                "id=" + id +
                ", idFornecedor=" + idFornecedor +
                ", data_inicial=" + dataInicial +
                ", data_final=" + dataFinal +
                ", retirada=" + retirada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
