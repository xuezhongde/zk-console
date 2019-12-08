/**
 * Copyright (c) 2014, Deem Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zdxue.zk.console.vo;

import java.io.UnsupportedEncodingException;

public class LeafBean implements Comparable<LeafBean> {
    private String path;
    private String name;
    private byte[] value;

    public LeafBean(String path, String name, byte[] value) {
        super();
        this.path = path;
        this.name = name;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getStrValue() throws UnsupportedEncodingException {
        return new String(this.value, "UTF-8");
    }

    @Override
    public int compareTo(LeafBean o) {
        return (this.path + this.name).compareTo((o.path + o.name));
    }
}
