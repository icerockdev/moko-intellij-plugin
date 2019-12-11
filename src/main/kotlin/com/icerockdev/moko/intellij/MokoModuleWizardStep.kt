/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.moko.intellij

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import javax.swing.JComponent
import javax.swing.JLabel

class MokoModuleWizardStep(): ModuleWizardStep() {
    override fun updateDataModel() {
    }

    override fun getComponent(): JComponent {
        //todo: package name input, feature type selection, dependencies checkbox
        return JLabel("Test")
    }

}