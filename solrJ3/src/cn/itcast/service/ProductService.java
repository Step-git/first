package cn.itcast.service;

import org.springframework.ui.Model;

import cn.itcast.pojo.ResultModel;

public interface ProductService {
   //根据条件搜索信息列表
	public ResultModel getProductList (String queryString,String catalog_name,String price,
			Integer page, String sort) throws Exception;
}
