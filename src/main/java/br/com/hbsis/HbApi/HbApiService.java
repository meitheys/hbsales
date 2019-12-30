package br.com.hbsis.HbApi;

import br.com.hbsis.HbApi.employee.EmployeeDTO;
import br.com.hbsis.HbApi.employee.EmployeeSavingDTO;
import br.com.hbsis.HbApi.invoice.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class HbApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbApiService.class);
    //Url API Swagger
    private final  String urlApiEmployee = "http://10.2.54.25:9999/api/employees";
    private final String urlApiInvoice = "http://10.2.54.25:9999/api/invoice";

    //Gerando uuid com API
    public String hbUuidGenerator(String nome) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setNome(nome);
        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(employeeDTO, headerApiHbEmployee());
        ResponseEntity<EmployeeSavingDTO> result = restTemplate.exchange(urlApiEmployee, HttpMethod.POST, entity, EmployeeSavingDTO.class);

        if (result.getStatusCodeValue() == (200)) {
            return result.getBody().getEmployeeUuid();
        } else if (result.getStatusCodeValue() == 403) {
            throw new IllegalArgumentException("Acesso não permitido");
        } else
            throw new IllegalArgumentException("Falha na comunicação " + result.getStatusCode());
    }

    //Validando produto com permissão API
    public String hbInvoice(InvoiceDTO invoiceDTO) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<InvoiceDTO> entity = new HttpEntity<>(invoiceDTO, headerApiHbEmployee());
        ResponseEntity<InvoiceDTO> result = restTemplate.exchange(urlApiInvoice, HttpMethod.POST, entity, InvoiceDTO.class);
        LOGGER.info(result.getStatusCode().toString());

        return result.getStatusCode().toString();
    }

    //Adicionando Header que permite validação adicionando permissão com chave API
    public static HttpHeaders headerApiHbEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "f59fec50-1b67-11ea-978f-2e728ce88125");
        return headers;
    }
}
