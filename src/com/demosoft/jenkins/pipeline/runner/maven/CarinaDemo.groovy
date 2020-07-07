package com.demosoft.jenkins.pipeline.runner.maven

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.pipeline.runner.maven.TestNG

@InheritConstructors
class CarinaDemo extends TestNG {

	def overriddenFactories = ['com.qaprosoft.jenkins.jobdsl.factory.pipeline.TestJobFactory' : 'com.demosoft.jenkins.jobdsl.factory.pipeline.DemoTestJobFactory']

	@Override
	public void onPush() {
		pipelineLibrary = 'QPS-Pipeline-demo'
		runnerClass = 'com.demosoft.jenkins.pipeline.runner.maven.CarinaDemo'
		prepare()
		super.onPush()
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
            scmClient.clone(configuration.getGlobalProperty("CARINA_DEMO_PIPELINE_GIT_URL"), configuration.getGlobalProperty("CARINA_DEMO_PIPELINE_GIT_BRANCH"), "carina-demo-pipeline")
			def additionalClasspath = "qps-pipeline/src" + "\n" + "carina-demo-pipeline/src"
			context.println("additionalClasspath: " + additionalClasspath)
			setDslClasspath(additionalClasspath)
		}
	}
}