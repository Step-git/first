package cn.itcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.pojo.ResultModel;
import cn.itcast.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
	public ProductService productService;
	@RequestMapping("/list")
	public String list(String queryString,String catalog_name,String price,
			Integer page, String sort,Model model) throws Exception{
		
		ResultModel resultModel = productService.getProductList(queryString, catalog_name, price, page, sort);
		
		model.addAttribute("queryString",queryString);
		model.addAttribute("catalog_name",catalog_name);
		model.addAttribute("price",price);
		//model.addAttribute("result", );
		model.addAttribute("sort",sort);
		
		model.addAttribute("result",resultModel );
		return "product_list";
	}
	 
}
