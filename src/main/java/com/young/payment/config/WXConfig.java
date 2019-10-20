package com.young.payment.config;

import java.io.*;

public class WXConfig extends WXPayConfig {
    private byte[] certData;

    public WXConfig() throws Exception {
        InputStream certStream = this.getClass().getResourceAsStream("/apiclient_cert.p12");
        this.certData = read(certStream);
        certStream.close();
    }

    public String getAppID() {
        return "wx3dc8e67ebe73bf0e";
    }

    public String getMchID() {
        return "1559020831";
    }

    /**
     * API密钥ajoshdasojdalksdioqwekasndlasndl
     * APIv3密钥asjdhalsfhdasjfhquwoeqlkwaklshdq
     *
     * @return
     */
    public String getKey() {
        return "ajoshdasojdalksdioqwekasndlasndl";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        // 这个方法需要这样实现, 否则无法正常初始化WXPay
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {

            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }

    public static byte[] read(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
