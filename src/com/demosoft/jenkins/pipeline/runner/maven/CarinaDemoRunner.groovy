package com.demosoft.jenkins.pipeline.runner.maven

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.pipeline.runner.maven.TestNG

@InheritConstructors
class CarinaDemoRunner extends TestNG {

	def overridenFactories = ['com.qaprosoft.jenkins.jobdsl.factory.pipeline.view.ListViewFactory' : 'com.demosoft.jenkins.jobdsl.factory.pipeline.view.DemoListViewFactory']

	@Override
	public void onPush() {
		pipelineLibrary = 'QPS-Pipeline-demo'
		runnerClass = 'com.demosoft.jenkins.pipeline.runner.maven.CarinaDenmoRunner'
		logger.info('CarinaDemoRunner')
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

}