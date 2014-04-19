package de.codebucket.barapi.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import de.codebucket.barapi.Utils;

public class v1_6 
{
	@SuppressWarnings("deprecation")
	public static Object getSpawnPacket(FakeDragon fd) 
	{
		Class<?> mob_class = Utils.getCraftClass("Packet24MobSpawn");
		Object mobPacket = null;
		try 
		{
			mobPacket = mob_class.newInstance();

			Field a = Utils.getField(mob_class, "a");
			a.setAccessible(true);
			a.set(mobPacket, Integer.valueOf(fd.EntityID));
			Field b = Utils.getField(mob_class, "b");
			b.setAccessible(true);
			b.set(mobPacket, Short.valueOf(EntityType.ENDER_DRAGON.getTypeId()));

			Field c = Utils.getField(mob_class, "c");
			c.setAccessible(true);
			c.set(mobPacket, Integer.valueOf(fd.x));
			Field d = Utils.getField(mob_class, "d");
			d.setAccessible(true);
			d.set(mobPacket, Integer.valueOf(fd.y));
			Field e = Utils.getField(mob_class, "e");
			e.setAccessible(true);
			e.set(mobPacket, Integer.valueOf(fd.z));
			Field f = Utils.getField(mob_class, "f");
			f.setAccessible(true);
			f.set(mobPacket, Byte.valueOf((byte) (int) (fd.pitch * 256.0F / 360.0F)));
			Field g = Utils.getField(mob_class, "g");
			g.setAccessible(true);
			g.set(mobPacket, Byte.valueOf((byte) (int) (fd.head_pitch * 256.0F / 360.0F)));

			Field h = Utils.getField(mob_class, "h");
			h.setAccessible(true);
			h.set(mobPacket,
					Byte.valueOf((byte) (int) (fd.yaw * 256.0F / 360.0F)));
			Field i = Utils.getField(mob_class, "i");
			i.setAccessible(true);
			i.set(mobPacket, Byte.valueOf(fd.xvel));
			Field j = Utils.getField(mob_class, "j");
			j.setAccessible(true);
			j.set(mobPacket, Byte.valueOf(fd.yvel));
			Field k = Utils.getField(mob_class, "k");
			k.setAccessible(true);
			k.set(mobPacket, Byte.valueOf(fd.zvel));

			Object watcher = getWatcher(fd);
			Field t = Utils.getField(mob_class, "t");
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

	public static Object getDestroyPacket(FakeDragon fd) 
	{
		Class<?> packet_class = Utils.getCraftClass("Packet29DestroyEntity");
		Object packet = null;
		try
		{
			packet = packet_class.newInstance();

			Field a = Utils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, new int[] { fd.EntityID });
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

	public static Object getMetaPacket(FakeDragon fd, Object watcher) 
	{
		Class<?> packet_class = Utils.getCraftClass("Packet40EntityMetadata");
		Object packet = null;
		try 
		{
			packet = packet_class.newInstance();

			Field a = Utils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, Integer.valueOf(fd.EntityID));

			Method watcher_c = Utils.getMethod(watcher.getClass(), "c");
			Field b = Utils.getField(packet_class, "b");
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

	public static Object getTeleportPacket(FakeDragon fd, Location loc) 
	{
		Class<?> packet_class = Utils.getCraftClass("Packet34EntityTeleport");
		Object packet = null;
		try
		{
			packet = packet_class.newInstance();

			Field a = Utils.getField(packet_class, "a");
			a.setAccessible(true);
			a.set(packet, Integer.valueOf(fd.EntityID));
			Field b = Utils.getField(packet_class, "b");
			b.setAccessible(true);
			b.set(packet, Integer.valueOf((int) Math.floor(loc.getX() * 32.0D)));
			Field c = Utils.getField(packet_class, "c");
			c.setAccessible(true);
			c.set(packet, Integer.valueOf((int) Math.floor(loc.getY() * 32.0D)));
			Field d = Utils.getField(packet_class, "d");
			d.setAccessible(true);
			d.set(packet, Integer.valueOf((int) Math.floor(loc.getZ() * 32.0D)));
			Field e = Utils.getField(packet_class, "e");
			e.setAccessible(true);
			e.set(packet, Byte.valueOf((byte) (int) (loc.getYaw() * 256.0F / 360.0F)));
			Field f = Utils.getField(packet_class, "f");
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

	public static Object getRespawnPacket() 
	{
		Class<?> packet_class = Utils.getCraftClass("Packet205ClientCommand");
		Object packet = null;
		try 
		{
			packet = packet_class.newInstance();

			Field a = Utils.getField(packet_class, "a");
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

	public static Object getWatcher(FakeDragon fd) 
	{
		Class<?> watcher_class = Utils.getCraftClass("DataWatcher");
		Object watcher = null;
		try 
		{
			watcher = watcher_class.newInstance();

			Method a = Utils.getMethod(watcher_class, "a", new Class[] { Integer.TYPE, Object.class });
			a.setAccessible(true);
			a.invoke(watcher, new Object[] { Integer.valueOf(0), Byte.valueOf((byte) (fd.visible ? 0 : 32)) });
			a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(fd.health) });
			a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
			a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
			a.invoke(watcher, new Object[] { Integer.valueOf(10), fd.name });
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
