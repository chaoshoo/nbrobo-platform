package com.sys.jfinal;

import java.lang.reflect.Modifier;
import java.util.List;

import javax.sql.DataSource;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
/***
 * Auto bindingmodelDatabase table
 * @author loyin
 *  2012-9-4 morning11:45:09
 */
public class AutoTableBindPlugin extends ActiveRecordPlugin {
	private TableNameStyle tableNameStyle;
	public AutoTableBindPlugin(DataSource dataSource) {
		super(dataSource);
	}

	public AutoTableBindPlugin(IDataSourceProvider dataSourceProvider, TableNameStyle tableNameStyle) {
		super(dataSourceProvider);
		this.tableNameStyle = tableNameStyle;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean start() {
		try {
			List<Class> modelClasses = ClassSearcher.findClasses(Model.class);
			TableBind tb = null;
			for (Class modelClass : modelClasses) {
				tb = (TableBind) modelClass.getAnnotation(TableBind.class);
				if (tb == null) {
					//判断是否为抽像类
					if(!Modifier.isAbstract(modelClass.getModifiers())){
						this.addMapping(tableName(modelClass), modelClass);
					}
				} else {
					if(StringKit.notBlank(tb.name())){
						if (StringKit.notBlank(tb.pk())) {
							this.addMapping(tb.name(), tb.pk(), modelClass);
						} else {
							this.addMapping(tb.name(), modelClass);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw new RuntimeException(e);
		}
		return super.start();
	}

	@Override
	public boolean stop() {
		return super.stop();
	}

	private String tableName(Class<?> clazz) {
		String tableName = clazz.getSimpleName();
		if (tableNameStyle == TableNameStyle.UP) {
			tableName = tableName.toUpperCase();
		} else if (tableNameStyle == TableNameStyle.LOWER) {
			tableName = tableName.toLowerCase();
		} else {
			tableName = StringKit.firstCharToLowerCase(tableName);
		}
		return tableName;
	}
}