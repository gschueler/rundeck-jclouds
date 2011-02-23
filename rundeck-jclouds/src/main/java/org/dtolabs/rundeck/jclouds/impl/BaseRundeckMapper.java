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
    public void mapNode(final NodeMetadata metadata, final RundeckNodesRepBuilder builder) {
        if (metadata.getState() != NodeState.RUNNING) {
            return;
        }
        builder.node()
            .name(mapName(metadata))
            .description(mapDescription(metadata))
            .hostname(mapHostname(metadata))
            .tags(mapTags(metadata))
            .username(mapUsername(metadata))
            .osFamily(mapOsFamily(metadata))
            .osName(mapOsName(metadata))
            .osVersion(mapOSVersion(metadata))
            .osArch(mapOsArch(metadata))
            .editUrl(mapEditUrl(metadata))
            .remoteUrl(mapRemoteUrl(metadata))
            .build();
    }

    String mapOSVersion(final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getVersion();
    }

    String mapOsName(final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getName();
    }

    String mapOsFamily(final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getFamily().toString();
    }

    String mapOsArch(final NodeMetadata metadata) {
        final OperatingSystem operatingSystem = metadata.getOperatingSystem();
        return operatingSystem.getArch();
    }

    String mapUsername(final NodeMetadata metadata) {
        if (null != metadata.getCredentials() && null != metadata.getCredentials().identity) {
            return metadata.getCredentials().identity;
        }
        return null;
    }

    String mapTags(final NodeMetadata metadata) {
        String tags = "";
        if (null != metadata.getGroup()) {
            tags += metadata.getGroup() ;
        }
        return tags;
    }

    String mapHostname(final NodeMetadata metadata) {
        if (null != metadata.getPublicAddresses() && metadata.getPublicAddresses().size() > 0) {
            return Iterables.get(metadata.getPublicAddresses(), 0);
        } else if (null != metadata.getPrivateAddresses() && metadata.getPrivateAddresses().size() > 0) {
            return Iterables.get(metadata.getPrivateAddresses(), 0);
        }
        return null;
    }

    String mapName(final NodeMetadata metadata) {
        return null != metadata.getName() ? metadata.getName() : metadata.getId();
    }

    String mapDescription(final NodeMetadata metadata) {
        final StringBuffer desc = new StringBuffer();
        desc.append("Node ").append(metadata.getProviderId());
        if(null!=metadata.getName()) {
            desc.append(" (").append(metadata.getName()).append(")");
        }
        desc.append(" : ").append(metadata.getLocation().getIso3166Codes().toString());
        desc.append(" imageid: ").append(metadata.getImageId());

        return desc.toString();
    }

    String mapEditUrl(final NodeMetadata metadata) {
        //TODO:
        return null;
    }

    String mapRemoteUrl(final NodeMetadata metadata) {
        //TODO:
        return null;
    }
}
