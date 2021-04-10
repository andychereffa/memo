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

package com.zeoflow.memo.processor;

import androidx.annotation.NonNull;

import com.google.common.base.VerifyException;
import com.zeoflow.jx.file.MethodSpec;
import com.zeoflow.jx.file.TypeName;
import com.zeoflow.memo.annotation.MemoComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SuppressWarnings({"WeakerAccess", "SimplifyStreamApiCallChains"})
public class PreferenceComponentAnnotatedClass
{

    private static final String ENTITY_PREFIX = "_MemoEntity";

    public final String packageName;
    public final TypeElement annotatedElement;
    public final TypeName typeName;
    public final String clazzName;
    public final List<String> entities;
    public final List<String> keyNames;
    public final List<String> generatedClazzList;

    public PreferenceComponentAnnotatedClass(
            @NonNull TypeElement annotatedElement,
            @NonNull Elements elementUtils,
            @NonNull Map<String, String> annotatedEntityTypeMap)
            throws VerifyException
    {
        PackageElement packageElement = elementUtils.getPackageOf(annotatedElement);
        this.packageName =
                packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
        this.annotatedElement = annotatedElement;
        this.typeName = TypeName.get(annotatedElement.asType());
        this.clazzName = annotatedElement.getSimpleName().toString();
        this.entities = new ArrayList<>();
        this.keyNames = new ArrayList<>();
        this.generatedClazzList = new ArrayList<>();

        annotatedElement.getEnclosedElements().stream()
                .filter(element -> element instanceof ExecutableElement)
                .map(element -> (ExecutableElement) element)
                .forEach(
                        method ->
                        {
                            MethodSpec methodSpec = MethodSpec.overriding(method).build();
//                            if (methodSpec.returnType != TypeName.get(Void.TYPE)
//                                    && methodSpec.parameters.size() == 1)
//                            {
//                                throw new VerifyException(
//                                        String.format(
//                                                "return type should be void : '%s' method with return type '%s'",
//                                                methodSpec.name, methodSpec.returnType));
//                            } else if (methodSpec.parameters.size() > 1)
//                            {
//                                throw new VerifyException(
//                                        String.format(
//                                                "length of parameter should be 1 or 0 : '%s' method with parameters '%s'",
//                                                methodSpec.name, methodSpec.parameters.toString()));
//                            }
                        });

        Set<String> entitySet = new HashSet<>();
        annotatedElement.getAnnotationMirrors().stream()
                .filter(
                        annotationMirror ->
                                TypeName.get(annotationMirror.getAnnotationType())
                                        .equals(TypeName.get(MemoComponent.class)))
                .forEach(
                        annotationMirror ->
                                annotationMirror
                                        .getElementValues()
                                        .forEach(
                                                (type, value) ->
                                                {
                                                    String[] values = value.getValue().toString().split(",");
                                                    List<String> valueList = Arrays.asList(values);
                                                    entitySet.addAll(valueList);
                                                }));

        entitySet.stream().forEach(
                value ->
                {
                    if (!annotatedEntityTypeMap.containsKey(value))
                    {
                        throw new VerifyException(String.format("%s is not a preference entity.", value));
                    } else
                    {
                        keyNames.add(annotatedEntityTypeMap.get(value));
                        generatedClazzList.add(annotatedEntityTypeMap.get(value) + ENTITY_PREFIX);
                    }
                });

        entities.addAll(entitySet);
    }

}
