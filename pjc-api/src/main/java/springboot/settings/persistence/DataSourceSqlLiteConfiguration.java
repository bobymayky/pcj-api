package springboot.settings.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/* https://www.baeldung.com/spring-data-jpa-multiple-databases */

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
		basePackages = { "br.com.pjc.model.repositories.impl" },
		entityManagerFactoryRef = "sqlLiteEntityManagerFactory", 
		transactionManagerRef = "sqlLiteTransactionManager"

		)
public class DataSourceSqlLiteConfiguration {
	
	protected Map<String, Object> sqlLiteProperties() {
	    HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "springboot.settings.dialect.SQLiteDialect");
        properties.put("spring.jpa.hibernate.ddl-auto", "update");
        properties.put("spring.jpa.show-sql", "true");
        properties.put("spring.jpa.database-platform", "springboot.settings.dialect.SQLiteDialect");
	    return properties;
	}


	@Primary
	@Bean(name = "sqlLiteDataSource")
	public DataSource sqlLiteDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:A:/pjc.db");
        return dataSourceBuilder.build();
	}
	
	@Primary
	@Bean(name = "sqlLiteEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean sqlLiteEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("sqlLiteDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("br.com.pjc.model.entities").persistenceUnit("sqlLitePU").properties(sqlLiteProperties()).build();
	}
	@Primary
	@Bean(name = "sqlLiteTransactionManager")
	public PlatformTransactionManager sqlLiteTransactionManager(@Qualifier("sqlLiteEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}


