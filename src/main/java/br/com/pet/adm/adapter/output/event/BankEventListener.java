package br.com.pet.adm.adapter.output.event;

import br.com.pet.adm.domain.event.BankCreatedEvent;
import br.com.pet.adm.domain.event.BankDeactivatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener de Domain Events do Bank.
 * Desacoplado — reage ao evento sem o emissor saber.
 */
@Component
public class BankEventListener {

    @EventListener
    public void onBankCreated(BankCreatedEvent event) {
        System.out.println("[🏦 EVENT] Banco criado: " + event.getCdBank()
                + " - " + event.getDsBank()
                + " em " + event.getOccurredAt());
    }

    @EventListener
    public void onBankDeactivated(BankDeactivatedEvent event) {
        System.out.println("[🔴 EVENT] Banco inativado: " + event.getCdBank()
                + " por usuário " + event.getUserId()
                + " em " + event.getOccurredAt());
    }
}
