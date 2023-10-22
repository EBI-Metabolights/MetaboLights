FROM tomcat:9.0-jdk8
LABEL maintainer="MetaboLights (metabolights-help@ebi.ac.uk)"

ARG CONF_FOLDER
ARG CONTEXT_FOLDER

RUN mkdir -p /context && mkdir -p /war-files && mkdir -p /webapps  && mkdir -p /usr/local/tomcat/conf
ARG GROUP1_ID=2222
ARG GROUP2_ID=2223
ARG USER_ID=2222

RUN groupadd group1 -g $GROUP1_ID \
    && groupadd group2 -g $GROUP2_ID \ 
    && useradd -Ms /bin/bash -u $USER_ID -g group1 -G group1,group2 metabolights 
RUN chown -R metabolights:group1 /webapps \
    && chown -R metabolights:group1 /war-files \
    && chown -R metabolights:group1 /context \
    && chown -R metabolights:group1 /usr/local/tomcat/conf
USER metabolights 

COPY metabolights-webapps/build/libs/metabolights-webapp-3.0.war /war-files/metabolights-webapp.war
COPY metabolights-webservice/build/libs/metabolights-webservice-3.0.war /war-files/metabolights-webservice.war

# COPY $CONF_FOLDER /usr/local/tomcat/conf
# COPY $CONTEXT_FOLDER /context

EXPOSE 8080

CMD ["catalina.sh", "run"]
#CMD ["tail", "-f", "/dev/null"]