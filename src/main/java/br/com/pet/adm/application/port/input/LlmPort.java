package br.com.pet.adm.application.port.input;

public interface LlmPort {
    String complete(String prompt);
}
