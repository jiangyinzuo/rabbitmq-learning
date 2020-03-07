package quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消息的属性
 *
 * @author jiang
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {

        // 创建一个ConnectionFactory, 并配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 通过connection创建一个Channel
        Channel channel = connection.createChannel();

        channel.queueDeclare("test001", true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 创建消费者
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            var map = delivery.getProperties().getHeaders();
            System.out.println(map);
        };

        // 设置Channel
        channel.basicConsume("test001", true, deliverCallback, System.out::println);

    }
}
