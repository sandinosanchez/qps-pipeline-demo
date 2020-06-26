package com.demosoft.jenkins.pipeline.runner.maven

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.pipeline.Configuration
import com.qaprosoft.jenkins.pipeline.runner.maven.TestNG

@InheritConstructors
class CarinaDemo extends TestNG {

	def overriddenFactories = ['com.qaprosoft.jenkins.jobdsl.factory.pipeline.TestJobFactory' : 'com.demosoft.jenkins.jobdsl.factory.pipeline.DemoTestJobFactory']

	@Override
	protected void onPush() {
		pipelineLibrary = 'QPS-Pipeline-demo'
		runnerClass = 'com.demosoft.jenkins.pipeline.runner.maven.CarinaDemo'
		super.onPush()
	}

	@Override
	protected void build() {
		setLogLevel(Configuration.get('debug')?.toBoolean()))
		super.build()
		clean()
	}

	@Override
	protected clean() {
		super.clean()
		setLogLevel(false)
	}

	@Override
	protected void registerObject(name, object) {
		if (overriddenFactories.containsKey(object.clazz)) {
			context.println("overriding ${object.clazz} by ${overriddenFactories.get(object.clazz)}")
			object.setClass(overriddenFactories.get(object.clazz))
		}
		super.registerObject(name, object)
	}

	protected void prepare() {
		context.node("master") {
			scmClient.clone(false)
            scmClient.clone(context.env.getEnvironment().get("CARINA_DEMO_PIPELINE_URL"), context.env.getEnvironment().get("CARINA_DEMO_PIPELINE_BRANCH"), "qps-demo-pipeline")
			def additionalClasspath = "qps-pipeline/src" + "\n" + "qps-demo-pipeline/src"
			context.println("additionalClasspath: " + additionalClasspath)
			setDslClasspath(additionalClasspath)
		}
	}

	protected void setLogLevel(isDebugEnabled) {
		context.env.QPS_PIPELINE_LOG_LEVEL = isDebugEnabled ? "DEBUG" : "INFO"
	}
}