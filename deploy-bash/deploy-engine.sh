#!/bin/bash

mvn clean package -DskipTests
docker-compose -f docker-compose.prod1.yml down

if [ -d "$DIR" ]; then
	rm -rf deploy1_env
fi

mkdir deploy1_engine
mv -v ./* ./deploy1_engine/

cd deploy1_engine

sudo docker-compose -f docker-compose.prod1.yml up --build -d
