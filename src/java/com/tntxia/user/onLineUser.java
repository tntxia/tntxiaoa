package com.tntxia.user;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class onLineUser
  implements HttpSessionBindingListener
{
  private static onLineUser oneInstance = null;
  private Hashtable users;

  public onLineUser()
  {
    System.out.println("new onLineUser");
    oneInstance = this;
    this.users = new Hashtable();
  }

  public boolean deleteUser(String paramString)
  {
    if (existUser(paramString))
    {
      this.users.remove(paramString);
      return true;
    }
    return false;
  }

  public boolean existUser(String paramString)
  {
    return (this.users.get(paramString) != null);
  }

  public int getCount()
  {
    return this.users.size();
  }

  public static onLineUser getInstance()
  {
    return oneInstance;
  }

  public Vector getOnLineUser()
  {
    Vector localVector = new Vector();
    for (Enumeration localEnumeration = this.users.keys(); localEnumeration.hasMoreElements(); )
    {
      localVector.addElement(localEnumeration.nextElement());
    }
    return localVector;
  }

  public void valueBound(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
    if (!(existUser(paramHttpSessionBindingEvent.getName())))
    {
      this.users.put(paramHttpSessionBindingEvent.getName(), "YES");
      System.out.print(paramHttpSessionBindingEvent.getName() + "\t   登入到系统\t" + new Date());
      System.out.println("      在线用户数为：" + getCount());
    }
    else {
      System.out.println(paramHttpSessionBindingEvent.getName() + "已经存在");
    }
  }

  public void valueUnbound(HttpSessionBindingEvent paramHttpSessionBindingEvent)
  {
    String str = paramHttpSessionBindingEvent.getName();
    deleteUser(str);
    System.out.print(str + "\t   退出系统\t" + Calendar.getInstance().getTime());
    System.out.println("      在线用户数为：" + getCount());
  }
}
