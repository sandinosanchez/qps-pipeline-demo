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
}