package xyz.selfenrichment.spring_data_rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import xyz.selfenrichment.spring_data_rest.model.Match;

@RepositoryRestResource(collectionResourceRel = "matches", path = "matches")
public interface MatchRepository extends PagingAndSortingRepository<Match, Long> {

}
