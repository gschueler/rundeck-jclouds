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
* ResourcesGenerator.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:01:42 AM
* 
*/
package org.dtolabs.rundeck.resources;

import org.dtolabs.rundeck.resources.RundeckNodesRep;

import java.io.OutputStream;
import java.io.IOException;

/**
 * ResourcesGenerator interface for generating resources data from a {@link RundeckNodesRep}
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public interface ResourcesGenerator {

    /**
     * generate the output for the visited nodes
     *
     * @param out
     */
    public void generate(RundeckNodesRep builder, OutputStream out) throws IOException;
}
