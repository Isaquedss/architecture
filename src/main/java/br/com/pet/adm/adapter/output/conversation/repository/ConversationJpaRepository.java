package br.com.pet.adm.adapter.output.conversation.repository;

import br.com.pet.adm.adapter.output.conversation.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationJpaRepository extends JpaRepository<ConversationEntity, String> {
}