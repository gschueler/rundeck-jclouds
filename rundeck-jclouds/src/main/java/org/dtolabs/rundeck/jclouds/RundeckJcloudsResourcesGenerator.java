/*
 * Copyright 2011 DTO Labs, Inc. (http://dtolabs.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
* RundeckJcloudsResourcesGenerator.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 10:06:28 AM
* 
*/
package org.dtolabs.rundeck.jclouds;

import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.logging.jdk.config.JDKLoggingModule;
import org.dtolabs.rundeck.jclouds.impl.BaseRundeckMapper;
import org.dtolabs.rundeck.resources.ResourcesGenerator;
import org.dtolabs.rundeck.resources.RundeckNodesRep;
import org.dtolabs.rundeck.resources.RundeckNodesRepBuilder;
import org.dtolabs.rundeck.resources.ResourcesGeneratorFactory;

import java.util.*;
import java.io.*;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * RundeckJcloudsResourcesGenerator produces RunDeck formatted Resources data from Nodes discovered via Jclouds.
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class RundeckJcloudsResourcesGenerator {

    String provider;
    ComputeServiceContext context;
    ResourcesGenerator generator;
    Properties mapping;
    private static final String USAGE =
        "Usage: <provider.properties> <mapping.properties> <format(xml/yaml)> [output file]";


    public RundeckJcloudsResourcesGenerator(final String provider, final ComputeServiceContext context,
                                            final ResourcesGenerator generator, final Properties mapping) {
        this.provider = provider;
        this.context = context;
        this.generator = generator;
        this.mapping = mapping;
    }

    public static void main(final String[] args) throws RunNodesException, IOException, GenerateResourcesException {
        if (args.length < 3) {
            System.err.println(USAGE);
            System.exit(2);
        }

        /**
         * Provider properties
         */
        final Properties props = new Properties();
        props.load(new FileInputStream(new File(args[0])));

        final String provider = props.getProperty("provider");
        final String accesskeyid = props.getProperty("accessKey");
        final String secretkey = props.getProperty("secretKey");

        /**
         * mapping properties
         */
        final Properties mapping = new Properties();
        mapping.load(new FileInputStream(new File(args[1])));

        // get a context with ec2 that offers the portable ComputeService api
        final ComputeServiceContext context = new ComputeServiceContextFactory().createContext(provider, accesskeyid,
            secretkey, ImmutableSet.<Module>of(new JDKLoggingModule()));


        if ("stub".equals(provider)) {
            //XXX: test stub with 10 instances
            final ComputeService client = context.getComputeService();
            final Template template = context.getComputeService().templateBuilder().osFamily(OsFamily.CENTOS).build();
            // run a couple nodes accessible via group
            try {
                final Set<? extends NodeMetadata> nodes = client.createNodesInGroup("webserver", 10, template);
            } catch (RunNodesException e) {
                throw new GenerateResourcesException("Could not create stub nodes: " + e.getMessage(), e);
            }
        }

        //create resources generator format
        final ResourcesGenerator gen = ResourcesGeneratorFactory.forFormat(args[2]);

        final RundeckJcloudsResourcesGenerator generator = new RundeckJcloudsResourcesGenerator(provider, context, gen,
            mapping);

        OutputStream out = System.out;
        if (args.length > 3) {
            out = new FileOutputStream(new File(args[3]));
        }
        generator.generateResources(out);

        context.close();
        //XXX: system.exit seems necessary to finish
        System.exit(0);
    }


    public void generateResources(final OutputStream outstream) throws GenerateResourcesException, IOException {
        final ComputeService client = context.getComputeService();

        final RundeckNodesRepBuilder builder = RundeckNodesRepBuilder.create();
        BaseRundeckMapper visitor = new BaseRundeckMapper(context, mapping);
        for (ComputeMetadata node : client.listNodes()) {
            visitor.mapNode(node, builder);
        }
        final RundeckNodesRep nodesRep = builder.build();
        generator.generate(nodesRep, outstream);
    }

    /**
     * An exception during resources generation
     */
    public static class GenerateResourcesException extends Exception {
        public GenerateResourcesException() {
            super();
        }

        public GenerateResourcesException(String s) {
            super(s);
        }

        public GenerateResourcesException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public GenerateResourcesException(Throwable throwable) {
            super(throwable);
        }
    }
}
