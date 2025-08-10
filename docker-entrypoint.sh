#!/bin/sh
set -e

# --- Default Git metadata ---
: "${RENDER_GIT_BRANCH:=main}"
: "${RENDER_GIT_COMMIT:=local-dev}"

echo "Starting service..."
echo "Branch: $RENDER_GIT_BRANCH"
echo "Commit: $RENDER_GIT_COMMIT"

# --- Optional Redis configuration ---
if [ -n "$REDIS_HOST" ]; then
  echo "Using Redis at $REDIS_HOST"
  export SPRING_REDIS_HOST="$REDIS_HOST"
fi

# --- Optional Database configuration (full URL) ---
if [ -n "$DATABASE_URL" ]; then
  echo "Using database: $DATABASE_URL"
  export SPRING_DATASOURCE_URL="$DATABASE_URL"
fi

# --- Start Spring Boot app ---
exec java \
  -Dspring.profiles.active="${SPRING_PROFILES_ACTIVE:-default}" \
  -DRENDER_GIT_BRANCH="$RENDER_GIT_BRANCH" \
  -DRENDER_GIT_COMMIT="$RENDER_GIT_COMMIT" \
  -jar app.jar
