package pl.doublecodestudio.nexuserp.config.database;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "pl.doublecodestudio.nexuserp.infrastructure.radius",
        entityManagerFactoryRef = "radiusEntityManagerFactory",
        transactionManagerRef = "radiusTransactionManager"
)
@RequiredArgsConstructor
public class RadiusDbConfig {

    private final RadiusProperties props;

    @Primary
    @Bean
    public DataSource radiusDataSource() {
        return DataSourceBuilder.create()
                .url(props.getUrl())
                .username(props.getUsername())
                .password(props.getPassword())
                .driverClassName(props.getDriverClassName())
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean radiusEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(radiusDataSource())
                .packages("pl.doublecodestudio.nexuserp.infrastructure.radius")
                .persistenceUnit("radiusDb")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager radiusTransactionManager(
            @Qualifier("radiusEntityManagerFactory") EntityManagerFactory radiusEntityManagerFactory) {
        return new JpaTransactionManager(radiusEntityManagerFactory);
    }
}
