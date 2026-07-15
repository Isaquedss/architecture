package br.com.pet.adm.adapter.input.web.dto;

import java.util.Map;

public record IngestRequest(String content, Map<String, Object> metadata) {
}
