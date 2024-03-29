package nicki0.editor.elements;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import nicki0.editor.Main;
import nicki0.editor.pictures.Picture;
import nicki0.editor.window.IconPanel;

public class Object {
	/* Procedure for adding items:
	 *  - Add GId to translateGIdName
	 *  - Add Type to translateGIdType
	 *  - Add Picture (if special) to translateGIdPicturePath
	 *  - Add Picturenumber (if animation) to numberOfPicture 
	 *  - Add Coordinate Width to coordinateWidthOfGId
	 *  - Add Supply
	 * */
	// For constants etc. 
	// TODO complete filling the list, update to v0.7.*
	// THINGS_AT_0 is complete... The SpaceMultiplier***** are shown at -500,?,-500 but not the rockets...
	public static final String[] THINGS_AT_0 = {"Container1","Container2","RocketBiomass1","RocketHeat1","RocketInsects1","RocketOxygen1","RocketPressure1","wreckpilar"};
	public static final long[] INVENTORIES_WITHOUT_CONTAINER = {1,2,109487734,101703877,108035701,109811866,109441691,101767269}; // Player Inventory, Player Addons, Fusion Reactors, Warden Altars (only after completing story, not where they were taken from) 
	
	public static final String SpaceMultiplierBiomass = "SpaceMultiplierBiomass";
	public static final String SpaceMultiplierHeat = "SpaceMultiplierHeat";
	public static final String SpaceMultiplierInsects = "SpaceMultiplierInsects";
	public static final String SpaceMultiplierOxygen = "SpaceMultiplierOxygen";
	public static final String SpaceMultiplierPlants = "SpaceMultiplierPlants";
	public static final String SpaceMultiplierPressure = "SpaceMultiplierPressure";
	
	public static final List<String> EXCLUDED_GIDs = new ArrayList<String>(Arrays.asList("wreckpilar", SpaceMultiplierBiomass, SpaceMultiplierHeat, SpaceMultiplierInsects, SpaceMultiplierOxygen, SpaceMultiplierPlants, SpaceMultiplierPressure));
	
	private static final Map<String, Integer> numberOfPictures = createNumberOfPictures(); 
	private static final Map<String, Integer> createNumberOfPictures() {
		int lootcrateimages = 20;
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("Container1" + LOOTCRATEGID, lootcrateimages);
		result.put("Container2" + LOOTCRATEGID, lootcrateimages);
		result.put("GoldenContainer" + LOOTCRATEGID, lootcrateimages);
		result.put("canister" + LOOTCRATEGID, lootcrateimages);
		result.put("Vegetube1" + LOOTCRATEGID, lootcrateimages);
		//result.put("wreckpilar" + LOOTCRATEGID, 1);
		return result;
	}
	public static Integer getNumberOfPicture(String pGId) {
		return numberOfPictures.getOrDefault(pGId, 1);
	}
	
	private static final Map<String, Double> coordinateWidthOfGId = createCoordinateWidthOfGId();
	private static final Map<String, Double> createCoordinateWidthOfGId() {
		Map<String, Double> result = new HashMap<>();
		result.put("Aquarium2", 24d);
		result.put("Foundation", 6d);
		result.put("Farm1", 6d);
		result.put("Container1", 2d);
		result.put("Container2", 2d);
		result.put("OreExtractor1", 6d);
		result.put("OreExtractor2", 6d);
		result.put("OreExtractor3", 6d);
		result.put("pod", 8d);
		result.put("podAngle", 8d);
		//result.put("Pod4x", 16d); // commented because not centered (centered at one segment)
		//result.put("biolab", 16d); // commented because not centered (centered at one segment)
		result.put("EscapePod", 12d);
		result.put("ButterflyDome1", 24d);
		result.put("biodome", 24d);
		result.put("Biodome2", 24d);
		//result.put("LaunchPlatform", 36d); // average 24 and 48 // commented because not centered (centered at stairs)
		result.put("Drill4", 24d);
		result.put("Heater5", 16d);
		result.put("TreeSpreader2", 3d); // only because picture is square
		return result;
	}
	public static Double getCoordinateWidthOfGId(String pGId) {
		return coordinateWidthOfGId.getOrDefault(pGId, 2d);
	}
	public static boolean getCoordinateWidthOfGIdIsEnlarged(String pGId) {
		return coordinateWidthOfGId.getOrDefault(pGId, 2d) > 2;
	}
	
	public static final Map<String, String> translateGIdName = createTranslationMap();
	private static final Map<String, String> createTranslationMap() {
		Map<String, String> result = new TreeMap<String, String>(Comparator.comparing(s->s, String.CASE_INSENSITIVE_ORDER));
		result.put("AirFilter1", "Air Filter");
		result.put("Algae1Growable", "Algae1Growable");
		result.put("Algae1Seed", "Algae");
		result.put("AlgaeGenerator1", "Algae Generator T1");
		result.put("AlgaeGenerator2", "Algae Generator T2");
		result.put("Alloy", "Super Alloy");
		result.put("Aluminium", "Aluminium");
		result.put("Aquarium1", "Aquarium T1");
		result.put("Aquarium2", "Aquarium T2");
		result.put("AutoCrafter1", "Auto-Crafter");
		result.put("Backpack1", "Backpack T1");
		result.put("Backpack2", "Backpack T2");
		result.put("Backpack3", "Backpack T3");
		result.put("Backpack4", "Backpack T4");
		result.put("Backpack5", "Backpack T5");
		result.put("Bacteria1", "Bacteria Sample");
		result.put("Beacon", "Beacon");
		result.put("BedDouble", "Double Bed");
		result.put("BedSimple", "Bed");
		result.put("Bee1Hatched", "Bee1Hatched");
		result.put("Bee1Larvae", "Bee Larva");
		result.put("Beehive1", "Beehive T1");
		result.put("Beehive2", "Beehive T2");
		result.put("Biodome2", "Biodome T2");
		result.put("Biolab", "Biolab");
		result.put("Bioplastic1", "Bioplastic nugget");
		result.put("BlueprintGreyscale", "BlueprintGreyscale");
		result.put("BlueprintT1", "Blueprint microchip");
		result.put("BootsSpeed1", "Agility Boots T1");
		result.put("BootsSpeed2", "Agility Boots T2");
		result.put("BootsSpeed3", "Agility Boots T3");
		result.put("Butterfly1Larvae", "Butterfly Azurae Larva (120%)");
		result.put("Butterfly2Larvae", "Butterfly Leani Larva (175%)");
		result.put("Butterfly3Larvae", "Butterfly Fensea Larva (200%)");
		result.put("Butterfly4Larvae", "Butterfly Galaxe Larva (175%)");
		result.put("Butterfly5Larvae", "Butterfly Empalio Larva (120%)");
		result.put("Butterfly6Larvae", "Butterfly Penga Larva (230%)");
		result.put("Butterfly7Larvae", "Butterfly Chevrone Larva (250%)");
		result.put("Butterfly8Larvae", "Butterfly Aemel Larva (300%)");
		result.put("Butterfly9Larvae", "Butterfly Abstreus Larva (175%)");
		result.put("Butterfly10Larvae", "Butterfly Liux Larva (500%)");
		result.put("Butterfly11Larvae", "Butterfly Nere Larva (600%)");
		result.put("Butterfly12Larvae", "Butterfly Lorpen Larva (600%)");
		result.put("Butterfly13Larvae", "Butterfly Fiorente Larva (600%)");
		result.put("Butterfly14Larvae", "Butterfly Alben Larva (700%)");
		result.put("Butterfly15Larvae", "Butterfly Futura Larva (800%)");
		result.put("Butterfly16Larvae", "Butterfly Imeo Larva (1000%)");
		result.put("Butterfly17Larvae", "Butterfly Serena Larva (1200%)");
		result.put("Butterfly18Larvae", "Butterfly Golden Larva (1500%)");
		result.put("ButterflyDisplayer1", "Butterfly Display Box");
		result.put("ButterflyDome1", "Butterfly Dome");
		result.put("ButterflyFarm1", "Butterfly Farm");
		result.put("ButterflyFarm2", "Butterfly Farm T2");
		result.put("Chair1", "Chair");
		result.put("CircuitBoard1", "Circuit Board");
		result.put("Cobalt", "Cobalt");
		result.put("ComAntenna", "Communication Antenna");
		result.put("Container1", "Storage crate");
		result.put("Container2", "Locker Storage");
		result.put("CraftStation1", "Craft station T2");
		result.put("CraftStation2", "Advanced Craft Station");
		result.put("Desktop1", "Desktop");
		result.put("Destructor1", "Shredder machine");
		result.put("DisplayCase", "Display case");
		result.put("Drill0", "Drill T1");
		result.put("Drill1", "Drill T2");
		result.put("Drill2", "Drill T3");
		result.put("Drill3", "Drill T4");
		result.put("Drill4", "Drill T5");
		result.put("Drone1", "Drone T1");
		result.put("DroneStation1", "Drone Station");
		result.put("EnergyGenerator1", "Wind turbine");
		result.put("EnergyGenerator2", "Solar panel T1");
		result.put("EnergyGenerator3", "Solar panel T2");
		result.put("EnergyGenerator4", "Nuclear Reactor T1");
		result.put("EnergyGenerator5", "Nuclear Reactor T2");
		result.put("EnergyGenerator6", "Nuclear Fusion generator");
		result.put("EquipmentIncrease1", "Exoskeleton T1");
		result.put("EquipmentIncrease2", "Exoskeleton T2");
		result.put("EquipmentIncrease3", "Exoskeleton T3");
		result.put("EscapePod", "Escape pod");
		result.put("FabricBlue", "Fabric");
		result.put("Farm1", "Outdoor Farm");
		result.put("Fence", "Fence");
		result.put("Fertilizer1", "Fertilizer");
		result.put("Fertilizer2", "Fertilizer t2");
		result.put("Fish1Eggs", "Fish Provios Eggs (100%)");
		result.put("Fish1Hatched", "Fish1Hatched");
		result.put("Fish2Eggs", "Fish Vilnus Eggs (150%)");
		result.put("Fish2Hatched", "Fish2Hatched");
		result.put("Fish3Eggs", "Fish Gerrero Eggs (150%)");
		result.put("Fish3Hatched", "Fish3Hatched");
		result.put("Fish4Eggs", "Fish Khrom Eggs (200%)");
		result.put("Fish4Hatched", "Fish4Hatched");
		result.put("Fish5Eggs", "Fish Ulani Eggs (250%)");
		result.put("Fish5Hatched", "Fish5Hatched");
		result.put("Fish6Eggs", "Fish Aelera Eggs (300%)");
		result.put("Fish6Hatched", "Fish6Hatched");
		result.put("Fish7Eggs", "Fish Tegede Eggs (300%)");
		result.put("Fish7Hatched", "Fish7Hatched");
		result.put("Fish8Eggs", "Fish Ecaru Eggs (325%)");
		result.put("Fish8Hatched", "Fish8Hatched");
		result.put("Fish9Eggs", "Fish Buyu Eggs (350%)");
		result.put("Fish9Hatched", "Fish9Hatched");
		result.put("Fish10Eggs", "Fish Tiloo Eggs (400%)");
		result.put("Fish10Hatched", "Fish10Hatched");
		result.put("Fish11Eggs", "Golden Fish Eggs (500%)");
		result.put("Fish11Hatched", "Fish11Hatched");
		result.put("FishDisplayer1", "Fish Display");
		result.put("FishFarm1", "Fish Farm");
		result.put("FloorGlass", "Living compartment glass");
		result.put("FlowerPot1", "Flower Pot");
		result.put("Foundation", "Foundation grid");
		result.put("FusionEnergyCell", "Fusion Energy Cell");
		result.put("FusionGenerator1", "Fusion reactor");
		result.put("GasExtractor1", "Gas Extractor");
		result.put("GasExtractor2", "Gas Extractor T2");
		result.put("GeneticManipulator1", "DNA Manipulator");
		result.put("GoldenContainer", "Golden Crate");
		result.put("GoldenEffigie1", "Golden effigie Rocket");
		result.put("GoldenEffigie2", "Golden effigie Escape Pod");
		result.put("GoldenEffigie3", "Golden effigie Antenna");
		result.put("GoldenEffigie4", "Golden Effige Jet Pack");
		result.put("GoldenEffigie5", "Golden Effige Warden's Key");
		result.put("GoldenEffigie6", "Golden Effige Drone");
		result.put("GoldenEffigieSpawner", "GoldenEffigieSpawner");
		result.put("GrassSpreader1", "Grass spreader");
		result.put("Heater1", "Heater T1");
		result.put("Heater2", "Heater T2");
		result.put("Heater3", "Heater T3");
		result.put("Heater4", "Heater T4");
		result.put("Heater5", "Heater T5");
		result.put("HudCompass", "Microchip - Compass");
		result.put("Incubator1", "Incubator");
		result.put("InsideLamp1", "Area Lamp");
		result.put("Iridium", "Iridium");
		result.put("Iron", "Iron");
		result.put("Jetpack1", "Jetpack T1");
		result.put("Jetpack2", "Jetpack T2");
		result.put("Jetpack3", "Jetpack T3");
		result.put("Ladder", "Indoor Ladder");
		result.put("LarvaeBase1", "Common Larva");
		result.put("LarvaeBase2", "Uncommon Larva");
		result.put("LarvaeBase3", "Rare Larva");
		result.put("LaunchPlatform", "Launch Platform");
		result.put("Magnesium", "Magnesium");
		result.put("MethanCapsule1", "Methane cartridge");
		result.put("MultiBuild", "Microchip - Construction");
		result.put("MultiDeconstruct", "Microchip - Deconstruction");
		result.put("MultiDefault", "MultiDefault");
		result.put("MultiDefault2", "MultiDefault2");
		result.put("HudChipCleanConstruction", "Microchip - Construction Menu Filter");
		result.put("MultiToolCompass", "Microchip - Compass");
		result.put("MultiToolDeconstruct2", "Microchip - Deconstruction T2");
		result.put("MultiToolDeconstructT2_0", "Microchip - Deconstruction T2_0");
		result.put("MultiToolLight", "Microchip - Torch");
		result.put("MultiToolLight2", "Microchip � Torch T2");
		result.put("MultiToolMineSpeed", "Microchip - Mining speed");
		result.put("MultiToolMineSpeed1", "Microchip - Mining speed T1");
		result.put("MultiToolMineSpeed2", "Microchip - Mining speed T2");
		result.put("MultiToolMineSpeed3", "Microchip - Mining speed T3");
		result.put("MultiToolMineSpeed4", "Microchip - Mining speed T4");
		result.put("Mutagen1", "Mutagen");
		result.put("Mutagen2", "Mutagen T2");
		result.put("Mutagen3", "Mutagen T3");
		result.put("NitrogenCapsule1", "Nitrogen Cartridge");
		result.put("OreExtractor1", "Ore Extractor");
		result.put("OreExtractor2", "Ore Extractor T2");
		result.put("OreExtractor3", "Ore Extractor T3");
		result.put("Osmium", "Osmium");
		result.put("OutsideLamp1", "Outside lamp");
		result.put("OxygenCapsule1", "Oxygen capsule");
		result.put("OxygenTank1", "Oxygen tank T1");
		result.put("OxygenTank2", "Oxygen tank T2");
		result.put("OxygenTank3", "Oxygen tank T3");
		result.put("OxygenTank4", "Oxygen tank T4");
		result.put("Phytoplankton1", "Phytoplankton A");
		result.put("Phytoplankton2", "Phytoplankton B");
		result.put("Phytoplankton3", "Phytoplankton C");
		//result.put("Phytoplankton4", "Phytoplankton D");
		result.put("Pod4x", "Big living compartment");
		result.put("PulsarChip", "Pulsar Chip");
		result.put("PulsarQuartz", "Pulsar Quartz");
		result.put("PulsarShard", "Pulsar Quartz Shard");
		result.put("RecyclingMachine", "Recycling machine");
		result.put("RedPowder1", "Explosive powder");
		result.put("RocketBiomass1", "Plants Rocket (1000% Plants)");
		result.put("RocketDrones1", "Drones Visibility Rocket");
		result.put("RocketHeat1", "Asteroids attraction rocket (1000% Heat)");
		result.put("RocketInformations1", "Map information rocket");
		result.put("RocketInsects1", "Insect Spreader Rocket (1000% Insects)");
		result.put("RocketMap1", "GPS satellite T1");
		result.put("RocketMap2", "GPS satellite T2");
		result.put("RocketMap3", "GPS satellite T3");
		result.put("RocketMap4", "GPS Satellite T4");
		result.put("RocketOxygen1", "Seeds spreader rocket (1000% Oxygen)");
		result.put("RocketPressure1", "Magnetic field protection rocket (1000% Pressure)");
		result.put("RocketReactor", "Rocket Engine");
		result.put("Rod-alloy", "Super Alloy Rod");
		result.put("Rod-iridium", "Iridium Rod");
		result.put("Rod-osmium", "Osmium Rod");
		result.put("Rod-uranium", "Uranium Rod");
		result.put("Satellite", "Satellite");
		result.put("ScreenBiomass", "Screen Biomass");
		result.put("ScreenEnergy", "Screen - Energy levels");
		result.put("ScreenMap1", "Screen - Mapping");
		result.put("ScreenMessage", "Screen - Transmissions");
		result.put("ScreenRockets", "Screen � Orbital informations");
		result.put("ScreenTerraStage", "Screen - Progress");
		result.put("ScreenTerraformation", "Screen - Terraformation");
		result.put("ScreenTerraformationOld", "Screen - Terraformation Old");
		result.put("ScreenUnlockables", "Screen - Blueprints");
		result.put("Seed0", "Seed Lirma");
		result.put("Seed0Growable", "Plant Lirma");
		result.put("Seed1", "Seed Shanga (150%)");
		result.put("Seed1Growable", "Plant Shanga");
		result.put("Seed2", "Seed Pestera (200%)");
		result.put("Seed2Growable", "Plant Pestera");
		result.put("Seed3", "Seed Nulna (300%)");
		result.put("Seed3Growable", "Plant Nulna");
		result.put("Seed4", "Seed Tuska (400%)");
		result.put("Seed4Growable", "Plant Tuska");
		result.put("Seed5", "Plant Orema (450%)");
		result.put("Seed5Growable", "Seed5Growable");
		result.put("Seed6", "Plant Volnus (500%)");
		result.put("Seed6Growable", "Seed6Growable");
		result.put("SeedGold", "Golden Seed (600%)");
		result.put("SeedGoldGrowable", "Plant Golden");
		result.put("SeedSpreader1", "Flower Spreader");
		result.put("SeedSpreader2", "Flower Spreader t2");
		result.put("Sign", "Sign");
		result.put("Silicon", "Silicon");
		result.put("Silk", "Silk");
		result.put("SilkGenerator", "Silk Generator");
		result.put("SilkWorm", "Silk Worm");
		result.put("Sofa", "Sofa");
		result.put("SofaAngle", "Sofa angle");
		result.put("SpaceMultiplierBiomass", "SpaceMultiplierBiomass");
		result.put("SpaceMultiplierHeat", "Space heat multiplier");
		result.put("SpaceMultiplierInsects", "Space insects multiplier");
		result.put("SpaceMultiplierOxygen", "Space oxygen multiplier");
		result.put("SpaceMultiplierPlants", "Space plants multiplier");
		result.put("SpaceMultiplierPressure", "Space pressure multiplier");
		result.put("Stairs", "Outside Stairs");
		result.put("Sulfur", "Sulfur");
		result.put("TableSmall", "Table");
		result.put("Teleporter1", "Teleporter");
		result.put("Titanium", "Titanium");
		result.put("Tree0Growable", "Tree0Growable");
		result.put("Tree0Seed", "Tree Seed Iterra (125%)");
		result.put("Tree1Growable", "Tree1Growable");
		result.put("Tree1Seed", "Tree Seed Linifolia (125%)");
		result.put("Tree2Growable", "Tree2Growable");
		result.put("Tree2Seed", "Tree Seed Aleatus (125%)");
		result.put("Tree3Growable", "Tree3Growable");
		result.put("Tree3Seed", "Tree Seed Cernea (150%)");
		result.put("Tree4Growable", "Tree4Growable");
		result.put("Tree4Seed", "Tree Seed Elegea (175%)");
		result.put("Tree5Growable", "Tree5Growable");
		result.put("Tree5Seed", "Tree Seed Humelora (250%)");
		result.put("Tree6Growable", "Tree6Growable");
		result.put("Tree6Seed", "Tree Seed Aemora (400%)");
		result.put("Tree7Growable", "Tree7Growable");
		result.put("Tree7Seed", "Tree Seed Pleom (350%)");
		result.put("Tree8Growable", "Tree8Growable");
		result.put("Tree8Seed", "Tree Seed Soleus (125%)");
		result.put("Tree9Seed", "Tree Seed Shreox (450%)");
		result.put("TreeRoot", "Tree bark");
		result.put("TreeSpreader0", "Tree spreader");
		result.put("TreeSpreader1", "Tree spreader T2");
		result.put("TreeSpreader2", "Tree spreader T3");
		//result.put("TreeSpreaderGiant", "TreeSpreaderGiant"); // does not exist???
		result.put("Uranim", "Uranium");
		result.put("UraniumRod", "Uranium-Rod");
		result.put("Vegetable0Growable", "Eggplant");
		result.put("Vegetable0Seed", "Eggplant seeds");
		result.put("Vegetable1Growable", "Squash");
		result.put("Vegetable1Seed", "Squash seeds");
		result.put("Vegetable2Growable", "Beans");
		result.put("Vegetable2Seed", "Beans seeds");
		result.put("Vegetable3Growable", "Mushroom");
		result.put("Vegetable3Seed", "Mushroom seeds");
		result.put("VegetableGrower1", "Food grower");
		result.put("VegetableGrower2", "Food grower T2");
		result.put("Vegetube1", "Vegetube T1");
		result.put("Vegetube2", "Vegetube T2");
		result.put("VegetubeOutside1", "Vegetube T3");
		result.put("WardenKey", "Warden's Key");
		result.put("WaterBottle1", "Water bottle");
		result.put("WaterCollector1", "Atmospheric Water Collector");
		result.put("WaterCollector2", "Lake Water Collector");
		result.put("WaterFilter", "Water Filter");
		result.put("WaterLifeCollector1", "Water Life Collector");
		result.put("Zeolite", "Zeolite");
		result.put("astrofood", "Space Food");
		result.put("astrofood2", "High Quality Food");
		result.put("biodome", "Biodome");
		result.put("biolab", "Biolab");
		result.put("canister", "canister");
		result.put("door", "Living compartment door");
		result.put("honey", "Honey");
		result.put("ice", "Ice");
		result.put("pod", "Living compartment");
		result.put("podAngle", "Living compartment corner");
		result.put("wallplain", "Plain wall");
		result.put("window", "Living compartment window");
		result.put("WreckServer", "Technical Wreck");
		result.put("wreckpilar", "Wrecks");
		
		return result;
	}
	
	public static final int TypeNONE = 0;
	public static final int TypeMULTIPLIER = 1;
	public static final int TypeITEM = 10;
	public static final int TypeEQUIPMENT = 11;
	public static final int Type3D = 12; // placed by game in the world. 
	public static final int TypeBUILDING = 20;
	public static final int TypePNLS = 21;
	public static final int TypeGAMEPLACED = 22;
	public static final int TypeROCKET = 30; // placed outside of world
	public static final List<Integer> cheatableItemTypes = Arrays.asList(TypeITEM, TypeEQUIPMENT, TypeROCKET);
	public static final List<Integer> cheatableBuildingTypes = Arrays.asList(TypeBUILDING, TypeGAMEPLACED);
	private static final Map<String, Integer> translateGIdType = createTranslateGIdType();
	private static final Map<String, Integer> createTranslateGIdType() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		result.put("AirFilter1", TypeEQUIPMENT);
		result.put("Algae1Growable", Type3D);
		result.put("Algae1Seed", TypeITEM);
		result.put("AlgaeGenerator1", TypeBUILDING);
		result.put("AlgaeGenerator2", TypeBUILDING);
		result.put("Alloy", TypeITEM);
		result.put("Aluminium", TypeITEM);
		result.put("Aquarium1", TypeBUILDING);
		result.put("Aquarium2", TypeBUILDING);
		result.put("AutoCrafter1", TypeBUILDING);
		result.put("Backpack1", TypeEQUIPMENT);
		result.put("Backpack2", TypeEQUIPMENT);
		result.put("Backpack3", TypeEQUIPMENT);
		result.put("Backpack4", TypeEQUIPMENT);
		result.put("Backpack5", TypeEQUIPMENT);
		result.put("Bacteria1", TypeITEM);
		result.put("Beacon", TypeBUILDING);
		result.put("BedDouble", TypeBUILDING);
		result.put("BedSimple", TypeBUILDING);
		result.put("Bee1Hatched", Type3D);
		result.put("Bee1Larvae", TypeITEM);
		result.put("Beehive1", TypeBUILDING);
		result.put("Beehive2", TypeBUILDING);
		result.put("Biodome2", TypeBUILDING);
		result.put("Biolab", TypeBUILDING);
		result.put("Bioplastic1", TypeITEM);
		result.put("BlueprintGreyscale", TypeNONE);
		result.put("BlueprintT1", TypeITEM);
		result.put("BootsSpeed1", TypeEQUIPMENT);
		result.put("BootsSpeed2", TypeEQUIPMENT);
		result.put("BootsSpeed3", TypeEQUIPMENT);
		result.put("Butterfly10Larvae", TypeITEM);
		result.put("Butterfly11Larvae", TypeITEM);
		result.put("Butterfly12Larvae", TypeITEM);
		result.put("Butterfly13Larvae", TypeITEM);
		result.put("Butterfly14Larvae", TypeITEM);
		result.put("Butterfly15Larvae", TypeITEM);
		result.put("Butterfly16Larvae", TypeITEM);
		result.put("Butterfly17Larvae", TypeITEM);
		result.put("Butterfly18Larvae", TypeITEM);
		result.put("Butterfly1Larvae", TypeITEM);
		result.put("Butterfly2Larvae", TypeITEM);
		result.put("Butterfly3Larvae", TypeITEM);
		result.put("Butterfly4Larvae", TypeITEM);
		result.put("Butterfly5Larvae", TypeITEM);
		result.put("Butterfly6Larvae", TypeITEM);
		result.put("Butterfly7Larvae", TypeITEM);
		result.put("Butterfly8Larvae", TypeITEM);
		result.put("Butterfly9Larvae", TypeITEM);
		result.put("ButterflyDisplayer1", TypeITEM);
		result.put("ButterflyDome1", TypeBUILDING);
		result.put("ButterflyFarm1", TypeBUILDING);
		result.put("ButterflyFarm2", TypeBUILDING);
		result.put("Chair1", TypeBUILDING);
		result.put("CircuitBoard1", TypeITEM);
		result.put("Cobalt", TypeITEM);
		result.put("ComAntenna", TypeBUILDING);
		result.put("Container1", TypeBUILDING);
		result.put("Container2", TypeBUILDING);
		result.put("CraftStation1", TypeBUILDING);
		result.put("CraftStation2", TypeBUILDING);
		result.put("Desktop1", TypeBUILDING);
		result.put("Destructor1", TypeEQUIPMENT);
		result.put("DisplayCase", TypeBUILDING);
		result.put("Drill0", TypeBUILDING);
		result.put("Drill1", TypeBUILDING);
		result.put("Drill2", TypeBUILDING);
		result.put("Drill3", TypeBUILDING);
		result.put("Drill4", TypeBUILDING);
		result.put("Drone1", TypeITEM);
		result.put("DroneStation1", TypeBUILDING);
		result.put("EnergyGenerator1", TypeBUILDING);
		result.put("EnergyGenerator2", TypeBUILDING);
		result.put("EnergyGenerator3", TypeBUILDING);
		result.put("EnergyGenerator4", TypeBUILDING);
		result.put("EnergyGenerator5", TypeBUILDING);
		result.put("EnergyGenerator6", TypeBUILDING);
		result.put("EquipmentIncrease1", TypeEQUIPMENT);
		result.put("EquipmentIncrease2", TypeEQUIPMENT);
		result.put("EquipmentIncrease3", TypeEQUIPMENT);
		result.put("EscapePod", TypeGAMEPLACED);
		result.put("FabricBlue", TypeITEM);
		result.put("Farm1", TypeBUILDING);
		result.put("Fence", TypeBUILDING);
		result.put("Fertilizer1", TypeITEM);
		result.put("Fertilizer2", TypeITEM);
		result.put("Fish10Eggs", TypeITEM);
		result.put("Fish10Hatched", Type3D);
		result.put("Fish11Eggs", TypeITEM);
		result.put("Fish11Hatched", Type3D);
		result.put("Fish1Eggs", TypeITEM);
		result.put("Fish1Hatched", Type3D);
		result.put("Fish2Eggs", TypeITEM);
		result.put("Fish2Hatched", Type3D);
		result.put("Fish3Eggs", TypeITEM);
		result.put("Fish3Hatched", Type3D);
		result.put("Fish4Eggs", TypeITEM);
		result.put("Fish4Hatched", Type3D);
		result.put("Fish5Eggs", TypeITEM);
		result.put("Fish5Hatched", Type3D);
		result.put("Fish6Eggs", TypeITEM);
		result.put("Fish6Hatched", Type3D);
		result.put("Fish7Eggs", TypeITEM);
		result.put("Fish7Hatched", Type3D);
		result.put("Fish8Eggs", TypeITEM);
		result.put("Fish8Hatched", Type3D);
		result.put("Fish9Eggs", TypeITEM);
		result.put("Fish9Hatched", Type3D);
		result.put("FishDisplayer1", TypeBUILDING);
		result.put("FishFarm1", TypeBUILDING);
		result.put("FloorGlass", TypePNLS);
		result.put("FlowerPot1", TypeBUILDING);
		result.put("Foundation", TypeBUILDING);
		result.put("FusionEnergyCell", TypeITEM);
		result.put("FusionGenerator1", TypeGAMEPLACED);
		result.put("GasExtractor1", TypeBUILDING);
		result.put("GasExtractor2", TypeBUILDING);
		result.put("GeneticManipulator1", TypeBUILDING);
		result.put("GoldenContainer", TypeBUILDING);
		result.put("GoldenEffigie1", TypeITEM);
		result.put("GoldenEffigie2", TypeITEM);
		result.put("GoldenEffigie3", TypeITEM);
		result.put("GoldenEffigie4", TypeITEM);
		result.put("GoldenEffigie5", TypeITEM);
		result.put("GoldenEffigie6", TypeITEM);
		result.put("GoldenEffigieSpawner", TypeNONE);
		result.put("GrassSpreader1", TypeBUILDING);
		result.put("Heater1", TypeBUILDING);
		result.put("Heater2", TypeBUILDING);
		result.put("Heater3", TypeBUILDING);
		result.put("Heater4", TypeBUILDING);
		result.put("Heater5", TypeBUILDING);
		result.put("HudCompass", TypeNONE);
		result.put("Incubator1", TypeBUILDING);
		result.put("InsideLamp1", TypeBUILDING);
		result.put("Iridium", TypeITEM);
		result.put("Iron", TypeITEM);
		result.put("Jetpack1", TypeEQUIPMENT);
		result.put("Jetpack2", TypeEQUIPMENT);
		result.put("Jetpack3", TypeEQUIPMENT);
		result.put("Ladder", TypeBUILDING);
		result.put("LarvaeBase1", TypeITEM);
		result.put("LarvaeBase2", TypeITEM);
		result.put("LarvaeBase3", TypeITEM);
		result.put("LaunchPlatform", TypeBUILDING);
		result.put("Magnesium", TypeITEM);
		result.put("MethanCapsule1", TypeITEM);
		result.put("MultiBuild", TypeEQUIPMENT);
		result.put("MultiDeconstruct", TypeEQUIPMENT);
		result.put("MultiDefault", TypeNONE);
		result.put("MultiDefault2", TypeNONE);
		result.put("HudChipCleanConstruction", TypeEQUIPMENT);
		result.put("MultiToolCompass", TypeEQUIPMENT);
		result.put("MultiToolDeconstruct2", TypeEQUIPMENT);
		result.put("MultiToolDeconstructT2_0", TypeNONE);
		result.put("MultiToolLight", TypeEQUIPMENT);
		result.put("MultiToolLight2", TypeEQUIPMENT);
		result.put("MultiToolMineSpeed", TypeNONE);
		result.put("MultiToolMineSpeed1", TypeEQUIPMENT);
		result.put("MultiToolMineSpeed2", TypeEQUIPMENT);
		result.put("MultiToolMineSpeed3", TypeEQUIPMENT);
		result.put("MultiToolMineSpeed4", TypeEQUIPMENT);
		result.put("Mutagen1", TypeITEM);
		result.put("Mutagen2", TypeITEM);
		result.put("Mutagen3", TypeITEM);
		result.put("NitrogenCapsule1", TypeITEM);
		result.put("OreExtractor1", TypeBUILDING);
		result.put("OreExtractor2", TypeBUILDING);
		result.put("OreExtractor3", TypeBUILDING);
		result.put("Osmium", TypeITEM);
		result.put("OutsideLamp1", TypeBUILDING);
		result.put("OxygenCapsule1", TypeITEM);
		result.put("OxygenTank1", TypeEQUIPMENT);
		result.put("OxygenTank2", TypeEQUIPMENT);
		result.put("OxygenTank3", TypeEQUIPMENT);
		result.put("OxygenTank4", TypeEQUIPMENT);
		result.put("Phytoplankton1", TypeITEM);
		result.put("Phytoplankton2", TypeITEM);
		result.put("Phytoplankton3", TypeITEM);
		//result.put("Phytoplankton4", TypeITEM);
		result.put("Pod4x", TypeBUILDING);
		result.put("PulsarChip", TypeNONE);
		result.put("PulsarQuartz", TypeITEM);
		result.put("PulsarShard", TypeNONE);
		result.put("RecyclingMachine", TypeBUILDING);
		result.put("RedPowder1", TypeITEM);
		result.put("RocketBiomass1", TypeROCKET);
		result.put("RocketDrones1", TypeROCKET);
		result.put("RocketHeat1", TypeROCKET);
		result.put("RocketInformations1", TypeROCKET);
		result.put("RocketInsects1", TypeROCKET);
		result.put("RocketMap1", TypeROCKET);
		result.put("RocketMap2", TypeROCKET);
		result.put("RocketMap3", TypeROCKET);
		result.put("RocketMap4", TypeROCKET);
		result.put("RocketOxygen1", TypeROCKET);
		result.put("RocketPressure1", TypeROCKET);
		result.put("RocketReactor", TypeROCKET);
		result.put("Rod-alloy", TypeITEM);
		result.put("Rod-iridium", TypeITEM);
		result.put("Rod-osmium", TypeITEM);
		result.put("Rod-uranium", TypeITEM);
		result.put("Satellite", TypeGAMEPLACED);
		result.put("ScreenBiomass", TypeBUILDING);
		result.put("ScreenEnergy", TypeBUILDING);
		result.put("ScreenMap1", TypeBUILDING);
		result.put("ScreenMessage", TypeBUILDING);
		result.put("ScreenRockets", TypeBUILDING);
		result.put("ScreenTerraStage", TypeBUILDING);
		result.put("ScreenTerraformation", TypeBUILDING);
		result.put("ScreenTerraformationOld", TypeNONE);
		result.put("ScreenUnlockables", TypeBUILDING);
		result.put("Seed0", TypeITEM);
		result.put("Seed0Growable", Type3D);
		result.put("Seed1", TypeITEM);
		result.put("Seed1Growable", Type3D);
		result.put("Seed2", TypeITEM);
		result.put("Seed2Growable", Type3D);
		result.put("Seed3", TypeITEM);
		result.put("Seed3Growable", Type3D);
		result.put("Seed4", TypeITEM);
		result.put("Seed4Growable", Type3D);
		result.put("Seed5", TypeITEM);
		result.put("Seed5Growable", Type3D);
		result.put("Seed6", TypeITEM);
		result.put("Seed6Growable", Type3D);
		result.put("SeedGold", TypeITEM);
		result.put("SeedGoldGrowable", Type3D);
		result.put("SeedSpreader1", TypeBUILDING);
		result.put("SeedSpreader2", TypeBUILDING);
		result.put("Sign", TypeBUILDING);
		result.put("Silicon", TypeITEM);
		result.put("Silk", TypeITEM);
		result.put("SilkGenerator", TypeBUILDING);
		result.put("SilkWorm", TypeITEM);
		result.put("Sofa", TypeBUILDING);
		result.put("SofaAngle", TypeBUILDING);
		result.put("SpaceMultiplierBiomass", TypeMULTIPLIER);
		result.put("SpaceMultiplierHeat", TypeMULTIPLIER);
		result.put("SpaceMultiplierInsects", TypeMULTIPLIER);
		result.put("SpaceMultiplierOxygen", TypeMULTIPLIER);
		result.put("SpaceMultiplierPlants", TypeMULTIPLIER);
		result.put("SpaceMultiplierPressure", TypeMULTIPLIER);
		result.put("Stairs", TypeBUILDING);
		result.put("Sulfur", TypeITEM);
		result.put("TableSmall", TypeBUILDING);
		result.put("Teleporter1", TypeBUILDING);
		result.put("Titanium", TypeITEM);
		result.put("Tree0Growable", Type3D);
		result.put("Tree0Seed", TypeITEM);
		result.put("Tree1Growable", Type3D);
		result.put("Tree1Seed", TypeITEM);
		result.put("Tree2Growable", Type3D);
		result.put("Tree2Seed", TypeITEM);
		result.put("Tree3Growable", Type3D);
		result.put("Tree3Seed", TypeITEM);
		result.put("Tree4Growable", Type3D);
		result.put("Tree4Seed", TypeITEM);
		result.put("Tree5Growable", Type3D);
		result.put("Tree5Seed", TypeITEM);
		result.put("Tree6Growable", Type3D);
		result.put("Tree6Seed", TypeITEM);
		result.put("Tree7Growable", Type3D);
		result.put("Tree7Seed", TypeITEM);
		result.put("Tree8Growable", Type3D);
		result.put("Tree8Seed", TypeITEM);
		result.put("Tree9Seed", TypeITEM);
		result.put("TreeRoot", TypeITEM);
		result.put("TreeSpreader0", TypeBUILDING);
		result.put("TreeSpreader1", TypeBUILDING);
		result.put("TreeSpreader2", TypeBUILDING);
		//result.put("TreeSpreaderGiant", TypeNONE); // does not exist???
		result.put("Uranim", TypeITEM);
		result.put("UraniumRod", TypeNONE);
		result.put("Vegetable0Growable", TypeITEM);
		result.put("Vegetable0Seed", TypeITEM);
		result.put("Vegetable1Growable", TypeITEM);
		result.put("Vegetable1Seed", TypeITEM);
		result.put("Vegetable2Growable", TypeITEM);
		result.put("Vegetable2Seed", TypeITEM);
		result.put("Vegetable3Growable", TypeITEM);
		result.put("Vegetable3Seed", TypeITEM);
		result.put("VegetableGrower1", TypeBUILDING);
		result.put("VegetableGrower2", TypeBUILDING);
		result.put("Vegetube1", TypeBUILDING);
		result.put("Vegetube2", TypeBUILDING);
		result.put("VegetubeOutside1", TypeBUILDING);
		result.put("WardenKey", TypeITEM);
		result.put("WaterBottle1", TypeITEM);
		result.put("WaterCollector1", TypeBUILDING);
		result.put("WaterCollector2", TypeBUILDING);
		result.put("WaterFilter", TypeEQUIPMENT);
		result.put("WaterLifeCollector1", TypeBUILDING);
		result.put("Zeolite", TypeITEM);
		result.put("astrofood", TypeITEM);
		result.put("astrofood2", TypeITEM);
		result.put("biodome", TypeBUILDING);
		result.put("biolab", TypeBUILDING);
		result.put("canister", TypeGAMEPLACED);
		result.put("door", TypePNLS);
		result.put("honey", TypeITEM);
		result.put("ice", TypeITEM);
		result.put("pod", TypeBUILDING);
		result.put("podAngle", TypeBUILDING);
		result.put("wallplain", TypePNLS);
		result.put("window", TypePNLS);
		result.put("WreckServer", TypeGAMEPLACED);
		result.put("wreckpilar", TypeGAMEPLACED);
		
		return result;
	}
	public static int getType(String pGId) {
		return translateGIdType.getOrDefault(pGId, TypeNONE);
	}
	
	private static final Map<String, String> translateGIdPicturePath = createTranslateGIdPicturePath();
	private static final Map<String, String> createTranslateGIdPicturePath() {
		Map<String, String> result = new HashMap<String, String>();
		for (String hGId : translateGIdName.keySet()) {
			result.put(hGId, "GId/" + hGId + ".png");
		}
		
		result.put("Container1" + LOOTCRATEGID, "Default/Container1.png"); // remember to add to numberOfPicture (on the top)
		result.put("Container2" + LOOTCRATEGID, "Default/Container2.png");
		result.put("GoldenContainer" + LOOTCRATEGID, "Default/GoldenContainer.png");
		result.put("canister" + LOOTCRATEGID, "Default/canister.png");
		result.put("Vegetube1" + LOOTCRATEGID, "Default/Vegetube1.png");
		result.put("wreckpilar" + LOOTCRATEGID, "Default/Satellite.png");
		
		result.put("MultiToolMineSpeed1", "GId/MultiToolMineSpeed.png");
		result.put("MultiToolMineSpeed2", "GId/MultiToolMineSpeed.png");
		result.put("MultiToolMineSpeed3", "GId/MultiToolMineSpeed.png");
		result.put("MultiToolMineSpeed4", "GId/MultiToolMineSpeed.png");
		
		result.put("Algae1Growable", "GId/Algae1Seed.png");
		result.put("Seed0Growable", "GId/Seed0.png");
		result.put("Seed1Growable", "GId/Seed1.png");
		result.put("Seed2Growable", "GId/Seed2.png");
		result.put("Seed3Growable", "GId/Seed3.png");
		result.put("Seed4Growable", "GId/Seed4.png");
		result.put("Seed5Growable", "GId/Seed5.png");
		result.put("Seed6Growable", "GId/Seed6.png");
		result.put("SeedGoldGrowable", "GId/SeedGold.png");
		result.put("Tree0Growable", "GId/Tree0Seed.png");
		result.put("Tree1Growable", "GId/Tree1Seed.png");
		result.put("Tree2Growable", "GId/Tree2Seed.png");
		result.put("Tree3Growable", "GId/Tree3Seed.png");
		result.put("Tree4Growable", "GId/Tree4Seed.png");
		result.put("Tree5Growable", "GId/Tree5Seed.png");
		result.put("Tree6Growable", "GId/Tree6Seed.png");
		result.put("Tree7Growable", "GId/Tree7Seed.png");
		result.put("Tree8Growable", "GId/Tree8Seed.png");
		
		return result;
	}
	
	private static final BufferedImage defaultBuildingPicture = Picture.loadPicture(nicki0.editor.Map.defaultBuildingPicture);
	private static final Map<String, BufferedImage> translateGIdPicture = createTranslationMapPicture();
	private static final Map<String, BufferedImage> createTranslationMapPicture() {
		Map<String, BufferedImage> result = new HashMap<String, BufferedImage>();
		
		for (String hGId : translateGIdPicturePath.keySet()) {
			BufferedImage bi = Picture.tryLoadPicture(translateGIdPicturePath.get(hGId));
			if (bi != null) result.put(hGId, bi); else if (Main.debug) System.out.println("No picture for: " + hGId  + " : " + translateGIdPicturePath.get(hGId));
		}
		return result;
	}
	public static BufferedImage getItemPicture(String pGId) {
		return translateGIdPicture.getOrDefault(pGId, defaultBuildingPicture);
	}
	public static BufferedImage getItemPictureOrNull(String pGId) {
		return translateGIdPicture.get(pGId);
	}
	
	private static Map<String, List<BufferedImage>> translateGIdPictureList = createTranslateGIdPictureList();
	private static Map<String, List<BufferedImage>> createTranslateGIdPictureList() {
		Map<String, List<BufferedImage>> map = new HashMap<String, List<BufferedImage>>();
		numberOfPictures.keySet().stream().forEach(s -> map.put(s, Picture.loadPicture(translateGIdPicturePath.get(s), getNumberOfPicture(s))));
		return map;
	}
	public static List<BufferedImage> getItemPictureList(String pGId) {
		List<BufferedImage> lst = translateGIdPictureList.get(pGId);
		if (lst == null) {
			lst = new ArrayList<>();
			lst.add(getItemPicture(pGId));
		}
		return lst;
	}
	
	public static final String lootCrateVersion = "v0.7.008"; // TODO Update version number and list
	public static final List<Item> allContainersInWorld = createAllContainersInWorldList();
	public static final String LOOTCRATEGID = "_LootCrate"; // remember to change picture name
	/**
	 * createAllContainersInWorldList should only be used if List intended to be changed
	 */
	private static final List<Item> createAllContainersInWorldList() {
		List<Item> aCiWL = new ArrayList<Item>();
		aCiWL.add(new Item(101002001,"Container1",101002001,761.7f,51.5f,1060.3f,""));
		aCiWL.add(new Item(101087450,"Container1",101087450,-394.7f,-33.4f,187.6f,""));
		aCiWL.add(new Item(101115297,"Container1",101115297,-633.1f,70.4f,1549.7f,""));
		aCiWL.add(new Item(101214947,"Container1",101214947,-335.4f,24.2f,-694.5f,""));
		aCiWL.add(new Item(101318483,"Container1",101318483,925.1f,44.2f,1196.0f,""));
		aCiWL.add(new Item(101398689,"Container1",101398689,662.4f,3.2f,300.9f,""));
		aCiWL.add(new Item(101401639,"Container1",101401639,-1953.0f,76.0f,2942.2f,"DeveloperChest"));
		aCiWL.add(new Item(101402516,"Container1",101402516,-92.0f,37.0f,24.9f,""));
		aCiWL.add(new Item(101418434,"Container1",101418434,1033.2f,20.4f,813.1f,""));
		aCiWL.add(new Item(101427881,"Container1",101427881,-617.1f,76.5f,1545.9f,""));
		aCiWL.add(new Item(101445703,"Container1",101445703,767.6f,60.8f,1314.9f,""));
		aCiWL.add(new Item(101464942,"Container1",101464942,248.3f,141.0f,1023.1f,""));
		aCiWL.add(new Item(101558254,"Container1",101558254,803.8f,159.4f,979.3f,""));
		aCiWL.add(new Item(101687782,"Container1",101687782,1623.6f,65.8f,-654.6f,""));
		aCiWL.add(new Item(101700657,"Container1",101700657,-616.5f,76.5f,1548.8f,""));
		aCiWL.add(new Item(101977370,"Container1",101977370,415.3f,115.0f,1568.2f,""));
		aCiWL.add(new Item(102018440,"Container1",102018440,-320.3f,33.8f,-717.3f,""));
		aCiWL.add(new Item(102091729,"Container1",102091729,451.7f,11.8f,156.6f,""));
		aCiWL.add(new Item(102179975,"Container1",102179975,956.1f,27.9f,409.4f,""));
		aCiWL.add(new Item(102226074,"Container1",102226074,253.3f,144.6f,1071.1f,""));
		aCiWL.add(new Item(102243307,"Container1",102243307,1599.8f,74.6f,-623.3f,""));
		aCiWL.add(new Item(102366652,"Container1",102366652,241.7f,142.5f,1054.2f,""));
		aCiWL.add(new Item(102385799,"Container1",102385799,-69.9f,68.4f,-223.8f,""));
		aCiWL.add(new Item(102398054,"Container1",102398054,1696.0f,58.6f,-612.4f,""));
		aCiWL.add(new Item(102500334,"Container1",102500334,1377.9f,7.5f,647.5f,""));
		aCiWL.add(new Item(102574221,"Container1",102574221,-398.2f,-3.3f,621.1f,""));
		aCiWL.add(new Item(102614535,"Container1",102614535,565.0f,37.7f,-725.7f,""));
		aCiWL.add(new Item(102825655,"Container1",102825655,-608.9f,76.5f,1526.2f,""));
		aCiWL.add(new Item(102832082,"Container1",102832082,1461.7f,24.9f,475.7f,""));
		aCiWL.add(new Item(102962645,"Container1",102962645,1456.1f,15.0f,1354.8f,""));
		aCiWL.add(new Item(102964198,"Container1",102964198,1453.1f,15.3f,1355.4f,""));
		aCiWL.add(new Item(103064754,"Container1",103064754,909.8f,27.5f,49.5f,""));
		aCiWL.add(new Item(103217477,"Container1",103217477,1794.1f,21.5f,1871.3f,""));
		aCiWL.add(new Item(103309366,"Container1",103309366,-462.8f,1.4f,1368.6f,""));
		aCiWL.add(new Item(103317785,"Container1",103317785,-56.0f,54.0f,-803.6f,""));
		aCiWL.add(new Item(103404602,"Container1",103404602,239.9f,142.3f,1056.0f,""));
		aCiWL.add(new Item(103404985,"Container1",103404985,-308.3f,36.6f,1294.4f,""));
		aCiWL.add(new Item(103479341,"Container1",103479341,1593.5f,71.2f,-646.7f,""));
		aCiWL.add(new Item(103487203,"Container1",103487203,-447.8f,7.8f,1518.5f,""));
		aCiWL.add(new Item(103530128,"Container1",103530128,1037.6f,87.4f,-732.6f,""));
		aCiWL.add(new Item(103605493,"Container1",103605493,1472.6f,23.3f,1386.3f,""));
		aCiWL.add(new Item(103724413,"Container1",103724413,870.5f,36.4f,173.9f,""));
		aCiWL.add(new Item(103734714,"Container1",103734714,1488.2f,51.1f,564.7f,""));
		aCiWL.add(new Item(103804180,"Container1",103804180,1190.8f,16.4f,604.2f,""));
		aCiWL.add(new Item(103843431,"Container1",103843431,1471.3f,24.7f,1373.7f,""));
		aCiWL.add(new Item(103873564,"Container1",103873564,-365.4f,43.5f,-248.1f,""));
		aCiWL.add(new Item(103946105,"Container1",103946105,251.6f,142.2f,1020.9f,""));
		aCiWL.add(new Item(103967155,"Container1",103967155,535.9f,1.2f,453.8f,""));
		//aCiWL.add(new Item(103976415,"Container1",103976415,527.4f,5.0f,314.2f,"EscapePod")); // TODO where to put the escapePod Container?
		aCiWL.add(new Item(104011576,"Container1",104011576,1140.8f,14.4f,2370.3f,""));
		aCiWL.add(new Item(104060716,"Container1",104060716,2057.7f,7.8f,511.9f,""));
		aCiWL.add(new Item(104157983,"Container1",104157983,-148.3f,-58.0f,379.3f,""));
		aCiWL.add(new Item(104181383,"Container1",104181383,1615.4f,73.5f,-612.7f,""));
		aCiWL.add(new Item(104269043,"Container1",104269043,1241.1f,7.3f,706.9f,""));
		aCiWL.add(new Item(104299816,"Container1",104299816,1816.6f,2.8f,298.6f,""));
		aCiWL.add(new Item(104360626,"Container1",104360626,-239.0f,-42.0f,332.7f,""));
		aCiWL.add(new Item(104466690,"Container1",104466690,375.2f,165.2f,2343.6f,""));
		aCiWL.add(new Item(104701218,"Container1",104701218,62.4f,170.4f,1813.2f,""));
		aCiWL.add(new Item(104724597,"Container1",104724597,907.5f,29.1f,48.3f,""));
		aCiWL.add(new Item(104762081,"Container1",104762081,1476.0f,17.9f,1370.6f,""));
		aCiWL.add(new Item(104916206,"Container1",104916206,253.1f,144.5f,1065.9f,""));
		aCiWL.add(new Item(105042599,"Container1",105042599,528.8f,153.3f,2303.4f,""));
		aCiWL.add(new Item(105043524,"Container1",105043524,-330.0f,29.9f,-701.8f,""));
		aCiWL.add(new Item(105054066,"Container1",105054066,1511.0f,51.9f,558.5f,""));
		aCiWL.add(new Item(105082787,"Container1",105082787,1120.5f,19.0f,683.3f,""));
		aCiWL.add(new Item(105204783,"Container1",105204783,16.2f,158.0f,1777.8f,""));
		aCiWL.add(new Item(105306100,"Container1",105306100,1470.9f,23.0f,1387.6f,""));
		aCiWL.add(new Item(105371327,"Container1",105371327,434.5f,15.2f,664.3f,""));
		aCiWL.add(new Item(105416510,"Container1",105416510,-237.6f,0.8f,1529.3f,""));
		aCiWL.add(new Item(105438856,"Container1",105438856,1903.4f,-31.2f,432.9f,""));
		aCiWL.add(new Item(105443001,"Container1",105443001,-293.3f,34.1f,-678.9f,""));
		aCiWL.add(new Item(105448654,"Container1",105448654,952.3f,66.8f,1657.1f,""));
		aCiWL.add(new Item(105489957,"Container1",105489957,1232.8f,17.9f,-15.1f,""));
		aCiWL.add(new Item(105503117,"Container1",105503117,2095.0f,75.7f,386.9f,""));
		aCiWL.add(new Item(105535765,"Container1",105535765,532.7f,131.9f,2393.9f,""));
		aCiWL.add(new Item(105566986,"Container1",105566986,1096.1f,18.8f,684.3f,""));
		aCiWL.add(new Item(105596902,"Container1",105596902,1473.7f,11.7f,1361.4f,""));
		aCiWL.add(new Item(105655876,"Container1",105655876,1618.1f,70.9f,-628.5f,""));
		aCiWL.add(new Item(105671385,"Container1",105671385,1372.9f,27.8f,1811.3f,""));
		aCiWL.add(new Item(105678401,"Container1",105678401,-473.2f,82.2f,307.4f,""));
		aCiWL.add(new Item(105832023,"Container1",105832023,596.3f,85.3f,-823.4f,""));
		aCiWL.add(new Item(105909757,"Container1",105909757,1495.2f,52.4f,573.1f,""));
		aCiWL.add(new Item(105921460,"Container1",105921460,250.7f,108.3f,1584.2f,""));
		aCiWL.add(new Item(105936242,"Container1",105936242,-263.5f,48.1f,-71.4f,""));
		aCiWL.add(new Item(105996214,"Container1",105996214,281.2f,147.6f,1055.2f,""));
		aCiWL.add(new Item(106107629,"Container1",106107629,12.0f,211.2f,1006.9f,""));
		aCiWL.add(new Item(106130984,"Container1",106130984,-189.0f,76.7f,532.9f,""));
		aCiWL.add(new Item(106171406,"Container1",106171406,1520.4f,52.7f,555.2f,""));
		aCiWL.add(new Item(106186759,"Container1",106186759,-35.4f,179.3f,1559.7f,""));
		aCiWL.add(new Item(106212433,"Container1",106212433,257.9f,21.2f,646.0f,""));
		aCiWL.add(new Item(106217174,"Container1",106217174,348.8f,167.0f,1924.2f,""));
		aCiWL.add(new Item(106322534,"Container1",106322534,1357.9f,10.7f,2148.7f,""));
		aCiWL.add(new Item(106382128,"Container1",106382128,511.1f,18.7f,-211.2f,""));
		aCiWL.add(new Item(106416417,"Container1",106416417,1541.4f,78.0f,2410.3f,""));
		aCiWL.add(new Item(106461839,"Container1",106461839,-81.1f,158.3f,1786.2f,""));
		aCiWL.add(new Item(106508161,"Container1",106508161,1643.8f,62.0f,-652.7f,""));
		aCiWL.add(new Item(106508742,"Container1",106508742,231.6f,141.0f,1051.7f,""));
		aCiWL.add(new Item(106546279,"Container1",106546279,713.5f,68.9f,1717.8f,""));
		aCiWL.add(new Item(106548847,"Container1",106548847,1577.8f,10.6f,2097.4f,""));
		aCiWL.add(new Item(106551826,"Container1",106551826,263.1f,145.4f,1057.3f,""));
		aCiWL.add(new Item(106554282,"Container1",106554282,1104.8f,17.3f,693.6f,""));
		aCiWL.add(new Item(106657747,"Container1",106657747,54.2f,30.6f,-472.2f,""));
		aCiWL.add(new Item(106663599,"Container1",106663599,1775.4f,2.3f,98.7f,""));
		aCiWL.add(new Item(106686196,"Container1",106686196,-337.8f,24.3f,-693.6f,""));
		aCiWL.add(new Item(106707070,"Container1",106707070,2036.4f,2.4f,190.6f,""));
		aCiWL.add(new Item(106748889,"Container1",106748889,541.6f,142.3f,2363.8f,""));
		aCiWL.add(new Item(106837622,"Container1",106837622,1463.1f,107.0f,2503.1f,""));
		aCiWL.add(new Item(106846846,"Container1",106846846,444.1f,11.0f,-207.9f,""));
		aCiWL.add(new Item(106894169,"Container1",106894169,-230.9f,-44.4f,554.4f,""));
		aCiWL.add(new Item(106896496,"Container1",106896496,279.1f,147.7f,1056.8f,""));
		aCiWL.add(new Item(106905146,"Container1",106905146,1012.2f,25.1f,114.8f,""));
		aCiWL.add(new Item(106945766,"Container1",106945766,367.3f,176.4f,2137.1f,""));
		aCiWL.add(new Item(106978172,"Container1",106978172,540.6f,136.7f,2382.2f,""));
		aCiWL.add(new Item(106989649,"Container1",106989649,767.2f,32.2f,-18.2f,""));
		aCiWL.add(new Item(106997653,"Container1",106997653,1094.8f,28.5f,-1103.0f,""));
		aCiWL.add(new Item(107035446,"Container1",107035446,579.1f,70.1f,1272.4f,""));
		aCiWL.add(new Item(107055232,"Container1",107055232,529.9f,131.8f,2392.5f,""));
		aCiWL.add(new Item(107090832,"Container1",107090832,253.0f,141.7f,1019.2f,""));
		aCiWL.add(new Item(107176525,"Container1",107176525,1854.2f,2.5f,-25.5f,""));
		aCiWL.add(new Item(107232565,"Container1",107232565,994.8f,64.7f,1734.3f,""));
		aCiWL.add(new Item(107260350,"Container1",107260350,1500.1f,52.4f,575.1f,""));
		aCiWL.add(new Item(107281700,"Container1",107281700,661.3f,4.4f,763.6f,""));
		aCiWL.add(new Item(107301147,"Container1",107301147,-337.7f,24.2f,-691.3f,""));
		aCiWL.add(new Item(107319398,"Container1",107319398,281.5f,146.2f,1020.1f,""));
		aCiWL.add(new Item(107341460,"Container1",107341460,225.5f,140.1f,1052.2f,""));
		aCiWL.add(new Item(107351724,"Container1",107351724,234.2f,141.4f,1049.5f,""));
		aCiWL.add(new Item(107397924,"Container1",107397924,1364.0f,2.4f,1475.9f,""));
		aCiWL.add(new Item(107523894,"Container1",107523894,545.6f,64.1f,1587.8f,""));
		aCiWL.add(new Item(107632502,"Container1",107632502,1474.4f,22.9f,1384.6f,""));
		aCiWL.add(new Item(107756141,"Container1",107756141,-439.6f,84.7f,754.6f,""));
		aCiWL.add(new Item(107764885,"Container1",107764885,1101.2f,17.2f,694.3f,""));
		aCiWL.add(new Item(107917136,"Container1",107917136,-337.2f,12.3f,-709.9f,""));
		aCiWL.add(new Item(107965429,"Container1",107965429,-261.3f,38.1f,-694.5f,""));
		aCiWL.add(new Item(108034268,"Container1",108034268,1333.8f,28.0f,145.9f,""));
		aCiWL.add(new Item(108037300,"Container1",108037300,1474.7f,11.9f,1362.4f,""));
		aCiWL.add(new Item(108111014,"Container1",108111014,630.6f,33.9f,1934.2f,""));
		aCiWL.add(new Item(108133067,"Container1",108133067,1294.4f,68.0f,-736.5f,""));
		aCiWL.add(new Item(108147902,"Container1",108147902,554.9f,1.6f,628.2f,""));
		aCiWL.add(new Item(108286345,"Container1",108286345,1054.3f,2.8f,489.9f,""));
		aCiWL.add(new Item(108286894,"Container1",108286894,533.2f,134.8f,2370.4f,""));
		aCiWL.add(new Item(108480098,"Container1",108480098,-216.1f,55.2f,188.8f,""));
		aCiWL.add(new Item(108483197,"Container1",108483197,579.4f,36.0f,-622.9f,""));
		aCiWL.add(new Item(108488074,"Container1",108488074,1668.1f,31.3f,1126.5f,""));
		aCiWL.add(new Item(108501101,"Container1",108501101,317.5f,46.0f,768.4f,""));
		aCiWL.add(new Item(108524470,"Container1",108524470,2203.4f,3.3f,623.3f,""));
		aCiWL.add(new Item(108541223,"Container1",108541223,1022.9f,28.2f,-1115.5f,""));
		aCiWL.add(new Item(108567725,"Container1",108567725,1111.4f,17.1f,694.5f,""));
		aCiWL.add(new Item(108616902,"Container1",108616902,866.7f,150.3f,1033.3f,""));
		aCiWL.add(new Item(108662362,"Container1",108662362,348.7f,88.0f,1299.7f,""));
		aCiWL.add(new Item(108713577,"Container1",108713577,-26.2f,47.9f,-112.8f,""));
		aCiWL.add(new Item(108716571,"Container1",108716571,1317.0f,51.6f,566.9f,""));
		aCiWL.add(new Item(108732071,"Container1",108732071,187.4f,10.1f,-218.0f,""));
		aCiWL.add(new Item(108835572,"Container1",108835572,155.2f,32.2f,-614.3f,""));
		aCiWL.add(new Item(108857323,"Container1",108857323,810.7f,68.1f,1525.2f,""));
		aCiWL.add(new Item(108873282,"Container1",108873282,512.0f,156.1f,2021.5f,""));
		aCiWL.add(new Item(108882405,"Container1",108882405,-593.2f,76.5f,1538.0f,""));
		aCiWL.add(new Item(108883543,"Container1",108883543,1287.3f,16.5f,606.5f,""));
		aCiWL.add(new Item(108921922,"Container1",108921922,-252.1f,30.2f,-470.0f,""));
		aCiWL.add(new Item(108944185,"Container1",108944185,1620.2f,68.0f,-635.3f,""));
		aCiWL.add(new Item(109028290,"Container1",109028290,-11.8f,-37.8f,404.4f,""));
		aCiWL.add(new Item(109060274,"Container1",109060274,-354.9f,82.5f,515.8f,""));
		aCiWL.add(new Item(109079461,"Container1",109079461,24.2f,149.1f,1952.6f,""));
		aCiWL.add(new Item(109096497,"Container1",109096497,1545.1f,11.0f,2335.5f,""));
		aCiWL.add(new Item(109105665,"Container1",109105665,920.7f,28.7f,82.4f,""));
		aCiWL.add(new Item(109158598,"Container1",109158598,769.8f,35.9f,50.0f,""));
		aCiWL.add(new Item(109190396,"Container1",109190396,1374.3f,4.0f,1031.8f,""));
		aCiWL.add(new Item(109241221,"Container1",109241221,1602.4f,71.7f,-639.1f,""));
		aCiWL.add(new Item(109350938,"Container1",109350938,-671.0f,125.8f,977.8f,""));
		aCiWL.add(new Item(109435647,"Container1",109435647,1574.3f,81.9f,-610.1f,""));
		aCiWL.add(new Item(109561812,"Container1",109561812,506.0f,20.1f,-201.9f,""));
		aCiWL.add(new Item(109657504,"Container1",109657504,245.7f,46.6f,-253.8f,""));
		aCiWL.add(new Item(109702771,"Container1",109702771,794.9f,150.1f,1097.1f,""));
		aCiWL.add(new Item(109938271,"Container1",109938271,2078.6f,3.9f,-81.4f,""));
		aCiWL.add(new Item(109981619,"Container1",109981619,267.9f,145.2f,1037.6f,""));
		aCiWL.add(new Item(104853363,"Container1",104853363,1480.5f,25.5f,1357.8f,""));//v0.7.008
		aCiWL.add(new Item(102708640,"Container1",102708640,1479.4f,24.8f,1364.4f,""));//v0.7.008
		aCiWL.add(new Item(103171339,"Container1",103171339,1481.6f,27.6f,1343.6f,""));//v0.7.008
		aCiWL.add(new Item(102191751,"Container1",102191751,1483.4f,27.1f,1341.9f,""));//v0.7.008
		aCiWL.add(new Item(101518049,"Container1",101518049,1472.8f,21.3f,1342.1f,""));//v0.7.008
		aCiWL.add(new Item(105920105,"Container1",105920105,-986.0f,90.6f,478.7f,""));//v0.7.008
		aCiWL.add(new Item(104061574,"Container1",104061574,-977.9f,90.6f,475.1f,""));//v0.7.008
		aCiWL.add(new Item(107545812,"Container1",107545812,-778.3f,163.1f,540.0f,""));//v0.7.008
		aCiWL.add(new Item(108797889,"Container1",108797889,-1063.4f,60.7f,308.4f,""));//v0.7.008
		aCiWL.add(new Item(106762679,"Container1",106762679,-1253.7f,35.3f,521.9f,""));//v0.7.008
		aCiWL.add(new Item(108493457,"Container1",108493457,-1357.8f,86.8f,450.9f,""));//v0.7.008
		aCiWL.add(new Item(103467708,"Container1",103467708,-1365.0f,85.9f,450.1f,""));//v0.7.008
		aCiWL.add(new Item(105919606,"Container1",105919606,-1361.7f,84.6f,414.8f,""));//v0.7.008
		aCiWL.add(new Item(104650555,"Container1",104650555,-1404.7f,83.4f,447.4f,""));//v0.7.008
		aCiWL.add(new Item(109823677,"Container1",109823677,-1402.8f,83.4f,448.4f,""));//v0.7.008
		aCiWL.add(new Item(108658830,"Container1",108658830,-1389.3f,96.6f,470.3f,""));//v0.7.008
		aCiWL.add(new Item(101853424,"Container1",101853424,-1388.3f,97.3f,472.0f,""));//v0.7.008
		aCiWL.add(new Item(103457908,"Container1",103457908,-1397.7f,95.3f,451.5f,""));//v0.7.008
		aCiWL.add(new Item(101624588,"Container1",101624588,-1368.2f,96.0f,434.7f,""));//v0.7.008
		aCiWL.add(new Item(105198800,"Container1",105198800,-1365.1f,95.9f,429.8f,""));//v0.7.008
		aCiWL.add(new Item(101267009,"Container1",101267009,-1359.1f,96.4f,432.9f,""));//v0.7.008
		aCiWL.add(new Item(102330057,"Container1",102330057,-1349.2f,96.7f,424.5f,""));//v0.7.008
		aCiWL.add(new Item(101055579,"Container2",101055579,1130.4f,8.8f,2355.6f,""));
		aCiWL.add(new Item(102242710,"Container2",102242710,1094.9f,10.0f,2383.5f,""));
		aCiWL.add(new Item(103406983,"Container2",103406983,-592.8f,75.6f,1557.3f,""));
		aCiWL.add(new Item(103442833,"Container2",103442833,-304.5f,-2.4f,1353.1f,""));
		aCiWL.add(new Item(104561210,"Container2",104561210,1132.5f,8.8f,2357.8f,""));
		aCiWL.add(new Item(104877872,"Container2",104877872,1165.8f,2.1f,1289.5f,""));
		aCiWL.add(new Item(106424819,"Container2",106424819,-633.0f,75.6f,1531.2f,""));
		aCiWL.add(new Item(107723550,"Container2",107723550,692.3f,29.2f,-873.7f,""));
		aCiWL.add(new Item(108158758,"Container2",108158758,-336.4f,17.4f,-705.4f,""));
		aCiWL.add(new Item(108189029,"Container2",108189029,1130.7f,8.8f,2356.4f,""));
		aCiWL.add(new Item(109235737,"Container2",109235737,1608.1f,72.8f,-618.9f,""));
		aCiWL.add(new Item(109709359,"Container2",109709359,-332.7f,11.4f,-690.5f,""));
		aCiWL.add(new Item(105362096,"Container2",105362096,-1381.9f,81.0f,392.7f,""));//v0.7.008
		aCiWL.add(new Item(105549205,"Container2",105549205,-1368.4f,93.5f,404.1f,""));//v0.7.008
		aCiWL.add(new Item(101293320,"GoldenContainer",101293320,926.4f,86.2f,-775.4f,""));
		aCiWL.add(new Item(101896178,"GoldenContainer",101896178,1100.0f,124.5f,2934.9f,""));
		aCiWL.add(new Item(102281766,"GoldenContainer",102281766,2156.1f,3.4f,242.3f,""));
		aCiWL.add(new Item(103248374,"GoldenContainer",103248374,706.8f,155.4f,1887.7f,""));
		aCiWL.add(new Item(103762341,"GoldenContainer",103762341,260.4f,27.0f,487.2f,""));
		aCiWL.add(new Item(103811904,"GoldenContainer",103811904,1022.7f,17.5f,7.4f,""));
		aCiWL.add(new Item(103931496,"GoldenContainer",103931496,641.7f,24.0f,2086.6f,""));
		aCiWL.add(new Item(104275552,"GoldenContainer",104275552,1748.9f,0.7f,1992.3f,""));
		aCiWL.add(new Item(104405814,"GoldenContainer",104405814,464.4f,66.9f,1567.1f,""));
		aCiWL.add(new Item(105308637,"GoldenContainer",105308637,-421.6f,-55.5f,111.1f,""));
		aCiWL.add(new Item(105674853,"GoldenContainer",105674853,833.4f,40.1f,1194.9f,""));
		aCiWL.add(new Item(105811267,"GoldenContainer",105811267,398.3f,7.0f,-246.1f,""));
		aCiWL.add(new Item(106014910,"GoldenContainer",106014910,920.0f,19.7f,360.8f,""));
		aCiWL.add(new Item(106785344,"GoldenContainer",106785344,1099.0f,124.6f,2931.7f,""));
		aCiWL.add(new Item(106960229,"GoldenContainer",106960229,293.0f,162.6f,1005.7f,""));
		aCiWL.add(new Item(107183786,"GoldenContainer",107183786,-141.9f,180.7f,783.8f,""));
		aCiWL.add(new Item(108243927,"GoldenContainer",108243927,1480.9f,14.1f,647.9f,""));
		aCiWL.add(new Item(105502699,"GoldenContainer",105502699,-584.6f,53.3f,99.7f,""));//v0.7.008
		aCiWL.add(new Item(106354955,"GoldenContainer",106354955,-854.2f,285.1f,594.8f,""));//v0.7.008
		aCiWL.add(new Item(108228777,"GoldenContainer",108228777,-977.8f,46.3f,645.7f,""));//v0.7.008
		aCiWL.add(new Item(105873881,"Vegetube1",105873881,-297.8f,34.5f,-677.6f,""));
		aCiWL.add(new Item(101045324,"canister",101045324,1481.0f,10.6f,1357.9f,""));
		aCiWL.add(new Item(101182048,"canister",101182048,221.6f,140.3f,1059.5f,""));
		aCiWL.add(new Item(103094689,"canister",103094689,1453.1f,21.0f,1360.6f,""));
		aCiWL.add(new Item(103522694,"canister",103522694,1096.4f,18.4f,685.8f,""));
		aCiWL.add(new Item(103765743,"canister",103765743,1634.2f,61.0f,-664.2f,""));
		aCiWL.add(new Item(103976295,"canister",103976295,1515.1f,52.4f,570.2f,""));
		aCiWL.add(new Item(103988075,"canister",103988075,2195.9f,1.6f,617.0f,""));
		aCiWL.add(new Item(103990756,"canister",103990756,264.6f,142.9f,1012.2f,""));
		aCiWL.add(new Item(105882274,"canister",105882274,513.0f,20.6f,-198.7f,""));
		aCiWL.add(new Item(105897288,"canister",105897288,1455.0f,26.2f,1372.5f,""));
		aCiWL.add(new Item(107682618,"canister",107682618,1101.3f,17.3f,693.7f,""));
		aCiWL.add(new Item(108250279,"canister",108250279,1497.2f,51.3f,575.9f,""));
		aCiWL.add(new Item(109699456,"canister",109699456,1601.4f,68.0f,-658.5f,""));
		aCiWL.add(new Item(109126245,"canister",109126245,-1373.3f,95.4f,440.5f,""));//v0.7.008
		aCiWL.add(new Item(101026560,"canister",101026560,-1389.9f,80.9f,403.6f,""));//v0.7.008
		aCiWL.add(new Item(101046169,"wreckpilar",101046169,1083.7f,63.5f,1922.8f,"Satellite"));
		aCiWL.add(new Item(103919282,"wreckpilar",103919282,928.6f,40.5f,-579.4f,"Satellite"));
		return aCiWL;
	}
	
	public static final Map<String, List<String>> translateGIdToSupplied = createTranslateGIdToSupplied();
	private static final Map<String, List<String>> createTranslateGIdToSupplied() {
		Map<String, List<String>> map = new HashMap<>();
		map.put("GasExtractor1", Arrays.asList("OxygenCapsule1","MethanCapsule1","NitrogenCapsule1"));
		map.put("Beehive1", Arrays.asList("honey"));
		map.put("Beehive2", Arrays.asList("Bee1Larvae","honey"));
		map.put("Biodome2", Arrays.asList("TreeRoot"));
		map.put("SilkGenerator", Arrays.asList("Silk"));
		//map.put("WaterCollector1", Arrays.asList("WaterBottle1")); // vllt. f�r Spieler als Trinkquelle... 
		map.put("WaterCollector2", Arrays.asList("WaterBottle1"));
		map.put("WaterLifeCollector1", Arrays.asList("Phytoplankton1","Phytoplankton2","Phytoplankton3"));
		
		return map;
	}
	public static final List<String> getSupplyOfBuilding(String pGId) {
		return translateGIdToSupplied.getOrDefault(pGId, new ArrayList<>());
	}
	
	public static final Map<String, Icon> translateGIdEONCImageIcon = createTranslateGIdEONCImageIcon();
	private static final Map<String, Icon> createTranslateGIdEONCImageIcon() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Map<String, Icon> result = new HashMap<String, Icon>();
		// what the stream does: filter(gid with existing picture) forEach(new scaled Picture)
		Object.translateGIdName.keySet().stream().filter(k -> Object.translateGIdPicture.containsKey(k)).forEach(s -> result.put(s, new ImageIcon(translateGIdPicture.get(s).getScaledInstance((int) (screenSize.height / 1080.0 * IconPanel.height), (int) (screenSize.height / 1080.0 * IconPanel.height), Image.SCALE_SMOOTH))));
		return result;
	}
	
	public Object() {}
	public static void ping() {} // does nothing. For creating the static objects
	
	/*public static List<Item> allItemsOfType(String pGId, List<Item> pItemList) {
		List<Item> listOfType = new ArrayList<>();
		for (int i = 0; i < pItemList.size(); i++) if (pItemList.get(i).getGId().equalsIgnoreCase(pGId)) listOfType.add(pItemList.get(i));
		return listOfType;
	}*/
}
