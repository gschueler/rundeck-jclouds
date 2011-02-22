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

package org.dtolabs.rundeck.jclouds;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.*;
import org.jclouds.ssh.jsch.config.JschSshClientModule;
import org.jclouds.domain.Location;
import org.jclouds.ec2.compute.options.EC2TemplateOptions;
import org.jclouds.ec2.EC2Client;
import org.jclouds.logging.jdk.config.JDKLoggingModule;
import org.yaml.snakeyaml.Yaml;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;

public class Test {

    StringBuffer sb;
    Yaml yaml;
    Map<String,Object> build;
    public Test(){
        sb=new StringBuffer();
        yaml = new Yaml();
        build = new HashMap<String,Object>();
    }
    private void build(ComputeMetadata node, NodeMetadata metadata) {
        sb.append("Node: ").append(node.getId()).append("\n");
        sb.append("\tProviderId: ").append(node.getProviderId()).append("\n");
        sb.append("\tLocation: ").append(node.getLocation()).append("\n");
        sb.append("\tName: ").append(node.getName()).append("\n");
        sb.append("\t-metadata-\n");
        sb.append("\tGroup: ").append(metadata.getGroup()).append("\n");
        sb.append("\tHardware: ").append(metadata.getHardware()).append("\n");
        sb.append("\tImageId: ").append(metadata.getImageId()).append("\n");
        sb.append("\tOperatingSystem: ").append(metadata.getOperatingSystem()).append("\n");
        sb.append("\tstate: ").append(metadata.getState()).append("\n");
        sb.append("\tprivateAddresses: ").append(metadata.getPrivateAddresses()).append("\n");
        sb.append("\tpublicAddresses: ").append(metadata.getPublicAddresses()).append("\n");
        sb.append("\tcredentials: ").append(metadata.getCredentials()).append("\n");

        StringBuffer desc = new StringBuffer();

        desc.append("Node (provider:").append(node.getProviderId()).append(")");
        desc.append(" : ").append(node.getLocation().getDescription());
        desc.append(" image: ").append(metadata.getImageId());

        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("nodename", node.getName());
        hm.put("hostname", Iterables.get(metadata.getPublicAddresses(), 0));
        hm.put("tags", metadata.getGroup()+","+metadata.getState());
        hm.put("username", metadata.getCredentials().identity);
        hm.put("description", desc.toString());
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        if (null != operatingSystem.getArch()) {
            hm.put("os-arch", operatingSystem.getArch());
        }
        if (null != operatingSystem.getFamily()) {
            hm.put("os-family", operatingSystem.getFamily().toString());
        }
        if (null != operatingSystem.getName()) {
            hm.put("os-name", operatingSystem.getName());
        }
        if (null != operatingSystem.getVersion()) {
            hm.put("os-version", operatingSystem.getVersion());
        }


        build.put(node.getName(), hm);

    }
    public String generate(){
        return yaml.dump(build);
//        return sb.toString();
    }
    public static void main(String[] args) throws RunNodesException {

        /**
         * setup
         * 
         */
        String provider = args[0];//"aws-ec2"
        String accesskeyid=args[1];
        String secretkey=args[2];

        // get a context with ec2 that offers the portable ComputeService api
        ComputeServiceContext context = new ComputeServiceContextFactory().createContext(provider, accesskeyid,
            secretkey, ImmutableSet.<Module>of(new JDKLoggingModule() /*, new JschSshClientModule()*/));

        final ComputeService client = context.getComputeService();


        /**
         * Template
         */

        // pick the highest version of the RightScale CentOs template
        Template template = context.getComputeService().templateBuilder().osFamily(OsFamily.CENTOS).build();

        // run a couple nodes accessible via group
        Set<? extends NodeMetadata> nodes = client.createNodesInGroup("webserver", 10, template);


        /**
         * output
         */

        Test out = new Test();
        for (ComputeMetadata node : client.listNodes()) {

            out.build(node, client.getNodeMetadata(node.getId()));
        }
        System.err.println("# Nodes: ");
        System.err.println(out.generate());

/*

        // here's an example of the portable api
        Set<? extends Location> locations = context.getComputeService().listAssignableLocations();

        Set<? extends Image> images = context.getComputeService().listImages();


        // specify your own groups which already have the correct rules applied
        template.getOptions().as(EC2TemplateOptions.class).securityGroups(group1);

        // specify your own keypair for use in creating nodes
        template.getOptions().as(EC2TemplateOptions.class).keyPair(keyPair);
        */


/*
        // when you need access to very ec2-specific features, use the provider-specific context
        EC2Client ec2Client = EC2Client.class.cast(context.getProviderSpecificContext().getApi());

        // ex. to get an ip and associate it with a node
        NodeMetadata node = Iterables.get(nodes, 0);
        String ip = ec2Client.getElasticIPAddressServices().allocateAddressInRegion(node.getLocation().getId());
        ec2Client.getElasticIPAddressServices().associateAddressInRegion(node.getLocation().getId(), ip,
            node.getProviderId());*/

        context.close();
        System.exit(0);
    }
}