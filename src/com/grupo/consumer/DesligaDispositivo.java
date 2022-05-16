package com.grupo.consumer;

import com.grupo.device.SmartDevice;

import java.util.function.Consumer;

public
class DesligaDispositivo implements Consumer < SmartDevice > {
    @Override
    public
    void accept ( SmartDevice device ) {
        device.setEstado ( SmartDevice.Estado.DESLIGADO );
    }
}
