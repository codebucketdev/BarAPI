package de.codebucket.barapi.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import de.codebucket.barapi.Util;

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

	public FakeDragon(String name, int EntityID, Location loc)
	{
		this(name, EntityID, (int) Math.floor(loc.getBlockX() * 32.0D), (int) Math.floor(loc.getBlockY() * 32.0D), (int) Math.floor(loc.getBlockZ() * 32.0D));
	}

	public FakeDragon(String name, int EntityID, Location loc, float health, boolean visible) 
	{
		this(name, EntityID, (int) Math.floor(loc.getBlockX() * 32.0D), (int) Math.floor(loc.getBlockY() * 32.0D), (int) Math.floor(loc.getBlockZ() * 32.0D), health, visible);
	}

	public FakeDragon(String name, int EntityID, int x, int y, int z) 
	{
		this(name, EntityID, x, y, z, 200.0F, false);
	}

	public FakeDragon(String name, int EntityID, int x, int y, int z, float health, boolean visible) 
	{
		this.name = name;
		this.EntityID = EntityID;
		this.x = x;
		this.y = y;
		this.z = z;
		this.health = health;
		this.visible = visible;
	}

	@SuppressWarnings("deprecation")
	public Object getSpawnPacket() 
	{
		Class<?> mob_class = Util.getCraftClass("Packet24MobSpawn");
		Object mobPacket = null;
		try 
		{
			mobPacket = mob_class.newInstance();

			Field a = Util.getField(mob_class, "a");
			a.setAccessible(true);
			a.set(mobPacket, Integer.valueOf(this.EntityID));
			Field b = Util.getField(mob_class, "b");
			b.setAccessible(true);
			b.set(mobPacket, Short.valueOf(EntityType.ENDER_DRAGON.getTypeId()));

			Field c = Util.getField(mob_class, "c");
			c.setAccessible(true);
			c.set(mobPacket, Integer.valueOf(this.x));
			Field d = Util.getField(mob_class, "d");
			d.setAccessible(true);
			d.set(mobPacket, Integer.valueOf(this.y));
			Field e = Util.getField(mob_class, "e");
			e.setAccessible(true);
			e.set(mobPacket, Integer.valueOf(this.z));
			Field f = Util.getField(mob_class, "f");
			f.setAccessible(true);
			f.set(mobPacket, Byte.valueOf((byte) (int) (this.pitch * 256.0F / 360.0F)));
			Field g = Util.getField(mob_class, "g");
			g.setAccessible(true);
			g.set(mobPacket, Byte.valueOf((byte) (int) (this.head_pitch * 256.0F / 360.0F)));

			Field h = Util.getField(mob_class, "h");
			h.setAccessible(true);
			h.set(mobPacket,
					Byte.valueOf((byte) (int) (this.yaw * 256.0F / 360.0F)));
			Field i = Util.getField(mob_class, "i");
			i.setAccessible(true);
			i.set(mobPacket, Byte.valueOf(this.xvel));
			Field j = Util.getField(mob_class, "j");
			j.setAccessible(true);
			j.set(mobPacket, Byte.valueOf(this.yvel));
			Field k = Util.getField(mob_class, "k");
			k.setAccessible(true);
			k.set(mobPacket, Byte.valueOf(this.zvel));

			Object watcher = getWatcher();
			Field t = Util.getField(mob_class, "t");
			t.setAccessible(true);
			t.set(mobPacket, watcher);
		}
		catch (InstantiationException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IllegalAccessException e1) 
		{
			e1.printStackTrace();
		}

		return mobPacket;
	}

	public Object getDestroyPacket() 
	{
		Class<?> packet_class = Util.getCraftClass("Packet29DestroyEntity");
		Object packet = null;
		try
		{
			packet = packet_class.newInstance();

			Field a = Util.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, new int[] { this.EntityID });
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return packet;
	}

	public Object getMetaPacket(Object watcher) 
	{
		Class<?> packet_class = Util.getCraftClass("Packet40EntityMetadata");
		Object packet = null;
		try 
		{
			packet = packet_class.newInstance();

			Field a = Util.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, Integer.valueOf(this.EntityID));

			Method watcher_c = Util.getMethod(watcher.getClass(), "c");
			Field b = Util.getField(packet_class, "b");
			b.setAccessible(true);
			b.set(packet, watcher_c.invoke(watcher, new Object[0]));
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		}

		return packet;
	}

	public Object getTeleportPacket(Location loc) 
	{
		Class<?> packet_class = Util.getCraftClass("Packet34EntityTeleport");
		Object packet = null;
		try
		{
			packet = packet_class.newInstance();

			Field a = Util.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, Integer.valueOf(this.EntityID));
			Field b = Util.getField(packet_class, "b");
			b.setAccessible(true);
			b.set(packet, Integer.valueOf((int) Math.floor(loc.getX() * 32.0D)));
			Field c = Util.getField(packet_class, "c");
			c.setAccessible(true);
			c.set(packet, Integer.valueOf((int) Math.floor(loc.getY() * 32.0D)));
			Field d = Util.getField(packet_class, "d");
			d.setAccessible(true);
			d.set(packet, Integer.valueOf((int) Math.floor(loc.getZ() * 32.0D)));
			Field e = Util.getField(packet_class, "e");
			e.setAccessible(true);
			e.set(packet, Byte.valueOf((byte) (int) (loc.getYaw() * 256.0F / 360.0F)));
			Field f = Util.getField(packet_class, "f");
			f.setAccessible(true);
			f.set(packet, Byte.valueOf((byte) (int) (loc.getPitch() * 256.0F / 360.0F)));
		} 
		catch (InstantiationException e)
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
		return packet;
	}

	public Object getRespawnPacket() 
	{
		Class<?> packet_class = Util.getCraftClass("Packet205ClientCommand");
		Object packet = null;
		try 
		{
			packet = packet_class.newInstance();

			Field a = Util.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, Integer.valueOf(1));
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return packet;
	}

	public Object getWatcher() 
	{
		Class<?> watcher_class = Util.getCraftClass("DataWatcher");
		Object watcher = null;
		try 
		{
			watcher = watcher_class.newInstance();

			Method a = Util.getMethod(watcher_class, "a", new Class[] { Integer.TYPE, Object.class });
			a.setAccessible(true);
			a.invoke(watcher, new Object[] { Integer.valueOf(0), Byte.valueOf((byte) (this.visible ? 0 : 32)) });
			a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(this.health) });
			a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
			a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
			a.invoke(watcher, new Object[] { Integer.valueOf(10), this.name });
			a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		}

		return watcher;
	}
}
