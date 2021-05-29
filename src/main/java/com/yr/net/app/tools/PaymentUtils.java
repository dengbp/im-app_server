package com.yr.net.app.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *
 * @author dengbp
 * @since 2019-04-23
 **/
@Slf4j
public class PaymentUtils {

    private PaymentUtils() {
    }

    /**
     * 读取 request 中的数据
     *
     * @param request
     * @return
     */
    public static String getRequestData(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        String line;
        try {
            // 接收request数据流，并指定编码格式接收
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * 加密url
     *
     * @param value
     * @return
     */
    public static String urlEncoder(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码url
     *
     * @param value
     * @return
     */
    public static String urlDecoder(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 匹配ip是否合法
     *
     * @param ip
     * @return
     */
    public static Boolean isIp(String ip) {
        String re = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }


    /**
     * 支付参数生成签名
     *
     * @param params
     * @param apiKey
     * @return
     */
    public static String sign(Map<String, String> params, String apiKey) {
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(params.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }
            sb.append("key=").append(apiKey);
            log.info("签名参数:{}", sb.toString());
            result = Objects.requireNonNull(MD5(sb.toString())).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        log.info("签名值:{}",result);
        return result;
    }


    /**
     * 支付参数生成签名
     *
     * @param params
     * @return
     */
    public static String sign(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> set = new TreeMap<>(params).entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String k = entry.getKey();
            Object v = entry.getValue();
            sb.append(k).append("=").append(v).append("&");
        }
        String s = sb.toString();
        return Objects.requireNonNull(MD5(s.substring(0, s.length() - 1))).toUpperCase();
    }

    /**
     * 生成md5摘要
     *
     * @param content
     * @return
     */
    public static String MD5(String content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5ValueByteArray = messageDigest.digest(content.getBytes());
            BigInteger bigInteger = new BigInteger(1 , md5ValueByteArray);
            return bigInteger.toString(16).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成 HMAC_SHA256
     *
     * @param content
     * @param api_key
     * @return
     * @throws Exception
     */
    public static String HMAC_SHA256(String content, String api_key) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = generator.generateKey();
            byte[] key = secretKey.getEncoded();
            SecretKey secretKeySpec = new SecretKeySpec(api_key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(content.getBytes());
            return new HexBinaryAdapter().marshal(digest).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * XML格式字符串转换为Map
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, String> xmlToMap(String xmlStr) {
        try (InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8))) {
            Map<String, String> data = new HashMap<>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            return data;
        } catch (Exception ex) {
            log.warn("xml convert to map failed message: {}", ex.getMessage());
            return null;
        }
    }

    /**
     * map转换为xml格式
     *
     * @param params
     * @return
     */
    public static String mapToXml(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es = params.entrySet();
        Iterator<Map.Entry<String, String>> it = es.iterator();
        sb.append("<xml>");
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("e", "value1");
        map.put("f", "value2");
        map.put("a", "value3");
        map.put("c", "value4");
        System.out.println(sign(map));
    }

    /**
     * 获得request的ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (Objects.nonNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (Objects.nonNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 过滤掉关键参数
     *
     * @param param
     * @return
     */
    public static HashMap<String, String> paramFilter(Map<String, String> param) {
        HashMap<String, String> result = new HashMap<>();
        if (param == null || param.size() <= 0) {
            return result;
        }
        for (String key : param.keySet()) {
            String value = param.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把Request中的数据解析为xml
     *
     * @param request
     * @return
     */
    public static String requestDataToXml(HttpServletRequest request) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
