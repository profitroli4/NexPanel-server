package com.profitroli4.nexpanel.detector;

import org.usb4java.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AndroidDeviceDetector {
    private static final ArrayList<Integer> ANDROID_VENDORS = new ArrayList<>(Arrays.asList(
            0x18D1, // Google
            0x04E8, // Samsung
            0x0FCE, // Sony
            0x1004, // LG
            0x22B8, // Motorola
            0x2717, // Xiaomi
            0x0BB4, // HTC
            0x12D1, // Huawei
            0x1D4D, // Asus
            0x0489, // Meizu
            0x201E, // Oppo
            0x1F3A  // Vivo
    ));

    public void findAndroidDevices() {
        Context context = new Context();
        int result = LibUsb.init(context);
        if (result != LibUsb.SUCCESS) {
            System.err.println("Error initialization USB: " + LibUsb.strError(result));
            return;
        }

        DeviceList deviceList = new DeviceList();
        result = LibUsb.getDeviceList(context, deviceList);
        if (result < 0) {
            System.err.println("Error get list devices: " + LibUsb.strError(result));
            return;
        }

        try {
            for (Device device : deviceList) {

                DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
                int descriptorResult = LibUsb.getDeviceDescriptor(device, deviceDescriptor);
                if (descriptorResult != LibUsb.SUCCESS){
                    System.err.println("Error get device descriptor: " + LibUsb.strError(result));
                    continue;
                }

                if (ANDROID_VENDORS.contains((int) deviceDescriptor.idVendor())) {
                    System.out.printf("Find device: VID=%04x, PID=%04x\n",
                            deviceDescriptor.idVendor(), deviceDescriptor.idProduct());
                }

            }
        } finally {
            LibUsb.freeDeviceList(deviceList, true);
            LibUsb.exit(context);
        }
    }
}
