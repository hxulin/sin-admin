package tech.ldxy.sin.core.web.filter;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.util.StreamUtils;
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
 *
 * 1、预防 XSS 攻击
 * 2、拓展 RequestBody 无限获取(HttpServletRequestWrapper只能获取一次)
 *
 * @author Caratacus
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储 requestBody byte[]
     */
    private final byte[] body;

    RequestWrapper(HttpServletRequest request) {
        super(request);
        this.body = getByteBody(request);
    }

    private byte[] getByteBody(HttpServletRequest request) {
        byte[] body = new byte[0];
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    @Override
    public BufferedReader getReader() {
        return ObjectUtils.isEmpty(body) ? null : new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream is = new ByteArrayInputStream(body);
        return getServletInputStream(is);
    }

    /**
     * 获取ServletInputStream
     */
    private ServletInputStream getServletInputStream(ByteArrayInputStream is) {
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return is.read();
            }
        };
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
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value instanceof String) {
            htmlEscape((String) value);
        }
        return value;
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

}