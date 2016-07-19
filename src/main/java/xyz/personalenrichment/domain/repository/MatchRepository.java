package xyz.personalenrichment.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import xyz.personalenrichment.domain.model.Match;

@RepositoryRestResource(collectionResourceRel = "matches", path = "matches")
public interface MatchRepository extends PagingAndSortingRepository<Match, Long> {

}
