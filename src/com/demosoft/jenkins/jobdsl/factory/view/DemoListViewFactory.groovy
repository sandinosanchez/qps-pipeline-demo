package com.demosoft.jenkins.jobdsl.factory.view

import groovy.transform.InheritConstructors
import com.qaprosoft.jenkins.jobdsl.factory.DslFactory

@InheritConstructors
class DemoListViewFactory extends DslFactory {

	def folder
    def name
    def descFilter
    def nameFilter

    public DemoListViewFactory(folder, name, descFilter) {
        this(folder, name, descFilter, "")
    }
    
    public DemoListViewFactory(folder, name, descFilter, nameFilter) {
        this.folder = folder
        this.name = name
        this.descFilter = descFilter
        this.nameFilter = nameFilter
    }

    def create() {
        //TODO: reuse getFullName
        def view = _dslFactory.listView("${folder}/${name}")
        view.with {
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                progrsBar()
                scmType()
                buildButton()
            }

            //TODO: reorganize constructor to be able to provide RegexMatchValue
            if (!"${descFilter}".isEmpty()) {
                jobFilters {
                    regex {
                        matchType(MatchType.INCLUDE_MATCHED)
                        matchValue(RegexMatchValue.DESCRIPTION)
                        regex("${descFilter}")
                    }
                }
            }

            if (!"${nameFilter}".isEmpty()) {
                jobFilters {
                    regex {
                        matchType(MatchType.INCLUDE_MATCHED)
                        matchValue(RegexMatchValue.NAME)
                        regex("${nameFilter}")
                    }
                }
            }
        }
        return view
    }
}