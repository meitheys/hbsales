package br.com.hbsis.validacoes;

import org.apache.commons.lang.StringUtils;

public class IntValidations {

    public static void validateStringLength(String text, int totalLength) {
        if (text.length() > totalLength) {
            throw new IllegalArgumentException("Máximo de caracteres foi atingido!");
        }
    }

    public static void validateLengthOfInt(int value, int length) {
        if (value > length) {
            throw new IllegalArgumentException("Número não deve ser maior que...: " + length);
        }
    }

    public static void validateStringIsEmpty(String text, String messageError) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(messageError);
        }
    }

}
