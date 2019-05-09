#!/usr/bin/env sh
git clone https://github.com/GreenButtonAlliance/OpenESPI-Common-java.git common
cd common
mvn -P devmysql clean install
cd ..
