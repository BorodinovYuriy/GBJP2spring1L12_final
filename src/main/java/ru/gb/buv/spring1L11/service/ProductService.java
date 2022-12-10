
package ru.gb.buv.spring1L11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.buv.spring1L11.dto.ProductDto;
import ru.gb.buv.spring1L11.exceptions.ResourceNotFoundException;
import ru.gb.buv.spring1L11.repository.ProductRepository;
import ru.gb.buv.spring1L11.entity.Product;
import ru.gb.buv.spring1L11.repository.specification.ProductSpecification;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //*****************************************
    //find Page method!!!
    public Page<Product> getProducts(Long minCost, Long maxCost, String titlePart, Integer page){
        Specification<Product> spec = Specification.where(null);
        if(minCost != null){
            spec = spec.and(ProductSpecification.costGreaterOrEqualsThan(minCost));
        }
        if(maxCost != null){
            spec = spec.and(ProductSpecification.costLessOrEqualsThan(maxCost));
        }
        if(titlePart != null){
            spec = spec.and(ProductSpecification.nameLike(titlePart));
        }
        return (productRepository.findAll(spec,PageRequest.of(page -1,5)))/*.stream().toList()*/;
    }
    //*****************************************
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(String title, Long cost){
        Product product = new Product();
        product.setTitle(title);
        product.setCost(cost);
        return product;
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    public Long getCount() {
        return productRepository.count();
    }

    public Page<Product> getPage(Integer offset, Integer limit) {
        return productRepository
                .findAll(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id")));

    }
    //Updates method
    @Transactional
    public Product update(ProductDto productDto){
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Невозможно найти данный объект id: "+ productDto.getId()));
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        return product;
    }

    //Save method
    public void saveProduct(Product product){
        productRepository.save(product);
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    protected void fillDataBaseAfterStartApplication() {
        for (int i = 0; i < 100; i++) {
            saveProduct(createProduct("prod-" + i, 100L + i * 2));
        }
    }
}
