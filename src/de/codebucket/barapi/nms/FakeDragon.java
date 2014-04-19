package de.codebucket.barapi.nms;

import org.bukkit.Location;
import org.bukkit.World;

import de.codebucket.barapi.Utils;

public class FakeDragon 
{
	public static final int MAX_HEALTH = 200;
	public boolean visible;
	public int EntityID;
	public int x;
	public int y;
	public int z;
	public int pitch = 0;
	public int head_pitch = 0;
	public int yaw = 0;
	public byte xvel = 0;
	public byte yvel = 0;
	public byte zvel = 0;
	public float health;
	public String name;
	
	//1.7 Stuff
	public Object dragon;
	public Object world;

	public FakeDragon(String name, int EntityID, Location loc)
	{
		this(name, EntityID, (int) Math.floor(loc.getBlockX() * 32.0D), (int) Math.floor(loc.getBlockY() * 32.0D), (int) Math.floor(loc.getBlockZ() * 32.0D), loc.getWorld());
	}

	public FakeDragon(String name, int EntityID, Location loc, float health, boolean visible) 
	{
		this(name, EntityID, (int) Math.floor(loc.getBlockX() * 32.0D), (int) Math.floor(loc.getBlockY() * 32.0D), (int) Math.floor(loc.getBlockZ() * 32.0D), loc.getWorld(), health, visible);
	}

	public FakeDragon(String name, int EntityID, int x, int y, int z, World world) 
	{
		this(name, EntityID, x, y, z, world, 200.0F, false);
	}

	public FakeDragon(String name, int EntityID, int x, int y, int z, World world, float health, boolean visible) 
	{
		this.name = name;
		this.EntityID = EntityID;
		this.x = x;
		this.y = y;
		this.z = z;
		this.health = health;
		this.visible = visible;
		this.world = Utils.getHandle(world);
	}

	public Object getSpawnPacket() 
	{
		if(Utils.newProtocol) return v1_7.getSpawnPacket(this);
		else return v1_6.getSpawnPacket(this);
	}

	public Object getDestroyPacket() 
	{
		if(Utils.newProtocol) return v1_7.getDestroyPacket(this);
		else return v1_6.getDestroyPacket(this);
	}

	public Object getMetaPacket(Object watcher) 
	{
		if(Utils.newProtocol) return v1_7.getMetaPacket(this, watcher);
		else return v1_6.getMetaPacket(this, watcher);
	}

	public Object getTeleportPacket(Location loc) 
	{
		if(Utils.newProtocol) return v1_7.getTeleportPacket(this, loc);
		else return v1_6.getTeleportPacket(this, loc);
	}

	public Object getWatcher() 
	{
		if(Utils.newProtocol) return v1_7.getWatcher(this);
		else return v1_6.getWatcher(this);
	}
}
