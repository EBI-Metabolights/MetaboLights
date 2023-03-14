FROM tomcat:8.5.81-jdk8
LABEL maintainer="MetaboLights (metabolights-help@ebi.ac.uk)"

ARG CONF_FOLDER
ARG CONTEXT_FOLDER

RUN groupadd -g 1136 cm_pub && groupadd -g 1102 cheminf && useradd tc_cm01 -u 2813 -g 1136 -G 1102
RUN mkdir -p /context && mkdir -p /war-files
RUN chown -R tc_cm01:cm_pub /usr/local/tomcat \
    && chown tc_cm01:cm_pub -R  /context  \
    && chown -R tc_cm01:cm_pub /war-files
USER tc_cm01

COPY metabolights-webapps/target/metabolights-webapp-2.3.war /war-files/metabolights-webapp.war
COPY metabolights-webservice/target/metabolights-webservice-3.2-SNAPSHOT.war /war-files/metabolights-webservice.war

# COPY $CONF_FOLDER /usr/local/tomcat/conf
# COPY $CONTEXT_FOLDER /context

EXPOSE 8080

CMD ["catalina.sh", "run"]
#CMD ["tail", "-f", "/dev/null"]