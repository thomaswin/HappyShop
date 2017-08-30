/*
 *  Copyright 2017, Tun Lin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.sephora.happyshop.data;

public class Category {

    public String name;
    public String description;
    public int resource;

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String description, int resource) {
        this.name = name;
        this.description = description;
        this.resource = resource;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
