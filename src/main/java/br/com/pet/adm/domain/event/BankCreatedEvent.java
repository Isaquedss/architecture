package br.com.pet.adm.domain.event;

import java.util.Date;

/**
 * Domain Event — algo que aconteceu no domínio (passado, imutável).
 */
public class BankCreatedEvent {

    private final String cdBank;
    private final String dsBank;
    private final Date   occurredAt;

    public BankCreatedEvent(String cdBank, String dsBank) {
        this.cdBank     = cdBank;
        this.dsBank     = dsBank;
        this.occurredAt = new Date();
    }

    public String getCdBank()     { return cdBank; }
    public String getDsBank()     { return dsBank; }
    public Date   getOccurredAt() { return occurredAt; }
}
