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

import io.buildpal.bdd.test.StoreFront;
import org.junit.Assert;
import org.junit.runner.RunWith;

@RunWith(StoryRunner.class)
@Story(name = "Returns go to stock",
        description = "As a store owner, In order to keep track of stock, I want to add items back to stock when they're returned.")
public class StoreFrontTest {

    @Scenario("Refunded items should be returned to stock")
    public void refundAndRestock(Scene s) throws Exception {
        s.
                given("that a customer previously bought a black sweater from me",
                        () -> s.state.val("store", new StoreFront(0, 4).buyBlack(1))).

                and("I have three black sweaters in stock",
                        () -> Assert.assertTrue("Store should carry 3 black sweaters",
                                s.state.<StoreFront>val("store").getBlacks() == 3)).

                when("the customer returns the black sweater for a refund",
                        () -> s.state.<StoreFront>val("store").refundBlack(1)).

                then("I should have four black sweaters in stock",
                        () -> Assert.assertTrue("Store should carry 4 black sweaters",
                                s.state.<StoreFront>val("store").getBlacks() == 4)).
                run();
    }

    @Scenario("Replaced items should be returned to stock")
    public void replaceAndRestock(Scene s) throws Exception {
        s.
                given("that a customer previously bought a blue garment from me",
                        () -> s.state.val("store", new StoreFront(3, 3).buyBlue(1))).

                and("I have two blue garments in stock",
                        () -> Assert.assertTrue("Store should carry 2 blue garments",
                                s.state.<StoreFront>val("store").getBlues() == 2)).

                and("three black garments in stock",
                        () -> Assert.assertTrue("Store should carry 3 black garments",
                                s.state.<StoreFront>val("store").getBlacks() == 3)).

                when("she returns the blue garment for a replacement in black",
                        () -> s.state.<StoreFront>val("store").refundBlue(1).buyBlack(1)).

                then("I should have three blue garments in stock",
                        () -> Assert.assertTrue("Store should carry 3 blue garments",
                                s.state.<StoreFront>val("store").getBlues() == 3)).

                and("two black garments in stock",
                        () -> Assert.assertTrue("Store should carry 2 black garments",
                                s.state.<StoreFront>val("store").getBlacks() == 2)).

                run();
    }
}
