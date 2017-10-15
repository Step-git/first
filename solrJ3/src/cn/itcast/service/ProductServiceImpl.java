package cn.itcast.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.ResultModel;
@Service
public class ProductServiceImpl implements ProductService{

	public static final int PAGE_SIZE = 60;
	@Autowired
	public ProductDao productDao;
	@Override
	public ResultModel getProductList(String queryString, String catalog_name,
			String price, Integer page, String sort
			) throws Exception {
		// TODO Auto-generated method stub
		 SolrQuery solrQuery = new SolrQuery();
	        //设置查询字符串
		 if(queryString !=null && !"".equals(queryString)){
			 solrQuery.setQuery(queryString);
		 }else{
			 solrQuery.setQuery("*:*");
		 }
		 
	     //设置查询过虑条件
		 if(catalog_name != null && !"".equals(catalog_name)){
			 solrQuery.setFilterQueries("product_catalog_name:"+catalog_name);
			 //solrQuery.addFilterQuery(""); 也可以这么写
		 }
		 //价格过滤
		 if(price !=null && !"".equals(price)){
			 String[] split = price.split("-");
			 if(split !=null && split.length>1){
				 solrQuery.setFilterQueries("product_price:["+split[0]+" TO "+split[1]+"]");
			 }else if(split !=null && split.length==1){
				 solrQuery.setFilterQueries("product_price:[50 TO *]");
			 }
		 }
        //设置排序条件
		 if("1".equals(sort)){
			 solrQuery.setSort("product_price", ORDER.asc);
		 } else {
			 solrQuery.setSort("product_price", ORDER.desc);
		 }
        //设置分页信息（开始索引，每页条数）
		 if( page == null || page <=1){
			 page=1;
		 }
		 solrQuery.setStart((page-1) * PAGE_SIZE);
		 solrQuery.setRows(PAGE_SIZE);
        //设置默认搜索域
        solrQuery.set("df", "product_keywords");
        //高亮设置（是否要高亮显示，高亮显示的域，高亮显示的前缀和后缀）
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color=\"red\">");
        solrQuery.setHighlightSimplePost("</font>");
        
        ResultModel resultModel = productDao.getProductList(solrQuery);
        
        //计算出总页数
         Long pageCount=resultModel.getRecordCount() / PAGE_SIZE;
         if(resultModel.getRecordCount() % PAGE_SIZE >0){
        	 pageCount++;
         }
         resultModel.setPageCount(pageCount);
         //当前页
         resultModel.setCurPage(page);
		return resultModel;
		
	}
}
