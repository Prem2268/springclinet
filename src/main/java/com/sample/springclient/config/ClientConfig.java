package com.sample.springclient.config;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

	@Value("${server.ssl.key-store}")
	private String keyStore;

	@Value("${server.ssl.key-store-password}")
	private String keyStorePWD;

	@Value("${server.ssl.trust-store}")
	private String trustStore;

	@Value("${server.ssl.trust-store-password}")
	private String trustStorePWD;

	@Bean
	public RestTemplate getRestTemplate() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		RestTemplate restTemplate = new RestTemplate();

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
				.loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.loadKeyMaterial(new File(keyStore), "changeit".toCharArray(), "changeit".toCharArray()).build(),
				NoopHostnameVerifier.INSTANCE);

		HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
				.build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		return restTemplate;
	}

}
