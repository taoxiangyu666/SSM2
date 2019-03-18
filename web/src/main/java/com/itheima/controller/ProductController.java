package com.itheima.controller;


import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/findById.do")

    public ModelAndView findById(){
        Product product = productService.findById("itcast-001");
        System.out.println(product);
        ModelAndView mv = new ModelAndView();
        mv.addObject("product",product);
        mv.setViewName("index");
        return mv;
    }

    /**
     * 查询所有产品信息
     * @return
     */
    @RequestMapping("/findAll.do")
//    @Secured("ROLE_USER")
    @RolesAllowed("USER")
    public ModelAndView findAll(){
        List<Product> productList = productService.findAll();
        ModelAndView mv = new ModelAndView();
        mv.addObject("productList",productList);
        mv.setViewName("product_list");
        return mv;
    }

    /**
     * 添加产品
     * @param product
     * @return
     */
    @RequestMapping("/save.do")
    public String save(Product product){
        productService.save(product);
        return "redirect:findAll.do";
    }
}
