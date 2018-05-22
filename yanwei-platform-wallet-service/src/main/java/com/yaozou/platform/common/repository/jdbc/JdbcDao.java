package com.yanwei.platform.common.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import com.yanwei.platform.common.page.Pagination;
import com.yanwei.platform.common.utils.MyBeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 通用jdbc 数据库查询类
 * @author luojianhong
 * @version $Id: JdbcDao.java, v 0.1 2017年7月11日 下午4:34:09 luojianhong Exp $
 */
@Repository
public class JdbcDao {

    @Autowired
    private JdbcTemplate       jdbcTemplate;
    /**
     * 分页SQL
     */
    public static final String MYSQL_SQL       = "select * from ( {0}) sel_tab00 limit {1},{2}"; //mysql
    public static final String MYSQL_COUNT_SQL = " select count(*) from ({0}) sel_tab00";

    /**
     * 根据sql语句，返回对象集合
     * @param <T>
     * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
     * @param clazz类型
     * @param parameters参数集合(key为参数名，value为参数值)
     * @return bean对象集合
     */
    public <T> List<T> find(final String sql, Class<T> clazz, Map<String, Object> parameters) {
        try {
            if (parameters != null) {
                return jdbcTemplate.query(sql, resultBeanMapper(clazz), parameters);
            } else {
                return jdbcTemplate.query(sql, resultBeanMapper(clazz));
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据sql语句，返回对象
     * @param <T>
     * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
     * @param clazz类型
     * @param parameters参数集合(key为参数名，value为参数值)
     * @return bean对象
     */
    public <T> T findForObject(final String sql, Class<T> clazz, Map<String, Object> parameters) {
        try {
            if (parameters != null) {
                return (T) jdbcTemplate.queryForObject(sql, resultBeanMapper(clazz), parameters);
            } else {
                return (T) jdbcTemplate.queryForObject(sql, resultBeanMapper(clazz));
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据sql语句，返回Map对象集合
     * @see findForMap
     * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
     * @param parameters参数集合(key为参数名，value为参数值)
     * @return bean对象
     */
    public List<Map<String, Object>> findForListMap(final String sql,
                                                    Map<String, Object> parameters) {
        try {
            if (parameters != null) {
                return jdbcTemplate.queryForList(sql, parameters);
            } else {
                return jdbcTemplate.queryForList(sql);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 执行insert，update，delete等操作<br>
     * 例如insert into users (name,login_name,password) values(:name,:loginName,:password)<br>
     * 参数用冒号,参数为bean的属性名
     * @param sql
     * @param bean
     */
    public int executeForObject(final String sql, Object bean) {
        if (bean != null) {
            return jdbcTemplate.update(sql, paramBeanMapper(bean));
        } else {
            return jdbcTemplate.update(sql);
        }
    }

    /**
     * 执行insert，update，delete等操作<br>
     * 例如insert into users (name,login_name,password) values(:name,:login_name,:password)<br>
     * 参数用冒号,参数为Map的key名
     * @param sql
     * @param parameters
     */
    public int executeForMap(final String sql, Map<String, Object> parameters) {
        if (parameters != null) {
            return jdbcTemplate.update(sql, parameters);
        } else {
            return jdbcTemplate.update(sql);
        }
    }

    /*
     * 批量处理操作
     * 例如：update t_actor set first_name = :firstName, last_name = :lastName where id = :id
     * 参数用冒号
     */
    public int[] batchUpdate(final String sql, List<Object[]> batch) {
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, batch);
        return updateCounts;
    }

    /**
     * 使用指定的检索标准检索数据并分页返回数据
     */
    public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
        //封装分页SQL
        sql = createPageSql(sql, page, rows);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
        return this.jdbcTemplate.queryForList(sql, objs);
    }

    /**
     * 使用指定的检索标准检索数据并分页返回数据
     * @param <T>
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public <T> List<T> findObjForJdbc(String sql, int page, int rows, Class<T> clazz) {
        List<T> rsList = new ArrayList<T>();
        //封装分页SQL
        sql = createPageSql(sql, page, rows);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        T po = null;
        for (Map<String, Object> m : mapList) {
            try {
                po = clazz.newInstance();
                MyBeanUtils.copyMap2Bean_Nobig(po, m);
                rsList.add(po);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rsList;
    }

    /**
     * 使用指定的检索标准检索数据并分页返回数据-采用预处理方式
     * 
     * @param criteria
     * @param firstResult
     * @param maxResults
     * @return
     * @throws DataAccessException
     */
    public List<Map<String, Object>> findForJdbcParam(String sql, int page, int rows,
                                                      Object... objs) {
        //封装分页SQL
        sql = createPageSql(sql, page, rows);
        return jdbcTemplate.queryForList(sql, objs);
    }

    public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
        try {
            return this.jdbcTemplate.queryForMap(sql, objs);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer executeSql2(String sql, List<Object> param) {
        return this.jdbcTemplate.update(sql, param);
    }

    public Integer executeSql(String sql, Object... param) {
        return this.jdbcTemplate.update(sql, param);
    }

    /**
     * sql 分页查询
     * @param sqlCountRows
     * @param sqlFetchRows
     * @param args
     * @param pageNo
     * @param pageSize
     * @param rowMapper
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> Pagination<T> fetchPage(final String sqlCountRows, final String sqlFetchRows,
                                       final Object args[], final int pageNo, final int pageSize,
                                       final BeanPropertyRowMapper<T> rowMapper) {
        // determine how many rows are available  
        final int rowCount = jdbcTemplate.queryForObject(sqlCountRows, args, Integer.class);
        // calculate the number of pages  
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }
        // add the page object
        final Pagination<T> page = new Pagination<T>();
        page.setPageNo(pageNo);
        page.setTotalCount(rowCount);
        // fetch a single page of results  
        final int startRow = (pageNo - 1) * pageSize;
        jdbcTemplate.query(sqlFetchRows, args, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                final List<T> pageItems = new ArrayList<T>();
                int currentRow = 0;
                while (rs.next() && currentRow < startRow + pageSize) {
                    if (currentRow >= startRow) {
                        pageItems.add(rowMapper.mapRow(rs, currentRow));
                    }
                    currentRow++;
                }
                page.setList(pageItems);
                return page;
            }
        });
        return page;
    }

    /**
     * sql 分页查询
     * @param sqlFetchRows
     * @param pageNo
     * @param pageSize
     * @param clazz
     * @return
     */
    public <T> Pagination<T> getPage(final String sqlFetchRows, final int pageNo,
                                     final int pageSize, Class<T> clazz) {
        String sqlCountRows = JdbcDao.createCountSql(sqlFetchRows);
        BeanPropertyRowMapper<T> rowMapper = resultBeanMapper(clazz);
        return fetchPage(sqlCountRows, sqlFetchRows, null, pageNo, pageSize, rowMapper);
    }

    /**
     * 查询总数
     * 按照数据库类型，封装SQL
     * @param sql
     * @return
     */
    public static String createCountSql(String sql) {
        sql = MessageFormat.format(MYSQL_COUNT_SQL, sql);
        return sql;
    }

    /**
     * 按照数据库类型，封装SQL
     * @param sql
     * @param page
     * @param rows
     * @return
     */
    public static String createPageSql(String sql, int page, int rows) {
        int beginNum = (page - 1) * rows;
        sql = MessageFormat.format(MYSQL_SQL, sql, beginNum, rows);
        return sql;
    }

    protected static <T> BeanPropertyRowMapper<T> resultBeanMapper(Class<T> clazz) {
        return BeanPropertyRowMapper.newInstance(clazz);
    }

    protected BeanPropertySqlParameterSource paramBeanMapper(Object object) {
        return new BeanPropertySqlParameterSource(object);
    }

    /** 
     * 添加操作 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public boolean save(Object entity) throws IllegalArgumentException, IllegalAccessException,
                                       NoSuchMethodException, SecurityException {
        StringBuffer insertSql = new StringBuffer("insert into ");
        StringBuffer insertSqlValue = new StringBuffer();
        LinkedList<Object> insertParams = new LinkedList<Object>();
        Class<?> entityClass = entity.getClass();
        String tableName = getTableName(entityClass);
        insertSql.append(tableName);
        Field[] fields = entityClass.getDeclaredFields();
        insertSql.append("(");
        insertSqlValue.append(" values(");
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            String columnName = field.getName();
            // 查找当前属性上面是否有annotation注解   
            Object[] findAnnotationResult = findAnnotation(annotations);
            Boolean isAnnotaionOverField = (Boolean) findAnnotationResult[0];
            // 如果在field中上面没有找到annotation,继续到get属性上去找有没有annotation   
            if (!isAnnotaionOverField) {
                // 拼接出field的get属性名   
                String getMethodName = "get" + columnName.substring(0, 1).toUpperCase()
                                       + columnName.substring(1);
                Method method = entityClass.getMethod(getMethodName, new Class[] {});
                // 同上判断这个方法有没有我们要找的annotation   
                annotations = method.getAnnotations();
                findAnnotationResult = findAnnotation(annotations);
                isAnnotaionOverField = (Boolean) findAnnotationResult[0];
            }
            // 判断通过前面两步操作有没有在当前的字段上面找到有效的annotation   
            if (!isAnnotaionOverField)
                continue;
            // 到这步说明在当前的字段或字段get属性上面找到有效的annotation了   
            // 拼接insert sql 语句   
            String tempColumnName = (String) findAnnotationResult[1];
            if (tempColumnName != null && !"".equals(tempColumnName))
                columnName = tempColumnName;
            insertSql.append(columnName).append(",");// 前面列名部分   
            insertSqlValue.append("?,"); // 后面?参数部分   
            // 得到对应的字段值并记录,作为以后?部分值   
            field.setAccessible(true);
            insertParams.add(field.get(entity));
        }
        insertSql.replace(insertSql.lastIndexOf(","), insertSql.length(), ")");
        insertSqlValue.replace(insertSqlValue.lastIndexOf(","), insertSqlValue.length(), ")");
        // 拼接两部分的sql   
        insertSql.append(insertSqlValue);
        System.out.println(insertSql);
        Object[] objs = new Object[insertParams.size()];
        // 执行添加操作了   
        int i = 0;
        for (Object param : insertParams) {
            if (param instanceof Date) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date date = java.sql.Date.valueOf(dateFormat.format(param));
                objs[i] = date;
            } else {
                objs[i] = param;
            }
            i++;
        }
        if (this.jdbcTemplate.update(insertSql.toString(), objs) > 0)
            return true;
        return false;
    }

    /** 
    * 得到表的真实名 
    */
    private String getTableName(Class<?> entityClass) {
        String tableName = entityClass.getSimpleName();
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table entityAnnotation = entityClass.getAnnotation(Table.class);
            String tempTableName = entityAnnotation.name();
            if (tempTableName != null && !"".equals(tempTableName))
                tableName = tempTableName;
        }
        return tableName;
    }

    /** 
    * 查询字段或是属性上面有没有有效annotation 
    */
    private Object[] findAnnotation(Annotation[] annotations) {
        Object[] resurlt = new Object[] { false, null };
        if (annotations.length == 0)
            return resurlt;
        for (Annotation annotation : annotations) {
            // 我们假定当他找到下列标签中任何一个标签就认为是要与数据库映射的   
            if (annotation instanceof Column) {
                resurlt[0] = true;
                Column column = (Column) annotation;
                String tempColumnName = column.name();
                if (tempColumnName != null && !"".equals(tempColumnName))
                    resurlt[1] = tempColumnName;
            }
        }
        return resurlt;
    }

    /**
     *  修改操作 
     * @param entity
     * @return
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    public boolean update(Object entity) throws IllegalArgumentException, IllegalAccessException,
                                         NoSuchMethodException, SecurityException {
        StringBuffer updateSql = new StringBuffer("update ");
        LinkedList<Object> updateParams = new LinkedList<Object>();
        String primaryKeyColumn = "";
        Object primaryParam = null;
        Class<?> entityClass = entity.getClass();
        String tableName = getTableName(entityClass);

        updateSql.append(tableName).append(" tab set ");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            String columnName = field.getName();
            Annotation[] annotations = field.getAnnotations();
            // 判断是否是主键   
            boolean isfindPrimarykey = false;
            for (Annotation annotation : annotations) {
                if (annotation instanceof Id) {
                    primaryKeyColumn = field.getName();
                    field.setAccessible(true);
                    primaryParam =  field.get(entity);
                    isfindPrimarykey = true;
                    break;
                }

            }
            if (isfindPrimarykey)
                continue;
            Object[] findAnnotationResult = findAnnotation(annotations);
            boolean isAnnotaionOverField = (Boolean) findAnnotationResult[0];
            if (!isAnnotaionOverField) {
                String getMethodName = "get" + columnName.substring(0, 1).toUpperCase()
                                       + columnName.substring(1);
                Method method = entityClass.getMethod(getMethodName, new Class[] {});
                annotations = method.getAnnotations();
                findAnnotationResult = findAnnotation(annotations);
                isAnnotaionOverField = (Boolean) findAnnotationResult[0];
            }
            if (!isAnnotaionOverField)
                continue;
            String tempColumnName = (String) findAnnotationResult[1];
            if (tempColumnName != null && !"".equals(tempColumnName))
                columnName = tempColumnName;
            updateSql.append("tab.").append(columnName).append("=?,");
            field.setAccessible(true);
            updateParams.add(field.get(entity));
        }
        updateSql.replace(updateSql.lastIndexOf(","), updateSql.length(), "");
        updateSql.append(" where tab.").append(primaryKeyColumn).append("=?");
        System.out.println(updateSql);

        Object[] objs = new Object[updateParams.size()];
        int i = 1;
        for (Object param : updateParams) {
            if (param instanceof Date) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date date = java.sql.Date.valueOf(dateFormat.format(param));

                objs[i] = date;
            } else {
                objs[i] = param;
            }
            i++;
        }
        objs[i] = primaryParam;
        if (this.jdbcTemplate.update(updateSql.toString(), objs) > 0)
            return true;
        return false;
    }

    /** 
    *  删除操作 
    */
    public boolean delete(Object entity) throws IllegalArgumentException, IllegalAccessException,
                                         ClassNotFoundException, SQLException {
        StringBuffer deleteSql = new StringBuffer("delete from ");
        Integer primaryParam = null;
        Class<?> entityClass = entity.getClass();
        String tableName = getTableName(entityClass);
        deleteSql.append(tableName).append(" tab ").append("where ");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            boolean isfindPrimary = false;
            for (Annotation annotation : annotations) {
                if (annotation instanceof Id) {
                    deleteSql.append("tab.").append(field.getName()).append("=?");
                    field.setAccessible(true);
                    primaryParam = (Integer) field.get(entity);
                    isfindPrimary = true;
                    break;
                }
            }
            if (isfindPrimary)
                break;
        }
        Object[] objs = new Object[1];
        objs[0] = primaryParam;
        if (this.jdbcTemplate.update(deleteSql.toString(), objs) > 0)
            return true;
        return false;
    }

}
