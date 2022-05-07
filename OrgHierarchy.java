public class OrgHierarchy extends AvlTree implements OrgHierarchyInterface{
	public boolean isEmpty() {
		if (this.root==null) {
			return true;
		}
		else return false;
	}

	public int size(){
        return size(this.root);
    }

	public int level(int id) throws IllegalIDException {
		BinaryNode requiredEmployee = search(id);
		if (requiredEmployee==null) {
			throw new IllegalIDException("No Employee with given ID : " + id + "!");
		}
		else return requiredEmployee.level;
	}

	public void hireOwner(int id) throws NotEmptyException {
		if (this.root!=null) {
			throw new NotEmptyException("The organisation already has Employees (& hence an owner)!");
		}
		else{
			try {
				insertKey(id);
				// The level of this already set 1 in BinaryTree.java
			} catch (IllegalKeyException e) {
				// NOT Gonna happen but needs to be handled using try catch
				throw new NotEmptyException("The organisation already has an Employee with given ID : " + id + "!");
			}
		}
	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException {
		BinaryNode theBoss = search(bossid);
		if (theBoss==null) {
			throw new IllegalIDException("No Employee with given Boss ID : " + bossid + "!");
		}
		try {
			insertKey(id);
		} catch (IllegalKeyException e) {
			throw new IllegalIDException("The organisation already has an Employee with given ID : " + id + "!");
		}
		BinaryNode theNewEmployee = search(id);
		LinkedList employessUnderBoss = theBoss.children;
		theNewEmployee.level = theBoss.level + 1;
		theNewEmployee.parent = theBoss;
		employessUnderBoss.push(theNewEmployee);
	}

	public void fireEmployee(int id) throws IllegalIDException {
		BinaryNode theEmployee = search(id);
		
		if (theEmployee==null) {
			throw new IllegalIDException("No Employee with given ID : " + id + " to be deleted!");
		}
		if (theEmployee.children.head!=null) {
			System.out.println(theEmployee.children.head.getElement()+"\t"+theEmployee.children.head.getElement().key+"\t"+theEmployee.children.head.getElement().parent);
			theEmployee.children.head.getElement().children.printList();
			throw new IllegalIDException("Could NOT delete the Employee with given ID : " + id + 
			" as it has some Employees! Please provide manageid for new boss of Employees under him");
		}
		BinaryNode hisBoss = theEmployee.parent;
		//System.out.println("The Employee is : " + theEmployee + " and key : " + theEmployee.key);
		//System.out.println("The Boss is : " + hisBoss + " and key : " + hisBoss.key + " and its children are : ");
		//hisBoss.children.printList();
		if (hisBoss!=null) {
			hisBoss.children.remove(theEmployee);
		}
		theEmployee.parent = null;
		deleteKey(id); // No need to handle the case when Employee ID doesn't exist here, as already handled above
	}

	public void fireEmployee(int id, int manageid) throws IllegalIDException {
		BinaryNode theOldManager = search(id);
		if (theOldManager==null) {
			throw new IllegalIDException("No Employee with given ID : " + id + " to be deleted!");
		}
		BinaryNode theNewManager = search(manageid);
		if (theNewManager==null) {
			throw new IllegalIDException("No Employee with given ID : " + manageid + " for new manager!");
		}
		if (theNewManager.level!=theOldManager.level) {
			throw new IllegalIDException("The employees with IDs " + id + " & " + manageid + " are NOT on the same level!");
		}
		// Transferring Employees under id (if any)
		LinkedList employeesUnderOldManager = theOldManager.children;
		LinkedList employessUnderNewManager = theNewManager.children;
		LNode pointer = employeesUnderOldManager.head;
		while (pointer!=null) {
			employessUnderNewManager.push(pointer.getElement());
			pointer.getElement().parent = theNewManager;
			pointer = pointer.getNext();
		}
		theOldManager.children.head = null;

		BinaryNode hisBoss = theOldManager.parent;
		if (hisBoss!=null) {
			hisBoss.children.remove(theOldManager);
		}
		theOldManager.parent = null;

		//theEmployee.parent = null; // Should NOT matter
		deleteKey(id); // No need to handle the case when Employee ID doesn't exist here, as already handled above
		
	}

	public int boss(int id) throws IllegalIDException {
		BinaryNode theEmployee = search(id);
		// System.out.println("\n===="+theEmployee+"====\n");
		if (theEmployee==null) {
			throw new IllegalIDException("No Employee with given ID : " + id + "!");
		}
		// System.out.println("\nGiven ID: " +theEmployee.key + "\tIt's level "  + theEmployee.level  + " and parent " + theEmployee.parent);
		BinaryNode theBoss = theEmployee.parent;
		if (theBoss==null) {
			return -1;
		}
		return theBoss.key;
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException {
		BinaryNode pointer1 = search(id1);
		BinaryNode pointer2 = search(id2);
		if (pointer1==null||pointer2==null) {
			throw new IllegalIDException("Please check of both employee IDs " + id1 + " and " + id2 + " are present in the organisation!");
		}
		//System.out.println("CHECK IDs pointer 1: " + pointer1 + " & key : " + pointer1.key + " and its parent: "+ pointer1.parent + "with key " + pointer1.parent.key);
		//System.out.println("CHECK IDs pointer 2: " + pointer2 + " & key : " + pointer2.key + " and its parent: "+ pointer2.parent + "with key " + pointer2.parent.key);
		BinaryNode commonpointer = null;
		while (pointer1!=null) {
			pointer2 = search(id2);
			while (pointer2!=null) {
				// System.out.println("pointer1 : "+pointer1.key+" pointer2 : "+pointer2.key);
				if (pointer1==pointer2) {
					commonpointer = pointer1;
					break;
				}
				else{
					pointer2 = pointer2.parent;
				}
			}
			if (commonpointer!=null) {
				break;
			}
			else pointer1 = pointer1.parent;
		}
		return commonpointer.key;
	}

	public String toString(int id) throws IllegalIDException {
		BinaryNode rootid = search(id);
		if (rootid==null) {
			throw new IllegalIDException("No Employee with given Boss ID : " + id + "!");
		}
		String res = "";
		Queue queue = new Queue();
		queue.enqueue(rootid);
		int currentLevel = 0;
		LinkedList levelList = new LinkedList();
		while (!queue.isEmpty()) {
			BinaryNode varNode = queue.dequeue();

			if (varNode!=null) {

				// Always keep a track of current level
				if (varNode.level!=currentLevel) { // If next level
					// Print all of the elements at the previous level
					LNode levelPointer = levelList.head;
					while (levelPointer != null){
						res = res + levelPointer.getElement().key+" ";
						levelPointer = levelPointer.getNext();
					}
					// New levelList for new level
					levelList = new LinkedList();
					currentLevel += 1;
					currentLevel = varNode.level;
				}
				// Push currently dequeued Node to the levelList corresponding to that level each time
				levelList.push(varNode);
				LinkedList employessUnderIt = varNode.children;
				if (employessUnderIt.head==null) {
					continue;
				}
				else{
					LNode pointer = employessUnderIt.head;
					queue.enqueue(pointer.getElement());
					while (pointer.getNext()!=null) {
						pointer = pointer.getNext();
						queue.enqueue(pointer.getElement());
					}
				}
			}
		}
		// The last level
		LNode levelPointer = levelList.head;
		while (levelPointer != null){
			res = res + levelPointer.getElement().key+" ";
			levelPointer = levelPointer.getNext();
		}
		return res;
	}

}