package com.trustpilot.parser.repository;

import com.trustpilot.parser.model.Domain;
import com.trustpilot.parser.model.ParseStatus;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DomainRepository extends ReactiveCrudRepository<Domain, Long> {
  @Query("UPDATE domain SET parse_status = :parseStatus WHERE id = :id")
  Mono<Void> updateParseStatusById(Long id, ParseStatus parseStatus);

  @Query("SELECT COUNT(*) FROM domain WHERE domain_url = :domainUrl")
  Mono<Integer> countByDomainUrl(String domainUrl);

  @Query("SELECT * FROM domain WHERE domain_url LIKE CONCAT('%', :domainUrl) AND parse_status = 'FINISH' ORDER BY id DESC LIMIT 1")
  Mono<Domain> findParsedDomainByDomainName(@Param("domainUrl") String domainUrl);

}
