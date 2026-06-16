package br.com.pet.adm.domain.event;

import java.util.Date;

/**
 * Domain Event — banco foi inativado.
 */
public class BankDeactivatedEvent {

    private final String cdBank;
    private final String dsBank;
    private final Long   userId;
    private final Date   occurredAt;

    public BankDeactivatedEvent(String cdBank, String dsBank, Long userId) {
        this.cdBank     = cdBank;
        this.dsBank     = dsBank;
        this.userId     = userId;
        this.occurredAt = new Date();
    }

    public String getCdBank()     { return cdBank; }
    public String getDsBank()     { return dsBank; }
    public Long   getUserId()     { return userId; }
    public Date   getOccurredAt() { return occurredAt; }
}
