package com.qaprosoft.jenkins.jobdsl.factory.pipeline

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.jobdsl.factory.pipeline.TestJobFactory


@InheritConstructors
class DemoTestJobFactory extends TestJobFactory {

	@Override
	def create() {
		def pipelineJob = super.create()

		pipelineJob.with {
			parameters {
				booleanParam('debug', false, '')
			}
		}
	}
}