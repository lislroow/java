package spring.sample.app.service;

import java.util.List;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.vo.FibonacciVo;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticService {
  
  @NonNull
  private ElasticsearchOperations elasticOper;
  
  public Long findByDays2(Integer days) {
    Criteria criteria = Criteria.where("days").is(days);
    CriteriaQuery query = new CriteriaQuery(criteria);
    SearchHits<FibonacciVo> hits = elasticOper.search(query, FibonacciVo.class);
    return hits.getSearchHit(0).getContent().getNum();
  }
  
  public Long findByDays(Integer days) {
    SearchHits<FibonacciVo> hits = elasticOper.search(
        new CriteriaQuery(Criteria.
            where("days").is(days)), FibonacciVo.class);
    return hits.getSearchHit(0).getContent().getNum();
  }
  
  public List<FibonacciVo> findListByDays(Integer fromDays, Integer offset) {
    SearchHits<FibonacciVo> hits = elasticOper.search(
        new CriteriaQuery(
            Criteria.where("days").greaterThanEqual(fromDays)
            .and(Criteria.where("days").lessThan(fromDays + offset))
            ), FibonacciVo.class);
    hits.forEach(mapper -> log.info(mapper.toString()));
    List<FibonacciVo> result = hits.map(mapper -> mapper.getContent()).toList();
    return result;
  }
}
