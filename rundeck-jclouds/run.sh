#!/bin/bash

CP=$(cat jclouds.jars google.jars | awk -v ORS=: '{print $1}' )
#echo "cp: $CP"


exec java -classpath "$CP:target/rundeck-jclouds-1.0-SNAPSHOT.jar" org.dtolabs.rundeck.jclouds.RundeckJcloudsResourcesGenerator $@
