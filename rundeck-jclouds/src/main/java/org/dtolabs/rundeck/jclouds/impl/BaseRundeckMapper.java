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
* BaseGenerator.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:05:37 AM
* 
*/
package org.dtolabs.rundeck.jclouds.impl;

import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeState;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.ComputeServiceContext;
import org.dtolabs.rundeck.jclouds.NodeMapper;
import org.dtolabs.rundeck.resources.RundeckNodesRepBuilder;

import com.google.common.collect.Iterables;

import java.util.Properties;

/**
 * BaseRundeckMapper maps Jclouds node metadata to Rundeck node metadat using a {@link
 * org.dtolabs.rundeck.resources.RundeckNodesRepBuilder}
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class BaseRundeckMapper implements NodeMapper {
    private ComputeServiceContext context;
    private Properties mapping;

    public BaseRundeckMapper(final ComputeServiceContext context,
                             final Properties properties) {
        this.context = context;
        this.mapping = properties;

    }

    /**
     * XXX: rote production of rundeck metadata...this needs to be converted to use the mapping input. visit nodes and
     * update the rundeck nodes builder
     *
     * @param node
     * @param builder
     */
    public void mapNode(final ComputeMetadata node, final RundeckNodesRepBuilder builder) {
        final NodeMetadata metadata = context.getComputeService().getNodeMetadata(node.getId());
        if (metadata.getState() != NodeState.RUNNING) {
            return;
        }
        builder.node()
            .name(mapName(node, metadata))
            .description(mapDescription(node, metadata))
            .hostname(mapHostname(node, metadata))
            .tags(mapTags(node, metadata))
            .username(mapUsername(node, metadata))
            .osFamily(mapOsFamily(node, metadata))
            .osName(mapOsName(node, metadata))
            .osVersion(mapOSVersion(node, metadata))
            .osArch(mapOsArch(node, metadata))
            .editUrl(mapEditUrl(node, metadata))
            .remoteUrl(mapRemoteUrl(node, metadata))
            .build();
    }

    String mapOSVersion(final ComputeMetadata node, final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getVersion();
    }

    String mapOsName(final ComputeMetadata node, final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getName();
    }

    String mapOsFamily(final ComputeMetadata node, final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getFamily().toString();
    }

    String mapOsArch(final ComputeMetadata node, final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getArch();
    }

    String mapUsername(final ComputeMetadata node, final NodeMetadata metadata) {
        if (null != metadata.getCredentials() && null != metadata.getCredentials().identity) {
            return metadata.getCredentials().identity;
        }
        return null;
    }

    String mapTags(final ComputeMetadata node, final NodeMetadata metadata) {
        String tags = "";
        if (null != metadata.getGroup()) {
            tags += metadata.getGroup() ;
        }
        return tags;
    }

    String mapHostname(final ComputeMetadata node, final NodeMetadata metadata) {
        if (null != metadata.getPublicAddresses() && metadata.getPublicAddresses().size() > 0) {
            return Iterables.get(metadata.getPublicAddresses(), 0);
        } else if (null != metadata.getPrivateAddresses() && metadata.getPrivateAddresses().size() > 0) {
            return Iterables.get(metadata.getPrivateAddresses(), 0);
        }
        return null;
    }

    String mapName(final ComputeMetadata node, final NodeMetadata metadata) {
        return null != node.getName() ? node.getName() : node.getId();
    }

    String mapDescription(final ComputeMetadata node, final NodeMetadata metadata) {
        final StringBuffer desc = new StringBuffer();
        desc.append("Node ").append(node.getProviderId());
        if(null!=node.getName()) {
            desc.append(" (").append(node.getName()).append(")");
        }
        desc.append(" : ").append(node.getLocation().getIso3166Codes().toString());
        desc.append(" imageid: ").append(metadata.getImageId());

        return desc.toString();
    }

    String mapEditUrl(final ComputeMetadata node, final NodeMetadata metadata) {
        //TODO:
        return null;
    }

    String mapRemoteUrl(final ComputeMetadata node, final NodeMetadata metadata) {
        //TODO:
        return null;
    }
}
