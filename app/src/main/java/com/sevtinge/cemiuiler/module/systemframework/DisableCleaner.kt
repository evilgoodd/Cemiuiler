package com.sevtinge.cemiuiler.module.systemframework

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.sevtinge.cemiuiler.module.base.BaseHook
import de.robv.android.xposed.XposedHelpers

object DisableCleaner : BaseHook() {
    override fun init() {
        XposedHelpers.setStaticBooleanField(findClassIfExists("android.os.spc.PressureStateSettings"), "PROCESS_CLEANER_ENABLED", false)

        findMethod("com.android.server.am.ActivityManagerService") {
            name == "checkExcessivePowerUsage"
        }.hookBefore {
            it.result = null
        }
        findMethod("com.android.server.am.OomAdjuster") {
            name == "shouldKillExcessiveProcesses"
        }.hookBefore {
            it.result = false
        }
        findMethod("com.android.server.am.PhantomProcessList") {
            name == "trimPhantomProcessesIfNecessary"
        }.hookBefore {
            it.result = null
        }
    }
}
