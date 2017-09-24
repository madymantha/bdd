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

package io.buildpal.bdd;

import java.util.HashMap;
import java.util.Map;

public class StoryDetails {
    private String name;
    private String description;
    private String className;

    private Map<String, Object> store;

    public StoryDetails() {
        store = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public StoryDetails setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StoryDetails setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public StoryDetails setClassName(String className) {
        this.className = className;
        return this;
    }

    public Map<String, Object> getStore() {
        return store;
    }
}
