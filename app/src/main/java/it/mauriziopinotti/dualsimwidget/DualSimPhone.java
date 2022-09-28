package it.mauriziopinotti.dualsimwidget;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maurizio Pinotti
 */
@SuppressWarnings("squid:S1192")
public class DualSimPhone {

    public static final Intent DEFAULT_SETTINGS_INTENT =
            new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);

    private static final List<DualSimPhone> SUPPORTED_PHONES = new ArrayList<>();

    static {
        // Samsung S4 mini
        SUPPORTED_PHONES.add(new DualSimPhone("samsung", "GT-I9192",
                new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.NetworkManagement"))));

        // ZTE Blade S6
        SUPPORTED_PHONES.add(new DualSimPhone("ZTE", "Blade S6",
                new Intent("com.android.settings.sim.SIM_SUB_INFO_SETTINGS")));

        // Huawei P9
        SUPPORTED_PHONES.add(new DualSimPhone("HUAWEI", "EVA-AL10",
                new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.DualCardSettings"))));

        // Huawei Mate 10 pro
        SUPPORTED_PHONES.add(new DualSimPhone("HUAWEI", "BLA-L29",
                new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.DualCardSettings"))));

        // Honor 8 64GB
        SUPPORTED_PHONES.add(new DualSimPhone("HONOR", "FRD-L19",
                new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.DualCardSettings"))));
    }

    public final String brand;
    public final String model;
    public final Intent intent;

    public DualSimPhone(String brand, String model, Intent intent) {
        this.brand = brand;
        this.model = model;
        this.intent = intent;
    }

    public static Intent getDualSimSettingsIntent() {
        Intent brandIntent = null;

        for (DualSimPhone phone : SUPPORTED_PHONES) {
            if (phone.brand.equals(Build.BRAND)) {
                if (phone.model.equals(Build.MODEL)) return phone.intent;

                brandIntent = phone.intent;
            }
        }

        return brandIntent != null ? brandIntent : DEFAULT_SETTINGS_INTENT;
    }

    public static boolean isPhoneSupported() {
        return !getDualSimSettingsIntent().equals(DEFAULT_SETTINGS_INTENT);
    }
}
