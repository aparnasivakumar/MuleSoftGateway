MuleSoftGateway
===============

Create a script:

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

