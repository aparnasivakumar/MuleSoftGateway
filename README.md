MuleSoftGateway
===============

Design Process:

The design consists of a Controller and a DAO layer using the  Spring Framework 

Controller is responsible for getting the REST API from the browser to the target API

DAO layer is used to calculate the analytics ie. Maximum successful hits, Failure hits, SuccessProne URL etc. 

config file contains the URL for which this gateway service functions

Create a  MYSQL script
CREATE SCHEMA response;
CREATE TABLE response
(
  url character varying(5000) NOT NULL,
  inserttime timestamp(6)  NOT NULL,
  code integer
);
select * from response;

Basic URL format:
http://localhost/GatewayService/

To access the Google using a local gateway
http://localhost/GatewayService/search?q=mulesoft

To show the analytics
http://localhost/GatewayService/analytics

To change the URL for the gateway, link.properties needs to be changed.

