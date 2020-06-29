package com.context;

/**
 * 生命周期接口
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 18:14
 */
public interface Lifecycle {

    /**
     * 开始，发出一个信号，告知注解处理器可以开始工作了
     */
    void start();

    /**
     * 结束，告知注解处理器结束工作，销毁
     */
    void end();

    /**
     *判断容器是否允许中
     * @return
     */
    boolean isRunning();
}
