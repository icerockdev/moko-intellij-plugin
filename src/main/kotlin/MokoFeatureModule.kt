package intellij

import com.intellij.icons.AllIcons
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.module.*
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import javax.swing.*

class MokoFeatureModule: ModuleType<MokoFeatureBuilder>("moko_feature_module") {
    override fun createModuleBuilder(): MokoFeatureBuilder = MokoFeatureBuilder()
    override fun getName(): String = "Moko feature"
    override fun getDescription(): String = "some desc"
    override fun getNodeIcon(isOpened: Boolean): Icon {
        return AllIcons.General.Information
    }
}

class MokoModuleWizardStep(): ModuleWizardStep() {
    override fun updateDataModel() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getComponent(): JComponent {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return JLabel("Test")
    }

}