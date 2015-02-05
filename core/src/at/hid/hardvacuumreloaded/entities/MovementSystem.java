package at.hid.hardvacuumreloaded.entities;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class MovementSystem extends EntitySystem {
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	
	private ImmutableArray<Entity> entities;
	
	public MovementSystem() {
    }
	
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(PositionComponent.class, VelocityComponent.class));
	}

    public void processEntity(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
        	Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);

            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        }
    }
}
