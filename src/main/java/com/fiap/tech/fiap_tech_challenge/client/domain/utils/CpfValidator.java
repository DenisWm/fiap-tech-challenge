package com.fiap.tech.fiap_tech_challenge.client.domain.utils;

public final class CpfValidator {

    private CpfValidator(){}

    public static boolean isCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) return false;

        int[] pesos = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * pesos[i];
        }
        int primeiroDigito = 11 - soma % 11;
        if (primeiroDigito == 10 || primeiroDigito == 11) {
            primeiroDigito = 0;
        }

        if (primeiroDigito != cpf.charAt(9) - '0') return false;

        // Calcula o segundo dígito verificador
        soma = 0;
        pesos[0] = 11; // Ajuste do peso para o segundo dígito
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * pesos[i];
        }
        int segundoDigito = 11 - soma % 11;
        if (segundoDigito == 10 || segundoDigito == 11) {
            segundoDigito = 0;
        }

        return segundoDigito == cpf.charAt(10) - '0';
    }
}
