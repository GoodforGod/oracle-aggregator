package io.university.storage.impl.common;

import io.university.model.dao.common.CConference;
import io.university.repository.common.CConferenceRepository;
import io.university.storage.impl.BasicJpaStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 10.03.2019
 */
@Component
public class CConferenceStorage extends BasicJpaStorage<CConference, Integer> {

    @Autowired
    public CConferenceStorage(final CConferenceRepository repository) {
        super(repository);
    }
}
