#!/bin/bash

# VAR
CURRDIR=$( pwd -P )
APPNAME=$(basename $CURRDIR)
DOCKER_REGISTRY="172.28.200.40:5000"
# //VAR

# usage
function USAGE {
  cat << EOF
- USAGE
Usage: ${0##*/} <option>
 --build         : 전체 빌드 
 --build-java    : maven 빌드 실행
 --build-docker  : docker 이미지 빌드 실행
 --run           : docker 실행

EOF
  exit 1
}
# //usage

# options
OPTIONS="h"
LONGOPTIONS="help,build,build-app,build-docker,run"
function SetOptions {
  opts=$( getopt --options $OPTIONS \
                 --longoptions $LONGOPTIONS \
                 -- $* )
  eval set -- $opts
  
  while true; do
    if [ -z $1 ]; then
      break
    fi
    case $1 in
      -h | --help) ;;
      --build)
        TASKS=('build-java' 'build-docker')
        ;;
      --build-java)
        TASKS=('build-java')
        ;;
      --build-docker)
        TASKS=('build-docker')
        ;;
      --run)
        TASKS=('run')
        ;;
      --)
        ;;
      *)
        params+=($1)
        ;;
    esac
    shift
  done
}
SetOptions $*
if [ $? -ne 0 ]; then
  USAGE
  exit 1
fi
# //options

# main
function main {
  for task in ${TASKS[*]}; do
    echo "task: $task"
    case "${task}" in
      build-java)
        mvn clean package
        
        if [ $? -ne 0 ]; then
          echo "build failed"
        else
          echo "successful build"
        fi
        ;;
      build-docker)
        docker build -t ${DOCKER_REGISTRY}/${APPNAME} .
        docker login ${DOCKER_REGISTRY} -u admin -p 1
        docker push ${DOCKER_REGISTRY}/${APPNAME}
        docker rmi ${DOCKER_REGISTRY}/${APPNAME}
        ;;
      run)
        docker pull ${DOCKER_REGISTRY}/${APPNAME}
        ID=$(docker ps -a --filter "name=${APPNAME}" --format "{{.ID}}")
        if [ ! -z "${ID}" ]; then
          docker stop "${APPNAME}"
          docker rm "${ID}"
        fi
        docker run -itd \
          -e JAVA_OPTS="-Dspring.profiles.active=prod -Xms256g -Xmx256g -XX:MetaspaceSize=192M -XX:MaxMetaspaceSize=192M" \
          --network=host \
          --name=${APPNAME} \
          ${DOCKER_REGISTRY}/${APPNAME}
        ;;
    esac
  done
}
main
# //main

exit 0