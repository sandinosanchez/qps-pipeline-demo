package com.demosoft.jenkins.pipeline.runner.maven

import com.qaprosoft.jenkins.pipeline.runner.maven.TestNG

class CarinaDemoRunner extends TestNG {

	// overriden factories

	CarinaDemoRunner(context) {
		super(context)
	}

	CarinaDemoRunner(context, jobType) {
		super(context, jobType)
	}

	@Override
	public void onPush() {
		pipelineLibrary = 'QPS-Pipeline-demo'
		runnerClass = 'com.demosoft.jenkins.pipeline.runner.maven.CarinaDenmoRunner'
		logger.info('CarinaDemoRunner')
		super.onPush()
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