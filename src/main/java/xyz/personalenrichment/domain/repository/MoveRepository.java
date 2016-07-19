package xyz.personalenrichment.domain.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import xyz.personalenrichment.domain.model.Move;

@RepositoryRestResource(collectionResourceRel = "moves", path = "moves")
public interface MoveRepository extends PagingAndSortingRepository<Move, Long> {

}
