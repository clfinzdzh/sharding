package com.ems;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Description 代码自动生成
 * @Author caolifeng
 * @Date 2019-03-13 11:09
 **/
public class CodeAutoGenerator {

    /**
     * 项目名称
     */
    private static String projectName = "";

    /**
     * 模块名称
     */
    private static String moduleName = "";


    private static String author = "";


    private static String projectPath = "";

    /**
     * 开发人员直接从控制台键入要生成实体相关代码的库表名称
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.print(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * mybatis+ 全局配置
     */
    public static GlobalConfig createGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        projectPath = System.getProperty("user.dir");
        projectPath = projectPath.substring(0, projectPath.lastIndexOf("\\") + 1);
        projectPath = projectPath + projectName + "/src/main/java/";
        gc.setOutputDir(projectPath);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        return gc;
    }

    /**
     * 数据库连接的相关配置
     */
    public static DataSourceConfig createDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.200.51:3306/etcc_biz?useUnicode=true&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true&serverTimezone=GMT");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("yjanquan");
        dsc.setPassword("yjanquan");
        return dsc;
    }


    /**
     * 各模块的包名的相关配置
     */
    public static PackageConfig crearePackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("com.etcc");
        pc.setController("controller");
        pc.setMapper("mapper");
        pc.setEntity("entity.domain");
        pc.setService("api");
        pc.setServiceImpl("service");
        return pc;
    }

    /**
     * 扩展：自定义生成实体或其他文件的相关配置
     */
    public static InjectionConfig createInjectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        List<FileOutConfig> focList = new ArrayList();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/com/etcc/" + moduleName + "/mapper/xml/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig("/templates/adapter.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/com/etcc/" + moduleName + "/adapter/" + tableInfo.getEntityName() + "Adapter.java";
            }
        });

        focList.add(new FileOutConfig("/templates/vo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/com/etcc/" + moduleName + "/entity/vo/" + tableInfo.getEntityName() + "Vo.java";
            }
        });

        focList.add(new FileOutConfig("/templates/queryVo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/com/etcc/" + moduleName + "/entity/vo/" + tableInfo.getEntityName() + "QueryVo.java";
            }
        });

        focList.add(new FileOutConfig("/templates/dto.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/com/etcc/" + moduleName + "/entity/dto/" + tableInfo.getEntityName() + "Dto.java";
            }
        });
        focList.add(new FileOutConfig("/templates/queryDto.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/com/etcc/" + moduleName + "/entity/dto/" + tableInfo.getEntityName() + "QueryDto.java";
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 生成策略的配置
     */
    public static StrategyConfig createStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        //设置表前缀
        strategy.setTablePrefix("sys_", "t_", "o_", "v_", "d_");
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTableFillList(tableFills());
        strategy.setEntityTableFieldAnnotationEnable(true);
        String tableNames = scanner("表名");
        strategy.setInclude(tableNames.split("\\,"));
        strategy.setSuperEntityColumns(new String[]{"id"});
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setSuperControllerClass("com.etcc.page.PageController");
        strategy.setSuperEntityClass("com.etcc.base.BaseEntity");
        return strategy;
    }

    /**
     * 模版引擎相关的配置 本项目使用 velocity ： [vəˈlɒsəti]
     * 注意：指定模版路径时去掉对应模版引擎的后缀
     */
    public static TemplateConfig createTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setMapper("templates/mapper.java");
        templateConfig.setService("templates/service.java");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setController("templates/controller.java");
        templateConfig.setEntity("templates/po.java");
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 代码生成入口
     */
    public static void main(String[] args) {
        author = scanner("作者");
        projectName = scanner("项目名称");
        moduleName = scanner("模块名称");
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.setGlobalConfig(createGlobalConfig());
        mpg.setDataSource(createDataSourceConfig());
        mpg.setCfg(createInjectionConfig());
        mpg.setStrategy(createStrategyConfig());
        mpg.setTemplate(createTemplateConfig());
        mpg.setPackageInfo(crearePackageConfig());
        mpg.execute();
    }

    private static List<TableFill> tableFills() {
        List<TableFill> tableFields = Lists.newArrayList();
        tableFields.add(new TableFill("created_by", FieldFill.INSERT));
        tableFields.add(new TableFill("updated_by", FieldFill.INSERT_UPDATE));
        tableFields.add(new TableFill("created_at", FieldFill.INSERT));
        tableFields.add(new TableFill("updated_at", FieldFill.INSERT_UPDATE));
        return tableFields;
    }

}
