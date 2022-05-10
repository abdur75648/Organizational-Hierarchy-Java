# Organizational Database Management System For Employees
A program to store employee data and maintains the information about organization hierarchy using a tree-based data structure

## Goal:
We want to maintain a database of employees in a company. We will be concerned with 2 quantities associated with each employee in the company:
 - ID of the employee (you can assume no two employees in the company have the same ID)
 - Level of the employee (The level denotes where the person stands in the hierarchy. Level 1 denotes the highest post in the company (say the owner), level 2 comes below level 1 and so on.
 - There is only 1 person at level 1, but there can be several employees at level i > 1. Each level i employee works under a level i-1 employee, which is his/her immediate boss.


## Implementatiom
A hybrid tree data structure which uses AVL tree & generic tree sharing common node, has been implemented to perform following operations in a time and space efficient manner:
 - **public OrgHierarchy()**; *Initializes an empty organization*
 - **public boolean isEmpty()**; *Returns true if the organization is empty*
 - **public int size()**; *Returns the number of employees in the organization*
 - **public int level(int id)**; *Returns the level of the employee with ID=id*
 - **public void hireOwner(int id)**; *Adds the first employee of the organization, which we call the owner. There is only one owner in an org who cannot be deleted*
 - **public void hireEmployee(int id, int bossid)**; *Adds a new employee whose ID is id. This employee will work under an existing employee whose ID is bossid (note that this automatically decides the level of id, it is one more than that of bossid). Your code should throw an exception if the id already exists in the OrgHierarcy*
 - **public void fireEmployee(int id)**; *Deletes an employee who does not manage any other employees*
 - **public void fireEmployee(int id, int manageid)**; *Deletes an employee (id) who might manage other employees. Manageid is another employee who works at the same level as id. All employees working under id will now work under manageid*
 - **public int boss(int id)**; *Returns the immediate boss, the employee. Returns -1 if id is the owner’s ID*
 - **public int lowestCommonBoss(int id1, int id2)**; *Outputs the ID of the employee A who is a boss of both id1 and id2, and among all such persons has the largest level. In other words, we want to find the common boss who is lowest in the hierarchy in the company. If one of the input ids is the owner, output -1*
 - **public String toString(int id)**; *Returns the whole organization rooted at id in a String format. The return string should contain the employees (ids) level-wise. The return string should contain employee ids in a (single) space separated fashion. So the first input id (root) will appear, then all employees directly under that id, and then all employees directly under these employee ids and so on. Among employees at the same level, the output should be sorted in increasing order of the ids*
 
 
 ## Note
 - Anything has not been imported from *java.utils*. Do NOT copy any part of the code at all anywhere
 - The code has been written completely by me and I have its copyright ownership
 - This code is a solution of assignment given in a IITD Course, COL106 ([Course Webpage](https://www.cse.iitd.ac.in/~parags/teaching/col106)). I've uploaded it here, only for being used as a help by those who're not registered in the course, if they get stuck somewhere.
