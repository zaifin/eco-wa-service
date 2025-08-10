FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/whatsapp-service.jar /app/eco-wa-service.jar
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh

ENTRYPOINT ["docker-entrypoint.sh"]
