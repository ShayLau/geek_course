package com.github.shaylau.geekCourse.controller;

import com.github.shaylau.geekCourse.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ShayLau
 * @date 2022/3/26 6:42 PM
 */
@RequestMapping("/product")
@RestController
public class ProductController {


    @Autowired
    private StockService stockService;


    @RequestMapping(value = "/sale", method = RequestMethod.GET)
    public void saleProduct(@RequestParam String productId) {
        if (stockService.saleProduct(productId)) {
            System.out.println("销售一个产品库存！");
        }else{
            System.out.println("暂无库存");
        }

    }

    @RequestMapping(value = "/limit", method = RequestMethod.GET)
    public void limitSaleProduct(@RequestParam String productId) {
        stockService.limitSaleProduct(productId);
    }

}
