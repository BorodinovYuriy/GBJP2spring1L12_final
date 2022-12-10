package ru.gb.buv.spring1L11.api_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.buv.spring1L11.dto.ProductDto;
import ru.gb.buv.spring1L11.service.SingletonCartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/singleton_cart") // http://localhost:8080/myapp - отправная точка (index.html)!!!
@RequiredArgsConstructor
public class SingletonCartController {
    private final SingletonCartService singletonCartService;

    @GetMapping()
    public List<ProductDto> getSingletonCartList(){
        return singletonCartService
                .getCartList();
    }
    @PostMapping
    public void addDtoToSingletonCart(@RequestParam(name = "id")Long id,
                                      @RequestParam(name = "title")String title,
                                      @RequestParam(name = "cost")Long cost
    ){
         singletonCartService
                .addDtoToSingletonCart(new ProductDto(id, title, cost));
    }
    @DeleteMapping("/{id}")
    public void deleteDtoFromSingletonCartById(@PathVariable Long id){
         singletonCartService
                .deleteDtoFromSingletonCart(id);
    }

}
