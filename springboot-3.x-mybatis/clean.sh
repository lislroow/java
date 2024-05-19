#!/bin/bash
BASEDIR=$( cd $( dirname $0 ) && pwd -P )

if [ "${BASEDIR}" == '/usr/bin' ]; then
  echo "error. BASEDIR is /usr/bin"
  exit -1
fi

LIST=($(cat <<- EOF

.project
.settings
.classpath
.factorypath
.externalToolBuilders
target
.gradle
build
bin

EOF
))

read -r rows <<< ${LIST[*]}
for item in ${LIST[*]}; do
  rm -rf ${BASEDIR}/${item}
done
