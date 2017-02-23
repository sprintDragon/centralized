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

package org.sprintdragon.centralized.shared.constants;

/**
 * 仲裁器相关常量定义
 */
public interface ZkArbitrateConstants {

    /**
     * 的根节点
     */
    String PROJECT_ROOT = "/performance";

    String SPLIT_PLACH_HOLDER = "-";

    String UNIT_INFO_ROOT = PROJECT_ROOT + "/unit";

    String UNIT_INFO_FORMAT = UNIT_INFO_ROOT + "/{0}";

    String UNIT_INFO_NODE_FORMAT = UNIT_INFO_FORMAT + "/{1}";

    String UNIT_LEADER_ROOT = PROJECT_ROOT + "/unit-leader";

    String UNIT_LEADER_FORMAT = UNIT_LEADER_ROOT + "/{0}";

}
