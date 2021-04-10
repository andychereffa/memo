# Memo
Android processing and secured library for managing SharedPreferences as key-value elements persistence efficiently and structurally.

### 1. Depend on our library

Memo Library is available through Maven Repository.
To use it:

1.  Open the `build.gradle` file for your application.
2.  Make sure that the `repositories` section includes Maven Repository
    `mavenCentral()`. For example:
```groovy
  allprojects {
    repositories {
      mavenCentral()
    }
  }
```

3.  Add the library to the `dependencies` section:
```groovy
dependencies {
    // ...

    // parcelled version
    def memo_version = "1.0.0"

    // Memo Library
    implementation("com.zeoflow:memo:$parcelled_version")
    // Required if you want to use the injector
    implementation("com.zeoflow:memo-annotation:$memo_version")
    annotationProcessor("com.zeoflow:memo-compiler:$memo_version")
    // For kotlin projects use kapt instead of annotationProcessor
    kapt("com.zeoflow:memo-compiler:$memo_version")

    // ...
}
```

### 2. Usage
#### 2.1 Initialize
With Context
```java
Memo.init(context).build();
```
Without Context (the context is retrieved from the ContextProvider at runtime)
```java
Memo.init().build();
```
#### 2.2 Library Calls
Save any type (Any object, primitives, lists, sets, maps ...)
```java
Memo.put(key, T);
```
Get the original value with the original type
```java
T value = Memo.get(key);
```
Delete any entry
```java
Memo.delete(key);
```
Check if any key exists
```java
Memo.contains(key);
```
Check total entry count
```java
Memo.count();
```
Delete everything
```java
Memo.deleteAll();
```

#### 2.3 Customize the Library at initialisation
- Everything is pluggable, therefore you can change any layer with your custom implementation.
- NoEncryption implementation is provided out of box If you want to disable crypto.
```java
Memo.init(context)
  .setEncryption(new NoEncryption())
  // encryption_string is used to encrypt the data and is required
  .setEncryption(new ConcealEncryption(encryption_string))
  .setLogInterceptor(new MyLogInterceptor())
  .setConverter(new MyConverter())
  .setParser(new MyParser())
  .setStorage(new MyStorage())
  .build();
```

## License
    Copyright (C) 2021 ZeoFlow S.R.L.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## 🏆 Contributors 🏆

<!-- ZEOBOT-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<p float="left">
<a href="docs/contributors.md#pushpin-teodor-g-teodorhmx1"><img width="100" src="https://avatars.githubusercontent.com/u/22307006?v=4" hspace=5 title='Teodor G. (@TeodorHMX1) - click for details about the contributions'></a>
</p>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ZEOBOT-LIST:END -->