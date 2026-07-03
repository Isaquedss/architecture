package br.com.pet.adm.domain.event;

import java.util.Date;

public record UfCreatedEvent(String ufName, Date occurredAt) {

}
