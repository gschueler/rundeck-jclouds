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
* RundeckNodesRep.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:59:16 AM
* 
*/
package org.dtolabs.rundeck.resources;

import org.dtolabs.rundeck.resources.RundeckNodeMeta;

import java.util.*;

/**
 * RundeckNodesRep contains a collection of node metadata maps.
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class RundeckNodesRep {
    private Collection<Map<RundeckNodeMeta, String>> content;

    public RundeckNodesRep(final Collection<Map<RundeckNodeMeta, String>> content) {
        this.content = content;
    }


    public Collection<Map<RundeckNodeMeta, String>> getContent() {
        return content;
    }
}
