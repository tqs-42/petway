#!/bin/bash

mvn clean package -DskipTests
docker-compose -f docker-compose.prod2.yml down

if [ -d "$DIR" ]; then
	rm -rf deploy2_env
fi

mkdir deploy2_engine
mv -v ./* ./deploy2_engine/

cd deploy2_engine

sudo docker-compose -f docker-compose.prod2.yml up --build -d
