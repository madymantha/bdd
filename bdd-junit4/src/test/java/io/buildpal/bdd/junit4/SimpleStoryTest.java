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

import org.junit.Assert;
import org.junit.runner.RunWith;

@RunWith(StoryRunner.class)
@Story(name = "Adds two numbers")
public class SimpleStoryTest {

    @Scenario("return the result of adding two numbers")
    public void addTwoNumbers(Scene s) throws Exception {
        s.
                given("two numbers a and b",
                        () -> s.state.integer("a", 1).integer("b", 2)).

                when("addition is performed on them",
                        () -> s.state.integer("c", s.state.integer("a") + s.state.integer("b"))).

                then("the result should be equal to the mathematical addition of two numbers",
                        () -> Assert.assertTrue("Result of addition should be 3",
                                s.state.integer("c") == 3)).
                run();
    }
}
