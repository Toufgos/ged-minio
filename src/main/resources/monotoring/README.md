# Docker Compose

run : docker-compose up -d 
NB: 
in case of multiple aps
    (i) please lunch docker-compose up -d for each app
    (ii) to display the server monotoring : 1- in the left menu go to: + --> Import --> import via grafana.com : 1860 --> click OK and choose promotheus DS

## Test Promotheuse

To test that promotheus is able to collect metrics go to: http://localhost:9090/

## Test Grafana

1- Go to : http://localhost:3000/ <br>
2- in the left menu go to: Setting --> Data source --> add Promotheus DS (using http://prometheus:9090 as HTTP URL) <br>
3- in your application, go to "grafana-template.json" in the monotoring folder and change the line   "title": "Zynerator" with "title": "yourApplicationName" <br>
4- in your application, go to "grafana-template.json" in the monotoring folder and change the line    "uid": "X034JGT7Gz", with  "uid": "yourUUID", <br>
5- in the left menu go to: + --> Import --> in "Import via panel json" copy the content of  grafana-template.json and select promotheusDataSource <br>
6- in the left menu go to: windowns icone (just below + icon) --> dashbord --> and your visualise data <br>