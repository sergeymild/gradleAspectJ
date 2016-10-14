package com.sergeymild.aspectJ

class GradleAspectJPluginExtension {
    def enabled = true

    def enabled() {
        return enabled
    }

    void setEnabled(enabledForDebug) {
        this.enabled = enabledForDebug
    }
}
