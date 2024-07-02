package Teacher;

import java.util.ArrayList;

public class Teacher {
    /** 教师号 */
    private String id;
    /** 姓名 */
    private String name;
    /** 性别 */
    private String sex;
    /** 职称 */
    private String rank;
    /** 任教课程 */
    private ArrayList<String> lesson;
    /** 班级 */
    private ArrayList<String> classes;
    /** 班级数目 */
    private int numOfClasses;
    /** 理论课时 */
    private int theoryClassLength;
    /** 实验课时 */
    private int labClassLength;

    public Teacher(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
