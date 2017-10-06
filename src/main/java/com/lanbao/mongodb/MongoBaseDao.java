package com.lanbao.mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import com.lanbao.base.Page;

public interface MongoBaseDao<T> {
	
	public T save(T entity);
	
	public T save(T entity,String name);
	
	public long count(Query query);
	
	/** 
     * ��ҳ��ѯ 
     */  
    public Page<T> findPage(Page<T> page, Query query);

	public T findById(String id);

	public List<T> findAll();

	public T findOne(Query query);

	public List<T> find(Query query);

	public WriteResult update(Query query, Update update);

	public T updateOne(Query query, Update update);

	public WriteResult update(T entity);

	public void remove(Query query);  
    
}
