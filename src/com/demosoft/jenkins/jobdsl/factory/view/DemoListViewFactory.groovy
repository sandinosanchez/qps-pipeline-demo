package com.demosoft.jenkins.jobdsl.factory.view

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.jobdsl.factory.view.ListViewFactory

@InheritConstructors
class DemoListViewFactory extends ListViewFactory {

	@Override
	def create() {
		def view = super.create()

		view.with {
			columns {
				progressBar()
				scmType()
			}
		}
	}

}