package org.example.es.Repository;

import org.example.es.Added.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElasticRepository
        extends ElasticsearchRepository<ProductDocument, String> {
}
