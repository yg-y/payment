package com.young.payment.config;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReadPKCS12File {
    // 线程个数
    private static final int THREAD_POOL_SIZE = 10;

    // 初始化线程池
    private ExecutorService executorService = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    // HTTPS证书的本地路径
    private static final String CERT_LOCAL_PATH = "apiclient_cert.pem";

    // HTTPS证书密码，默认密码等于商户号MCHID
    private static final String CERT_PASSWORD = "1559020831";

    private static InputStream certStream = ReadPKCS12File.class.getClassLoader().getResourceAsStream(CERT_LOCAL_PATH);


//    public static void main(String[] args) {
//
//        ReadPKCS12File readPKCS12File = new ReadPKCS12File();
//        for (int threadNo = 0; threadNo < THREAD_POOL_SIZE; threadNo++) {
//            readPKCS12File.executorService.execute(readPKCS12File.new LoadCertInputStream());
//        }
//        readPKCS12File.executorService.shutdown();
//    }

    public class LoadCertInputStream implements Runnable {

        @Override
        public void run() {
            // 证书
            char[] password = CERT_PASSWORD.toCharArray();
            InputStream certStream = ReadPKCS12File.certStream;
            try {
                KeyStore ks = KeyStore.getInstance("PKCS12");
                ks.load(certStream, password);

                // 实例化密钥库 & 初始化密钥工厂
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(ks, password);

                // 创建 SSLContext
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

                // 余下代码就不写了，，，

                System.out.println("初始化SSL成功！");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        }
    }
}
