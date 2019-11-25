package org.zdxue.zk.console.servlet.http;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author xuezhongde
 */
public class MultiReadableHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream baos;

    public MultiReadableHttpServletRequest(HttpServletRequest request) {
        super(request);
        baos = new ByteArrayOutputStream();
        try {
            InputStream inputStream = super.getInputStream();
            int len = 0;
            byte[] buf = new byte[4096];
            while ((len = inputStream.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
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
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
