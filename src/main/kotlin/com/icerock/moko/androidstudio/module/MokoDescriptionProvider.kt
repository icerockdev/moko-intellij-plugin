/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerock.moko.androidstudio.module

import com.android.tools.idea.npw.model.NewModuleModel
import com.android.tools.idea.npw.module.ModuleDescriptionProvider
import com.android.tools.idea.npw.module.ModuleGalleryEntry
import com.android.tools.idea.wizard.model.SkippableWizardStep
import com.google.common.collect.ImmutableList
import com.intellij.openapi.project.Project
import com.intellij.ui.layout.panel
import javax.swing.Icon
import javax.swing.JComponent

// https://jetbrains.org/intellij/sdk/docs/intro/welcome.html
// https://github.com/PStrelchenko/android-studio-new-module-plugin-sample
// https://github.com/hhru/android-multimodule-plugin/tree/master/plugins/hh-carnival
class MokoDescriptionProvider : ModuleDescriptionProvider {
    override fun getDescriptions(project: Project?): MutableCollection<out ModuleGalleryEntry> {
        return ImmutableList.of(MokoModuleEntry())
    }
}

class MokoModuleEntry : ModuleGalleryEntry {
    override val name: String = "moko"
    override val description: String = "my moko module"
    override val icon: Icon? = null

    override fun createStep(model: NewModuleModel): SkippableWizardStep<*> =
        CreateFeatureModuleStep(model)

    private class CreateFeatureModuleStep<M : com.android.tools.idea.wizard.model.WizardModel>(
        model: M
    ) : SkippableWizardStep<M>(model, "Step 1") {

        override fun getComponent(): JComponent = panel {
            titledRow("My step title") {}
        }
    }
}
