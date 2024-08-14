package spring.app.common.elastic.api;

import java.util.List;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticService {
  
  @NonNull
  private ElasticsearchOperations elasticOper;
  
  public Long findByDays2(Integer days) {
    Criteria criteria = Criteria.where("days").is(days);
    CriteriaQuery query = new CriteriaQuery(criteria);
    SearchHits<Fibonacci> hits = elasticOper.search(query, Fibonacci.class);
    return hits.getSearchHit(0).getContent().getNum();
  }
  
  public Long findByDays(Integer days) {
    SearchHits<Fibonacci> hits = elasticOper.search(
        new CriteriaQuery(Criteria.
            where("days").is(days)), Fibonacci.class);
    return hits.getSearchHit(0).getContent().getNum();
  }
  
  public List<Fibonacci> findListByDays(Integer fromDays, Integer offset) {
    SearchHits<Fibonacci> hits = elasticOper.search(
        new CriteriaQuery(
            Criteria.where("days").greaterThanEqual(fromDays)
            .and(Criteria.where("days").lessThan(fromDays + offset))
            ), Fibonacci.class);
    hits.forEach(mapper -> log.info(mapper.toString()));
    List<Fibonacci> result = hits.map(mapper -> mapper.getContent()).toList();
    return result;
  }
}
