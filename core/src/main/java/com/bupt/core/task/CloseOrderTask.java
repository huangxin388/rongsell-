package com.bupt.core.task;

import com.bupt.core.common.Const;
import com.bupt.core.config.cache.RedisUtil;
import com.bupt.core.service.OrderService;
import com.bupt.core.utils.PropertyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @Author huang xin
 * @Date 2020/4/28 16:18
 * @Version 1.0
 */
@Component
@Slf4j
//@EnableScheduling
public class CloseOrderTask {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 每分钟执行一次
     * 关闭两个小时内未付款的订单
     * 在分布式系统中不适用，因为可能有几个节点同时执行此任务，冗余执行，浪费数据库资源
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void closeOrderTaskMethod() {
        log.info("定时任务启动");
        int hour = Integer.parseInt(PropertyUtil.getProperty("close.order.task.time.hour", "2"));
        orderService.closeOrder(hour);
        log.info("定时任务关闭");
    }

    /**
     * 采用分布式锁关闭订单
     * 但是当代码执行到setnx时如果服务器被关闭，容易引起死锁
     */
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void closeOrderTaskDistribute() {
        log.info("定时任务启动");
        long timeout = Long.parseLong(PropertyUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = redisUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + timeout));
        if(setnxResult != null && setnxResult.intValue() == 1) {
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获得分布式锁{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("定时任务关闭");
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void closeOrderTaskDistributeImprove() {
        log.info("定时任务启动");
        long timeout = Long.parseLong(PropertyUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = redisUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + timeout));
        if(setnxResult != null && setnxResult.intValue() == 1) {
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            // 未获取到分布式锁
            String lockValueStr = redisUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            // 如果锁已经超时了，那么在这里是可以获取锁的
            if(lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                // 因为在一个tomcat集群中可能有其他的tomcat也在操作这个锁，所以getsetResult有可能不等于lockValueStr
                String getsetResult = redisUtil.getset(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,
                        String.valueOf(System.currentTimeMillis() + timeout));
                // 如果之前的锁已经被删除了，或者锁没有被删除但是与此同时，这个锁没有被其他任何进程或服务器改变，我们是可以获取这个锁的
                if((getsetResult == null) || (getsetResult != null && lockValueStr.equals(getsetResult))) {
                    // 真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁");
                }
            } else {
                log.info("没有获取到分布式锁");
            }
        }
        log.info("定时任务关闭");
    }

    private void closeOrder(String lockName) {
        // 设置有效期5s，防止死锁
        redisUtil.expire(lockName, 5);
        log.info("获取{},当前线程名字：{}", lockName, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertyUtil.getProperty("close.order.task.time.hour", "2"));
        orderService.closeOrder(hour);
        // 任务执行完毕，手动释放锁
        redisUtil.del(lockName);
        log.info("释放{},当前线程名字：{}", lockName, Thread.currentThread().getName());
    }

    /**
     * 关闭tomcat之前，先释放锁，避免代码执行到setnx时，服务器被关闭。锁永远得不到释放
     * 但是此方法只会在执行tomcat的shutdown时才会被调用
     * 如果直接kill -9则不会执行此方法
     */
    @PreDestroy
    public void deleteLock() {
        redisUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

}
