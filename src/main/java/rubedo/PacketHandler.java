package rubedo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler 
{
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		
		// Packet Handler for Blood Magic?
		/*if (packet.channel.equals("SetLifeEssence")) //Sets the data for the character
        {
            ByteArrayInputStream bin = new ByteArrayInputStream(packet.data);
            DataInputStream din = new DataInputStream(bin);

            try
            {
                EntityPlayer user = (EntityPlayer) player;
                int length = din.readInt();
                String ownerName = "";

                for (int i = 0; i < length; i++)
                {
                    ownerName = ownerName + din.readChar();
                }

                int addedEssence = din.readInt();
                int maxEssence = din.readInt();
                World world = MinecraftServer.getServer().worldServers[0];
                LifeEssenceNetwork data = (LifeEssenceNetwork) world.loadItemData(LifeEssenceNetwork.class, ownerName);

                if (data == null)
                {
                    data = new LifeEssenceNetwork(ownerName);
                    world.setItemData(ownerName, data);
                }

                if (addedEssence > 0)
                {
                    if (data.currentEssence < maxEssence)
                    {
                        data.currentEssence = Math.min(maxEssence, data.currentEssence + addedEssence);
                        data.markDirty();
                    }

                    if (!user.capabilities.isCreativeMode)
                    {
                        for (int i = 0; i < ((addedEssence + 99) / 100); i++)
                        {
                            //player.setEntityHealth((player.getHealth()-1));
                            user.setHealth((user.getHealth() - 1));

                            if (user.getHealth() <= 0.5f)
                            {
                                //user.inventory.dropAllItems();
                                user.onDeath(DamageSource.generic);
                                return;
                            }
                        }
                    }
                } else
                {
                    int removedEssence = -addedEssence;

                    if ((data.currentEssence - removedEssence) >= 0)
                    {
                        data.currentEssence -= removedEssence;
                        data.markDirty();
                    } else
                    {
                        if (removedEssence >= 100)
                        {
                            for (int i = 0; i < ((removedEssence + 99) / 100); i++)
                            {
                                //player.setEntityHealth((player.getHealth()-1));
                                user.setHealth((user.getHealth() - 1));

                                if (user.getHealth() <= 0.5f)
                                {
                                    //user.inventory.dropAllItems();
                                    user.onDeath(DamageSource.generic);
                                    return;
                                }
                            }
                        } else
                        {
                            if (user.worldObj.rand.nextInt(100) <= removedEssence)
                            {
                                user.setHealth((user.getHealth() - 1));

                                if (user.getHealth() <= 0.5f)
                                {
                                    //user.inventory.dropAllItems();
                                    user.onDeath(DamageSource.generic);
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }*/
	}
}
