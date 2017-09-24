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

import io.buildpal.bdd.json.Json;
import io.buildpal.bdd.writer.BasicStoryWriter;
import io.buildpal.bdd.StoryDetails;
import io.buildpal.bdd.json.JSONArray;
import org.junit.runner.Description;

import java.util.Map;

import static io.buildpal.bdd.config.Constants.GIVEN;
import static io.buildpal.bdd.config.Constants.METHOD_NAME;
import static io.buildpal.bdd.config.Constants.NAME;
import static io.buildpal.bdd.config.Constants.THEN;
import static io.buildpal.bdd.config.Constants.WHEN;

class StoryWriter extends BasicStoryWriter {

    private final Map<Description, SceneStatement> statementsMap;

    StoryWriter(StoryDetails storyDetails, Map<Description, SceneStatement> statementsMap) {
        super(storyDetails);

        this.statementsMap = statementsMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void writeScenarios(JSONArray scenarios, Json report) throws Exception {
        for (Description description : statementsMap.keySet()) {
            SceneStatement statement = statementsMap.get(description);

            Json scenario = new Json();
            scenario.put(METHOD_NAME, description.getMethodName());
            scenario.put(NAME, statement.getDescription());

            if (statement.given().length() > 0) {
                Json given = new Json();
                given.put(NAME, statement.given());
                addAnds(statement.givenAnds(), given);

                scenario.put(GIVEN, given);
            }

            if (statement.when().length() > 0) {
                Json when = new Json();
                when.put(NAME, statement.when());

                scenario.put(WHEN, when);
            }

            Json then = new Json();
            then.put(NAME, statement.then());
            addAnds(statement.thenAnds(), then);

            scenario.put(THEN, then);

            scenarios.add(scenario);
        }
    }
}
