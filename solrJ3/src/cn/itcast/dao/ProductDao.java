package cn.itcast.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.pojo.ResultModel;

public interface ProductDao {

	public ResultModel getProductList (SolrQuery query) throws Exception;
	
}
