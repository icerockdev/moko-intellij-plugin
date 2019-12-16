package com.icerockdev.moko.intellij;

import javax.swing.*;

public class MokoModuleForm {
    public JTextField packageNameInput;
    public JPanel rootPanel;

    public void updateConfig(MokoFeatureConfig config) {
        packageNameInput.setText(config.getProjectPrefix());
    }
}
