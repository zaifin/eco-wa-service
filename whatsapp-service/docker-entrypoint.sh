#!/bin/sh
set -e

echo "Starting whatsapp-service with env vars:"
echo "WHATSAPP_SERVICE_URL=${WHATSAPP_SERVICE_URL}"
echo "WH_VERIFY_TOKEN=${WH_VERIFY_TOKEN}"


# Run the Java application
exec java -jar /app/app.jar
