package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;
import com.example.demo.model.AppVersionKey;
import com.example.demo.repository.AppVersionKeyRepository;

public class AppVersionService_UnitTest {

	AppVersionKeyRepository keyRepository;
	
	AppVersionService service;
	
	private static final String GLOBAL = "global";
	
	public AppVersionService_UnitTest() {
		keyRepository = Mockito.mock(AppVersionKeyRepository.class);
		service = new AppVersionService(keyRepository);
	}

	@Test
	public void testFilterConfig() {
		AppVersionKey key = TestHelper.getAppVersionKey("App", "1.2", "ios", "11.2");
		
		Map<String, Object> expectedKeyMap = TestHelper.getKeyMap();
		
		Map<String, Object> actualMap = service.filterConfig(key.getConfigs());
		
		assertThat(actualMap).isEqualTo(expectedKeyMap);
	}
	
	@Test
	public void testFilterConfig_EmptyConfig() {
		AppVersionKey key = new AppVersionKey();
		
		Map<String, Object> actualMap = service.filterConfig(key.getConfigs());
		
		assertThat(actualMap).isEmpty();
	}
	
	@Test
	public void testGlobalFilterConfig() {
		AppVersionKey globalKey = TestHelper.getGlobalAppVersionKey("App");
		
		Map<String, Object> keyMap = TestHelper.getKeyMap();
		Map<String, Object> expectedGlobalKeyMap = TestHelper.getGlobalKeyMap();
		
		Map<String, Object> actualMap = service.filterGlobalConfig(keyMap, globalKey.getConfigs());
		
		assertThat(actualMap).isEqualTo(expectedGlobalKeyMap);
	}
	
	@Test
	public void testGlobalFilterConfig_WithEmptySpecificConfig() {
		AppVersionKey globalKey = TestHelper.getGlobalAppVersionKey("App");
		
		Map<String, Object> keyMap = new HashMap<>();
		Map<String, Object> expectedGlobalKeyMap = TestHelper.getGlobalKeyMapForEmptyKeyMap();
		
		Map<String, Object> actualMap = service.filterGlobalConfig(keyMap, globalKey.getConfigs());
		
		assertThat(actualMap).isEqualTo(expectedGlobalKeyMap);
	}
	
	@Test
	public void testGlobalFilterConfig_WithEmptyConfig() {
		AppVersionKey globalKey = TestHelper.getGlobalAppVersionKey("App");
		
		Map<String, Object> keyMap = new HashMap<>();
		Map<String, Object> expectedGlobalKeyMap = TestHelper.getGlobalKeyMapForEmptyKeyMap();
		
		Map<String, Object> actualMap = service.filterGlobalConfig(keyMap, globalKey.getConfigs());
		
		assertThat(actualMap).isEqualTo(expectedGlobalKeyMap);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetConfig() {
		when(keyRepository.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion("App", "1.2", "ios", "11.2"))
			.thenReturn(TestHelper.getAppVersionKey("App", "1.2", "ios", "11.2"));
		when(keyRepository.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion("App", GLOBAL, GLOBAL, GLOBAL))
			.thenReturn(TestHelper.getGlobalAppVersionKey("App"));
		AppVersionResponse expectedResponse = TestHelper.getAppVersionResponse();
		
		AppVersionResponse response = service.getConfig("App", "1.2", "ios", "11.2");
		
		assertThat(response.getConfigs().get("contact")).isEqualTo(expectedResponse.getConfigs().get("contact"));
		assertThat(((List<String>)response.getConfigs().get("whitelist")).size()).isEqualTo(((List<String>)expectedResponse.getConfigs().get("whitelist")).size());
		assertThat(((List<String>)response.getConfigs().get("blacklist")).size()).isEqualTo(((List<String>)expectedResponse.getConfigs().get("blacklist")).size());
		assertThat(response.getConfigs().get("email")).isEqualTo(expectedResponse.getConfigs().get("email"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testgetConfigWithOnlySpecificConfig() {
		when(keyRepository.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion("App", "1.2", "ios", "11.2"))
		.thenReturn(TestHelper.getAppVersionKey("App", "1.2", "ios", "11.2"));
		when(keyRepository.findByAppNameAndAppVersionAndPlatformNameAndPlatformVersion("App", GLOBAL, GLOBAL, GLOBAL))
		.thenReturn(TestHelper.getEmptyGlobalAppVersionKey("App"));
		AppVersionResponse expectedResponse = TestHelper.getAppVersionResponse_WithNoGlobalConfig();
		
		AppVersionResponse response = service.getConfig("App", "1.2", "ios", "11.2");
		
		assertThat(response.getConfigs().get("contact")).isEqualTo(expectedResponse.getConfigs().get("contact"));
		assertThat(((List<String>)response.getConfigs().get("whitelist")).size()).isEqualTo(((List<String>)expectedResponse.getConfigs().get("whitelist")).size());
		assertThat(response.getConfigs().get("email")).isEqualTo(expectedResponse.getConfigs().get("email"));
	}
}
