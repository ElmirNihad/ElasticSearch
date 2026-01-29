package org.example.es.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import org.example.es.Added.ProductDocument;
import org.example.es.Added.ProductMapper;
import org.example.es.Entity.Product;
import org.example.es.Repository.ProductElasticRepository;
import org.example.es.Repository.ProductRepository;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductElasticRepository elasticRepository;
    private final ElasticsearchClient elasticsearchClient;

    public ProductService(ProductRepository productRepository,
                          ProductElasticRepository elasticRepository,
                          ElasticsearchClient elasticsearchClient) {
        this.productRepository = productRepository;
        this.elasticRepository = elasticRepository;
        this.elasticsearchClient = elasticsearchClient;
    }


    public Product save(Product product) {
        Product saved = productRepository.save(product);


        ProductDocument doc = ProductMapper.toDocument(saved);
        elasticRepository.save(doc);

        return saved;
    }


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<ProductDocument> search(String name, Double minPrice, Double maxPrice) throws IOException {

        BoolQuery.Builder boolBuilder = new BoolQuery.Builder();

        // 1️⃣ Name search
        if (name != null && !name.isEmpty()) {
            boolBuilder.must(q -> q
                    .matchPhrasePrefix(mpp -> mpp
                            .field("name")
                            .query(name)
                    )
            );

        }

        if (minPrice != null || maxPrice != null) {
            boolBuilder.filter(f -> f.range(r -> {
                r.field("price");
                if (minPrice != null) r.gte(JsonData.of(minPrice));
                if (maxPrice != null) r.lte(JsonData.of(maxPrice));
                return r;
            }));
        }


        Query boolQuery = Query.of(q -> q.bool(boolBuilder.build()));


        SearchRequest request = SearchRequest.of(s -> s
                .index("products")
                .query(boolQuery)
        );


        SearchResponse<ProductDocument> response = elasticsearchClient.search(request, ProductDocument.class);


        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }



}
