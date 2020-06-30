package com.context;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 22:12
 */
public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {

    private String configLocations;

    /**
     * 设置配置文件的位置
     * @param location 配置文件路径
     */
    public void setConfigLocation(String location) {
        this.configLocations = location;
    }

    /**
     * 获取配置文件位置
     * @return 配置文件路径
     */
    protected String getConfigLocations() {
        return configLocations;
    }
}
