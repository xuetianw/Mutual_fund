#FROM alpine
#RUN mkdir code


FROM mysql
MAINTAINER Fred Wu

ENV MYSQL_ROOT_PASSWORD=pwd

COPY sqlfile.sql /docker-entrypoint-initdb.d/

