rundeck-jclouds: jclouds resource model provider for RunDeck.

Jclouds: http://jclouds.org
RunDeck: http://rundeck.org

Work in progress..

Build
---

    cd rundeck-jclouds
    mv package

Execute
---

Create "provider.properties" file to specify Jclouds provider name and access credentials, for example:

    provider= aws-ec2
    accessKey= [access key]
    secretKey = [secrete key]

(You can test with stub using file `rundeck-jclouds/src/test/resources/stubprovider.properties`)

1. configure classpath to contain necessary jars. (jclouds, google guice, etc. see below)
2. run java:

    java -classpath "$CLASSPATH:target/rundeck-jclouds-1.0-SNAPSHOT.jar" org.dtolabs.rundeck.jclouds.RundeckJcloudsResourcesGenerator rundeck-jclouds/src/test/resources/stubprovider.properties rundeck-jclouds/src/test/resources/test-mapping.properties xml

This will generate XML formatted RunDeck resources from 10 jclouds stub instances:

    <?xml version="1.0" encoding="UTF-8"?>

    <project>
      <node type="Node" osFamily="centos" tags="webserver" hostname="144.175.1.10" username="root" name="webserver-7d9" osVersion="5.5" osName="stub centos true" description="Node 10 (webserver-7d9) : [] imageid: 6"/>
      <node type="Node" osFamily="centos" tags="webserver" hostname="144.175.1.2" username="root" name="webserver-d30" osVersion="5.5" osName="stub centos true" description="Node 2 (webserver-d30) : [] imageid: 6"/>
      ...
    </project>

RundeckJcloudsResourcesGenerator usage: `<provider.properties> <mapping.properties> <format(xml/yaml)> [output file]`

classpath jars
----

include jclouds compute jars

Other dependencies:

    gson-1.6.jar
    guava-r08.jar
    guice-assistedinject-3.0-rc2.jar
    guice-3.0-rc2.jar
    javax.inject-1.jar
    aopalliance-1.0.jar
    jersey-core-1.4.jar
    snakeyaml-1.7.jar
    dom4j-1.6.1.jar