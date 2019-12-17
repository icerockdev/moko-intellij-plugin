/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.moko.intellij

import com.intellij.ide.util.PropertiesComponent

private const val prefixKey = "project_package_name"
data class MokoFeatureConfig(
    var projectPrefix: String = PropertiesComponent.getInstance().getValue(prefixKey) ?: "com.example.app"
) {
    fun syncProperties() {
        PropertiesComponent.getInstance().setValue(prefixKey, projectPrefix)
    }
}