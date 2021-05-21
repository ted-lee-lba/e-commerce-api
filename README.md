# e-commerce-api
This solution used to solved part of the problems in [here](https://1drv.ms/b/s!AhrrjRVtzISrgsIOg-bP1AQm_gbusQ?e=ugk9oQ)

Setup:
1. Deploy sql/database-initialize.sql
2. Change the jdbc configuration in application.properties

Postman Script:
1. Import TPM-TEST.postman_collection.json into postman
2. Setup an environment with following variables: url, admin-otken, merchant-token, user-token 

First Step:
1. Login to get admin-token via api in postman with credential below:
Username: admin
password: admin

Once admin-token obtained, create user or merchant accordingly to test around the available api.