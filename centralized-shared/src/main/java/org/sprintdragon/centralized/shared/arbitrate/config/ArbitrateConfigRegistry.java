package org.sprintdragon.centralized.shared.arbitrate.config;

/**
 * Created by wangdi on 17-2-22.
 */
public class ArbitrateConfigRegistry {

    private static ArbitrateConfig config;

    public static void regist(ArbitrateConfig config) {
        ArbitrateConfigRegistry.config = config;
    }

    public static void unRegist(ArbitrateConfig config) {
        ArbitrateConfigRegistry.config = config;
    }

    public static ArbitrateConfig getConfig() {
        return config;
    }

}
