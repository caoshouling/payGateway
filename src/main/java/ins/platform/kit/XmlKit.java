package ins.platform.kit;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.mapper.DefaultMapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public final class XmlKit {
	public static final int StartNode = 0;
	public static final int EndNode = 1;
	
	public static Document newDocument() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		return document;
	}

	public static Document parse(File file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		document.normalize();
		return document;
	}

	public static Document parse(InputStream is) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document document = builder.parse(is);
		document.normalize();
		return document;
	}

	public static Document parse(String fileName) throws Exception {
		return parse(new File(fileName));
	}

	public static Node getChildNodeByTagName(Node currentNode, String tagName) {
		Node returnNode = null;
		NodeList nodeList = currentNode.getChildNodes();
		Node node = null;
		int i = 0;
		for (int n = nodeList.getLength(); i < n; ++i) {
			node = nodeList.item(i);
			if (isNodeName(node, tagName)) {
				returnNode = node;
				break;
			}
		}
		return returnNode;
	}

	public static Node[] getChildNodesByTagName(Node currentNode, String tagName) {
		ArrayList<Node> nodes = new ArrayList<>();
		if ((currentNode == null) || (!(currentNode.hasChildNodes())))
			return new Node[0];

		NodeList nodeList = currentNode.getChildNodes();
		Node node = null;
		int i = 0;
		for (int n = nodeList.getLength(); i < n; ++i) {
			node = nodeList.item(i);
			if (isNodeName(node, tagName))
				nodes.add(node);
		}

		Node[] tempNodes = new Node[nodes.size()];
		nodes.toArray(tempNodes);
		return tempNodes;
	}

	public static Node[] getChildElements(Node currentNode) {
		ArrayList<Node> nodes = new ArrayList<>();
		if ((currentNode == null) || (!(currentNode.hasChildNodes())))
			return new Node[0];

		NodeList nodeList = currentNode.getChildNodes();
		Node node = null;
		int i = 0;
		for (int n = nodeList.getLength(); i < n; ++i) {
			node = nodeList.item(i);
			if (node.getNodeType() == 1)
				nodes.add(node);
		}

		Node[] tempNodes = new Node[nodes.size()];
		nodes.toArray(tempNodes);
		return tempNodes;
	}

	public static String getChildNodeValue(Node currentNode, String nodeName) {
		String value = StrKit.Empty;
		if (currentNode != null) {
			NodeList nodeList = currentNode.getChildNodes();
			Node node = null;
			int i = 0;
			for (int n = nodeList.getLength(); i < n; ++i) {
				node = nodeList.item(i);
				if (isNodeName(node, nodeName)) {
					if (node.getFirstChild() != null) {
						value = node.getFirstChild().getNodeValue();
					}
				}

			}
		}
		return value == null ? StrKit.Empty : value;
	}

	public static String getChildNodeDefault(Node currentNode, String nodeName, String def) {
		String value = StrKit.Empty;
		NodeList nodeList = currentNode.getChildNodes();
		Node node = null;
		int i = 0;
		for (int n = nodeList.getLength(); i < n; ++i) {
			node = nodeList.item(i);
			if (isNodeName(node, nodeName)) {
				if (node.getFirstChild() != null) {
					value = node.getFirstChild().getNodeValue();
				}
			}

		}
		return value == null ? def : value;
	}

	public static void writeXMLFile(Document document, String fileName) throws Exception {
		DOMSource source = new DOMSource(document);
		writeXMLFile(source, fileName);
	}

	public static void writeXMLFile(DOMSource source, String fileName) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		StreamResult fileResult = new StreamResult(new File(fileName));
		transformer.transform(source, fileResult);
	}

	public static void writeXMLFile(Node node, String fileName) throws Exception {
		DOMSource source = new DOMSource(node);
		writeXMLFile(source, fileName);
	}

	public static Node getChildNodeByPath(Node currentNode, String path) {
		String tagName = path;
		int pos = path.indexOf("/");
		if (pos > -1) {
			tagName = path.substring(0, pos);
			path = path.substring(pos + 1);
			if (tagName.equals(""))
				return getChildNodeByPath(currentNode, path);

			if (tagName.equals("/"))
				return getChildNodeByPath(currentNode.getOwnerDocument(), path);

			Node node = getChildNodeByTagName(currentNode, tagName);
			if (node == null)
				return null;

			return getChildNodeByPath(node, path);
		}
		return getChildNodeByTagName(currentNode, tagName);
	}

	/**
	 * 判断是否是一个节点
	 * 
	 * @param node
	 * @param name
	 * @return
	 */
	private static boolean isNodeName(Node node, String name) {
		return node.getNodeName().equalsIgnoreCase(name) || node.getNodeName().replace("_", "").equalsIgnoreCase(name);
	}

	/**
	 * 创建org.w3c.dom.Document
	 * 
	 * @param xmlString
	 *            XML格式的字符串
	 * @return 返回建立的org.w3c.dom.Document对象，建立不成功返回null 。
	 * @throws Exception
	 */
	public static org.w3c.dom.Document createW3CDocument(String xmlString) throws Exception {
		org.jdom2.Document document = buildFromXMLString(xmlString);
		
		if (document != null) {
			return outputToDom(document);
		} else {
			return null;
		}
	}

	/**
	 * 根据XML 字符串 建立JDom对象
	 * 
	 * @param xmlString
	 *            XML格式的字符串
	 * @return 返回建立的JDom对象，建立不成功返回null 。
	 * @throws Exception
	 */
	public static org.jdom2.Document buildFromXMLString(String xmlString) throws Exception {
		
		SAXBuilder builder = new SAXBuilder();
		org.jdom2.Document document = builder.build(new StringReader(xmlString));
		return document;
	}

	/**
	 * 将jdocument转换成org.w3c.dom.Document
	 * 
	 * @param jdomDoc
	 * @return
	 * @throws JDOMException
	 */
	public static org.w3c.dom.Document outputToDom(org.jdom2.Document jdomDoc) throws Exception   {
		org.jdom2.output.DOMOutputter outputter = new org.jdom2.output.DOMOutputter();
		return outputter.output(jdomDoc);
	}
	
	 /**
	  * 增加节点
	  * @param buffer
	  * @param name
	  * @param value
	  */
	public static void addNode(StringBuffer buffer, String name, String value) 
	{
	    value = StrKit.replace(value, "<", "&lt;");
	    value = StrKit.replace(value, ">", "&gt;");
	    value = StrKit.replace(value, "&", "&amp;");
	    value = StrKit.replace(value, "'", "&apos;");
	    value = StrKit.replace(value, "\"", "&quot;");
	    buffer.append("<");
	    buffer.append(name);
	    buffer.append(">");
	    buffer.append(ChgDataKit.nullToString(value));
	    buffer.append("</");
	    buffer.append(name);
	    buffer.append(">");
	    buffer.append("\r\n");
	 }
	
	public static void addNodeE(StringBuffer buffer, String name, String value) {
		value = StrKit.replace(value, "<", "&lt;");
		value = StrKit.replace(value, ">", "&gt;");
		value = StrKit.replace(value, "&", "&amp;");
		value = StrKit.replace(value, "'", "&apos;");
		value = StrKit.replace(value, "\"", "&quot;");
		if(!"SpecialAgreementDesc".equals(name)){
			value = StrKit.replace(value, "\"", "&quot;");
		}

		buffer.append("<");
		buffer.append(name);
		buffer.append(">");
		buffer.append(value);
		buffer.append("</");
		buffer.append(name);
		buffer.append(">");
		buffer.append("\r\n");
	}

	
	 /**
	  * 增加节点-值不为空才添加
	  * @param buffer
	  * @param name
	  * @param value
	  */
	public static void addNodeNotIncludeNull(StringBuffer buffer, String name, String value) 
	{
		
		if (StrKit.isNoEmpty(value)) {
			
			value = StrKit.replace(value, "<", "&lt;");
		    value = StrKit.replace(value, ">", "&gt;");
		    value = StrKit.replace(value, "&", "&amp;");
		    value = StrKit.replace(value, "'", "&apos;");
		    value = StrKit.replace(value, "\"", "&quot;");
		    buffer.append("<");
		    buffer.append(name);
		    buffer.append(">");
		    buffer.append(value);
		    buffer.append("</");
		    buffer.append(name);
		    buffer.append(">");
		    buffer.append("\r\n");
		}
	    
	 }
	 public static String toXMLBusiFormat(Object object)
	 {
		 Class clazz = object.getClass();
		 XStream xStream = new XStream(new DomDriver(null,
					new XmlFriendlyReplacer("_-", "_"))) {
			};
		 xStream.aliasAttribute(null, "class");
		 xStream.alias(object.getClass().getSimpleName(), object.getClass());
		 xStream.useAttributeFor(clazz.getSuperclass(), "type");
		 xStream.useAttributeFor(clazz.getSuperclass(), "version");
		 //指定使用别名   
		 xStream.autodetectAnnotations(true);
			
		 xStream.registerConverter(new CollectionConverter(new MyArrayMapper(
		 xStream.getMapper())) {
			public boolean canConvert(Class type) {
				return type==null?false:Collection.class.isAssignableFrom(type);
			}
			public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
				Collection collection = (Collection) source;
				for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
				    Object item = iterator.next();
				    if(item==null)
				       continue;
				    writeItem(item, context, writer);
				 }
			}
				
		});
			
		StringBuffer buf = new StringBuffer();

		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n");
	    buf.append(xStream.toXML(object));

		return buf.toString()
					.replace("Type=\"request\" Version=\"1.0\"",
							"type=\"request\" version=\"1.0\"")
					.replace("Type=\"response\" Version=\"1.0\"",
							"type=\"response\" version=\"1.0\"");

	}
	/**
	 * String --> object
	 * @param xmlStr
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		//注册日期转换器
		xstream.registerConverter(new XStreamYearToDayConverter());
		xstream.registerConverter(new XStreamYearToSecondConverter());
		T obj = (T)xstream.fromXML(xmlStr);
		return obj;
	}
	
	/**
	 * 忽略掉Object对象中未有的属性字段的XML序列化
	 * @param xmlStr
	 * @param cls
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBeanOfIgnoreUnKnownElements(String xmlStr, Class<T> cls) {
		XStream xstream = new XStream(new DomDriver()){
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next){
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(Class definedIn,String fieldName){
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xstream.processAnnotations(cls);
		//注册日期转换器
		xstream.registerConverter(new XStreamYearToDayConverter());
		xstream.registerConverter(new XStreamYearToSecondConverter());

		T obj = (T)xstream.fromXML(xmlStr);
		return obj;
	}
	
	/**
	 * object --> String
	 * @param object
	 * @return
	 */
	public static String toXML(Object object) {
	    XStream xstream = new XStream(new DomDriver());
        // 识别obj类中的注解
        xstream.processAnnotations(object.getClass());
        //注册日期转换器
		xstream.registerConverter(new XStreamYearToDayConverter());
		xstream.registerConverter(new XStreamYearToSecondConverter());
        //去掉生成的class属性
        xstream.aliasSystemAttribute(null, "class");
        return xstream.toXML(object);

	}
	
	/**
	 * object --> String
	 * @param object
	 * @return
	 */
	public static String toXMLOFAddMapConverter(Object object) {
	    XStream xstream = new XStream(new DomDriver());
        // 识别obj类中的注解
        xstream.processAnnotations(object.getClass());
        //注册日期转换器
		xstream.registerConverter(new XStreamYearToDayConverter());
		xstream.registerConverter(new XStreamYearToSecondConverter());
		xstream.registerConverter(new MapConverter(new DefaultMapper(XStream.class.getClassLoader())));
        //去掉生成的class属性
        xstream.aliasSystemAttribute(null, "class");
        return xstream.toXML(object);

	}
	/**
	 * object --> String
	 * @param object
	 * @return
	 */
	public static String toXMLOfMap(Object object) {
	    XStream xstream = new XStream(new DomDriver());
	    xstream.registerConverter(new MapConverter(xstream.getMapper()));
	    //去掉生成的class属性
        xstream.aliasSystemAttribute(null, "class");
        return xstream.toXML(object);

	}
	/**
	 * 移除无效的XML字符
	 * 官方定义了XML的无效字符分为三段：
    	0x00 - 0x08
    	0x0b - 0x0c
    	0x0e - 0x1f
	 * @param value
	 * @return
	 */
	public static String removeInvalidXMLCharacter(String value) 
    {

		return value.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
    }
	 /**
		 * 添加父节点（可以包含子节点的节点）
		 * 
		 * @param buffer
		 * @param name
		 * @param value
		 */
		public static void addParentNode(StringBuffer buff, String name, int node) {
			name = StrKit.replace(name, "<", "&lt;");
			name = StrKit.replace(name, ">", "&gt;");
			name = StrKit.replace(name, "&", "&amp;");
			name = StrKit.replace(name, "'", "&apos;");
			name = StrKit.replace(name, "\"", "&quot;");
			if (node == StartNode) {
				buff.append("<");
				buff.append(name);
				buff.append(">");
				buff.append("\r\n");
			} else if (node == EndNode) {
				buff.append("</");
				buff.append(name);
				buff.append(">");
				buff.append("\r\n");
			}

		}
		public static void main(String[] args) throws JDOMException, XMLStreamException {

		
		}


}