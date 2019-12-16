/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.moko.intellij

import com.icerockdev.moko.intellij.utils.createFile
import com.intellij.ide.util.projectWizard.*
import com.intellij.openapi.*
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.*
import com.intellij.openapi.util.io.*
import com.intellij.openapi.vfs.*
import java.io.*

class MokoFeatureBuilder: ModuleBuilder() {
    private var config = MokoFeatureConfig()

    override fun getModuleType(): ModuleType<MokoFeatureBuilder> {
        return MokoFeatureModule()
    }

    override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?): ModuleWizardStep? {
        return MokoModuleWizardStep()
    }

    override fun setupRootModel(modifiableRootModel: ModifiableRootModel) {
        var root = createAndGetRoot() ?: return
        val project = modifiableRootModel.project
        val module = modifiableRootModel.module
        modifiableRootModel.addContentEntry(root)

        val featureName = module.name
        val packageName = featureName.decapitalize()
        val className = featureName.split(".").last().capitalize()

        //classes
        val diClassName = className + "Factory"
        val modelClassName = className + "Repository"
        val viewModelClassName = className + "ViewModel"

        //folders
        val srcDir = "src"
        val commonDir = srcDir + "/commonMain/kotlin/" + packageName.replace(".","/")

        //imports
        val diImport = packageName + ".di." + diClassName
        val modelImport = packageName + ".model." + modelClassName
        val viewModelImport = packageName + ".presentation." + viewModelClassName


        //TODO: Apply checkbox for mvvm, media, permission, widgets, units, fields

        //Create build.gradle.kts
        root.createFile("build.gradle.kts", gradleContent())

        // Create AndroidManifest
        root.createFile(srcDir + "/androidMain/AndroidManifest.xml", androidManifestContent(packageName))

        // Create DI file
        val diContent =
            "package $packageName.di\n" +
                "\n" +
                "import $modelImport\n" +
                "import $viewModelImport\n" +
                "import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher\n" +
                "import dev.icerock.moko.resources.desc.StringDesc\n" +
                "\n" +
                "class $diClassName(\n" +
                "    private val ${modelClassName.decapitalize()}: $modelClassName,\n" +
                "    private val errorFactory: (Throwable) -> StringDesc,\n" +
                "    private val validation: $viewModelClassName.Validation,\n" +
                "    private val strings: $viewModelClassName.Strings,\n" +
                "    ) {\n" +
                "    fun create$viewModelClassName(eventsListener: EventsDispatcher<$viewModelClassName.EventListener>) =\n" +
                "        $viewModelClassName(\n" +
                "            ${modelClassName.decapitalize()} = ${modelClassName.decapitalize()},\n" +
                "            eventsDispatcher = eventsListener,\n" +
                "            errorFactory = errorFactory,\n" +
                "            validation = validation,\n" +
                "            strings = strings" +
                "        )\n" +
                "}"
        root.createFile(commonDir+"/di/$diClassName.kt", diContent)

        //Create model file
        val modelContent =
            "package $packageName.model\n" +
                    "\n" +
                    "interface $modelClassName {\n" +
                    "    //TODO: Append your data calls\n" +
                    "}"
        root.createFile(commonDir+"/model/$modelClassName.kt", modelContent)

        //Create viewModel file
        val viewModelContent =
            "package $packageName.presentation\n" +
                    "\n" +
                    "import $modelImport\n" +
                    "import dev.icerock.moko.fields.FormField\n" +
                    "import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher\n" +
                    "import dev.icerock.moko.mvvm.livedata.*\n" +
                    "import dev.icerock.moko.mvvm.viewmodel.ViewModel\n" +
                    "import dev.icerock.moko.resources.desc.StringDesc\n" +
                    "import dev.icerock.moko.time.Timer\n" +
                    "\n" +
                    "class $viewModelClassName(\n" +
                    "    private val eventsDispatcher: EventsDispatcher<EventListener>,\n" +
                    "    private val ${modelClassName.decapitalize()}: $modelClassName,\n" +
                    "    private val validation: Validation,\n" +
                    "    private val strings: Strings\n" +
                    ") : ViewModel(), ThrowableCatcher {\n" +
                    "\n" +
                    "    interface Validation {\n" +
                    "        //fun validatePhone(phone: String): StringDesc?\n" +
                    "        //fun validateInput(input: String): StringDesc?\n" +
                    "    }\n" +
                    "\n" +
                    "    interface Strings {\n" +
                    "        //val successMessage: StringDesc\n" +
                    "        //val errorMessage: StringDesc\n" +
                    "    }\n" +
                    "\n" +
                    "    interface EventListener {\n" +
                    "        //fun showError(stringDesc: StringDesc)\n" +
                    "        //fun showMessage(stringDesc: StringDesc)\n" +
                    "    }\n" +
                    "}"
        root.createFile(commonDir+"/presentation/$viewModelClassName.kt", viewModelContent)
    }

    private fun createAndGetRoot(): VirtualFile? {
        val path = contentEntryPath?.let { FileUtil.toSystemIndependentName(it) } ?: return null
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(File(path).apply { mkdirs() }.absolutePath)
    }

    private fun androidManifestContent(packageName: String): String {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<manifest package=\"${packageName}}\" />"
    }

    private fun gradleContent(): String {
        return "plugins {\n" +
                "    id(\"com.android.library\")\n" +
                "    id(\"org.jetbrains.kotlin.multiplatform\")\n" +
                "    id(\"kotlin-android-extensions\")\n" +
                "    id(\"kotlin-kapt\")\n" +
                "    id(\"dev.icerock.mobile.multiplatform\")\n" +
                "}\n" +
                "\n" +
                "android {\n" +
                "    compileSdkVersion(Versions.Android.compileSdk)\n" +
                "\n" +
                "    defaultConfig {\n" +
                "        minSdkVersion(Versions.Android.minSdk)\n" +
                "        targetSdkVersion(Versions.Android.targetSdk)\n" +
                "    }\n" +
                "\n" +
                "    dataBinding {\n" +
                "        isEnabled = true\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "dependencies {\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.kotlinStdLib)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.coroutines)\n" +
                "\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoMvvm)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoResources)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoFields)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoUnits)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoPermissions)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoMedia)\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.mokoTime)\n" +
                "\n" +
                "    mppLibrary(Deps.Libs.MultiPlatform.napier)\n" +
                "\n" +
                "    androidLibrary(Deps.Libs.Android.lifecycle)\n" +
                "}"
    }
}

