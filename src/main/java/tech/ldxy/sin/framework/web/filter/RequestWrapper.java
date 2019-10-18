package tech.ldxy.sin.framework.web.filter;

import org.apache.commons.io.IOUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 功能描述: Request 请求包装类
 *          1、预防 XSS 攻击
 *          2、拓展 RequestBody 无限次读取
 *
 * @author hxulin
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    private BufferedReader reader;

    private ServletInputStream inputStream;

    RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = IOUtils.toByteArray(request.getInputStream());
        inputStream = new RequestCachingInputStream(body);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEscape(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    @Override
    public String getQueryString() {
        String value = super.getQueryString();
        if (value == null) {
            return null;
        }
        return htmlEscape(value);
    }

    /**
     * 使用 Spring HtmlUtils 转义 HTML 标签预防 XSS 攻击
     */
    private String htmlEscape(String str) {
        return HtmlUtils.htmlEscape(str);
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    public static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }

}