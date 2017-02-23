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

package org.sprintdragon.centralized.shared.arbitrate.helper;


import org.sprintdragon.centralized.shared.constants.ZkArbitrateConstants;

import java.text.MessageFormat;
import java.util.List;

/**
 * 对应zookeeper path构建的helper类
 */
public class ManagePathUtils {

    /**
     * 返回对应的otter root path
     */
    public static String getRoot() {
        return ZkArbitrateConstants.PROJECT_ROOT;
    }

    public static String getUnitInfoRootPath() {
        return ZkArbitrateConstants.UNIT_INFO_ROOT;
    }

    public static String getUnitInfoPath(String unitId) {
        return MessageFormat.format(ZkArbitrateConstants.UNIT_INFO_FORMAT, unitId);
    }

    public static String getUnitLeaderPath(String unitId) {
        return MessageFormat.format(ZkArbitrateConstants.UNIT_LEADER_FORMAT, unitId);
    }

    public static String getUnitNodePath(String unitId, String ip) {
        return MessageFormat.format(ZkArbitrateConstants.UNIT_INFO_NODE_FORMAT, unitId, ip);
    }

    public static boolean checkIfSysOff(List<String> paths, String sysId) {
        for (String path : paths) {
            if (path.split(ZkArbitrateConstants.SPLIT_PLACH_HOLDER)[0].equals(sysId)) {
                return false;
            }
        }
        return true;
    }

}
