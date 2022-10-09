/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.cloud.router.data.controlplane.ControlPlaneConnection;
import com.alibaba.cloud.router.data.crd.LabelRouteData;
import com.alibaba.cloud.router.data.crd.MatchService;
import com.alibaba.cloud.router.data.crd.UntiedRouteDataStructure;
import com.alibaba.cloud.router.data.crd.rule.HeaderRule;
import com.alibaba.cloud.router.data.crd.rule.RouteRule;
import com.alibaba.cloud.router.data.crd.rule.UrlRule;
import com.alibaba.cloud.router.ribbon.LabelRouteRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HH
 */
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = true)
@EnableFeignClients
@RibbonClient(name = "label-route", configuration = LabelRouteRule.class)
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@FeignClient(name = "service-provider")
	public interface FeignService {

		/**
		 * Feign test.
		 * @return String
		 */
		@GetMapping("/test")
		String test();

	}

	@RestController
	public class Controller {

		@Autowired
		private ConsumerApplication.FeignService feignService;

		@Autowired
		ControlPlaneConnection controlPlaneConnection;

		@GetMapping("/router-test")
		public String notFound() {
			return feignService.test();
		}

		@GetMapping("/add")
		public void getDataFromControlPlaneTest() {
			List<RouteRule> routeRules = new ArrayList<>();
			List<MatchService> matchServices = new ArrayList<>();

			UntiedRouteDataStructure untiedRouteDataStructure = new UntiedRouteDataStructure();
			untiedRouteDataStructure.setTargetService("service-provider");

			LabelRouteData labelRouteData = new LabelRouteData();
			labelRouteData.setDefaultRouteVersion("v1");

			RouteRule routeRule = new HeaderRule();
			routeRule.setType("header");
			routeRule.setCondition("=");
			routeRule.setKey("tag");
			routeRule.setValue("gray");
			RouteRule routeRule1 = new UrlRule.Parameter();
			routeRule1.setType("parameter");
			routeRule1.setCondition("=");
			routeRule1.setKey("test");
			routeRule1.setValue("gray");
			routeRules.add(routeRule);
			routeRules.add(routeRule1);

			MatchService matchService = new MatchService();
			matchService.setVersion("v2");
			matchService.setWeight(100);
			matchService.setRuleList(routeRules);
			matchServices.add(matchService);

			labelRouteData.setMatchRouteList(matchServices);

			untiedRouteDataStructure.setLabelRouteData(labelRouteData);

			List<UntiedRouteDataStructure> untiedRouteDataStructureList=new ArrayList<>();
			untiedRouteDataStructureList.add(untiedRouteDataStructure);
			controlPlaneConnection.getDataFromControlPlane(untiedRouteDataStructureList);
		}

		@GetMapping("/update")
		public void updateDataFromControlPlaneTest() {
			List<RouteRule> routeRules = new ArrayList<>();
			List<MatchService> matchServices = new ArrayList<>();

			UntiedRouteDataStructure untiedRouteDataStructure = new UntiedRouteDataStructure();
			untiedRouteDataStructure.setTargetService("service-provider");

			LabelRouteData labelRouteData = new LabelRouteData();
			labelRouteData.setDefaultRouteVersion("v1");

			RouteRule routeRule = new HeaderRule();
			routeRule.setType("header");
			routeRule.setCondition("=");
			routeRule.setKey("tag");
			routeRule.setValue("gray");
			RouteRule routeRule1 = new UrlRule.Parameter();
			routeRule1.setType("parameter");
			routeRule1.setCondition("=");
			routeRule1.setKey("test");
			routeRule1.setValue("gray");
			routeRules.add(routeRule);
			routeRules.add(routeRule1);

			MatchService matchService = new MatchService();
			matchService.setVersion("v2");
			matchService.setWeight(50);
			matchService.setRuleList(routeRules);
			matchServices.add(matchService);

			labelRouteData.setMatchRouteList(matchServices);

			untiedRouteDataStructure.setLabelRouteData(labelRouteData);

			List<UntiedRouteDataStructure> untiedRouteDataStructureList=new ArrayList<>();
			untiedRouteDataStructureList.add(untiedRouteDataStructure);
			controlPlaneConnection.getDataFromControlPlane(untiedRouteDataStructureList);
		}
	}
}