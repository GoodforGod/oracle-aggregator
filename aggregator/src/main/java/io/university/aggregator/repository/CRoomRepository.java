package io.university.aggregator.repository;

import io.university.aggregator.model.dao.mongo.CRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 11.03.2019
 */
@Repository
public interface CRoomRepository extends JpaRepository<CRoom, String> {

}
