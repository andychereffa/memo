/*
 * Copyright (C) 2017 zeoflow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zeoflow.demo.components;

import com.zeoflow.demo.LoginActivity;
import com.zeoflow.demo.MainActivity;
import com.zeoflow.demo.entities.Device;
import com.zeoflow.demo.entities.Profile;
import com.zeoflow.memo.annotation.PreferenceComponent;

/**
 * Component integrates entities.
 */
@PreferenceComponent(entities = {Profile.class, Device.class})
public interface AppComponent
{

    /**
     * declare dependency injection targets.
     */
    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

}
