package werkbench.reference;

import cpw.mods.fml.common.ModMetadata;
import java.util.Arrays;
import net.minecraft.util.ResourceLocation;

/*
 * A compendium of all constants for the mod, such as the modID and
 * ResourceLocations, as well as more general things like color codes
 */
public class Compendium
{
    public static enum AdjacentBlockType
    {
        CHEST_DOUBLE(new int[]
        {
            0, 0
        }, new int[]
        {
            54, 0
        }, new int[]
        {
            0, 0
        }, new int[]
        {
            0, 0
        }),
        CHEST_SINGLE(new int[]
        {
            54, 0
        }, new int[]
        {
            0, 0
        }, new int[]
        {
            54, 0
        }, new int[]
        {
            0, 0
        }),
        EMPTY(new int[]
        {
            0, 0
        }, new int[]
        {
            0, 0
        }, new int[]
        {
            0, 0
        }, new int[]
        {
            0, 0
        }),
        FURNACE_ACTIVE(new int[]
        {
            47, 13
        }, new int[]
        {
            1, 13
        }, new int[]
        {
            46, 10
        }, new int[]
        {
            0, 10
        }),
        FURNACE_INACTIVE(new int[]
        {
            47, 13
        }, new int[]
        {
            1, 13
        }, new int[]
        {
            46, 10
        }, new int[]
        {
            0, 10
        }),
        OFFSET(new int[]
        {
            8, 38
        }, new int[]
        {
            306, 38
        }, new int[]
        {
            0, 30
        }, new int[]
        {
            298, 30
        });

        public static int[] getGUIBackgroundCoordinates(RelativeBenchSide side, AdjacentBlockType type)
        {
            if (side == RelativeBenchSide.LEFT)
            {
                return new int[]
                {
                    type.guiLeft[0] + AdjacentBlockType.OFFSET.guiLeft[0],
                    type.guiLeft[1] + AdjacentBlockType.OFFSET.guiLeft[1]
                };
            } else
            {
                return new int[]
                {
                    type.guiRight[0] + AdjacentBlockType.OFFSET.guiRight[0],
                    type.guiRight[1] + AdjacentBlockType.OFFSET.guiRight[1]
                };
            }
        }

        public static int[] getGUISlotCoordinates(RelativeBenchSide side, AdjacentBlockType type)
        {
            if (side == RelativeBenchSide.LEFT)
            {
                return new int[]
                {
                    type.slotLeft[0] + AdjacentBlockType.OFFSET.slotLeft[0],
                    type.slotLeft[1] + AdjacentBlockType.OFFSET.slotLeft[1]
                };
            } else
            {
                return new int[]
                {
                    type.slotRight[0] + AdjacentBlockType.OFFSET.slotRight[0],
                    type.slotRight[1] + AdjacentBlockType.OFFSET.slotRight[1]
                };
            }
        }
        private final int[] guiLeft, guiRight;
        private final int[] slotLeft;
        private final int[] slotRight;

        AdjacentBlockType(int[] slotLeft, int[] slotRight, int[] guiLeft, int[] guiRight)
        {
            this.slotLeft = slotLeft;
            this.slotRight = slotRight;
            this.guiLeft = guiLeft;
            this.guiRight = guiRight;
        }

    }

    public static enum TabState
    {
        CLOSED, CLOSING,
        OPEN, OPENING
    }

    public static enum RelativeBenchSide
    {
        LEFT, RIGHT, BACK, FRONT, TOP, BOTTOM
    }

    public static final class Naming
    {
        public static final String id = "werkbench";
        public static final String name = "Werkbench";
        public static final String block = "Werkbench";
        public static final String tileEntity = "werkbenchTileEntity";
        public static final String inventoryName = "container.bench.name";
    }

    public static final class Version
    {
        public static final String major = "@MAJOR@";
        public static final String minor = "@MINOR@";
        public static final String build = "@BUILD@";
        public static final String full = major + "." + minor + "." + build;
    }

    public static final class MetaData
    {
        /**
         * Setup mod metadata
         *
         * @param metadata
         * @return edited metadata object
         */
        public static ModMetadata init(ModMetadata metadata)
        {
            metadata.modId = Compendium.Naming.id;
            metadata.name = Compendium.Naming.name;
            metadata.description = Compendium.Naming.name + " adds a useful extended workbench";
            metadata.url = "http://patreon.com/jakimfett";
            metadata.logoFile = "assets/" + Compendium.Naming.id + "/logo.png";
            metadata.version = Compendium.Version.major + "." + Compendium.Version.minor + "." + Compendium.Version.build;
            metadata.authorList = Arrays.asList("jakimfett");
            metadata.autogenerated = false;
            return metadata;
        }
    }

    public static final class Texture
    {

        public static final class IIcon
        {

        }

        public static final class Icon
        {

        }

        public static final class GUI
        {
            public static final String background = "textures/gui/werkBenchGUIBackground.png";
            public static final String chestTabBackground = "textures/gui/chestTabBackground.png";
            public static final String doubleChest = "textures/gui/werkBenchGUIBackgroundChestDouble.png";
            public static final String furnace = "textures/gui/werkBenchGUIBackgroundFurnace.png";
            public static final String singleChest = "textures/gui/werkBenchGUIBackgroundChestSingle.png";
        }

        public static final class Model
        {

            public static final String bench = "textures/blocks/werkBench.png";
        }
    }

    public static final class Resource
    {
        public static final class Icon
        {

        }

        public static final class GUI
        {

            public static final ResourceLocation background = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.background);
            public static final ResourceLocation singleChest = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.singleChest);
            public static final ResourceLocation doubleChest = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.doubleChest);
            public static final ResourceLocation furnace = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.GUI.furnace);
        }

        public static final class Model
        {
            public static final ResourceLocation bench = new ResourceLocation(Compendium.Naming.id, Compendium.Texture.Model.bench);
        }
    }

    public static final class Config
    {
        public static final String configPrefix = "config/werkbench/";
        public static final String assetPrefix = "/assets/werkbench/";
        public static final String categoryPerformance = "performance";
    }

    public static final class Color
    {

        public static final String black = "\u00A70";
        public static final String darkBlue = "\u00A71";
        public static final String darkCyan = "\u00A73";
        public static final String darkGreen = "\u00A72";
        public static final String darkGrey = "\u00A78";
        public static final String darkRed = "\u00A74";
        public static final String lightBlue = "\u00A79";
        public static final String lightCyan = "\u00A7b";
        public static final String lightGreen = "\u00A7a";
        public static final String lightGrey = "\u00A77";
        public static final String lightRed = "\u00A7c";
        public static final String orange = "\u00A76";
        public static final String pink = "\u00A7d";
        public static final String purple = "\u00A75";
        public static final String white = "\u00A7f";
        public static final String yellow = "\u00A7e";

    }
}
