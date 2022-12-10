package ru.gb.buv.spring1L11.singleton_cart;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gb.buv.spring1L11.dto.ProductDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Корзина простая, без указания количества, одна на всех)))
//Это временное решение - для ДЗ
@Component
/*@Scope("prototype") //Создание по требованию экземпляра! -это дальше возможно понадобится*/
public class SingletonCart {

    List<ProductDto> cartList = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void test(){
        addDtoToCart(new ProductDto(999L,"test_title_1",999999L));
        addDtoToCart(new ProductDto(888L,"test_title_2",888888L));
        System.out.println(Arrays.toString(cartList.toArray()));
    }

    public List<ProductDto> getCartList() {
        return cartList;
    }

    public void addDtoToCart(ProductDto productDto){
        cartList.add(productDto);
    }
    public void deleteDtoFromCartById(Long id){
        for (int i = 0; i < cartList.size(); i++) {
            if(cartList.get(i).getId().equals(id)){
                cartList.remove(i);
            }
        }
    }
}
