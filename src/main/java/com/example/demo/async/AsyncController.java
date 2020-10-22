package com.example.demo.async;

import com.example.demo.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import sun.plugin2.message.GetAppletMessage;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

//异步请求
@RestController
@RequestMapping("/async")
public class AsyncController {
    Logger logger = LoggerFactory.getLogger(AsyncController.class);

    // 异步请求方法一
    @RequestMapping(value = "/email/servletReq", method = RequestMethod.GET)
    public void servletReq(HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
                //做⼀些超时后的相关操作...
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发⽣错误：" + event.getThrowable());
            }

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执⾏完成");
                //这⾥可以做⼀些清理资源的操作...
            }
        });
        //设置超时时间
        asyncContext.setTimeout(2000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100000);
                    System.out.println("内部线程：" + Thread.currentThread().getName());
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");
                } catch (Exception e) {
                    System.out.println("异常：" + e);
                }
                //异步请求完成通知
                //此时整个请求才完成
                asyncContext.complete();
            }
        });
        //此时之类 request的线程连接已经释放了
        System.out.println("主线程：" + Thread.currentThread().getName());
    }
    //方法二
    @RequestMapping(value = "/email/webAsyncReq", method = RequestMethod.GET)
    @ResponseBody
    public WebAsyncTask<String> webAsyncReq(@RequestParam int cost,@RequestParam long timeout) {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        Callable<String> result = () -> {
            System.out.println("内部线程开始：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(cost);
            } catch (Exception e) {
            }
            logger.info("副线程返回");
            System.out.println("内部线程返回：" + Thread.currentThread().getName());
            return "success";
        };
        WebAsyncTask<String> wat = new WebAsyncTask<String>(timeout, result);
        wat.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "超时";
            }
        });
        return wat;
    }

    //获取上下文
    @Autowired
    private ApplicationContext applicationContext;

    //异步调用
    @GetMapping("asyncInvoke")
    public String asyncTest(){
        System.out.println(applicationContext);
//        并没有异步
        //在spring中像@Async和@Transactional、cache等注解本质使⽤的是动态代理，Spring容器在初始化的时候Spring容器会
        //将含有AOP注解的类对象“替换”为代理对象（简单这么理解），那么@Async注解失效的原因就很明显了，就是因为调⽤⽅法的是对象
        //本⾝⽽不是代理对象
        this.runAsync();
        //通过上下⽂获取⾃⼰的代理对象调⽤异步⽅法
        HelloController bean = applicationContext.getBean(HelloController.class);
        //这样才是异步
//        bean.runAsync();
        return "";
    }

    @Async
    public void runAsync(){
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("async end");
    }
}