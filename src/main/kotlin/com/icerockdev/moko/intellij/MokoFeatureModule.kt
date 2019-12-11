/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.moko.intellij

import com.intellij.icons.AllIcons
import com.intellij.openapi.module.*
import javax.swing.*

class MokoFeatureModule: ModuleType<MokoFeatureBuilder>("moko_feature_module") {
    override fun createModuleBuilder(): MokoFeatureBuilder = MokoFeatureBuilder()
    override fun getName(): String = "Moko feature"
    override fun getDescription(): String = "some desc"
    override fun getNodeIcon(isOpened: Boolean): Icon {
        return AllIcons.General.Information
    }
}