package org.personal.user_service.user.repository;

import org.personal.user_service.user.domain.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends CrudRepository<Token, String> {
}
