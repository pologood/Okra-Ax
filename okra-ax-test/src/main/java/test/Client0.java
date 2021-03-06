package test;

import org.ogcs.ax.gate.client.C2GClient;
import org.ogcs.gpb.GpbD;
import org.ogcs.gpb.GpbD.Request;
import org.ogcs.gpb.GpbD.Response;
import org.ogcs.gpb.generated.Chat;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class Client0 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        long uid = 100;
        C2GClient client = new C2GClient(uid, "127.0.0.1", 10000) {
            @Override
            public void messageReceived(ChannelHandlerContext ctx, Response msg) {

                System.out.println(msg.toString());
            }
        };
        client.start();
        Channel channel = client.client();

// Send Message
        Chat.ReqChat reqChat = Chat.ReqChat.newBuilder()
                .setRid(1)
                .setContent("Im good man.")
                .setName("Uid-100")
                .setUid(100)
                .setTarget(101)
                .build();
        Request request = Request.newBuilder()
                .setId(1000)
                .setCmd(10000) // CHAT
                .setData(reqChat.toByteString())
                .build();

        channel.writeAndFlush(request);
    }
}
