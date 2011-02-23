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
* ResourcesGeneratorFactory.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: Feb 22, 2011 10:27:42 AM
* 
*/
package org.dtolabs.rundeck.resources;

import org.dtolabs.rundeck.resources.format.XmlGenerator;
import org.dtolabs.rundeck.resources.format.YamlGenerator;

/**
 * ResourcesGeneratorFactory creates generators for a format
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class ResourcesGeneratorFactory {
    public static enum Format {
        xml,
        yaml
    }

    public static ResourcesGenerator forFormat(final String format) {
        try {
            return forFormat(Format.valueOf(format));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown format: " + format);
        }
    }

    public static ResourcesGenerator forFormat(final Format format) {
        switch (format) {
            case xml:
                return new XmlGenerator();
            case yaml:
                return new YamlGenerator();
            default:
                throw new IllegalArgumentException("Unknown format: " + format);
        }
    }
}
