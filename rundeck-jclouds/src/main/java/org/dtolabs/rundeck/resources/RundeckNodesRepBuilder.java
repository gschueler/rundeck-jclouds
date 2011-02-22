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
* RundeckNodesetBuilder.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:29:48 AM
* 
*/
package org.dtolabs.rundeck.resources;

import java.util.*;

import static org.dtolabs.rundeck.resources.RundeckNodeMeta.*;
import org.dtolabs.rundeck.resources.RundeckNodeMeta;
import org.dtolabs.rundeck.resources.RundeckNodesRep;


/**
 * RundeckNodesRepBuilder for constructing RundeckNodesRep
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class RundeckNodesRepBuilder {
    private Set<Map<RundeckNodeMeta, String>> build;

    RundeckNodesRepBuilder() {
        this.build = new HashSet<Map<RundeckNodeMeta, String>>();
    }

    public static RundeckNodesRepBuilder create() {
        return new RundeckNodesRepBuilder();
    }

    public Node node() {
        return new Node();
    }

    public RundeckNodesRep build() {
        return new RundeckNodesRep(build);
    }

    public class Node {
        Map<RundeckNodeMeta, String> nodebuild = new HashMap<RundeckNodeMeta, String>();

        public RundeckNodesRepBuilder build() {
            if (null == nodebuild.get(NAME)) {
                throw new IllegalArgumentException("NAME was not set");
            }
            if (null == nodebuild.get(HOSTNAME)) {
                throw new IllegalArgumentException("HOSTNAME was not set");
            }
            if (null == nodebuild.get(USERNAME)) {
                throw new IllegalArgumentException("USERNAME was not set");
            }
            build.add(nodebuild);
            return RundeckNodesRepBuilder.this;
        }

        public Node hostname(final String key) {
            return setValue(HOSTNAME, key);
        }

        public Node name(final String key) {
            return setValue(NAME, key);
        }

        public Node username(final String key) {
            return setValue(USERNAME, key);
        }

        public Node description(final String key) {
            return setValue(DESCRIPTION, key);
        }

        public Node tags(final String key) {
            return setValue(TAGS, key);
        }

        public Node osFamily(final String key) {
            return setValue(OS_FAMILY, key);
        }

        public Node osArch(final String key) {
            return setValue(OS_ARCH, key);
        }

        public Node osVersion(final String key) {
            return setValue(OS_VERSION, key);
        }

        public Node osName(final String key) {
            return setValue(OS_NAME, key);
        }

        public Node editUrl(final String key) {
            return setValue(EDIT_URL, key);
        }

        public Node remoteUrl(final String key) {
            return setValue(REMOTE_URL, key);
        }

        private Node setValue(final RundeckNodeMeta prop, final String value) {
            if (null != value) {
                nodebuild.put(prop, value);
            }
            return this;
        }
    }
}
