package net.mcreator.wildernessoddesyapi.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.Entity;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityAiMixin {
	@Shadow
	private GoalSelector goalSelector;
	@Shadow
	private int age;
	private static int daysElapsed = 0;
	private static final Random RANDOM = new Random();

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo info) {
		// Custom AI evolution logic goes here
		evolveAI();
	}

	private void evolveAI() {
		if (this.age % 24000 == 0) { // Every in-game day
			daysElapsed++;
			// Evolve AI by adding new goals or modifying existing ones
			if (daysElapsed % 5 == 0) { // Evolve every 5 days as an example
				this.goalSelector.addGoal(1, new CustomGoal());
			}
		}
	}

	private class CustomGoal extends Goal {
		@Override
		public boolean canUse() {
			// Define when the goal can start
			return RANDOM.nextBoolean(); // Example: Randomly decide if the goal can be used
		}

		@Override
		public boolean canContinueToUse() {
			// Define if the goal should continue
			return false; // Example: Goal does not continue by default
		}

		@Override
		public void start() {
			// Define what happens when the goal starts
			System.out.println("CustomGoal started for entity: " + EntityAiMixin.this);
		}

		@Override
		public void stop() {
			// Define what happens when the goal stops
			System.out.println("CustomGoal stopped for entity: " + EntityAiMixin.this);
		}

		@Override
		public void tick() {
			// Define the behavior of the goal
			// Example: Print a message or perform some action
			System.out.println("CustomGoal tick for entity: " + EntityAiMixin.this);
		}
	}
}
