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
* JcloudsNodeVisiter.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:25:19 AM
* 
*/
package org.dtolabs.rundeck.jclouds;

import org.jclouds.compute.domain.ComputeMetadata;
import org.dtolabs.rundeck.resources.RundeckNodesRepBuilder;

/**
 * NodeMapper is used to map jclouds node metadata to Rundeck metadata
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public interface NodeMapper {

    /**
     * map node to a builder
     *
     * @param node
     * @param builder
     */
    public void mapNode(ComputeMetadata node, final RundeckNodesRepBuilder builder);
}
