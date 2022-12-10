
package ru.gb.buv.spring1L11.api_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.buv.spring1L11.converters.ProductConverter;
import ru.gb.buv.spring1L11.dto.ProductDto;
import ru.gb.buv.spring1L11.entity.Product;
import ru.gb.buv.spring1L11.exceptions.ResourceNotFoundException;
import ru.gb.buv.spring1L11.service.ProductService;
import ru.gb.buv.spring1L11.validators.ProductValidator;

@RestController
//Доступ auth
@RequestMapping("/api/v1/products") // http://localhost:8080/myapp - отправная точка (index.html)!!!
@RequiredArgsConstructor
public class ProductsController {
   private final ProductService productService;
   private final ProductConverter productConverter;

   private final ProductValidator productValidator;


    //**************************
    //Основной метод по возвращению фильтрованного списка dto сущностей!
    //Доступ auth
    @GetMapping()
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_cost", required = false) Long minCost,
            @RequestParam(name = "max_cost", required = false) Long maxCost,
            @RequestParam(name = "title_part", required = false) String titlePart
    ){
        if(page < 1){page = 1;}
        return productService.getProducts(minCost,maxCost,titlePart,page).map(
                p -> productConverter.entityToDto(p)
        );
    }
    //**************************

    //Получение сущности по id
    //Доступ auth
    @GetMapping("/{id}")
    public ProductDto getProductDtoById(@PathVariable Long id){
        Product product = productService.getProductById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productConverter.entityToDto(product);
        /*throw new UnsupportedOperationException();*/
    }
    //Обновление
    //Доступ MANAGER ADMIN SUPERADMIN
    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto){
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }
    //Добавление
    //Доступ MANAGER ADMIN SUPERADMIN
    @PostMapping()
    public Page<ProductDto> saveNewProduct(@RequestBody ProductDto productDto){
        productValidator.validate(productDto);
        productDto.setId(null);
        productService.saveProduct(productConverter.dtoToEntity(productDto));
        return getAllProducts(1, null,null, null);
    }
    //Удаление
    //список самообновляется location.reload(); на html после удаления!!!!
    //Доступ MANAGER ADMIN SUPERADMIN
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);

    }
    // TODO: 10.12.2022 реализовать инициализацию!!!
}
