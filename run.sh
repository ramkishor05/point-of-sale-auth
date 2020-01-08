#!/bin/bash

set -e

cd `dirname $0`
r=`pwd`
echo $r

# Cloudserver
cd $r/project-brijframework-cloudserver
echo "Starting project brijframework cloudserver..."
mvn -q clean spring-boot:run &

# Application
echo "Starting project brijframework application..."
cd $r/project-brijframework-application
mvn -q clean spring-boot:run &

# Inventory
echo "Starting project brijframework inventory..."
cd $r/project-brijframework-inventory
mvn -q clean spring-boot:run &

# Client
#cd $r/client
#npm install
#echo "Starting Angular Client..."
#npm start
