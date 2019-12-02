package com.fj.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "masterslave")
@Component
@Data
public class DataSourceConstant {

    private String msname;

    private String ms0name;

    private String ms1name;

    private String sqlShow;

    private String executorSize;

    private String logicTableName;
}
