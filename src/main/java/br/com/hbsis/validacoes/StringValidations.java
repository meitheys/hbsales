package br.com.hbsis.validacoes;

import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StringValidations {

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(StringValidations.class);

    public StringValidations() {
    }

    /**Fornecedor*/
    public String formatarCnpj(String cnpj) {
        Fornecedor fornecedor = new Fornecedor();
        String mask = fornecedor.getCnpj();
        mask = (cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14));
        return mask;
    }

    public String desformatando(String cnpj) {

        String desformatando =   cnpj.charAt(0)+""+cnpj.charAt(1)+
                cnpj.charAt(3)+cnpj.charAt(4)+cnpj.charAt(5)+
                cnpj.charAt(7)+cnpj.charAt(8)+cnpj.charAt(9)+
                cnpj.charAt(11)+cnpj.charAt(12)+cnpj.charAt(13)+cnpj.charAt(14)+
                cnpj.charAt(16)+cnpj.charAt(17);

        return desformatando;

    }

    /**Categoria*/
    public String codigoValidar (String codigo){
        String codigoProcessador = StringUtils.leftPad(codigo, 3, "0");
        return codigoProcessador;
    }

    public String quatroCNPJ(String cnpj){
        String ultimosDigitos = cnpj.substring(cnpj.length() - 4);

        return ultimosDigitos;
    }

    /**Linha*/
    public String zeroAEsquerda(String zero) {
        String zerinho = StringUtil.leftPad(zero, 10, "0");

        return zerinho;
    }


}
