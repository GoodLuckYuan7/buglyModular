package com.icegps.buglymodular;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @Explain  enableProxyApplication = false 的情况
 *      继承TinkerApplication（bugly），更具 android N 兼容性
 *      这个类集成TinkerApplication类，这里面不做任何操作，
 *      所有Application的代码都会放到ApplicationLike继承类当中
 * @Author chenqi
 * @Time 2020/3/12
 */

public class ModularApplication extends TinkerApplication {
    public ModularApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.icegps.buglymodular.ModularApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
