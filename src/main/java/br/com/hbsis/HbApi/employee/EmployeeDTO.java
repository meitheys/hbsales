package br.com.hbsis.HbApi.employee;

public class EmployeeDTO {
    String nome;
    EmployeeSavingDTO employeeSavingDTO;

    public EmployeeDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EmployeeSavingDTO getEmployeeSavingDTO() {
        return employeeSavingDTO;
    }

    public void setEmployeeSavingDTO(EmployeeSavingDTO employeeSavingDTO) {
        this.employeeSavingDTO = employeeSavingDTO;
    }
}
