package com.mybatis.parse;

import com.mybatis.exception.BuilderException;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 12:18
 */
@Data
public class XPathParser {

    private XPath xPath;
    private Document document;

    public XPathParser(InputStream inputStream) {
        this.xPath = createXPath();
        this.document = createDocument(new InputSource(inputStream));
    }

    /**
     * 初始化XPath
     * @return
     */
    private XPath createXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        return factory.newXPath();
    }

    /**
     * 根据XPath表达式解析xml结点
     * @param expression
     * @return
     */
    public Node xNode(String expression) {
        Node node = null;
        try {
            node = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return node;
    }

    private Document createDocument(InputSource source) {
        try {
            //JDK提供的文档解析工厂对象
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //是否支持命名空间
            factory.setNamespaceAware(false);
            //是否忽略注释
            factory.setIgnoringComments(true);
            //是否忽略元素内容的空白
            factory.setIgnoringElementContentWhitespace(false);
            //是否将CDATA结点转化为文本节点
            factory.setCoalescing(false);
            //是否展开实体引用结点，这里是sql片段引用
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            //设置解析文档错误的处理
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {

                }
                @Override
                public void error(SAXParseException exception) throws SAXException {

                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {

                }
            });
            //解析输入源的xml数据为一个Document文件
            return builder.parse(source);
        } catch (Exception e) {
            throw new BuilderException("Error creating document instance. Cause: " + e, e);
        }
    }
}
