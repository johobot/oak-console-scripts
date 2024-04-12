// Original: https://gist.github.com/andrewmkhoury/b2599fa59079828bea83

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import org.apache.jackrabbit.oak.spi.commit.CommitInfo
import org.apache.jackrabbit.oak.spi.commit.EmptyHook
import org.apache.jackrabbit.oak.spi.state.NodeStore
import org.apache.jackrabbit.oak.commons.PathUtils
import com.google.common.collect.Lists
import java.util.List

def setProperty(def session, def nodepath, def propertyName, def propertyValue, def isMulti) {
    NodeStore ns = session.store
    def rnb = ns.root.builder()
    def nb = rnb;
    String path;
    if (PathUtils.isAbsolute(nodepath)) {
        path = nodepath;
    } else {
        path = PathUtils.concat(session.getWorkingPath(), nodepath);
    }
    List<String> elements = Lists.newArrayList();
    PathUtils.elements(path).each{String element ->
        if (PathUtils.denotesParent(element)) {
            if (!elements.isEmpty()) {
                elements.remove(elements.size() - 1);
            }
        } else if (!PathUtils.denotesCurrent(element)) {
            elements.add(element);
        }
    }

    elements.each {
      if(it.size() > 0) {
        nb = nb.getChildNode(it)
      }
    }
    println "Setting property ${propertyName}: ${propertyValue} on node ${nodepath}"
    if(isMulti) {
       nb.setProperty(propertyName, Lists.newArrayList(propertyValue), org.apache.jackrabbit.oak.api.Type.STRINGS)
    } else {
       nb.setProperty(propertyName, propertyValue)
    }
    ns.merge(rnb, EmptyHook.INSTANCE, CommitInfo.EMPTY)

    println "Done"
}
