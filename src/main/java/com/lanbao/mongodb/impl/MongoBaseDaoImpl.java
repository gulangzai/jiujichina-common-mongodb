package com.lanbao.mongodb.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.mongodb.WriteResult;

import com.lanbao.base.Page;
import com.lanbao.mongodb.MongoBaseDao;
import com.lanbao.utils.ReflectionUtils;

/*@Repository("mongoBaseDao") */
public class MongoBaseDaoImpl<T> implements MongoBaseDao<T> {

	/*@Autowired*/
	protected MongoTemplate mongoTemplate;
	
	@Override
	public T save(T entity) {
		// TODO Auto-generated method stub
		mongoTemplate.insert(entity);
		return entity;
	}
	
	public T save(T entity,String name){
		mongoTemplate.insert(entity,name);
		return entity;
	}
	
	public T findById(String id) {  
	        return mongoTemplate.findById(id, this.getEntityClass());  
    }  
	
	 public List<T> findAll() {  
	        return mongoTemplate.findAll(this.getEntityClass());  
	 }  
	 
	 public T findOne(Query query) {  
	        return mongoTemplate.findOne(query, this.getEntityClass());  
     } 
	 
	
	public List<T> find(Query query){
		return mongoTemplate.find(query, this.getEntityClass());
	}
	
	
	/** 
     * 锟斤拷梅锟斤拷锟斤拷锟� 
     */  
	public Class<T> getEntityClass() {   
        return ReflectionUtils.getSuperClassGenricType(getClass());  
    }

	@Override
	public long count(Query query) {
		// TODO Auto-generated method stub
		return mongoTemplate.count(query, this.getEntityClass());
	}

	@Override
	public Page<T> findPage(Page<T> page, Query query) {
		// TODO Auto-generated method stub
		//锟斤拷锟矫伙拷锟斤拷锟斤拷锟� 锟斤拷锟斤拷锟斤拷全锟斤拷  
        query=query==null?new Query(Criteria.where("_id").exists(true)):query;  
        long count = this.count(query);  
        // 锟斤拷锟斤拷  
        page.setTotalResult((int) count);  
        int currentPage = page.getCurrentPage();  
        int pageSize = page.getPageSize();  
        query.skip((currentPage - 1) * pageSize).limit(pageSize);  
        List<T> rows = this.find(query);  
        page.build(rows);  
        return page; 
	}   
	
	  public WriteResult update(Query query, Update update) {  
	        if (update==null) {  
	            return null;  
	        }  
	        return mongoTemplate.updateMulti(query, update, this.getEntityClass());  
	 } 
	  
	  
	  public T updateOne(Query query, Update update) {  
	        if (update==null) {  
	            return null;  
	        }  
	        return mongoTemplate.findAndModify(query, update, this.getEntityClass());  
	    }  
	  
	    public WriteResult update(T entity) {  
	        Field[] fields = this.getEntityClass().getDeclaredFields();  
	        if (fields == null || fields.length <= 0) {  
	            return null;  
	        }  
	        Field idField = null;  
	        // 锟斤拷锟斤拷ID锟斤拷field  
	        for (Field field : fields) {  
	            if (field.getName() != null  
	                    && "id".equals(field.getName().toLowerCase())) {  
	                idField = field;  
	                break;  
	            }  
	        }  
	        if (idField == null) {  
	            return null;  
	        }  
	        idField.setAccessible(true);  
	        String id=null;  
	        try {  
	            id = (String) idField.get(entity);  
	        } catch (IllegalArgumentException e) {  
	            e.printStackTrace();  
	        } catch (IllegalAccessException e) {  
	            e.printStackTrace();  
	        }  
	        if (id == null || "".equals(id.trim()))  
	            return null;  
	        // 锟斤拷锟斤拷ID锟斤拷锟斤拷  
	        Query query = new Query(Criteria.where("_id").is(id));  
	        // 锟斤拷锟斤拷  
	        Update update = ReflectionUtils.getUpdateObj(entity);  
	        if (update == null) {  
	            return null;  
	        }  
	        return mongoTemplate.updateFirst(query, update, getEntityClass());  
	    }  
	  
	    public void remove(Query query) {  
	        mongoTemplate.remove(query, this.getEntityClass());  
	    }  
	    
	
}
