package org.Nicki0.editor.elements;

import java.util.ArrayList;
import java.util.List;

public abstract class Object {
	// F�r Konstanten etc. 
	// TODO liste vervollst�ndigen
	public static final String ADVANCED_CRAFT_STATION="CraftStation2";
	public static final String AGILITY_BOOTS_T1="BootsSpeed1";
	public static final String AGILITY_BOOTS_T2="BootsSpeed2";
	public static final String AGILITY_BOOTS_T3="BootsSpeed3";
	public static final String ALGAE_GENERATOR_T1="AlgaeGenerator1";
	public static final String ALGAE_GENERATOR_T2="AlgaeGenerator2";
	public static final String ALGAE="Algae1Seed";
	public static final String ALGAE1GROWABLE="Algae1Growable";
	public static final String ALUMINIUM="Aluminium";
	public static final String AREA_LAMP="InsideLamp1";
	public static final String ASTEROIDS_ATTRACTION_ROCKET="RocketHeat1";
	public static final String ATMOSPHERIC_WATER_COLLECTOR="WaterCollector1";
	public static final String BACKPACK_T1="Backpack1";
	public static final String BACKPACK_T2="Backpack2";
	public static final String BACKPACK_T3="Backpack3";
	public static final String BACKPACK_T4="Backpack4";
	public static final String BACKPACK_T5="Backpack5";
	public static final String BACTERIA_SAMPLE="Bacteria1";
	public static final String BEACON="Beacon";
	public static final String BEANS_SEEDS="Vegetable2Seed";
	public static final String BEANS="Vegetable2Growable";
	public static final String BED="BedSimple";
	public static final String BIG_LIVING_COMPARTMENT="Pod4x";
	public static final String BIODOME_T2="Biodome2";
	public static final String BIODOME="biodome";
	public static final String BIOLAB="Biolab";
	public static final String BIOMASS_ROCKET="RocketBiomass1";
	public static final String BIOPLASTIC_NUGGET="Bioplastic1";
	public static final String BLUEPRINT_MICROCHIP="BlueprintT1";
	public static final String CANISTER="canister";
	public static final String CHAIR="Chair1";
	public static final String COBALT="Cobalt";
	public static final String COMMUNICATION_ANTENNA="ComAntenna";
	public static final String CRAFT_STATION_T2="CraftStation1";
	public static final String DESKTOP="Desktop1";
	public static final String DISPLAY_CASE="DisplayCase";
	public static final String DNA_MANIPULATOR="GeneticManipulator1";
	public static final String DOUBLE_BED="BedDouble";
	public static final String DRILL_T1="Drill0";
	public static final String DRILL_T2="Drill1";
	public static final String DRILL_T3="Drill2";
	public static final String DRILL_T4="Drill3";
	public static final String EGGPLANT_SEEDS="Vegetable0Seed";
	public static final String EGGPLANT="Vegetable0Growable";
	public static final String ESCAPE_POD="EscapePod";
	public static final String EXOSKELETON_T1="EquipmentIncrease1";
	public static final String EXOSKELETON_T2="EquipmentIncrease2";
	public static final String EXPLOSIVE_POWDER="RedPowder1";
	public static final String FABRIC="FabricBlue";
	public static final String FERTILIZER_T2="Fertilizer2";
	public static final String FERTILIZER="Fertilizer1";
	public static final String FLOWER_POT="FlowerPot1";
	public static final String FLOWER_SPREADER_T2="SeedSpreader2";
	public static final String FLOWER_SPREADER="SeedSpreader1";
	public static final String FOOD_GROWER_T2="VegetableGrower2";
	public static final String FOOD_GROWER="VegetableGrower1";
	public static final String FOUNDATION_GRID="Foundation";
	public static final String FUSION_ENERGY_CELL="FusionEnergyCell";
	public static final String FUSION_REACTOR="FusionGenerator1";
	public static final String GAS_EXTRACTOR="GasExtractor1";
	public static final String GOLDEN_CRATE="GoldenContainer";
	public static final String GOLDEN_EFFIGIE1="GoldenEffigie1"; // TODO effigie richtig zuordnen
	public static final String GOLDEN_EFFIGIE2="GoldenEffigie2";
	public static final String GOLDEN_EFFIGIE3="GoldenEffigie3";
	public static final String GOLDEN_SEED="SeedGold";
	public static final String GOLDENEFFIGIESPAWNER="GoldenEffigieSpawner";
	public static final String GPS_SATELLITE_T1="RocketMap1";
	public static final String GPS_SATELLITE_T2="RocketMap2";
	public static final String GPS_SATELLITE_T3="RocketMap3";
	public static final String GRASS_SPREADER="GrassSpreader1";
	public static final String HEATER_T1="Heater1";
	public static final String HEATER_T2="Heater2";
	public static final String HEATER_T3="Heater3";
	public static final String HEATER_T4="Heater4";
	public static final String ICE="ice";
	public static final String INDOOR_LADDER="Ladder";
	public static final String IRIDIUM_ROD="Rod-iridium";
	public static final String IRIDIUM="Iridium";
	public static final String IRON="Iron";
	public static final String JETPACK_T1="Jetpack1";
	public static final String JETPACK_T2="Jetpack2";
	public static final String JETPACK_T3="Jetpack3";
	public static final String LAKE_WATER_COLLECTOR="WaterCollector2";
	public static final String LAUNCH_PLATFORM="LaunchPlatform";
	public static final String LIVING_COMPARTMENT_CORNER="podAngle";
	public static final String LIVING_COMPARTMENT_DOOR="door";
	public static final String LIVING_COMPARTMENT_GLASS="FloorGlass";
	public static final String LIVING_COMPARTMENT_WINDOW="window";
	public static final String LIVING_COMPARTMENT="pod";
	public static final String LOCKER_STORAGE="Container2";
	public static final String MAGNESIUM="Magnesium";
	public static final String MAGNETIC_FIELD_PROTECTION_ROCKET="RocketPressure1";
	public static final String MAP_INFORMATION_ROCKET="RocketInformations1";
	public static final String METHANE_CARTRIDGE="MethanCapsule1";
	public static final String MICROCHIP_COMPASS="HudCompass";
	public static final String MICROCHIP_CONSTRUCTION="MultiBuild";
	public static final String MICROCHIP_DECONSTRUCTION="MultiDeconstruct";
	public static final String MICROCHIP_MINING_SPEED_T1="MultiToolMineSpeed1";
	public static final String MICROCHIP_MINING_SPEED_T2="MultiToolMineSpeed2";
	public static final String MICROCHIP_MINING_SPEED_T3="MultiToolMineSpeed3";
	public static final String MICROCHIP_MINING_SPEED_T4="MultiToolMineSpeed4";
	public static final String MICROCHIP_TORCH_T2="MultiToolLight2";
	public static final String MICROCHIP_TORCH="MultiToolLight";
	public static final String MUSHROOM_SEEDS="Vegetable3Seed";
	public static final String MUSHROOM="Vegetable3Growable";
	public static final String MUTAGEN="Mutagen1";
	public static final String NUCLEAR_FUSION_GENERATOR="EnergyGenerator6";
	public static final String NUCLEAR_REACTOR_T1="EnergyGenerator4";
	public static final String NUCLEAR_REACTOR_T2="EnergyGenerator5";
	public static final String ORE_EXTRACTOR_T2="OreExtractor2";
	public static final String ORE_EXTRACTOR="OreExtractor1";
	public static final String OSMIUM="Osmium";
	public static final String OUTSIDE_LAMP="OutsideLamp1";
	public static final String OUTSIDE_STAIRS="Stairs";
	public static final String OXYGEN_CAPSULE="OxygenCapsule1";
	public static final String OXYGEN_TANK_T1="OxygenTank1";
	public static final String OXYGEN_TANK_T2="OxygenTank2";
	public static final String OXYGEN_TANK_T3="OxygenTank3";
	public static final String OXYGEN_TANK_T4="OxygenTank4";
	public static final String PLAIN_WALL="wallplain";
	public static final String PLANT_GOLDEN="SeedGoldGrowable";
	public static final String PLANT_LIRMA="Seed0Growable";
	public static final String PLANT_NULNA="Seed3Growable";
	public static final String PLANT_OREMA="Seed5";
	public static final String PLANT_PESTERA="Seed2Growable";
	public static final String PLANT_SHANGA="Seed1Growable";
	public static final String PLANT_TUSKA="Seed4Growable";
	public static final String PLANT_VOLNUS="Seed6";
	public static final String PULSAR_QUARTZ_SHARD="PulsarShard";
	public static final String PULSAR_QUARTZ="PulsarQuartz";
	public static final String PULSARCHIP="PulsarChip";
	public static final String RECYCLING_MACHINE="RecyclingMachine";
	public static final String ROCKET_ENGINE="RocketReactor";
	public static final String SCREEN_BLUEPRINTS="ScreenUnlockables";
	public static final String SCREEN_ENERGY_LEVELS="ScreenEnergy";
	public static final String SCREEN_MAPPING="ScreenMap1";
	public static final String SCREEN_ORBITAL_INFORMATIONS="ScreenRockets";
	public static final String SCREEN_PROGRESS="ScreenTerraStage";
	public static final String SCREEN_TERRAFORMATION="ScreenTerraformation";
	public static final String SCREEN_TRANSMISSIONS="ScreenMessage";
	public static final String SEED_LIRMA="Seed0";
	public static final String SEED_NULNA="Seed3";
	public static final String SEED_PESTERA="Seed2";
	public static final String SEED_SHANGA="Seed1";
	public static final String SEED_TUSKA="Seed4";
	public static final String SEED5GROWABLE="Seed5Growable";
	public static final String SEED6GROWABLE="Seed6Growable";
	public static final String SEEDS_SPREADER_ROCKET="RocketOxygen1";
	public static final String SHREDDER_MACHINE="Destructor1";
	public static final String SIGN="Sign";
	public static final String SILICON="Silicon";
	public static final String SOFA_ANGLE="SofaAngle";
	public static final String SOFA="Sofa";
	public static final String SOLAR_PANEL_T1="EnergyGenerator2";
	public static final String SOLAR_PANEL_T2="EnergyGenerator3";
	public static final String SPACE_FOOD="astrofood";
	public static final String SPACE_HEAT_MULTIPLIER="SpaceMultiplierHeat";
	public static final String SPACE_OXYGEN_MULTIPLIER="SpaceMultiplierOxygen";
	public static final String SPACE_PRESSURE_MULTIPLIER="SpaceMultiplierPressure";
	public static final String SPACEMULTIPLIERBIOMASS="SpaceMultiplierBiomass";
	public static final String SQUASH_SEEDS="Vegetable1Seed";
	public static final String SQUASH="Vegetable1Growable";
	public static final String STORAGE_CRATE="Container1";
	public static final String SULFUR="Sulfur";
	public static final String SUPER_ALLOY_ROD="Rod-alloy";
	public static final String SUPER_ALLOY="Alloy";
	public static final String TABLE="TableSmall";
	public static final String TELEPORTER="Teleporter1";
	public static final String TITANIUM="Titanium";
	public static final String TREE_BARK="TreeRoot";
	public static final String TREE_SEED_AEMORA="Tree6Seed";
	public static final String TREE_SEED_ALEATUS="Tree2Seed";
	public static final String TREE_SEED_CERNEA="Tree3Seed";
	public static final String TREE_SEED_ELEGEA="Tree4Seed";
	public static final String TREE_SEED_HUMELORA="Tree5Seed";
	public static final String TREE_SEED_ITERRA="Tree0Seed";
	public static final String TREE_SEED_LINIFOLIA="Tree1Seed";
	public static final String TREE_SEED_PLEOM="Tree7Seed";
	public static final String TREE_SEED_SOLEUS="Tree8Seed";
	public static final String TREE_SPREADER_T2="TreeSpreader1";
	public static final String TREE_SPREADER_T3="TreeSpreader2";
	public static final String TREE_SPREADER="TreeSpreader0";
	public static final String TREE0GROWABLE="Tree0Growable";
	public static final String TREE1GROWABLE="Tree1Growable";
	public static final String TREE2GROWABLE="Tree2Growable";
	public static final String TREE3GROWABLE="Tree3Growable";
	public static final String TREE4GROWABLE="Tree4Growable";
	public static final String TREE5GROWABLE="Tree5Growable";
	public static final String TREE6GROWABLE="Tree6Growable";
	public static final String TREE7GROWABLE="Tree7Growable";
	public static final String TREE8GROWABLE="Tree8Growable";
	public static final String TREESPREADERGIANT="TreeSpreaderGiant";
	public static final String URANIUM_ROD="Rod-uranium";
	public static final String URANIUM="Uranim";
	public static final String VEGETUBE_T1="Vegetube1";
	public static final String VEGETUBE_T2="Vegetube2";
	public static final String VEGETUBE_T3="VegetubeOutside1";
	public static final String WATER_BOTTLE="WaterBottle1";
	public static final String WATER_FILTER="WaterFilter";
	public static final String WIND_TURBINE="EnergyGenerator1";
	public static final String WRECKS="wreckpilar";
	public static final String ZEOLITE="Zeolite";
	public static final String[] THINGS_AT_0 = {"Container1","Container2","RocketBiomass1","RocketHeat1","RocketInsects1","RocketOxygen1","RocketPressure1","wreckpilar"};
	public static final long[] INVENTORIES_WITHOUT_CONTAINER = {1,2,109487734,101703877,108035701,109811866,109441691,101767269}; // Player Inventory, Player Addons, Warden Altars (only after completing story, not where they were taken from), Fusion Reactors 
	
	public Object() {}
	
	public static List<Item> allItemsOfType(String pGId, List<Item> pItemList) {
		List<Item> listOfType = new ArrayList<>();
		for (int i = 0; i < pItemList.size(); i++) if (pItemList.get(i).getGId().equalsIgnoreCase(pGId)) listOfType.add(pItemList.get(i));
		return listOfType;
	}
}