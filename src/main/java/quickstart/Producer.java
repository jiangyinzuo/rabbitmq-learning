package quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息的属性
 * @author jiang
 */
public class Producer {
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

        // 声明(创建)一个队列
        channel.queueDeclare("test001", true, false, false, null);

        String msg = "Hello RabbitMQ!";
        for (int i = 0; i < 5; ++i) {
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        // 关闭连接
        channel.close();
        connection.close();
    }
}
