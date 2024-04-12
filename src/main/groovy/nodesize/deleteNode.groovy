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

import org.apache.jackrabbit.oak.commons.PathUtils

def delete(def session, String toDelete) {
  def EMPTY = org.apache.jackrabbit.oak.spi.commit.CommitInfo.EMPTY
  def INSTANCE = org.apache.jackrabbit.oak.spi.commit.EmptyHook.INSTANCE
  def store = session.store
  def rootBuilder = store.getRoot().builder()
  def builder = rootBuilder

  for (String elem : PathUtils.elements(toDelete)) {
    builder = builder.getChildNode(elem);
  }

  builder.remove()
  store.merge(rootBuilder, INSTANCE, EMPTY)
  println("Mischief managed...");
}

delete(session, '/oak:index/cqMobileAppLucene/:suggest-data')
