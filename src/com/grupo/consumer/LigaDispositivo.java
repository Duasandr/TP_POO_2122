package com.grupo.consumer;

import com.grupo.device.SmartDevice;

import java.util.function.Consumer;

public
class LigaDispositivo implements Consumer < SmartDevice > {
    @Override
    public
    void accept ( SmartDevice device ) {
        device.setEstado ( SmartDevice.Estado.LIGADO );
    }
}
