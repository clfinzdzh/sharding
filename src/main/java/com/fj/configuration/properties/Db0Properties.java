package com.fj.configuration.properties;


import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@ConfigurationProperties(prefix = "master.equip0")
@Component
@Data
public class Db0Properties {

    private String type;
    private String driverClassName;
    private String databaseName;
    private String url;
    private String username;
    private String password;

    public DataSource createDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }
}

