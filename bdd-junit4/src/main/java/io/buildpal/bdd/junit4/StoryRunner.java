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

import io.buildpal.bdd.StoryDetails;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Runs the scenarios defined in a test class that is annotated with {@link Story}.
 * Extends the standard JUnit 4 test case runner.
 * Use this runner to execute scenarios and generate BDD reports.
 */
public class StoryRunner extends BlockJUnit4ClassRunner {

    private StoryDetails storyDetails;
    private final Map<Description, SceneStatement> statementsMap;

    public StoryRunner(Class<?> clazz) throws InitializationError {
        super(clazz);

        Story story = clazz.getAnnotation(Story.class);

        storyDetails = new StoryDetails()
                .setName(story.name())
                .setDescription(story.description())
                .setClassName(getTestClass().getJavaClass().getName());

        statementsMap = new ConcurrentHashMap<>();
    }

    @Override
    public void run(final RunNotifier notifier) {
        // Create a new story writer to generate BDD reports.
        // Using listener will not be invoked by certain build tools like gradle.
        StoryWriter writer = new StoryWriter(storyDetails, statementsMap);

        super.run(notifier);

        try {
            writer.write();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);

        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);

        } else {
            Statement statement = methodBlock(method);
            statementsMap.putIfAbsent(description, (SceneStatement) statement);
            runLeaf(statement, description, notifier);
        }
    }

    @Override
    protected void collectInitializationErrors(List<Throwable> errors) {
        super.collectInitializationErrors(errors);

        Class<?> clazz = getTestClass().getJavaClass();

        if (clazz.getAnnotation(Story.class) == null) {
            errors.add(new Exception("Class " + getTestClass().getName() + " should be annotated with @Story."));

        } else if (clazz.getAnnotation(Story.class).name().length() == 0) {
            errors.add(new Exception("@Story annotation on the class " + getTestClass().getName() +
                    " should NOT have an empty name."));
        }
    }

    /**
     * Returns the methods that run tests.
     * Returns all methods annotated with {@link @Scenario} on this class and superclasses that
     * are not overridden.
     */
    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        return getTestClass().getAnnotatedMethods(Scenario.class);
    }

    /**
     * Adds to {@code errors} for each method annotated with {@code @Scenario} that
     * is not a public, not a void instance method and doesn't have one argument.
     */
    @Override
    protected void validateTestMethods(List<Throwable> errors) {
        List<FrameworkMethod> scenarios = this.getTestClass().getAnnotatedMethods(Scenario.class);

        for (FrameworkMethod scenario : scenarios) {
            scenario.validatePublicVoid(false, errors);

            Method method = scenario.getMethod();

            if (method.getParameterCount() != 1) {
                errors.add(new Exception("Method " + method.getName() +
                        " should have a single parameter of type: Scene"));

            } else if (method.getParameterTypes()[0] != Scene.class) {
                errors.add(new Exception("Method " + method.getName() +
                        " should have a single parameter of type: Scene"));
            }
        }
    }

    /**
     * Returns a {@link Statement} that invokes {@code method} on {@code test}
     */
    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new SceneStatement(method.getAnnotation(Scenario.class).value(), method, test);
    }
}
