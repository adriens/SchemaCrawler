@java -classpath ../schemacrawler-6.1.jar;../hsqldb.jar schemacrawler.tools.integration.scripting.Main -g schemacrawler.config.properties -c hsqldb -command verbose_schema -outputformat tables.js