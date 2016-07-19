package xyz.selfenrichment.spring_data_rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import xyz.selfenrichment.spring_data_rest.model.Move;

@RepositoryRestResource(collectionResourceRel = "moves", path = "moves")
public interface MoveRepository extends PagingAndSortingRepository<Move, Long> {

}
