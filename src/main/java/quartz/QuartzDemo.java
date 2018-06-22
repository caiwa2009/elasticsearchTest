package quartz;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class QuartzDemo {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler=new StdSchedulerFactory().getScheduler();
//        开始调度
        scheduler.start();
//        job的唯一标识
        JobKey jobKey=new JobKey("test","test-1");
        JobDetail jobDetail= JobBuilder.newJob(TestJob.class).withIdentity(jobKey).build();
        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("TEST","test")
//                延迟一秒
                        .startAt(new Date(System.currentTimeMillis()+1000))
//                        每隔一秒重复执行
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
                        scheduler.scheduleJob(jobDetail,trigger);
                        Thread.sleep(5000);
//                        删除Job
        scheduler.deleteJob(jobKey);
    }
}
