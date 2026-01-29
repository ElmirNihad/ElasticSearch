package org.example.es.Added;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDocument {

    @Id
    private String id;

    private String name;
    private Double price;
    private Integer stock;
}
