#!/bin/bash

mvn clean package -DskipTests
docker-compose -f docker-compose.prod.yml down

if [ -d "$DIR" ]; then
	rm -rf deploy_env
fi

mkdir deploy_engine
mv -v ./* ./deploy_engine/

cd deploy_engine

sudo docker-compose -f docker-compose.prod.yml up -d
