/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.moko.intellij

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import javax.swing.*

class MokoModuleWizardStep(private val config: MokoFeatureConfig): ModuleWizardStep() {
    override fun updateDataModel() {
        config.projectPrefix = packageNameInput.text
        config.syncProperties()
    }

    lateinit var packageNameInput: JTextField

    override fun getComponent(): JComponent {
        //todo: package name input, feature type selection, dependencies checkbox
        val form = MokoModuleForm().apply { updateConfig(config) }
        packageNameInput = form.packageNameInput
        return form.rootPanel
    }

}