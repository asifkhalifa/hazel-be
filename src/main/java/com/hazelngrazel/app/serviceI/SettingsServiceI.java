package com.hazelngrazel.app.serviceI;

import com.hazelngrazel.app.dto.SettingsRequest;
import com.hazelngrazel.app.entity.SettingsEntity;

public interface SettingsServiceI {
    SettingsEntity getSettings();
    void updateSettings(SettingsRequest request);
}
