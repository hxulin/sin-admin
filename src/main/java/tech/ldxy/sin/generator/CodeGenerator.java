package tech.ldxy.sin.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * 功能描述: 代码生成器
 *
 * @author hxulin
 */
public enum CodeGenerator {

    INSTANCE;

    /**
     * 获取项目根目录
     */
    private String getRootPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 获取TemplateConfig
     */
    private TemplateConfig getTemplateConfig() {
        return new TemplateConfig().setXml(null);
    }

    /**
     * 获取InjectionConfig
     */
    private InjectionConfig getInjectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
                String resourcePath = getRootPath() + "/src/main/resources";
                System.err.println(" Generator Resource Path:【 " + resourcePath + " 】");
                return resourcePath + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
    }

    /**
     * 获取JAVA目录
     */
    private String getJavaPath() {
        String javaPath = getRootPath() + "/src/main/java";
        System.err.println(" Generator Java Path:【 " + javaPath + " 】");
        return javaPath;
    }

    /**
     * 获取GlobalConfig
     */
    private GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                .setOutputDir(getJavaPath())// 输出目录
                .setFileOverride(false)// 是否覆盖文件
                .setActiveRecord(false)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(false)// XML ResultMap
                .setBaseColumnList(false)// XML ColumnList
                .setKotlin(false)// 是否生成 kotlin 代码
                .setOpen(false)
                .setAuthor("hxulin")// 作者
                // 自定义文件命名, 注意 %s 会自动填充表实体属性
                .setEntityName("%s")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("I%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
    }

    /**
     * 获取DataSourceConfig
     */
    private DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().equals("bit")) {
                            return DbColumnType.BOOLEAN;
                        }
                        if (fieldType.toLowerCase().equals("tinyint")) {
                            return DbColumnType.INTEGER;
                        }
                        if (fieldType.toLowerCase().equals("date")) {
                            return DbColumnType.DATE;
                        }
                        if (fieldType.toLowerCase().equals("time")) {
                            return DbColumnType.DATE;
                        }
                        if (fieldType.toLowerCase().equals("datetime")) {
                            return DbColumnType.DATE;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("12345678")
                .setUrl("jdbc:mysql:///sin_admin?characterEncoding=utf8&serverTimezone=UTC&useSSL=false");
    }

    /**
     * 获取TableFill策略
     */
    private List<TableFill> getTableFills() {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("create_uid", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_uid", FieldFill.INSERT_UPDATE));
        return tableFillList;
    }

    /**
     * 获取StrategyConfig
     */
    private StrategyConfig getStrategyConfig(String tableName) {
        List<TableFill> tableFillList = getTableFills();
        return new StrategyConfig()
                .setCapitalMode(false)// 全局大写命名
                .setTablePrefix("sys_")// 去除前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                // .setInclude(new String[] { "user" }) // 需要生成的表
                // 自定义实体父类
                .setSuperEntityClass("tech.ldxy.sin.core.model.entity.BaseEntity")
                // 自定义实体, 公共字段
                .setSuperEntityColumns("id")
                .setTableFillList(tableFillList)
                // 自定义 mapper 父类
                .setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper")
                // 自定义 controller 父类
                // .setSuperControllerClass("xx.xx.SuperController")
                // 自定义 service 实现类父类
                .setSuperServiceImplClass("com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
                // 自定义 service 接口父类
                .setSuperServiceClass("com.baomidou.mybatisplus.extension.service.IService")
                // 【实体】是否生成字段常量（默认 false）
                .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                .setEntityBuilderModel(false)
                // 【实体】是否为lombok模型（默认 false）
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setRestControllerStyle(false)
                .setRestControllerStyle(true)
                .setInclude(tableName);
    }

    /**
     * 获取PackageConfig
     */
    private PackageConfig getPackageConfig() {
        return new PackageConfig()
                .setParent("tech.ldxy.sin.system")
                .setController("web.controller")
                .setEntity("model.entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl");
    }

    /**
     * 获取AutoGenerator
     */
    private AutoGenerator getAutoGenerator(String tableName) {
        return new AutoGenerator()
                // 全局配置
                .setGlobalConfig(getGlobalConfig())
                // 数据源配置
                .setDataSource(getDataSourceConfig())
                // 策略配置
                .setStrategy(getStrategyConfig(tableName))
                // 包配置
                .setPackageInfo(getPackageConfig())
                // 注入自定义配置，可以在 VM 中使用 ${cfg.abc} 获取属性
                .setCfg(getInjectionConfig())
                .setTemplate(getTemplateConfig());
    }

    /**
     * 代码生成器入口方法
     */
    public static void main(String[] args) {
        System.out.print("请输入需要生成实体对象的表名：");
        Scanner scanner = new Scanner(System.in);
        String tableName = scanner.nextLine();
        AutoGenerator mpg = INSTANCE.getAutoGenerator(tableName);
        mpg.execute();
        System.err.println(" TableName【 " + tableName + " 】Generate Success !");
    }
}
