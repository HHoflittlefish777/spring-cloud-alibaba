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

package com.alibaba.cloud.governance.auth.repository;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.cloud.commons.governance.auth.rule.AuthRule;
import com.alibaba.cloud.commons.governance.auth.rule.JwtRule;

/**
 * @author musi
 * @author <a href="liuziming@buaa.edu.cn"></a> To store auth rules in Spring Cloud
 * Alibaba.
 */
public class AuthRepository {

	private Map<String, AuthRule> allowAuthRules = new HashMap<>();

	private Map<String, AuthRule> denyAuthRules = new HashMap<>();

	private Map<String, JwtRule> jwtRules = new HashMap<>();

	public AuthRepository() {

	}

	public AuthRepository(Map<String, AuthRule> allowAuthRules,
			Map<String, AuthRule> denyAuthRules, Map<String, JwtRule> jwtRules) {
		this.allowAuthRules = allowAuthRules;
		this.denyAuthRules = denyAuthRules;
		this.jwtRules = jwtRules;
	}

	public Map<String, AuthRule> getAllowAuthRules() {
		return allowAuthRules;
	}

	public Map<String, AuthRule> getDenyAuthRules() {
		return denyAuthRules;
	}

	public Map<String, JwtRule> getJwtRules() {
		return jwtRules;
	}

	public void setAllowAuthRule(Map<String, AuthRule> allowAuthRules) {
		this.allowAuthRules = allowAuthRules;
	}

	public void setDenyAuthRules(Map<String, AuthRule> denyAuthRules) {
		this.denyAuthRules = denyAuthRules;
	}

	public void setJwtRule(Map<String, JwtRule> jwtRules) {
		this.jwtRules = jwtRules;
	}

}
