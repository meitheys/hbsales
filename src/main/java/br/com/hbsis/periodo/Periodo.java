package br.com.hbsis.periodo;

import br.com.hbsis.fornecedor.Fornecedor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "seg_periodo")
public class Periodo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor", referencedColumnName = "id")
    private Fornecedor idFornecedor;
    @Column(name = "data_inicial")
    private LocalDate dataInicial;
    @Column(name = "data_final")
    private LocalDate dataFinal;
    @Column(name = "retirada")
    private LocalDate retirada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
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
        return "Periodo{" +
                "id=" + id +
                ", idFornecedor=" + idFornecedor +
                ", data_inicial=" + dataInicial +
                ", dataFinal=" + dataFinal +
                ", retirada=" + retirada +
                '}';
    }
}
