# PlayBrew

### A library-less, ECS-based, pure Java game engine.

PlayBrew is a passion project, designed to explore game engine architecture.

## Features

- Entity Component System ?magic?!
- Zero dependencies (outside of the standard library).
- Basic engine features
    - Tilemaps
    - Input
    - Movement
    - Physics
    - Collision
    - Animation
    - And more!

---

## Examples

I think it's easier to learn PlayBrew by jumping into some examples, so here we go!

```java
import com.waffle.core.*;
import com.waffle.ecs.*;
import com.waffle.struct.Vec2f;

class Player extends GameObject {
    private TransformComponent transform;
    private SpriteRenderComponent sprites;
    private final static float SPEED = 50;

    @Override
    public void start() {
        BufferedImage img = null;
        try {
            // Relative to the "resources" folder of your project.
            img = Utils.loadImageFromPath("path/to/image");
        } catch (Exception e) {
            System.out.println("Something went wrong! " + e.getMessage());
        }
        if (img != null) {
            transform = new TransformComponent(200, 300);
            sprites = new SpriteRenderComponent();
            sprites.sprites.add(new SpriteRenderer(new Vec2f(), img, 50, 50));
        }
    }

    // Called each frame, "dt" is the difference in time between the last frame and the current frame
    @Override
    public void update(float dt) {
        transform.position.x += SPEED * dt;
    }
}

class TestGame extends Game {
    private Player player;

    @Override
    public void start() {
        // "world" is a protected member of Game, it is used to manage the ECS
        world.createLayers(2);
        player = new Player();

        // Defaults to bottom "layer", 0
        world.createGameObject(player);
    }

    // Called each frame, just like <Player>.update(float)
    @Override
    public void update(float dt) {
    }

    // Called when the game exits or crashes, used for resource cleanup.
    @Override
    public void free() {
    }
}

public class Main {
    public static void main(String[] args) {
        new GameTest();
    }
}
```

##### And just like that, you've made your first game!

There are many components and systems to discover, and you can make your own by simply doing
`class MyComponent implements IComponent`,
and
`class MySystem extends ECSSystem`.

---

## Contributing

If you wish to contribute, fantastic! I appreciate the effort put in by every contributor.

Fork this repository, then open a pull request when you're ready!