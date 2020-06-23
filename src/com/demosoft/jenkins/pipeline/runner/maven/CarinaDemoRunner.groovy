package com.demosoft.jenkins.pipeline.runner.maven

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.pipeline.runner.maven.TestNG

@InheritConstructors
class CarinaDemoRunner extends TestNG {

	def overriddenFactories = ['com.qaprosoft.jenkins.jobdsl.factory.view.ListViewFactory' : 'com.demosoft.jenkins.jobdsl.factory.view.DemoListViewFactory']

	@Override
	public void onPush() {
		pipelineLibrary = 'QPS-Pipeline-demo'
		runnerClass = 'com.demosoft.jenkins.pipeline.runner.maven.CarinaDenmoRunner'
		logger.info('CarinaDemoRunner')
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
			scmClient.clone(context.env.getEnvironment().get("CARINA_DEMO_PIPELINE_GIT_URL"), context.env.getEnvironment().get("CARINA_DEMO_GIT_BRANCH"), "qps-demo-pipeline")
			def additionalClasspath = "qps-pipeline/src" + "\n" + "qps-demo-pipeline/src"
			context.println("additionalClasspath: " + additionalClasspath)
			setDslClasspath(additionalClasspath)
		}
	}

}