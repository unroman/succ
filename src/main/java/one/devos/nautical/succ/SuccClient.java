package one.devos.nautical.succ;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import one.devos.nautical.succ.model.DepressedSuctionCupModel;
import one.devos.nautical.succ.model.SuctionCupModel;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientWorldTickEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

public class SuccClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		SuccKeybinds.init();
		GlobalClimbingManager.clientNetworkingInit();
		ClimbingSuctionCupEntity.clientNetworkingInit();

		EntityModelLayerRegistry.registerModelLayer(SuctionCupModel.LAYER, SuctionCupModel::getLayerDefinition);
		EntityModelLayerRegistry.registerModelLayer(DepressedSuctionCupModel.LAYER, DepressedSuctionCupModel::getLayerDefinition);

		EntityRendererRegistry.register(Succ.SUCTION_CUP_ENTITY_TYPE, SuctionCupEntityRenderer::new);

		ClientTickEvents.START.register(SuccKeybinds::tick);
		ClientTickEvents.START.register(LocalClimbingManager::clientTick);
		ClientWorldTickEvents.START.register(LocalClimbingManager::tick);
		ClientPlayConnectionEvents.DISCONNECT.register(LocalClimbingManager::onDisconnect);
	}
}
