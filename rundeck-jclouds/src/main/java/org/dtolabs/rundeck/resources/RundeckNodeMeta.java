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
* RundeckNodeMeta.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:24:25 AM
* 
*/
package org.dtolabs.rundeck.resources;

/**
 * RundeckNodeMeta enum of rundeck metadata for nodes
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public enum RundeckNodeMeta {


    NAME("name"),
    HOSTNAME("hostname"),
    USERNAME("username"),
    DESCRIPTION("description"),
    OS_FAMILY("os-family"),
    OS_ARCH("os-arch"),
    OS_VERSION("os-version"),
    OS_NAME("os-name"),
    EDIT_URL("edit-url"),
    REMOTE_URL("remote-url"),
    TAGS("tags"),;
    private String name;

    RundeckNodeMeta(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
