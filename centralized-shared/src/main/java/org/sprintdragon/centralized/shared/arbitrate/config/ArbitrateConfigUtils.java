/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
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

package org.sprintdragon.centralized.shared.arbitrate.config;

import org.sprintdragon.centralized.shared.model.Node;
import org.sprintdragon.centralized.shared.model.info.ServerInfo;

/**
 * 配置操作聚合类
 */
public class ArbitrateConfigUtils {

    /**
     * 获取当前节点的nid信息
     */
    public static ServerInfo getCurrentServerInfo() {
        Node node = ArbitrateConfigRegistry.getConfig().currentNode();
        if (node != null) {
            return node.getServerInfo();
        } else {
            return null;
        }
    }

}
