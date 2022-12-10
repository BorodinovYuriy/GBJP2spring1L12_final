package ru.gb.buv.spring1L11.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.buv.spring1L11.entity.Product;
//Спецификация для поиска: больше заданного/меньше/часть тайтла
public class ProductSpecification {

    public static Specification<Product> costGreaterOrEqualsThan(Long cost){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"),cost);
    }

    public static Specification<Product> costLessOrEqualsThan(Long cost){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"),cost);
    }

    public static Specification<Product> nameLike(String titlePart){
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("title"),String.format("%%%s%%",titlePart));
    }






}
