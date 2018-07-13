package com.yaozou.platform.common.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaozou.platform.common.page.Pagination;
import com.yaozou.platform.common.repository.jdbc.Finder;
import com.yaozou.platform.common.repository.jdbc.Updater;
import com.yaozou.platform.common.utils.MyBeanUtils;
 
 

/**
 * 定制通用工具类
 * @author luojianhong
 * @version $Id: CustomRepositoryImpl.java, v 0.1 2017年7月11日 上午11:54:18 luojianhong Exp $
 * @param <T>
 */
@Repository
public class CustomRepository {
    /**
     * 日志，可用于子类
     */
    protected Logger              log           = LoggerFactory.getLogger(getClass());
    /**
     * HIBERNATE 的 order 属性
     */
    protected static final String ORDER_ENTRIES = "orderEntries";

    /** 
    * 持久化上下文 
    */
    @Autowired
    @PersistenceContext
    private EntityManager         entityManager;

    /**
     * 保存
     * @param bean
     * @return
     */
    public <T> T save(T bean) {
        getSession().save(bean);
        return bean;
    }
    
    /**
     * 删除操作
     * @param bean
     * @return
     */
    public <T> T delete(T bean) {
        getSession().delete(bean);
        return bean;
    }

  
     /**
     * 修改
     * @param bean
     * @return
     */
     public <T> T update(T bean) {
        getSession().evict(bean);
        getSession().update(bean);
        getSession().flush();
        getSession().clear();
        return bean;
    }
   



    /**
     * 通过HQL查询对象列表
     * @param <T>
     * 
     * @param hql
     *            hql语句
     * @param values
     *            数量可变的参数
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> find(String hql, Object... values) {
        return createQuery(hql, values).list();
    }

    /**
     * 通过HQL查询唯一对象
     * @return 
     */
    public <T> Object findUnique(String hql, Object... values) {
        return createQuery(hql, values).uniqueResult();
    }

    /**
     * 通过Finder获得分页数据
     * @param <T>
     * 
     * @param finder
     *            Finder对象
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页条数
     * @return
     */
    public <T> Pagination<T> find(Finder finder, int pageNo, int pageSize) {
        int totalCount = countQueryResult(finder);
        Pagination<T> p = new Pagination<T>(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList<T>());
            return p;
        }
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        @SuppressWarnings("unchecked")
        List<T> list = query.list();
        
        
        p.setList(list);
        return p;
    }

    public <T> Pagination<T> findBigData(Finder finder, int pageNo, int pageSize) {
        int totalCount = pageNo * pageSize;
        Pagination<T> p = new Pagination<T>(pageNo, pageSize, totalCount);
        Query query = getSession().createQuery(finder.getOrigHql());
        finder.setParamsToQuery(query);
        query.setFirstResult(p.getFirstResult());
        query.setMaxResults(p.getPageSize());
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        @SuppressWarnings("unchecked")
        List<T> list = query.list();
        p.setList(list);
        return p;
    }

    /**
     * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
     */
    protected Query createQuery(String queryString, Object... values) {
        Query queryObject = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject;
    }

    /**
     * 通过Criteria获得分页数据
     * @param <T>
     * 
     * @param crit
     * @param pageNo
     * @param pageSize
     * @param projection
     * @param orders
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    protected <T> Pagination<T> findByCriteria(Criteria crit, int pageNo, int pageSize) {
        CriteriaImpl impl = (CriteriaImpl) crit;
        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries;
        try {
            orderEntries = (List<OrderEntry>) MyBeanUtils.getFieldValue(impl, ORDER_ENTRIES);
            MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
        } catch (Exception e) {
            throw new RuntimeException("cannot read/write 'orderEntries' from CriteriaImpl", e);
        }

        int totalCount = ((Number) crit.setProjection(Projections.rowCount()).uniqueResult())
            .intValue();
        Pagination<T> p = new Pagination<T>(pageNo, pageSize, totalCount);
        if (totalCount < 1) {
            p.setList(new ArrayList<T>());
            return p;
        }

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        crit.setProjection(projection);
        if (projection == null) {
            crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            crit.setResultTransformer(transformer);
        }
        try {
            MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, orderEntries);
        } catch (Exception e) {
            throw new RuntimeException("set 'orderEntries' to CriteriaImpl faild", e);
        }
        crit.setFirstResult(p.getFirstResult());
        crit.setMaxResults(p.getPageSize());
        p.setList(crit.list());
        return p;
    }

    protected int countQueryResultByGroup(Finder finder, String selectSql) {
        Query query = getSession().createQuery(finder.getRowCountTotalHql(selectSql));
        setParamsToQuery(finder, query);
        return ((Number) query.iterate().next()).intValue();
    }

    private Query setParamsToQuery(Finder finder, Query query) {
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return query;
    }

    /**
     * 通过Updater更新对象
     * @param <T>
     * @param <T>
     * 
     * @param updater
     * @return
     */
    public <T> T updateByUpdater(Updater<T> updater, Class<T> clazz) {
        ClassMetadata cm = getSession().getSessionFactory().getClassMetadata(clazz);
        T bean = updater.getBean();
        T po = (T) getSession().get(clazz, cm.getIdentifier(bean,
            (SessionImplementor)getSession()));
        updaterCopyToPersistentObject(updater, po, cm);
        
        getSession().flush();
        getSession().clear();
        
        return po;
    }

    /**
     * 将更新对象拷贝至实体对象，并处理many-to-one的更新。
     * @param <T>
     * 
     * @param updater
     * @param po
     */
    private <T> void updaterCopyToPersistentObject(Updater<T> updater, T po, ClassMetadata cm) {
        String[] propNames = cm.getPropertyNames();
        String identifierName = cm.getIdentifierPropertyName();
        T bean = updater.getBean();
        Object value;
        for (String propName : propNames) {
            if (propName.equals(identifierName)) {
                continue;
            }
            try {
                value = MyBeanUtils.getSimplePropertyObj(bean, propName);
                if (!updater.isUpdate(propName, value)) {
                    continue;
                }
                cm.setPropertyValue(po, propName, value);
            } catch (Exception e) {
                throw new RuntimeException(
                    "copy property to persistent object failed: '" + propName + "'", e);
            }
        }
    }

    /**
     * 通过Finder获得列表数据
     * @param finder
     * @return
     */
    public <T> List<T> find(Finder finder) {
        Query query = finder.createQuery(getSession());
        @SuppressWarnings("unchecked")
        List<T> list = query.list();
        return list;
    }

    /**
     * 获得Finder的记录总数
     * 
     * @param finder
     * @return
     */
    public int countQueryResult(Finder finder) {
        org.hibernate.Query query = getSession().createQuery(finder.getRowCountHql());
        finder.setParamsToQuery(query);
        if (finder.isCacheable()) {
            query.setCacheable(true);
        }
        return ((Number) query.iterate().next()).intValue();
    }

    /**
     * @param <T>
     * @see Session.get(Class,Serializable)
     * @param id
     * @return 持久化对象
     */
    public <T> T get(Class<T> clazz, Object id) {
        return get(id, clazz, false);
    }

    /**
     * @param <T>
     * @see Session.get(Class,Serializable,LockMode)
     * @param id
     *            对象ID
     * @param lock
     *            是否锁定，使用LockMode.UPGRADE
     * @return 持久化对象
     */
    protected <T> T get(Object id, Class<T> clazz, boolean lock) {
        T entity;
        if (lock) {
            entity = (T) entityManager.find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
        } else {
            entity = (T) entityManager.find(clazz, id);
        }
        return entity;
    }

    /**
     * 按属性查找对象列表
     * @param <T>
     */
    @SuppressWarnings({ "unchecked" })
    protected <T> List<T> findByProperty(Class<T> clazz, String property, Object value) {
        return createCriteria(clazz, Restrictions.eq(property, value)).list();
    }

    /**
     * 按属性查找唯一对象
     * @param <T>
     */
    @SuppressWarnings({ "unchecked" })
    protected <T> T findUniqueByProperty(Class<T> clazz, String property, Object value) {
        return (T) createCriteria(clazz, Restrictions.eq(property, value)).uniqueResult();
    }

    /**
     * 按属性统计记录数
     * @param <T>
     * 
     * @param property
     * @param value
     * @return
     */
    protected <T> int countByProperty(Class<T> clazz, String property, Object value) {

        return ((Number) (createCriteria(clazz, Restrictions.eq(property, value))
            .setProjection(Projections.rowCount()).uniqueResult())).intValue();
    }

    /**
     * 按Criterion查询列表数据.
     * @param <T>
     * 
     * @param criterion
     *            数量可变的Criterion.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> findByCriteria(Class<T> clazz, Criterion... criterion) {
        return createCriteria(clazz, criterion).list();
    }

    /**
     * 批量修改　新增
     * @param entitys
     */
    public <T> void batchSaveOrUpdate(List<T> entitys) {
        
        for (int i = 0; i < entitys.size(); i++) {
            getSession().saveOrUpdate(entitys.get(i));
            if (i % 20 == 0) {
                // 20个对象后才清理缓存，写入数据库
                getSession().flush();
                getSession().clear();
            }
        }
        
        // 最后清理一下----防止大于20小于40的不保存
        getSession().flush();
        getSession().clear();
    }

    /**
     * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
     * @param <T>
     */
    protected <T> Criteria createCriteria(Class<T> clazz, Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(clazz);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        //设置可查询缓存
        criteria.setCacheable(true);
        return criteria;
    }

    public Session getSession() {
        Session session = (Session) entityManager.getDelegate();
        return session;
    }
}
