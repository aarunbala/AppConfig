package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.junit.Rule;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class AppVersionService_Consumer_ContractTest extends ConsumerPactTestMk2 {

	private static final String APP_VERSIONING_PROVIDER = "AppVersioning";
	private static final String APP_VERSIONING_CONSUMER = "APP";

	@Rule
	public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2(APP_VERSIONING_PROVIDER, "localhost", 8082, this);

	@Pact(provider = APP_VERSIONING_PROVIDER, consumer = APP_VERSIONING_CONSUMER)
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder.given("App Versioning Config params are available")
				.uponReceiving("App Name, App Version, Platform Name & Platform Version")
				.path("/config/App/1.2/ios/11.2")
				.method("GET")
				.willRespondWith()
					.status(200)
					.body(TestHelper.getAppVersionResponseJSON())
				.toPact();
	}

	@Override
	protected String consumerName() {
		return APP_VERSIONING_CONSUMER;
	}

	@Override
	protected String providerName() {
		return APP_VERSIONING_PROVIDER;
	}

	@Override
	protected void runTest(MockServer arg0) throws IOException {
		AppServiceClient client = new AppServiceClient(arg0.getUrl());
		AppVersionResponse response = client.getConfig("App", "1.2", "ios", "11.2");
		assertThat(response).isNotEqualTo(null);
	}

	private class AppServiceClient {
		private String baseUrl;

		public AppServiceClient(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		public AppVersionResponse getConfig(String appName, String appVersion, String platformName, String platformVersion)
				throws IOException {
			StringBuilder builder = new StringBuilder(baseUrl).append("/config/").append(appName).append("/")
					.append(appVersion).append("/").append(platformName).append("/").append(platformVersion);
			Request.Get(builder.toString()).execute();
			return TestHelper.getAppVersionResponse();
		}
	}

}
