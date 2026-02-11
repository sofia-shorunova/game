package game;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

class Platform extends StaticBody {
    public Platform(World world) {
        super(world, new BoxShape(2, 0.5f));
        addImage(new BodyImage("data/platform.png", 1));
    }
}
