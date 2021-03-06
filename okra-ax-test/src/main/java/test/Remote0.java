package test;

import org.ogcs.ax.component.inner.AxInnerServer;
import org.ogcs.ax.room.remote.Commands;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class Remote0 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        Commands.register();
        AxInnerServer inner = new AxInnerServer("500", 9000);
        inner.start();
    }
}
