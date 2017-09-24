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

package io.buildpal.bdd.phase;

import io.buildpal.bdd.Given;

/**
 * Represents the {@link Given} phase in a scene.
 */
public class GivenPhase extends Phase {
    private Given given;

    public GivenPhase(String description, Given given) {
        super(description);
        this.given = given;
    }

    public Given getGiven() {
        return given;
    }
}
