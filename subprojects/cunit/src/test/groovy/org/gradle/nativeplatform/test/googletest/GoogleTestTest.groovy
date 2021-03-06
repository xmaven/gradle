/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.nativeplatform.test.googletest
import org.gradle.language.cpp.plugins.CppPlugin
import org.gradle.model.internal.core.ModelPath
import org.gradle.model.internal.type.ModelType
import org.gradle.nativeplatform.NativeLibrarySpec
import org.gradle.nativeplatform.test.googletest.plugins.GoogleTestPlugin
import org.gradle.platform.base.test.TestSuiteContainer
import org.gradle.util.TestUtil
import spock.lang.Specification

class GoogleTestTest extends Specification {
    final def project = TestUtil.createRootProject();

    def "check the correct binary type are created for the test suite"() {
        when:
        project.apply(plugin: CppPlugin)
        project.apply(plugin: GoogleTestPlugin)
        project.model {
            components {
                main(NativeLibrarySpec)
            }
        }
        project.evaluate()

        then:
        def binaries = project.modelRegistry.get(ModelPath.path("testSuites"), ModelType.of(TestSuiteContainer)).getByName("mainTest").binaries
        binaries.collect({ it instanceof GoogleTestTestSuiteBinarySpec }) == [true] * binaries.size()
    }
}
