/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.moko.intellij;

import javax.swing.*;

public class MokoModuleForm {
    public JTextField packageNameInput;
    public JPanel rootPanel;

    public void updateConfig(MokoFeatureConfig config) {
        packageNameInput.setText(config.getProjectPrefix());
    }
}
