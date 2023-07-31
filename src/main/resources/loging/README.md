# How to run docker compose ELK
1- run in background: docker-compose up -d
2- [Optional] int logback-spring.xml change ==> "app": "test-log" to "app": "typeYourAppName"
3- in a rest controller, log some data (annotate the rest controller as @Slf4j to get the log object): log.info("hello log")
4- go to http://localhost:5601/ (Kibana)
5- in the lefet menu go to : management --> stack management
6- in the lefet menu go to : kibana --> data view and create a view using your index
7- in the lefet menu go to : analytics --> discover and test using post man