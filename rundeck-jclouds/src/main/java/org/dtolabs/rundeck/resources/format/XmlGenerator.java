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
* XmlGenerator.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 9:04:57 AM
* 
*/
package org.dtolabs.rundeck.resources.format;

import org.dom4j.DocumentFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.util.*;
import java.io.OutputStream;
import java.io.IOException;

import static org.dtolabs.rundeck.resources.RundeckNodeMeta.*;
import org.dtolabs.rundeck.resources.ResourcesGenerator;
import org.dtolabs.rundeck.resources.RundeckNodeMeta;
import org.dtolabs.rundeck.resources.RundeckNodesRep;

/**
 * XmlGenerator produces RunDeck resources xml from a {@link RundeckNodesRep}
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class XmlGenerator implements ResourcesGenerator {

    private static Map<RundeckNodeMeta, String> attrMapping = new HashMap<RundeckNodeMeta, String>();

    static {
        attrMapping.put(NAME, "name");
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

    public void generate(final RundeckNodesRep nodesRep, final OutputStream out) throws IOException {
        //create dom
        final Document document = DocumentFactory.getInstance().createDocument();
        final Element root = document.addElement("project");

        for (final Map<RundeckNodeMeta, String> map : nodesRep.getContent()) {

            final Element element = root.addElement("node");
            element.addAttribute("type", "Node");

            for (final RundeckNodeMeta s1 : map.keySet()) {
                element.addAttribute(attrMapping.get(s1), map.get(s1));
            }
        }
        writeDocument(document, out);
    }

    private void writeDocument(final Document document, final OutputStream out) throws IOException {
        // Pretty print the document
        final OutputFormat format = OutputFormat.createPrettyPrint();
        final XMLWriter writer = new XMLWriter(out, format);
        writer.write(document);
    }
}
