package ltd.colingting.external.client;


import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Copyright © 2022 ChinaMobile Info. Tech Ltd. All rights reserved.
 * <p>
 * 文件远端请求
 *
 * @author <a href="19802021461@139.com">ZhiFeng.Wu</a>
 * @date 2022/7/25 10:13
 */
@FeignClient(name = "${hcy.feignclient.demo.service-name}", path = "${hcy.feignclient.demo.context-path}")
public interface YahooForexService {


	@RequestMapping(value = "/getExchangeRate", method = RequestMethod.POST)
	BigDecimal getExchangeRate(@RequestParam String value, @RequestParam String value1);
}
