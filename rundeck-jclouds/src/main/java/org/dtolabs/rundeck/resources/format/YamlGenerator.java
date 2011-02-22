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
* YamlGenerator.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:02:58 AM
* 
*/
package org.dtolabs.rundeck.resources.format;

import org.yaml.snakeyaml.Yaml;
import static org.dtolabs.rundeck.resources.RundeckNodeMeta.*;
import org.dtolabs.rundeck.resources.ResourcesGenerator;
import org.dtolabs.rundeck.resources.RundeckNodeMeta;
import org.dtolabs.rundeck.resources.RundeckNodesRep;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * YamlGenerator produces RunDeck resources xml from a {@link RundeckNodesRep}
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class YamlGenerator implements ResourcesGenerator {

    private static Map<RundeckNodeMeta, String> attrMapping = new HashMap<RundeckNodeMeta, String>();

    static {
        attrMapping.put(NAME, "nodename");
        attrMapping.put(OS_ARCH, "osArch");
        attrMapping.put(OS_FAMILY, "osFamily");
        attrMapping.put(OS_NAME, "osName");
        attrMapping.put(OS_VERSION, "osVersion");
        attrMapping.put(HOSTNAME, "hostname");
        attrMapping.put(USERNAME, "username");
        attrMapping.put(DESCRIPTION, "description");
        attrMapping.put(EDIT_URL, "editUrl");
        attrMapping.put(REMOTE_URL, "remoteUrl");
        attrMapping.put(TAGS, "tags");
    }

    Yaml yaml;

    public YamlGenerator() {
        yaml = new Yaml();
    }


    public void generate(final RundeckNodesRep nodesRep, final OutputStream out) throws IOException {
        final Map<String, Map<String, String>> gen = new HashMap<String, Map<String, String>>();

        for (final Map<RundeckNodeMeta, String> data : nodesRep.getContent()) {

            final Map<String, String> nodegen = new HashMap<String, String>();

            for (final RundeckNodeMeta rundeckNodeMeta : data.keySet()) {
                nodegen.put(attrMapping.get(rundeckNodeMeta), data.get(rundeckNodeMeta));
            }
            gen.put(data.get(NAME), nodegen);
        }
        yaml.dump(gen, new OutputStreamWriter(out));
    }
}
