# medical-records
Simple project for records managements using Maven, Java, Primefaces, CDI, Spring and Apache CXF

## Local development

Edit `src/main/resources/META-INF/persistence.xml` to override the datasource settings.

```datasource settings
<persistence-unit name="records-pu" transaction-type="JTA">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <jta-data-source>java:/jdbc/savics_recordDS</jta-data-source>
        <exclude-unlisted-classes>false</exclude-unlisted-classes>
        <properties>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            <property name="hibernate.useUnicode" value="true"/>
            <property name="hibernate.characterEncoding" value="utf-8"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
```
## WildFly standalone configuration

Add java:/jdbc/savics_recordDS in the datasources in standalone.xml  adjust to your RDBMS/database settings

``` RDBMS/Database settings
<datasource jndi-name="java:/jdbc/savics_recordDS" pool-name="savics_recordDS" enabled="true" use-java-context="true">
  <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
  <driver>h2</driver>
  <security>
    <user-name>sa</user-name>
    <password>sa</password>
  </security>
</datasource>      
## Commands

- Use `mvn clean install` to build front-end and the back-end
- Run the .war file (medical-records.war) in WildFly)
