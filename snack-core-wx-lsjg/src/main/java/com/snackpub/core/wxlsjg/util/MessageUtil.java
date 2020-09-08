package com.snackpub.core.wxlsjg.util;

import com.snackpub.core.wxlsjg.model.ImageMessage;
import com.snackpub.core.wxlsjg.model.TextMessage;
import com.snackpub.core.wxlsjg.model.TopicMessage;
import com.thoughtworks.xstream.XStream;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现消息的格式转换(Map类型和XML的互转)
 *
 * @author snackpub
 * @date 2020/9/8
 */
@UtilityClass
public class MessageUtil {

    /**
     * 将XML转换成Map集合
     */
    @SneakyThrows
    public Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(6);
        SAXReader reader = new SAXReader();
        // 从request中获取输入流
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        // 获取根节点
        Element rootElement = doc.getRootElement();
        // 获取所有节点
        List<Element> elements = rootElement.elements();
        elements.forEach(e -> {
            map.put(e.getName(), e.getText());
            System.err.println(e.getName() + "---->" + e.getText());
        });
        return map;
    }

    /**
     * 将文本消息对象转换成XML
     *
     * @param textMessage 文本消息对象
     * @return str
     */
    public String textMessageToXml(TextMessage textMessage) {
        // 使用XStream将实体类的实例转换成xml格式
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 将文本消息对象转换成XML
     *
     * @param imageMessage 图片消息对象
     * @return str
     */
    public String imageMessageToXml(ImageMessage imageMessage) {
        // 使用XStream将实体类的实例转换成xml格式
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * 将关注/取消关注事件消息对象转换成XML
     *
     * @param topicMessage 图片消息对象
     * @return str
     */
    public String topicEventMessageToXml(TopicMessage topicMessage) {
        // 使用XStream将实体类的实例转换成xml格式
        XStream xstream = new XStream();
        xstream.alias("xml", topicMessage.getClass());
        return xstream.toXML(topicMessage);
    }
}
