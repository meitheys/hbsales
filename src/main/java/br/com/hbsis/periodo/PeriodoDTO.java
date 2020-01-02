package br.com.hbsis.periodo;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;


public class PeriodoDTO {
    private long id;
    private long idFornecedor;
    private LocalDate data_inicial;
    private LocalDate data_final;
    private LocalDate retirada;
    private String descricao;

    public PeriodoDTO() {
    }

    public PeriodoDTO(long id, long idFornecedor, LocalDate data_inicial, LocalDate data_final, LocalDate retirada, String descricao) {
        this.id = id;
        this.idFornecedor = idFornecedor;
        this.data_inicial = data_inicial;
        this.data_final = data_final;
        this.retirada = retirada;
        this.descricao = descricao;
    }

    public static PeriodoDTO of(Periodo periodo) {
        return new PeriodoDTO(
                periodo.getId(),
                periodo.getIdFornecedor().getIdFornecedor(),
                periodo.getData_inicial(),
                periodo.getData_final(),
                periodo.getRetirada(),
                periodo.getDescricao()
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

    public LocalDate getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(LocalDate data_inicial) {
        this.data_inicial = data_inicial;
    }

    public LocalDate getData_final() {
        return data_final;
    }

    public void setData_final(LocalDate data_final) {
        this.data_final = data_final;
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
                ", data_inicial=" + data_inicial +
                ", data_final=" + data_final +
                ", retirada=" + retirada +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
