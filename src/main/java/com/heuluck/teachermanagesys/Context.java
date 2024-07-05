package com.heuluck.teachermanagesys;

import Teacher.Teacher;

import java.util.ArrayList;

/* 用来储存共用的变量 */
public class Context {
    public static Teacher currentTeacher;
    public static ArrayList<Teacher> allTeachers = new ArrayList<Teacher>();

    public static boolean isSQLConnect = false;

    public static ArrayList<Teacher> SQLTeachers = new ArrayList<Teacher>();//暂存数据库的老师
}
