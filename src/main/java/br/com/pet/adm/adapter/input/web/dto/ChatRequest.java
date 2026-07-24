package br.com.pet.adm.adapter.input.web.dto;

import java.util.Map;

public record ChatRequest(
        String question,
        Map<String, Object> filters
) {
}
