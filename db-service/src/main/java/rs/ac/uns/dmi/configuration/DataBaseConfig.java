package rs.ac.uns.dmi.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Configuration class which houses beans used for the database data source.
 *
 * @author keithc
 */
@Configuration // JPA vrsi najednostavnije mysql upite create, update... Mapira
				// java cod.
@EnableJpaRepositories(basePackages = {
		"rs.ac.uns.dmi.repository" }, entityManagerFactoryRef = "dataBaseEntityManagerFactory")
public class DataBaseConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConfig.class);

	@Value("${database.db.hibernate.show_sql:false}") // value, dodeljuje
														// vrednost i propertija
														// ili uzima po difoltu
														// vrednost false
	private String databaseDbShowSql;

	@Value("${database.db.connection.driver-class-name:com.mysql.jdbc.Driver}")
	private String dataBaseDbDriver;

	@Value("${database.db.connection.username}")
	private String dataBaseDbUser;

	@Value("${database.db.connection.password}")
	private String dataBaseDbPasswd;

	@Value("${database.db.connection.url}")
	private String dataBaseDbUrl;

	/**
	 * 
	 * Sets up an Entity Manager factory for the database data source.
	 * 
	 * @return The Entity Manager factory for the database data source.
	 * 
	 *
	 */
	@Bean // EntityManagerFactory() uzima definicije modela kojih imam
	public LocalContainerEntityManagerFactoryBean dataBaseEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataBaseTargetDataSource());
		entityManager.setPackagesToScan(new String[] { "rs.ac.uns.dmi.model" });

		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		jpaProperties.setProperty("hibernate.show_sql", databaseDbShowSql);
		//jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create");

		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setJpaProperties(jpaProperties);

		return entityManager;
	}

	/**
	 * The database Data Source setup. Currently using c3p0. This method can be
	 * used to configure the data source.
	 *
	 * @return The data source.
	 */
	@Bean // kreira bean, datasource provajduje konekcije ka bazi
	public DataSource dataBaseTargetDataSource() {
		ComboPooledDataSource dataBaseTargetDataSource = new ComboPooledDataSource();

		try {
			dataBaseTargetDataSource.setDriverClass(dataBaseDbDriver);
		} catch (PropertyVetoException e) {
			LOGGER.error("Failed to set up pooled data source.", e);
		}

		dataBaseTargetDataSource.setUser(dataBaseDbUser);
		dataBaseTargetDataSource.setPassword(dataBaseDbPasswd);
		dataBaseTargetDataSource.setJdbcUrl(dataBaseDbUrl);

		return dataBaseTargetDataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(dataBaseEntityManagerFactory().getObject());
		return jpaTransactionManager;
	}
}
