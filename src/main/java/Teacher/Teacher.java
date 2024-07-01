package Teacher;

import java.util.ArrayList;

public class Teacher {
    /** 教师号 */
    String id;
    /** 姓名 */
    String name;
    /** 性别 */
    String sex;
    /** 职称 */
    String rank;
    /** 任教课程 */
    ArrayList<String> lesson;
    /** 班级 */
    ArrayList<String> classes;
    /** 班级数目 */
    int numOfClasses;
    /** 理论课时 */
    int theoryClassLength;
    /** 实验课时 */
    int labClassLength;

    Teacher(){

    }
}
