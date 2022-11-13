FROM tomcat:8.5.81-jdk8
LABEL maintainer="MetaboLights (metabolights-help@ebi.ac.uk)"

ARG CONF_FOLDER
ARG CONTEXT_FOLDER

COPY metabolights-webapps/target/metabolights-webapp-2.3.war /war-files/metabolights-webapp.war
COPY metabolights-webservice/target/metabolights-webservice-3.2-SNAPSHOT.war /war-files/metabolights-webservice.war

# COPY $CONF_FOLDER /usr/local/tomcat/conf
# COPY $CONTEXT_FOLDER /context

EXPOSE 8080

CMD ["catalina.sh", "run"]
#CMD ["tail", "-f", "/dev/null"]