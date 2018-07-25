# Authorization

* what is authorization? 
* roles and authorities 
* configuring HttpSecurity
 ** configures a FilterInvocationSecurityMetadataSource which is map of request matchers to config attributes 
* `AccessDecisionManager` s (default is `AffirmativeBased`, others r unanimous and consensus). 
* It has one or more `DecisionVoter`s. It delegates to a `WebExpressionVoter` which contains config attributes.
*  
* `AbstractSecurityInterceptor` (one for web, one for methods)

@Configuration
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
			.anyRequest().permitAll();
	}

}


/**

	* 6.6 Path Parameterspath parameters
	* 6.7 mvcMatchers
	* 6.8 Actuator (Endpoint.*)
	*/
