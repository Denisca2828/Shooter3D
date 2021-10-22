package com.deniscag28.shooter3D.game.object;

import com.deniscag28.shooter3D.game.module.IModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface IGameObject {
    Map<String, IModule> modules = new HashMap<>();

    Collection<IModule> getModules();
}
