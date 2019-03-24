####### Build client application #######

FROM node:8-alpine as clientBuilder
WORKDIR /tmp/
USER root
COPY [ "client*/package.json","client/yarn.*", "./client/" ]
RUN npm config set registry http://157.230.125.223:8081/repository/npm-group/ \
    && [ -d "client" ] \
    && (cd "client" \
    && yarn install --pure-lockfile --network-timeout 100000) ;
COPY [ "client/", "/tmp/client/"]
RUN  ls && [ -d "client" ] \
     && (cd "client" && yarn build;) || true


####### Build Application Jar #######

#TODO will be based on grails image
FROM gradle:alpine as jarBuilder

WORKDIR /tmp/

USER root

RUN apk add --no-cache bash


# Include project which contains client application
# builded at the first stage
COPY [ "gradle*", "settings.gradle", "VERSION", "./" ]
COPY [ "grails-app", "./grails-app" ]
RUN set -v \
    && cd grails-app \
    && echo "Grails build started" \
    && chmod +x ./grailsw \
    && ./grailsw package



##### Application Image #######

FROM openjdk:8-jdk-alpine as buildImage

WORKDIR /usr/app
# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
COPY --from=jarBuilder /tmp/grails-app/build/libs/manerp.jar .
COPY --from=clientBuilder  /tmp/client/dist/ /www/
COPY docker .
EXPOSE 80

# Run the jar file
ENTRYPOINT ["/usr/app/entrypoint.sh"]
