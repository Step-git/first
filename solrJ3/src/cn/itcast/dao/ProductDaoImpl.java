package cn.itcast.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.pojo.ProductModel;
import cn.itcast.pojo.ResultModel;
@Repository
public class ProductDaoImpl implements ProductDao{
    @Autowired
	public HttpSolrServer solrServer;
	@Override
	public ResultModel getProductList(SolrQuery query) throws Exception {
		// TODO Auto-generated method stub
		QueryResponse response = solrServer.query(query);
		//获取查询结果集
  		SolrDocumentList results = response.getResults();
  		//获取高亮信息结果集
  		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
  		//定义返回结果
  		ResultModel resultModel = new ResultModel();
  		ArrayList<ProductModel> productList = new ArrayList<ProductModel>();

  		
  		//遍历结果集打印结果
         for (SolrDocument solrDocument : results) {
        	 ProductModel productModel = new ProductModel();
        	 //高亮设置
 	  		//先取德原始的商品名称
        	 String orgProductName = String.valueOf(solrDocument.get("product_name")); 	  		
 	  		//高亮设置
        	if (highlighting != null) {
        		List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
        		if(list != null && list.size()>0){
        			orgProductName = list.get(0);
        		}
        	}
        	 
        	productModel.setPid(String.valueOf(solrDocument.get("id")));
        	productModel.setName(orgProductName);
        	productModel.setCatalog_name(String.valueOf(solrDocument.get("product_catalog_name")));
        	productModel.setDescription(String.valueOf(solrDocument.get("product_description")));
        	productModel.setPrice(Float.parseFloat(String.valueOf(solrDocument.get("product_price"))));
        	productModel.setPicture(String.valueOf(solrDocument.get("product_picture")));
        	productList.add(productModel);
		}  		
  		
         //在项目中一定要判断严禁
         if(productList.size()>0){
        	 resultModel.setProductList(productList);
         }
         //设置总件数
         resultModel.setRecordCount(results.getNumFound());
       
         
		return resultModel;
	}

}
