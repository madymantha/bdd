/*
 * Copyright 2017 Buildpal

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.buildpal.bdd.junit4;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * Represents a scene to be executed during the course of running JUnit-4 tests.
 */
class SceneStatement extends Statement {
    private final Scene scene;

    private final String description;
    private final FrameworkMethod testMethod;
    private final Object target;

    SceneStatement(String description, FrameworkMethod testMethod, Object target) {
        this.description = description;
        this.testMethod = testMethod;
        this.target = target;

        this.scene = new Scene();
    }

    String getDescription() {
        return description;
    }

    public void evaluate() throws Throwable {
        this.testMethod.invokeExplosively(this.target, scene);
    }

    String given() {
        return scene.given();
    }

    List<String> givenAnds() {
        return scene.givenAnds();
    }

    String when() {
        return scene.when();
    }

    String then() {
        return scene.then();
    }

    List<String> thenAnds() {
        return scene.thenAnds();
    }
}
