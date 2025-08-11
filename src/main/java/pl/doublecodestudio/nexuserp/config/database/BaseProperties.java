package pl.doublecodestudio.nexuserp.config.database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String ddlAuto;
    private String physicalNamingStrategy;
}
