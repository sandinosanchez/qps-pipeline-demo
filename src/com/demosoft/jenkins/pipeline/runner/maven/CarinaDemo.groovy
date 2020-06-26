package com.demosoft.jenkins.pipeline.runner.maven

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.pipeline.Configuration
import com.qaprosoft.jenkins.pipeline.runner.maven.TestNG

@InheritConstructors
class CarinaDemo extends TestNG {

	def overriddenFactories = ['com.qaprosoft.jenkins.jobdsl.pipeline.TestJobFactory' : 'com.demosoft.jenkins.jobdsl.pipeline.DemoTestJobFactory']

	@Override
	public void onPush() {
		pipelineLibrary = 'QPS-Pipeline-demo'
		runnerClass = 'com.demosoft.jenkins.pipeline.runner.maven.CarinaDemo'
		prepare()
		super.onPush()
	}

	@Override
	public void registerObject(name, object) {
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
}