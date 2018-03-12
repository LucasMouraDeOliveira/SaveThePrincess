package com.lordkadoc.server.game.engine.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.lordkadoc.server.game.engine.component.InputComponent;
import com.lordkadoc.server.game.engine.component.MovementComponent;
import com.lordkadoc.server.game.engine.component.PlayerComponent;
import com.lordkadoc.server.game.engine.component.PositionComponent;
import com.lordkadoc.server.game.engine.component.SpeedComponent;
import com.lordkadoc.server.game.engine.component.TextureComponent;

public class Mapper {

	public static final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
	
	public static final ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);

	public static final ComponentMapper<SpeedComponent> speedMapper = ComponentMapper.getFor(SpeedComponent.class);

	public static final ComponentMapper<InputComponent> inputMapper = ComponentMapper.getFor(InputComponent.class);
	
	public static final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
	
	public static final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);	
}
