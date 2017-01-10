spark-launcher
===============

### DESCRIPTION

Spark application launcher. (for now available exclusively with jdk 7.0)
Need a conf file for starting : http://git.cdbdx.biz/big-data/services-config
Need an ansible file for fetching app : http://git.cdbdx.biz/big-data/services-config/ansible. This file must be placed in the same folder as the webapp.

### PROD

#### Commands
nohup java -Dspring.config.location=application-spark-launcher.yml -jar spark-launcher-{version}.jar &

#### IHM
http://a01hmaprb014.cdweb.biz:29111/


