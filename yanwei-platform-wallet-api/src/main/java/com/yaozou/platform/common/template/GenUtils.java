package com.yanwei.platform.common.template;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.yanwei.platform.common.domain.ColumnDO;
import com.yanwei.platform.common.domain.TableDO;
import com.yanwei.platform.common.utils.BDException;
import com.yanwei.platform.common.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * 代码生成器 工具类
 * @author luojh
 *@version $Id: GenUtils.java, v 0.1 2017年10月9日 下午4:22:58 luojianhong Exp $
 */
public class GenUtils {

	static Properties props = getProperties();

	public static final String SPT = File.separator;

	public static final String ENCODING = "UTF-8";

	public static List<String> getTemplates() {
		List<String> templates = new ArrayList<String>();
		templates.add("templates/common/generator/model.java.vm");
		templates.add("templates/common/generator/domain.java.vm");
		templates.add("templates/common/generator/Service.java.vm");
		templates.add("templates/common/generator/ServiceImpl.java.vm");
		templates.add("templates/common/generator/Controller.java.vm");
		return templates;
	}

	/**
	 * 生成代码
	 */

	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns) {
		// 配置信息

		// 表信息
		TableDO TableDO = new TableDO();

		TableDO.setTableName(table.get("TABLE_NAME"));
		TableDO.setComments(table.get("TABLE_COMMENT"));

		// 表名转换成Java类名
		String className = tableToJava(TableDO.getTableName(),
				props.getProperty("tablePrefix"));

		TableDO.setClassName(className);
		TableDO.setClassname(StringUtils.uncapitalize(className));

		// 列信息
		List<ColumnDO> columsList = new ArrayList<>();
		for (Map<String, String> column : columns) {

			ColumnDO ColumnDO = new ColumnDO();

			ColumnDO.setColumnName(column.get("COLUMN_NAME"));
			ColumnDO.setDataType(column.get("DATA_TYPE"));
			ColumnDO.setComments(column.get("COLUMN_COMMENT"));
			ColumnDO.setExtra(column.get("EXTRA"));
			// 列名转换成Java属性名
			String attrName = columnToJava(ColumnDO.getColumnName());
			ColumnDO.setAttrName(attrName);
			ColumnDO.setAttrname(StringUtils.uncapitalize(attrName));
			// 列的数据类型，转换成Java类型
			String attrType = props.getProperty(ColumnDO.getDataType(),
					"unknowType");
			ColumnDO.setAttrType(attrType);
			// 是否主键
			if ("PRI".equalsIgnoreCase(column.get("COLUMN_KEY"))
					&& TableDO.getPk() == null) {
				TableDO.setPk(ColumnDO);
			}
			columsList.add(ColumnDO);
		}
		TableDO.setColumns(columsList);
		// 没主键，则第一个字段为主键
		if (TableDO.getPk() == null) {
			TableDO.setPk(TableDO.getColumns().get(0));
		}
		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);

		// 封装模板数据
		Map<String, Object> map = new HashMap<>();
		
		map.put("tableName", TableDO.getTableName());
		
		map.put("comments", TableDO.getComments());
		
		map.put("pk", TableDO.getPk());
		
		map.put("className", TableDO.getClassName());
		
		map.put("classname", TableDO.getClassname());

		map.put("pathName", props.getProperty("packageName"));

		map.put("columns", TableDO.getColumns());

		map.put("package", props.getProperty("package"));

		map.put("author", props.getProperty("author"));

		map.put("email", props.getProperty("email"));

		map.put("datetime",DateUtils.getNowTime());
		
		VelocityContext context = new VelocityContext(map);

		// 获取模板列表
		List<String> templates = getTemplates();
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			try {
				// 生成包类
				String filePath = getFileName(template, TableDO.getClassname(),
						TableDO.getClassName(), props.getProperty("package"));
				// 生成相应的代码
				stringToFile(new File(filePath), sw.toString());
				
			} catch (Exception e) {
				throw new BDException("渲染模板失败，表名：" + TableDO.getTableName(), e);
			}
		}
	}

	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[] { '_' })
				.replace("_", "");
	}

	public static void generator(String tablename) {

		String[] tables = tablename.split(",");
		
		for (String tableName : tables) {
			
			Map<String, String> tableInfo = GeneratorDao.get(tableName);
			
			List<Map<String, String>> columnsInfo = GeneratorDao.listColumns(tableName);
			
			if (tableInfo == null || columnsInfo == null)
				throw new BDException("数据库查询表信息失败，表名：" + tableName);
			
			generatorCode(tableInfo, columnsInfo);
		}

	}

	/**
	 * 保留前+ 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Properties getProperties() {
		try {
			InputStream inStream = JdbcUtil.class.getResourceAsStream("generator.properties");
			Properties prop = new Properties();
			prop.load(inStream);
			return prop;
		} catch (Exception e) {
			throw new BDException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String classname,
			String className, String packageName) {
		String project=System.getProperty("user.dir");
		String projectStr=project.substring(0,project.lastIndexOf("\\"));	
		// 项目目录
		String packagePath = "src" + File.separator + "main" + File.separator
				+ "java" + File.separator;

		if (StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator)
					+ File.separator;
		}

		if (template.contains("domain.java.vm")) {
			packagePath=projectStr+"\\"+props.getProperty("project_api")+"\\"+packagePath;
			return packagePath + "domain" + File.separator + className
					+ "Vo.java";
		}

		if (template.contains("model.java.vm")) {
			packagePath=projectStr+"\\"+props.getProperty("project_service")+"\\"+packagePath;
			return packagePath + "model" + File.separator + className
					+ "Entity.java";
		}

		if (template.contains("Service.java.vm")) {
			packagePath=projectStr+"\\"+props.getProperty("project_service")+"\\"+packagePath;
			return packagePath + "service" + File.separator + "I" + className
					+ "Service.java";
		}

		if (template.contains("ServiceImpl.java.vm")) {
			packagePath=projectStr+"\\"+props.getProperty("project_service")+"\\"+packagePath;
			return packagePath + "service" + File.separator + "impl"
					+ File.separator + className + "Service.java";
		}

		if (template.contains("Controller.java.vm")) {
			packagePath=projectStr+"\\"+props.getProperty("project_api")+"\\"+packagePath;
			return packagePath + "controller" + File.separator + className
					+ "Controller.java";
		}
		return null;
	}

	private static void stringToFile(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, ENCODING);
	}

	public static void main(String args[]) {
		// 根据表结构信息，生成API接口 多张表用 sys_log,sys_log
		generator("member_article_read");
	}
}
