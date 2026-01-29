package org.example.es.Added;

import org.example.es.Entity.Product;

public class ProductMapper {

    public static ProductDocument toDocument(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId().toString());
        doc.setName(product.getName());
        doc.setPrice(product.getPrice());
        doc.setStock(product.getStock());
        return doc;
    }
}
