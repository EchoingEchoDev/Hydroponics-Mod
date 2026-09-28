package net.echoingechodev.hydroponics.ponders;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import static net.echoingechodev.hydroponics.ponders.ModPonders.SETUP_HYDROPONIC_TOWER;

public class HydroponicTowerScenes {

    public static void settingUpHydroponicTowers(SceneBuilder scene, SceneBuildingUtil util) {
            scene.title(SETUP_HYDROPONIC_TOWER.getPath(), "Setting up a Hydroponic Tower");
            scene.showBasePlate();
            //scene.world().setBlocks(util.select().fromTo(2,1,2,2,4,2), Blocks.DIRT.defaultBlockState(), true);
            scene.rotateCameraY(180);
            scene.world().showSection(util.select().fromTo(0,0,0, 5,4,5), Direction.UP);
            //scene.world().showSectionAndMerge(util.select().column(2,2), Direction.DOWN, );
            scene.idle(20);

            scene.overlay().showOutlineWithText(util.select().column(2,2), 130)
                    .colored(PonderPalette.WHITE)
                    .text("text_1");
            scene.idle(150);

            scene.idle(60);
            scene.addKeyframe();

            scene.overlay().showText(130)
                    .colored(PonderPalette.WHITE)
                    .text("text_2")
                    .independent();
            scene.overlay().showControls(new Vec3(2,4,2), Pointing.DOWN, 60)
                            .withItem(Items.WATER_BUCKET.getDefaultInstance());
            scene.overlay().showOutline(PonderPalette.WHITE, Items.WATER_BUCKET, util.select().position(2,4,2), 20);
            scene.idle(20);
            scene.overlay().showOutline(PonderPalette.WHITE, Items.WATER_BUCKET, util.select().position(2,3,2), 20);
            scene.idle(20);
            scene.overlay().showOutline(PonderPalette.WHITE, Items.WATER_BUCKET, util.select().position(2,2,2), 20);
            scene.idle(20);
            scene.overlay().showOutline(PonderPalette.WHITE, Items.WATER_BUCKET, util.select().position(2,1,2), 60);
            scene.idle(60);

            scene.idle(60);
            scene.addKeyframe();

            scene.overlay().showOutlineWithText(util.select().position(3,3,2), 130)
                    .colored(PonderPalette.WHITE)
                    .text("text_3");
            scene.idle(150);
            scene.overlay().showText(130)
                    .colored(PonderPalette.WHITE)
                    .text("text_4")
                    .independent();
            scene.idle(150);

            scene.idle(60);
            scene.addKeyframe();

            scene.overlay().showOutlineWithText(util.select().position(2,4,2), 130)
                    .colored(PonderPalette.WHITE)
                    .text("text_5");
            scene.idle(150);

    }
}
