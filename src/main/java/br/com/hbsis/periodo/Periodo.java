package br.com.hbsis.periodo;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "seg_periodo")
public class Periodo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_fornecedor")
    private long idFornecedor;
    @Column(name = "data_inicial")
    private LocalDate data_inicial;
    @Column(name = "data_final")
    private LocalDate data_final;
    @Column(name = "retirada")
    private LocalDate retirada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "Periodo{" +
                "id=" + id +
                ", idFornecedor=" + idFornecedor +
                ", data_inicial=" + data_inicial +
                ", data_final=" + data_final +
                ", retirada=" + retirada +
                '}';
    }
}
