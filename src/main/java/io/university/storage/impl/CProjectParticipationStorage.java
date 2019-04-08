package io.university.storage.impl;

import io.university.model.dao.CProjectParticipation;
import io.university.repository.CProjectParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 10.03.2019
 */
@Component
public class CProjectParticipationStorage extends BasicJpaStorage<CProjectParticipation, Integer> {

    @Autowired
    public CProjectParticipationStorage(final CProjectParticipationRepository repository) {
        super(repository);
    }
}