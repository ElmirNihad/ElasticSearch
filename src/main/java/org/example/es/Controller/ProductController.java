package org.example.es.Controller;

import org.example.es.Added.ProductDocument;
import org.example.es.Entity.Product;
import org.example.es.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping(path = "/save")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }


    @GetMapping(path = "/all")
    public List<Product> getAll() {
        return productService.getAll();
    }


    @GetMapping(path = "/search")
    public List<ProductDocument> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) throws IOException {
        return productService.search(name, minPrice, maxPrice);
    }
}
