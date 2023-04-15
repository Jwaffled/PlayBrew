package com.waffle.components;

import com.waffle.core.Vec2f;
import com.waffle.ecs.IComponent;

import java.awt.image.BufferedImage;

public record UITextureComponent(Vec2f position, BufferedImage sprite, int width, int height) implements IComponent {}
